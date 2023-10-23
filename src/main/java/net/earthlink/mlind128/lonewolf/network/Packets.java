package net.earthlink.mlind128.lonewolf.network;

import net.earthlink.mlind128.lonewolf.Lonewolf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public final class Packets {

	private static int id = 0;
	private static SimpleChannel instance;

	public static void register() {
		instance = NetworkRegistry.ChannelBuilder
				.named(new ResourceLocation(Lonewolf.MOD_ID, "messages"))
				.networkProtocolVersion(() -> "1.0")
				.clientAcceptedVersions(s -> true)
				.serverAcceptedVersions(s -> true)
				.simpleChannel();

		addPacket(NetworkDirection.PLAY_TO_SERVER, PacketEatWildMeat.class);
		addPacket(NetworkDirection.PLAY_TO_CLIENT, PacketToClientSyncThirst.class);
	}

	private static <T extends Packet> void addPacket(NetworkDirection direction, Class<T> packet) {
		instance.messageBuilder(packet, id++, direction)
				.decoder(new FriendlyByteBuf.Reader<>() {
					@Override
					public T apply(FriendlyByteBuf buf) {
						try {
							Packet newInstance = packet.newInstance();
							newInstance.read(buf);

							return (T) newInstance;

						} catch (ReflectiveOperationException ex) {
							throw new RuntimeException(ex);
						}

					}
				})
				.encoder(new BiConsumer<>() {
					@Override
					public void accept(T packet, FriendlyByteBuf buf) {
						packet.write(buf);
					}
				})
				.consumerMainThread(new BiConsumer<>() {
					@Override
					public void accept(T packet, Supplier<NetworkEvent.Context> supplier) {
						NetworkEvent.Context context = supplier.get();

						context.enqueueWork(() -> {
							packet.handle(context.getSender());
						});
					}
				})
				.add();
	}

	public static void sendToServer(Packet packet) {
		instance.sendToServer(packet);
	}

	public static void sendToPlayer(Packet packet, ServerPlayer player) {
		instance.send(PacketDistributor.PLAYER.with(() -> player), packet);
	}
}
