package workarounds;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;

import hudson.Extension;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.model.Environment;
import hudson.model.EnvironmentList;
import hudson.model.TaskListener;
import hudson.model.Run.RunnerAbortedException;
import hudson.model.listeners.RunListener;
import jenkins.util.BuildListenerAdapter;

/**
 * Ugly workaround for orphan <code>ssh-agent</code> processes, and maybe other similar bugs.<br>
 * The issue is the following:
 * <ul>
 * <li><code>Environment</code>s may be instanciated very early in the build process, for instance from:
 * <ul>
 * <li><code>RunListener.setUpEnvironment</code></li>
 * <li><code>BuildWrapper.preCheckout</code> (what the <code>ssh-agent</code> plugin does)</li>
 * </ul>
 * </li>
 * <li><code>Environment.tearDown</code> methods are called only from the <code>finally</code> block of
 * <code>BuildExecution.doRun</code>. The assumption there is probably that all <code>Environment</code>s come from
 * <code>setUp</code> methods of <code>BuildWrapper</code>s, which are executed in the corresponding <code>try</code>
 * block.</li>
 * <li>when an execution fails very early in <code>AbstractBuildExecution.run</code>, before even reaching
 * <code>BuildExecution.doRun</code> (for instance because the SCM checkout failed), it may have some
 * <code>Environment</code>s already, which will never be teared down. The symptom with the <code>ssh-agent</code>
 * plugin is an orphan <code>ssh-agent</code> process.</li>
 * </ul>
 * This <code>RunListener</code> does the following, to workaround the issue:
 * <ul>
 * <li>setup a dummy <code>Environment</code> whose only purpose is to record whether <code>Environment</code>s have
 * been teared down already</li>
 * <li>when the build completes, check this flag, and if it shows that <code>Environment</code>s have not been teared
 * down yet, go through all <code>Environment</code>s with a non-default <code>tearDown</code> implementation and
 * execute it</li>
 * </ul>
 * This might have some unexpected consequences, but so far so good, it seems to solve the issue with
 * <code>ssh-agent</code>...
 */
@Extension
public class ForgottenEnvironmentsTearDownRunListener extends RunListener<AbstractBuild<?, ?>> {

	@Override
	public Environment setUpEnvironment(AbstractBuild build, Launcher launcher, BuildListener listener)
			throws IOException, InterruptedException, RunnerAbortedException {
		return new TearDownCheckEnvironment();
	}

	@Override
	public void onCompleted(AbstractBuild r, TaskListener listener) {

		if (Boolean.getBoolean(ForgottenEnvironmentsTearDownRunListener.class.getName() + ".disable")) {
			return;
		}

		final EnvironmentList environments = r.getEnvironments();

		// Are environments already teared down?
		boolean alreadyTearedDown = true;
		for (Environment env : environments) {
			if (env instanceof TearDownCheckEnvironment) {
				alreadyTearedDown = ((TearDownCheckEnvironment) env).tearedDown;
				break;
			}
		}
		if (alreadyTearedDown) {
			// ok, Environments have been teared down already (or we've not found our own TearDownCheckEnvironment)
			return;
		}

		// Is there actually something that deserves invoking tearDown()?
		// (filtering avoids many useless lines of logs below)
		final ArrayList<Environment> environmentsToTearDown = new ArrayList<>();
		for (Environment env : environments) {
			if (env instanceof TearDownCheckEnvironment) {
				continue;
			} else if (hasOverridenTearDownMethod(env.getClass())) {
				environmentsToTearDown.add(env);
			}
		}
		if (environmentsToTearDown.isEmpty()) {
			// ok, there is no Environment with an actual tearDown() implementation
			return;
		}

		// Let's tear down these forgotten Environments! (in reverse order)
		Collections.reverse(environmentsToTearDown);
		final BuildListener buildListener = BuildListenerAdapter.wrap(listener);
		for (Environment env : environmentsToTearDown) {
			try {
				listener.getLogger().println("Tearing down forgotten Environment: " + env.getClass().getName());
				env.tearDown(r, buildListener);
			} catch (IOException | InterruptedException | RuntimeException e) {
				e.printStackTrace(listener.error("Failed to tear down Environment: " + env.getClass().getName()));
			}
		}
	}

	private boolean hasOverridenTearDownMethod(Class clazz) {
		try {
			Method m = clazz.getMethod("tearDown", AbstractBuild.class, BuildListener.class);
			return (m.getDeclaringClass() != Environment.class);
		} catch (NoSuchMethodException | SecurityException e) {
			// this should not happen
			return false;
		}
	}

	private class TearDownCheckEnvironment extends Environment {
		private boolean tearedDown = false;

		@Override
		public boolean tearDown(AbstractBuild build, BuildListener listener) throws IOException, InterruptedException {
			this.tearedDown = true;
			return true;
		}
	}

}
