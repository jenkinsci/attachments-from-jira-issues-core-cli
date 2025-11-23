package com.ullink.jenkins.plugins.buildqueueprioritizer;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import hudson.model.Queue;

public class CustomLogger {
    static final Logger LOGGER = CustomLogger.createLogger();

    static Logger createLogger() {
        Logger logger = Logger.getLogger(CustomSorter.class.getName());
        FileHandler handler = null;
        try {
            handler = new FileHandler("/var/jenkins/jobs/logs.log", 1024 * 1024 * 1024, 10, true);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        assert handler != null;
        handler.setFormatter(new SimpleFormatter());
        logger.addHandler(handler);
        return logger;
    }

    static void logBuildQueue(List<Queue.BuildableItem> items, String stage) {
        String msg = "[" + stage + " SORTING] Sorting the items: ";
        for (Queue.BuildableItem item : items) {
            msg = msg.concat(item.task.getName() + ", ");
        }
        msg = msg.substring(0, msg.length() - 2);
        LOGGER.log(Level.INFO, msg);
    }

    static void dumpThreadDump() {
        LOGGER.log(Level.WARNING, "**********GENERATING THREAD DUMP**********");
        ThreadMXBean threadMxBean = ManagementFactory.getThreadMXBean();
        for (ThreadInfo ti : threadMxBean.dumpAllThreads(true, true)) {
            LOGGER.log(Level.INFO, ti.toString());
        }
    }
}
