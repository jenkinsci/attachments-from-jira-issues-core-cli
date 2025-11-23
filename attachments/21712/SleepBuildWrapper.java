package com.cordys.jenkinsci;

import hudson.Extension;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.BuildListener;
import hudson.tasks.BuildWrapper;
import hudson.tasks.BuildWrapperDescriptor;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.StaplerRequest;

import java.io.IOException;

public class SleepBuildWrapper extends BuildWrapper {

    @Override
    public void preCheckout(AbstractBuild build, Launcher launcher, BuildListener listener) throws IOException, InterruptedException {
        Thread.sleep(20 * 1000);
        build.
        super.preCheckout(build, launcher, listener);
    }

    @Override
    public Environment setUp(AbstractBuild build, Launcher launcher, BuildListener listener) throws IOException, InterruptedException {
        return new Environment() {
        };
    }

    @Override
    public SleepBuildWrapperDescriptor getDescriptor() {
        return (SleepBuildWrapperDescriptor) super.getDescriptor();
    }

    @Extension
    public static class SleepBuildWrapperDescriptor extends BuildWrapperDescriptor {


        @Override
        public boolean isApplicable(AbstractProject<?, ?> abstractProject) {
            return true;
        }

        @Override
        public BuildWrapper newInstance(StaplerRequest req, JSONObject formData) throws FormException {
            return new SleepBuildWrapper();
        }

        @Override
        public String getDisplayName() {
            return "Sleep 20 seconds";
        }

        @Override
        public boolean configure(StaplerRequest req, JSONObject json) throws FormException {
            save();
            return super.configure(req, json);
        }
    }

}
