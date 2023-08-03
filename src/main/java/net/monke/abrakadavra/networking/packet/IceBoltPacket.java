package net.monke.abrakadavra.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.monke.abrakadavra.item.ModItems;

import java.util.function.Supplier;

public class IceBoltPacket {
    public IceBoltPacket() {}

    public IceBoltPacket(FriendlyByteBuf buf) {
            }

    public void toBytes(FriendlyByteBuf buf) {

    }
    public boolean handle (Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            ServerLevel level = player.getLevel();
            player.containerMenu.setItem(36, 1, new ItemStack(ModItems.WAND_ICE_BOLT.get()));
//            player.getInventory().setItem(41, new ItemStack(ModItems.WAND_LEVITATION_BLAST.get()));
//            player.getInventory().add(new ItemStack(ModItems.WAND_LEVITATION_BLAST.get()));
            level.playSound(null, player.getOnPos(), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS,
                    0.8F, level.random.nextFloat()*0.1F+0.9F);

        });
        return true;
    }
}
