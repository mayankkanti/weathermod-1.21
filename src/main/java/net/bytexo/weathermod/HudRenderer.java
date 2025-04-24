package net.bytexo.weathermod;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
//import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.world.ClientWorld;

public class HudRenderer {
    private static float totalTickDelta = 0;
    private static long lastAlertTime = 0;

    public static void init() {
        HudRenderCallback.EVENT.register((context, tickDeltaManager) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            ClientWorld world = client.world;

            if (world == null) return;

            totalTickDelta += tickDeltaManager.getTickDelta(true);

            long timeOfDay = world.getTimeOfDay() % 24000;
            String timeStatus = (timeOfDay >= 0 && timeOfDay < 13000) ? "Daytime" : "Nighttime";

            String weatherStatus;
            int weatherColor = 0xFFFFFFFF;

            if (world.isThundering()) {
                weatherStatus = "⚡ Thunderstorm ⚡";

                // color targets
                int color1 = 0xFFFF0000;
                int color2 = 0xFFFFFF00;
                float lerpedAmount = MathHelper.abs(MathHelper.sin(totalTickDelta / 30F));
                weatherColor = ColorHelper.Argb.lerp(lerpedAmount, color1, color2);
                //current world time
                long currentTime = world.getTime();

                if (currentTime - lastAlertTime > 100) {
                    // send chat msg and update lastsend, sends every 100 ticks i.e. 5 secs
                    ChatMessage.sendClientMessage("Thunderstorm Alert!");
                    lastAlertTime = currentTime;
                }

            } else if (world.isRaining()) {
                weatherStatus = "Rainy";
                weatherColor = 0xFF00FFFF;
            } else {
                weatherStatus = "Clear";
            }

            // Text Pos
            int xPos = client.getWindow().getScaledWidth() - 150;
            int yPos = 10;

            context.drawText(client.textRenderer, "Time: " + timeStatus, xPos, yPos, 0xFFFFFFFF, false);
            context.drawText(client.textRenderer, "Weather: " + weatherStatus, xPos, yPos + 15, weatherColor, false);
        });
    }
}