/*************************************************************************
 * ULLINK CONFIDENTIAL INFORMATION
 * _______________________________
 *
 * All Rights Reserved.
 *
 * NOTICE: This file and its content are the property of Ullink. The
 * information included has been classified as Confidential and may
 * not be copied, modified, distributed, or otherwise disseminated, in
 * whole or part, without the express written permission of Ullink.
 ************************************************************************/
package com.ullink.jenkins.plugins.buildqueueprioritizer;

import static com.ullink.jenkins.plugins.buildqueueprioritizer.CustomLogger.LOGGER;
import static com.ullink.jenkins.plugins.failureCauseCorrectiveAction.CorrectiveActions.publishOnSlack;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import hudson.Extension;
import hudson.model.Cause;
import hudson.model.CauseAction;
import hudson.model.ParameterValue;
import hudson.model.ParametersAction;
import hudson.model.Queue;
import hudson.model.queue.QueueSorter;
import jenkins.model.Jenkins;

@Extension
public class CustomSorter extends QueueSorter {

    public static void setDefaultSorter() {
        LOGGER.log(Level.WARNING, "Setting the default sorter...");
        Jenkins.getInstance().getQueue().setSorter(null);
    }

    static boolean shouldBePrioritized(Queue.BuildableItem item) {
        return isReleaseJob(item) || isTriggeredByReleaseCandidate(item);
    }

    private static boolean isReleaseJob(Queue.BuildableItem buildableItem) {
        if (buildableItem.getParams().contains("RELEASE=true")) {
            LOGGER.log(Level.INFO, "[RELEASE CANDIDATE] Job " + buildableItem.task.getName() + " is a release candidate");
            return true;
        }
        return false;
    }

    private static boolean isTriggeredByReleaseCandidate(Queue.BuildableItem buildableItem) {
        CauseAction action = buildableItem.getAction(CauseAction.class);
        Optional<Cause> triggerCause = action.getCauses().stream().filter(cause -> cause instanceof Cause.UpstreamCause).findAny();
        if (triggerCause.isPresent()) {
            List<ParameterValue> params = ((Cause.UpstreamCause) triggerCause.get()).getUpstreamRun().getAction(ParametersAction.class).getParameters();
            for (ParameterValue param : params) {
                if (param.getName().equals("RELEASE") && param.getValue().equals(true))
                    LOGGER.log(Level.INFO, "[TRIGGERED BY RELEASE CANDIDATE] Job " + buildableItem.task.getName() + " was triggered by a release candidate "
                        + ((Cause.UpstreamCause) triggerCause.get()).getUpstreamRun().getFullDisplayName());
                return true;
            }
        }
        return false;
    }

    private static int compare(Queue.BuildableItem lhs, Queue.BuildableItem rhs) {
        if (shouldBePrioritized(lhs) && !shouldBePrioritized(rhs)) {
            LOGGER.log(Level.INFO, "[COMPARATION] Comparing " + lhs.task.getName() + " and " + rhs.task.getName() + ". Result: " + lhs.task.getName());
            return -1;
        }
        if (shouldBePrioritized(rhs) && !shouldBePrioritized((lhs))) {
            LOGGER.log(Level.INFO, "[COMPARATION] Comparing " + lhs.task.getName() + " and " + rhs.task.getName() + ". Result: " + rhs.task.getName());
            return 1;
        }

        String result = "";
        switch (compareTimestamp(lhs, rhs)) {
            case -1:
                result = lhs.task.getName();
                break;
            case 1:
                result = rhs.task.getName();
                break;
            case 0:
                result = "Equal timestamps";
                break;
        }
        LOGGER.log(Level.INFO, "[COMPARATION] Comparing " + lhs.task.getName() + "and " + rhs.task.getName() + ". Result: Equal in terms of priority, result after comparing"
            + " timestamps is: " + result);

        return compareTimestamp(lhs, rhs);
    }

    private static int compareTimestamp(Queue.BuildableItem lhs, Queue.BuildableItem rhs) {
        return Long.compare(lhs.buildableStartMilliseconds, rhs.buildableStartMilliseconds);
    }

    @Override
    public void sortBuildableItems(List<Queue.BuildableItem> items) {
        return super.sortBuildableItems(items);
        CustomLogger.logBuildQueue(items, "BEFORE");
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future future = executorService.submit(new Runnable() {
            @Override public void run() {
                items.sort(CustomSorter::compare);
            }
        });
        try {
            future.get(2, TimeUnit.SECONDS);
        }
        catch (InterruptedException | ExecutionException | TimeoutException e) {
            LOGGER.log(Level.SEVERE, "An exception occurred: " + e.getClass().getCanonicalName(), e);
            publishOnSlack("jenkins-monitoring", "An exception occurred, check the logs at /var/jenkins/jobs/logs.log");
            CustomLogger.dumpThreadDump();
            setDefaultSorter();
        }
        CustomLogger.logBuildQueue(items, "AFTER");
    }
}