package com.mraof.minestuck.client.gui;

import com.mraof.minestuck.network.MinestuckChannelHandler;
import com.mraof.minestuck.network.MinestuckPacket;
import com.mraof.minestuck.network.PesterchumPacket;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import org.lwjgl.input.Mouse;

import java.io.IOException;


public class GuiPesterchum extends GuiScreen
{
	private static final ResourceLocation guiBackground = new ResourceLocation("minestuck", "textures/gui/pesterchum.png");

	/**private static final int guiWidth = 232;
	private static final int guiHeight = 346;*/

	private static final int guiWidth = 186;
	private static final int guiHeight = 157;
	private float left;
	private float top;
	private GuiTextField chumhandleTextField;
	private GuiButton PesterButton;
	private GuiButton AddChumButton;
	private GuiButton BlockButton;

	/**GuiButton button1;

	final int BUTTON1 = 0;*/

	/**
	 * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
	 * window resizes, the buttonList is cleared beforehand.
	 */
	@Override
	public void initGui() {
		int xOffset = (this.width / 2) - (guiWidth / 2);
		int yOffset = (this.height / 2) - (guiHeight / 2);

		//PesterButton = new GuiButtonExt(1, 230, 128, 64, 13, "PESTER");
		PesterButton = new GuiButtonExt(1, xOffset + 104, yOffset + 87, 64, 13, "PESTER");
		AddChumButton = new GuiButtonExt(2, xOffset + 104, yOffset + 102, 64, 13, "ADD CHUM");
		BlockButton = new GuiButtonExt(3, xOffset + 104, yOffset + 117, 64, 13, "BLOCK");

		this.chumhandleTextField = new GuiTextField(0, this.fontRendererObj, xOffset + 91, yOffset + 65, 87, 14);
		this.chumhandleTextField.setFocused(true);
		//this.chumhandleTextField.setMaxStringLength(4);
		//this.chumhandleTextField.setText();

		buttonList.add(PesterButton);
		buttonList.add(AddChumButton);
		buttonList.add(BlockButton);

		super.initGui();
		left = (this.width / 2) - (guiWidth / 2);
		top = (this.height / 2) - (guiHeight / 2);
	}

	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
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
		/**fontRendererObj.drawString("PESTER!", (int) left + 113, (int) top + 90, 0x404040);
		fontRendererObj.drawString("ADD CHUM", (int) left + 113, (int) top + 112, 0x404040);
		fontRendererObj.drawString("BLOCK", (int) left + 113, (int) top + 134, 0x404040);*/
		this.chumhandleTextField.drawTextBox();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	//TODO Add Custom Buttons

	@Override
	protected void actionPerformed(GuiButton guibutton)
	{

		if (guibutton == PesterButton)
		{
			if (Mouse.getEventButton() == 0)
			{
				//Get list of other people with Pesterchum
				MinestuckPacket packet = MinestuckPacket.makePacket(MinestuckPacket.Type.PESTERCHUM, PesterchumPacket.PESTER_LIST);
				MinestuckChannelHandler.sendToServer(packet);
			}
		}

	}

  @Override
	protected void keyTyped(char character, int key) throws IOException
	{
		super.keyTyped(character, key);
		this.chumhandleTextField.textboxKeyTyped(character, key);
	}

  @Override
	protected void mouseClicked(int x, int y, int button) throws IOException
	{
		super.mouseClicked(x, y, button);
		this.chumhandleTextField.mouseClicked(x, y, button);
	}

	//TODO Three buttons: Pester Grabs a list of all users who have a PDA and allows pestering, Add Chum simply gives a text box and adds the name to the left box, and block simply prevents the blocked user pestering you

	/*static class PesterButton extends GuiButton
	{

	}*/
}