package com.mraof.minestuck.world.gen.lands;

public interface ILandAspect
{
	
	/**
	 * Returns a string that represents a unique name for a land, Used in saving and loading land data.
	 * @return
	 */
	public String getPrimaryName();
	
	/**
	 * Returns a list of strings used in giving a land a random name.
	 */
	public String[] getNames();
}
