package com.mraof.minestuck.world.gen.lands.title;

import com.mraof.minestuck.world.gen.ChunkProviderLands;

public class LandAspectNull extends TitleAspect
{
	
	@Override
	public String getPrimaryName()
	{
		return "Null";
	}

	@Override
	public String[] getNames()
	{
		return new String[] {"Null"};
	}

	@Override
	public float getRarity()
	{
		return 0;
	}

	@Override
	protected void prepareChunkProvider(ChunkProviderLands chunkProvider)
	{
		
		
	}

}
