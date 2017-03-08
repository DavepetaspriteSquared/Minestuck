package com.mraof.minestuck.item;


import com.mraof.minestuck.client.gui.GuiPesterchum;
import com.mraof.minestuck.util.Debug;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemPDA extends Item{

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack item, World world, EntityPlayer player, EnumHand hand)
    {

        if(world.isRemote) {
            Minecraft.getMinecraft().displayGuiScreen(new GuiPesterchum());
        }


        return super.onItemRightClick(item, world, player, hand);
    }


}
