package com.tfar.mobgrinder.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;

public class SmallButton extends ButtonWidget {
  public SmallButton(int x, int y, int widthIn, int heightIn, String buttonText, PressAction callback) {
    super(x, y, widthIn, heightIn, buttonText, callback);
  }

  public boolean shouldDrawText(){
    return !getMessage().isEmpty();
  }

  public void tint(){
    RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
  }

  @Override
  public void render(int mouseX, int mouseY, float partialTicks) {
    if (visible) {
      MinecraftClient.getInstance().getTextureManager().bindTexture(WIDGETS_LOCATION);

      tint();

      isHovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;

      int i = getYImage(isHovered);

      RenderSystem.enableBlend();
      RenderSystem.blendFuncSeparate(770, 771, 1, 0);
      RenderSystem.blendFunc(770, 771);

      int halfwidth1 = this.width / 2;
      int halfwidth2 = this.width - halfwidth1;
      int halfheight1 = this.height / 2;
      int halfheight2 = this.height - halfheight1;
      blit(x, y, 0,
              46 + i * 20, halfwidth1, halfheight1);
      blit(x + halfwidth1, y, 200 - halfwidth2,
              46 + i * 20, halfwidth2, halfheight1);

      blit(x, y + halfheight1,
              0, 46 + i * 20 + 20 - halfheight2, halfwidth1, halfheight2);
      blit(x + halfwidth1, y + halfheight1,
              200 - halfwidth2, 46 + i * 20 + 20 - halfheight2, halfwidth2, halfheight2);
      if (shouldDrawText())drawText(halfwidth2);
    }
  }

  public void drawText(int halfwidth2){
    int textColor = 0xe0e0e0;
    if (!this.visible) {
      textColor = 0xa0a0a0;
    } else if (this.isHovered) {
      textColor = 0xffffa0;
    }
    this.drawCenteredString(MinecraftClient.getInstance().textRenderer, getMessage(), x + halfwidth2, y + (this.height - 8) / 2, textColor);
  }
}