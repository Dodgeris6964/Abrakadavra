package net.monke.abrakadavra.networking;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.monke.abrakadavra.Abrakadavra;
import net.monke.abrakadavra.networking.packet.C2SPacket;
import net.monke.abrakadavra.networking.packet.IceBoltPacket;
import net.monke.abrakadavra.networking.packet.LevitationPacket;
import net.monke.abrakadavra.networking.packet.SummonDemisedPacket;

public class ModMessages {
    private static SimpleChannel INSTANCE;
    private static int packetID = 0;
    private static int id() {
        return packetID++;
    }
    public static void register () {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(Abrakadavra.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();
        INSTANCE = net;

        net.messageBuilder(C2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(C2SPacket::new)
                .encoder(C2SPacket::toBytes)
                .consumer(C2SPacket::handle)
                .add();
        net.messageBuilder(LevitationPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(LevitationPacket::new)
                .encoder(LevitationPacket::toBytes)
                .consumer(LevitationPacket::handle)
                .add();
        net.messageBuilder(IceBoltPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(IceBoltPacket::new)
                .encoder(IceBoltPacket::toBytes)
                .consumer(IceBoltPacket::handle)
                .add();
        net.messageBuilder(SummonDemisedPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SummonDemisedPacket::new)
                .encoder(SummonDemisedPacket::toBytes)
                .consumer(SummonDemisedPacket::handle)
                .add();
    }
    public static <MSG> void sendToServer (MSG message) {
        INSTANCE.sendToServer(message);
    }
    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
