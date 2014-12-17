package com.mraof.minestuck.world.storage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.mraof.minestuck.network.skaianet.SkaianetHandler;
import com.mraof.minestuck.util.MinestuckPlayerData;
import com.mraof.minestuck.world.gen.lands.LandHelper.AspectCombination;
import com.mraof.minestuck.tileentity.TileEntityTransportalizer;

public class MinestuckSaveHandler 
{
	
	@SubscribeEvent
	public void onWorldSave(WorldEvent.Save event)
	{
		if(event.world.provider.getDimensionId() != 0)	//Only save one time each world-save instead of one per dimension each world-save.
			return;

		File dataFile = event.world.getSaveHandler().getMapFileFromName("MinestuckData");
		if (dataFile != null)
		{
			Byte[] landIDs = (Byte[])lands.keySet().toArray();
			NBTTagCompound nbt = new NBTTagCompound();
			byte[] landArray = new byte[landIDs.length];
			for(int i = 0; i < landIDs.length; i++)
				landArray[i] = landIDs[i];
			nbt.setByteArray("landList", landArray);
			
			TileEntityTransportalizer.saveTransportalizers(nbt);
			
			SkaianetHandler.saveData(nbt);
			
			MinestuckPlayerData.writeToNBT(nbt);
			
			try {
				CompressedStreamTools.writeCompressed(nbt, new FileOutputStream(dataFile));
			} catch(IOException e) {
				e.printStackTrace();
			}
		}

		//String[] oldFiles = {"gristCache", "minestuckLandList", "connectionList"};
		//for(String s : oldFiles) {
		dataFile = event.world.getSaveHandler().getMapFileFromName("gristCache");
		if(dataFile != null && dataFile.exists())
			dataFile.delete();
		//}
		
	}
	
	//Remove when the issue MinecraftForge#1551 is resolved
	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event)
	{
		if(!event.world.isRemote && event.world.villageCollectionObj == null)
			event.world.init();
	}
	
}
