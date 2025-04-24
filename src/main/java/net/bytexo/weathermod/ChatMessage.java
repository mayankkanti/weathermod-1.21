package net.bytexo.weathermod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class ChatMessage {
    public static void sendClientMessage(String message) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null) {
            client.player.sendMessage(Text.of(message), false);
        }
    }
}
