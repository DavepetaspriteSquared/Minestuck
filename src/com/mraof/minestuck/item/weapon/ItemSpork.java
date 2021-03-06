package com.mraof.minestuck.item.weapon;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.mraof.minestuck.Minestuck;

//I called it a spork because it includes both
public class ItemSpork extends ItemWeapon 
{
	private final EnumSporkType sporkType;
	
	/**
	 * whether it's a spoon or a fork, unused for the crocker spork, as it depends on the meta.
	 */
	public boolean isSpoon;

	public ItemSpork(EnumSporkType sporkType) 
	{
		super();
		this.isSpoon = sporkType.getIsSpoon();
		this.sporkType = sporkType;
		this.maxStackSize = 1;
		this.setMaxDamage(sporkType.getMaxUses());
		this.setCreativeTab(Minestuck.tabMinestuck);
		this.setUnlocalizedName(sporkType.getUnlocalizedName());
		this.weaponDamage = sporkType.getDamageVsEntity();
		this.weaponSpeed = sporkType.getAttackSpeed();
	}
	
	@Override
	public double getAttackDamage(ItemStack stack)
	{
		if(this.sporkType == EnumSporkType.CROCKER && !isSpoon(stack))
			return 6.0D;
		return super.getAttackDamage(stack);
	}
	
	@Override
	protected double getAttackSpeed(ItemStack stack)
	{
		if(this.sporkType == EnumSporkType.CROCKER && !isSpoon(stack))
			return EnumSporkType.FORK_ATTACK_SPEED;
		return super.getAttackSpeed(stack);
	}
	
	@Override
	public int getItemEnchantability()
	{
		return this.sporkType.getEnchantability();
	}

	@Override
	public boolean hitEntity(ItemStack itemStack, EntityLivingBase target, EntityLivingBase player)
	{
		itemStack.damageItem(1, player);
		return true;
	}

	public boolean isSpoon(ItemStack itemStack) {
		if (sporkType.equals(EnumSporkType.CROCKER))
			return !itemStack.hasTagCompound() ? true : itemStack.getTagCompound().getBoolean("isSpoon");
		else return isSpoon;
	}
	
	@Override	
	@SideOnly(Side.CLIENT)
	public boolean isFull3D()
	{
		return true;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack itemStack)
	{
		return 72000;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand)
	{
		if (sporkType.equals(EnumSporkType.CROCKER) && player.isSneaking())
		{
				if(!world.isRemote)
				{
					checkTagCompound(stack);
					stack.getTagCompound().setBoolean("isSpoon", !stack.getTagCompound().getBoolean("isSpoon"));
				}
				return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
			}
		return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
	}
	
	public void checkTagCompound(ItemStack stack)
	{
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		if(!stack.getTagCompound().hasKey("isSpoon"))
			stack.getTagCompound().setBoolean("isSpoon", true);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		if(this.sporkType != EnumSporkType.CROCKER)
			return getUnlocalizedName();
		else return "item."+(isSpoon(stack)?"crockerSpoon":"crockerFork");
	}
	
}