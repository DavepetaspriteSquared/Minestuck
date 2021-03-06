package com.mraof.minestuck.entity.consort;

import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

import com.mraof.minestuck.entity.EntityMinestuck;

public abstract class EntityConsort extends EntityMinestuck
{

	public EntityConsort(World world) 
	{
		super(world);
		setSize(0.6F, 1.5F);
		this.experienceValue = 1;
		this.tasks.addTask(5, new EntityAIWander(this, 0.6F));
		this.tasks.addTask(6, new EntityAILookIdle(this));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
	}

	@Override
	protected float getMaximumHealth() 
	{
		return 10;
	}
	

	@Override
	public boolean getCanSpawnHere()
	{
		return this.isValidLightLevel() && super.getCanSpawnHere();
	}
	
	protected boolean isValidLightLevel()
	{
		int i = MathHelper.floor_double(this.posX);
		int j = MathHelper.floor_double(this.getEntityBoundingBox().minY);
		int k = MathHelper.floor_double(this.posZ);
		BlockPos pos = new BlockPos(i, j, k);
		
		if (this.worldObj.getLightFor(EnumSkyBlock.SKY, pos) < this.rand.nextInt(8))
		{
			return false;
		}
		else
		{
			int l = this.worldObj.getLightFromNeighbors(pos);
			
			if (this.worldObj.isThundering())
			{
				int i1 = this.worldObj.getSkylightSubtracted();
				this.worldObj.setSkylightSubtracted(10);
				l = this.worldObj.getLightFromNeighbors(pos);
				this.worldObj.setSkylightSubtracted(i1);
			}
			
			return l >= this.rand.nextInt(8);
		}
	}
}