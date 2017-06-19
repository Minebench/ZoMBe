package de.minebench.zombe.core.messaging;

import de.minebench.zombe.api.messaging.ZData;
import de.minebench.zombe.api.messaging.PluginMessageDispatcher;
import de.minebench.zombe.api.messaging.PluginMessageHandler;

/**
 * @author dags_ <dags@dags.me>
 */

public class ChannelMessaging
{
    private PluginMessageHandler handler;
    private PluginMessageDispatcher dispatcher;

    public ChannelMessaging(PluginMessageHandler handler, PluginMessageDispatcher dispatcher)
    {
        this.handler = handler;
        this.dispatcher = dispatcher;
    }

    public void onPacketReceived(String channel, byte[] data)
    {
        if ((channel.equals("DaFlight") || channel.equals("ZoMBe")) && data.length == 2)
        {
            final byte type = data[0];
            final byte value = data[1];
            switch (type)
            {
                case ZData.NOCLIP:
                    handler.noClip(ZData.getBoolFor(value));
                    break;
                case ZData.FULL_BRIGHT:
                    handler.fullBright(ZData.getBoolFor(value));
                    break;
                case ZData.ORE_HIGHLIGHTER:
                    handler.oreHighlighter(ZData.getBoolFor(value));
                    break;
                case ZData.MOB_HIGHLIGHTER:
                    handler.mobHighlighter(ZData.getBoolFor(value));
                    break;
                case ZData.SEE_THROUGH:
                    handler.seeThrough(ZData.getBoolFor(value));
                    break;
                case ZData.FLY_MOD:
                    handler.flyMod(ZData.getBoolFor(value));
                    break;
                case ZData.NO_FALL_DAMAGE:
                    handler.softFall(ZData.getBoolFor(value));
                    break;
                case ZData.REFRESH:
                    handler.refresh(value);
                    break;
                case ZData.SPEED:
                    handler.setSpeed(value);
                    break;
            }
        }
    }

    public void dispatchMessage(byte[] data)
    {
        dispatcher.dispatchMessage(data);
    }

    public PluginMessageHandler getPluginMessageHandler() {
        return handler;
    }
}
