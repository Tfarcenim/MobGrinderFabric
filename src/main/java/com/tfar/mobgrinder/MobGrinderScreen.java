package com.tfar.mobgrinder;

import com.tfar.mobgrinder.inventory.SmallButton;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.gui.screen.ingame.AbstractContainerScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;

public class MobGrinderScreen extends AbstractContainerScreen<MobGrinderMenu> {

	public static final Identifier background = new Identifier(MobGrinder.MODID,
					"textures/gui/mob_grinder.png");

	public MobGrinderScreen(MobGrinderMenu screenContainer, PlayerInventory inv, Text titleIn) {
		super(screenContainer, inv, titleIn);
	}

	@Override
	protected void init() {
		super.init();
		this.addButton(new SmallButton(x+96,y+4,10,10,"e",this::onPressed));
	}

	private void onPressed(ButtonWidget buttonWidget) {
			PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
			ClientSidePacketRegistry.INSTANCE.sendToServer(MobGrinder.xp, passedData);
	}

	@Override
	public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
		renderBackground();
		super.render(p_render_1_, p_render_2_, p_render_3_);
		this.drawMouseoverTooltip(p_render_1_, p_render_2_);
	}

	/**
	 * Draws the background layer of this container (behind the items).
	 *
	 * @param partialTicks
	 * @param mouseX
	 * @param mouseY
	 */
	@Override
	protected void drawBackground(float partialTicks, int mouseX, int mouseY) {
		minecraft.getTextureManager().bindTexture(background);
		blit(x, y, 0, 0, containerWidth, containerHeight);
	}

	@Override
	protected void drawForeground(int p_146979_1_, int p_146979_2_) {
		this.font.draw(this.playerInventory.getDisplayName().asString(), 8, containerHeight - 94, 0x404040);
		int size = font.getStringWidth(title.asFormattedString());
		int start = (containerWidth - size)/2;
		this.font.draw(this.title.asString(), start - 37, 7, 0x404040);
		Utils.getEntityTypeFromStack(
						this.container.te.handler.getInvStack(0)).ifPresent(entityType ->
						this.font.draw(I18n.translate(entityType.getTranslationKey()), start + 27, 73, 0x404040));
		this.font.drawWithShadow("xp: "+this.container.te.xp,start+48,6,0x11ff00);
	}
}
