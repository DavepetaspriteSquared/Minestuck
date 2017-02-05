package com.mraof.minestuck.client.gui.captchalouge;

import net.minecraft.item.ItemStack;

import com.mraof.minestuck.inventory.captchalouge.Modus;

public class ArrayGuiHandler extends SylladexGuiHandler
{
  private static final int UNITS_BETWEEN_CARDS_X = 5;
  private static final int UNITS_BETWEEN_CARDS_Y = 5;
	private Modus modus;

    public ArrayGuiHandler(Modus modus)
    {
        super();
        this.modus = modus;
        this.textureIndex = 6;
    }

    @Override
    public void updateContent()
    {
        ItemStack[] stacks = modus.getItems();
        this.cards.clear();
        int columns = Math.min(5, stacks.length);
        int rows = (stacks.length + 4)/5;
        this.maxWidth = Math.max(mapWidth, 2*UNITS_BETWEEN_CARDS_X + (columns*CARD_WIDTH + (columns - 1)*UNITS_BETWEEN_CARDS_X));
        this.maxHeight = Math.max(mapHeight, 2*UNITS_BETWEEN_CARDS_Y + (rows*CARD_HEIGHT + (rows - 1)*UNITS_BETWEEN_CARDS_Y));
        super.updateContent();

        int startX = Math.max(UNITS_BETWEEN_CARDS_X, (mapWidth - (columns*CARD_WIDTH + (columns - 1)*UNITS_BETWEEN_CARDS_X))/2);
        int startY = Math.max(UNITS_BETWEEN_CARDS_Y, (mapHeight - (rows*CARD_HEIGHT + (rows - 1)*UNITS_BETWEEN_CARDS_Y))/2);
        for(int i = 0; i < stacks.length; i++)
            this.cards.add(new GuiCard(stacks[i], this, i, startX + i%5*(CARD_WIDTH + UNITS_BETWEEN_CARDS_X), startY + (i/5)*(CARD_HEIGHT + UNITS_BETWEEN_CARDS_Y)));
    }

    @Override
    public void updatePosition()
    {
        int columns = Math.min(5, cards.size());
        int rows = (cards.size() + 4)/5;
        this.maxWidth = Math.max(mapWidth, 2*UNITS_BETWEEN_CARDS_X + (columns*CARD_WIDTH + (columns - 1)*UNITS_BETWEEN_CARDS_X));
        this.maxHeight = Math.max(mapHeight, 2*UNITS_BETWEEN_CARDS_Y + (rows*CARD_HEIGHT + (rows - 1)*UNITS_BETWEEN_CARDS_Y));

        int startX = Math.max(UNITS_BETWEEN_CARDS_X, (mapWidth - (columns*CARD_WIDTH + (columns - 1)*UNITS_BETWEEN_CARDS_X))/2);
        int startY = Math.max(UNITS_BETWEEN_CARDS_Y, (mapHeight - (rows*CARD_HEIGHT + (rows - 1)*UNITS_BETWEEN_CARDS_Y))/2);
        for(int i = 0; i < cards.size(); i++)
        {
            GuiCard card = cards.get(i);
            card.xPos = startX + i%5*(CARD_WIDTH + UNITS_BETWEEN_CARDS_X);
            card.yPos = startY + (i/5)*(CARD_HEIGHT + UNITS_BETWEEN_CARDS_Y);
        }
    }
}
