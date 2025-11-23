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

import static com.ullink.jenkins.plugins.failureCauseCorrectiveAction.CorrectiveActions.publishOnSlack;

import hudson.Extension;
import hudson.model.Queue;

@Extension
public class QueueListener extends hudson.model.queue.QueueListener {

    @Override
    public void onEnterBuildable(Queue.BuildableItem buildableItem) {
        if (CustomSorter.shouldBePrioritized(buildableItem)) {
            publishOnSlack("jenkins-monitoring", "Job " + buildableItem.task.getName() + " is getting prioritized.");
        }
    }
}
