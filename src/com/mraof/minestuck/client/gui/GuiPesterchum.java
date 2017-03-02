package com.mraof.minestuck.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiPesterchum extends GuiScreen
{
	private static final ResourceLocation guiBackground = new ResourceLocation("minestuck", "textures/gui/pesterchum.png");

	/**private static final int guiWidth = 232;
	private static final int guiHeight = 346;*/

	private static final int guiWidth = 186;
	private static final int guiHeight = 157;
	private float left;
	private float top;

	/**
	 * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
	 * window resizes, the buttonList is cleared beforehand.
	 */
	@Override
	public void initGui() {
		super.initGui();
		left = (this.width / 2) - (guiWidth / 2);
		top = (this.height / 2) - (guiHeight / 2);
	}

	/**
	 * Draws the screen and all the components in it.
	 *
	 * @param mouseX
	 * @param mouseY
	 * @param partialTicks
	 */
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		GlStateManager.color(1F, 1F, 1F, 1F);
		this.mc.getTextureManager().bindTexture(guiBackground);
		this.drawTexturedModalRect(left, top, 0, 0, guiWidth, guiHeight);
		fontRendererObj.drawString("PESTER!", (int) left + 54, (int) top, 0x404040);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	//TODO Add Custom Buttons

	@Override
	protected void actionPerformed(GuiButton guibutton)
	{

	}

	//TODO Three buttons: Pester Grabs a list of all users who have a PDA and allows pestering, Add Chum simply gives a text box and adds the name to the left box, and block simply prevents the blocked user pestering you

	/*static class PesterButton extends GuiButton
	{

	}*/
}