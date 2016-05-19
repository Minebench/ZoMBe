package de.minebench.zombe.api.messaging;

/**
 * @author dags_ <dags@dags.me>
 */

public interface PluginMessageDispatcher
{
    public void dispatchMessage(byte[] data);
}
