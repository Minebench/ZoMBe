package de.minebench.zombe.liteloader.messaging;

import com.mumfrey.liteloader.core.ClientPluginChannels;
import com.mumfrey.liteloader.core.PluginChannels;
import de.minebench.zombe.api.messaging.PluginMessageDispatcher;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketBuffer;

/**
 * @author dags_ <dags@dags.me>
 */

public class MessageDispatcher implements PluginMessageDispatcher
{
    @Override
    public void dispatchMessage(byte[] data)
    {
        PacketBuffer outPacket = new PacketBuffer(Unpooled.copiedBuffer(data));
        ClientPluginChannels.sendMessage("DaFlight", outPacket, PluginChannels.ChannelPolicy.DISPATCH_ALWAYS);
        ClientPluginChannels.sendMessage("ZoMBe", outPacket, PluginChannels.ChannelPolicy.DISPATCH_ALWAYS);
    }
}
