package hudson.model.listeners;

import hudson.Extension;
import hudson.XmlFile;
import hudson.model.Item;
import hudson.model.Saveable;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Find out who is calling Item.save so often when <i>Job</i>&gt;Configure&gt;Save is called.
 */
public class WhoListensToTheListeners {

    private static final Logger LOG = Logger.getLogger("event-logger");

    private static void logStack(Object listener, Object subject) {
        LOG.log(Level.WARNING, String.format("%s heard %s", listener.getClass().getSimpleName(), subject), new Exception());
    }

    @Extension
    public static class SaveableListenerLogger extends SaveableListener {
        @Override
        public void onChange(Saveable o, XmlFile file) {
            logStack(this, o);
        }
    }

    @Extension
    public static class ItemListenerLogger extends ItemListener {
        @Override
        public void onUpdated(Item item) {
            logStack(this, item);
        }
    }
}
