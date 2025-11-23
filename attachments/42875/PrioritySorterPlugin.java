package com.ullink.jenkins.plugins.buildqueueprioritizer;

import static com.ullink.jenkins.plugins.buildqueueprioritizer.CustomLogger.LOGGER;
import static com.ullink.jenkins.plugins.failureCauseCorrectiveAction.CorrectiveActions.publishOnSlack;

import java.util.logging.Level;
import hudson.ExtensionList;
import hudson.Plugin;
import hudson.init.InitMilestone;
import hudson.init.Initializer;
import hudson.model.queue.QueueSorter;
import jenkins.model.Jenkins;

public class PrioritySorterPlugin extends Plugin {

    private static final QueueSorter originalQueueSorter = Jenkins.getInstance().getQueue().getSorter();

    public static QueueSorter getOriginalQueueSorter() {
        return originalQueueSorter;
    }

    public void setOriginalQueueSorter(QueueSorter sorter) {
        Jenkins.getInstance().getQueue().setSorter(sorter);
    }

    @Initializer(after = InitMilestone.PLUGINS_PREPARED, before = InitMilestone.PLUGINS_STARTED, fatal = false)
    public void setCustomSorter() {
        try {
            LOGGER.log(Level.INFO, "Setting the custom sorter...");
            ExtensionList<QueueSorter> sorters = ExtensionList.lookup(QueueSorter.class);
            sorters.stream().filter(sorter -> sorter instanceof CustomSorter).forEach(sorter -> Jenkins.getInstance().getQueue().setSorter(sorter));
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Cannot set the custom sorter!");
            publishOnSlack("jenkins-monitoring", "Cannot set custom sorter, exception is: " + e.getMessage());
        }
    }
}
