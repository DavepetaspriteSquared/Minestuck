package com.mraof.minestuck;



import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.world.WorldType;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;

import com.mraof.minestuck.block.BlockChessTile;
import com.mraof.minestuck.block.BlockGatePortal;
import com.mraof.minestuck.client.ClientProxy;
import com.mraof.minestuck.entity.carapacian.EntityBlackBishop;
import com.mraof.minestuck.entity.carapacian.EntityBlackPawn;
import com.mraof.minestuck.entity.carapacian.EntityWhiteBishop;
import com.mraof.minestuck.entity.carapacian.EntityWhitePawn;
import com.mraof.minestuck.entity.consort.EntityNakagator;
import com.mraof.minestuck.entity.consort.EntitySalamander;
import com.mraof.minestuck.entity.item.EntityGrist;
import com.mraof.minestuck.entity.underling.EntityGiclops;
import com.mraof.minestuck.entity.underling.EntityImp;
import com.mraof.minestuck.entity.underling.EntityOgre;
import com.mraof.minestuck.item.EnumBladeType;
import com.mraof.minestuck.item.EnumCaneType;
import com.mraof.minestuck.item.EnumClubType;
import com.mraof.minestuck.item.EnumHammerType;
import com.mraof.minestuck.item.EnumSickleType;
import com.mraof.minestuck.item.EnumSporkType;
import com.mraof.minestuck.item.ItemBlade;
import com.mraof.minestuck.item.ItemCane;
import com.mraof.minestuck.item.ItemChessTile;
import com.mraof.minestuck.item.ItemClub;
import com.mraof.minestuck.item.ItemHammer;
import com.mraof.minestuck.item.ItemSickle;
import com.mraof.minestuck.item.ItemSpork;
import com.mraof.minestuck.network.MinestuckPacketHandler;
import com.mraof.minestuck.tileentity.TileEntityGatePortal;
import com.mraof.minestuck.tracker.MinestuckPlayerTracker;
import com.mraof.minestuck.world.WorldProviderSkaia;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "Minestuck", name = "Minestuck", version = "0.0.4")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, packetHandler = MinestuckPacketHandler.class, channels = {"Minestuck"})
public class Minestuck
{
	//these ids are in case I need to raise or lower ids for whatever reason
	public static int toolIdStart = 5001;
	public static int entityIdStart = 5050;
	public static int blockIdStart = 500;
	public static int skaiaProviderTypeId = 2;
	public static int skaiaDimensionId = 2;

	//hammers
	public static Item clawHammer;
	public static Item sledgeHammer;
	public static Item pogoHammer;
	public static Item telescopicSassacrusher;
	public static Item fearNoAnvil;
	public static Item zillyhooHammer;
	public static Item popamaticVrillyhoo;
	public static Item scarletZillyhoo;
	//blades
	public static Item sord;
	public static Item ninjaSword;
	public static Item katana;
	public static Item caledscratch;
	public static Item royalDeringer;
	public static Item regisword;
	public static Item scarletRibbitar;
	public static Item doggMachete;
	//sickles
	public static Item sickle;
	public static Item homesSmellYaLater;
	public static Item regiSickle;
	public static Item clawSickle;
	//clubs
	public static Item deuceClub;
	//canes
	public static Item cane;
	public static Item spearCane;
	public static Item dragonCane;
	//Spoons/forks
	public static Item crockerSpork;
	public static Item skaiaFork;

	public static Achievement getHammer;

	//Blocks
	public static Block chessTile;
	public static Block gatePortal;
	
	
	// The instance of your mod that Forge uses.
	@Instance("Minestuck")
	public static Minestuck instance;

	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide="com.mraof.minestuck.client.ClientProxy", serverSide="com.mraof.minestuck.CommonProxy")

	//The proxy to be used by client and server
	public static CommonProxy proxy;

	public int currentEntityIdOffset = 0;
	@PreInit
	public void preInit(FMLPreInitializationEvent event) 
	{
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		blockIdStart = config.get("Block Ids", "blockIdStart", 500).getInt();
		toolIdStart = config.get("Item Ids", "toolIdStart", 5001).getInt();
		entityIdStart = config.get("Entity Ids", "entitydIdStart", 5050).getInt();
		skaiaProviderTypeId = config.get("Provider Type Ids", "skaiaProviderTypeId", 2).getInt();
		skaiaDimensionId = config.get("Dimension Ids", "skaiaDimensionId", 2).getInt();
		config.save();
	}

	@Init
	public void load(FMLInitializationEvent event) 
	{
		//blocks
		chessTile = new BlockChessTile(blockIdStart);
		gatePortal = new BlockGatePortal(blockIdStart + 1, Material.portal);
		//hammers
		clawHammer = new ItemHammer(toolIdStart, EnumHammerType.CLAW);
		sledgeHammer = new ItemHammer(toolIdStart + 1, EnumHammerType.SLEDGE);
		pogoHammer = new ItemHammer(toolIdStart + 2, EnumHammerType.POGO);
		telescopicSassacrusher = new ItemHammer(toolIdStart + 3, EnumHammerType.TELESCOPIC);
		fearNoAnvil = new ItemHammer(toolIdStart + 4, EnumHammerType.FEARNOANVIL);
		zillyhooHammer = new ItemHammer(toolIdStart + 5, EnumHammerType.ZILLYHOO);
		popamaticVrillyhoo = new ItemHammer(toolIdStart + 6, EnumHammerType.POPAMATIC);
		scarletZillyhoo = new ItemHammer(toolIdStart + 7, EnumHammerType.SCARLET);
		//blades
		sord = new ItemBlade(toolIdStart + 8, EnumBladeType.SORD);
		ninjaSword = new ItemBlade(toolIdStart + 9, EnumBladeType.NINJA);
		katana = new ItemBlade(toolIdStart + 10, EnumBladeType.KATANA);
		caledscratch = new ItemBlade(toolIdStart + 11, EnumBladeType.CALEDSCRATCH);
		royalDeringer = new ItemBlade(toolIdStart + 12, EnumBladeType.DERINGER);
		regisword = new ItemBlade(toolIdStart + 13, EnumBladeType.REGISWORD);
		scarletRibbitar = new ItemBlade(toolIdStart + 14, EnumBladeType.SCARLET);
		doggMachete = new ItemBlade(toolIdStart + 15, EnumBladeType.DOGG);
		//sickles
		sickle = new ItemSickle(toolIdStart + 16, EnumSickleType.SICKLE);
		homesSmellYaLater = new ItemSickle(toolIdStart + 17, EnumSickleType.HOMES);
		regiSickle = new ItemSickle(toolIdStart + 18, EnumSickleType.REGISICKLE);
		clawSickle = new ItemSickle(toolIdStart + 19, EnumSickleType.CLAW);
		//clubs
		deuceClub = new ItemClub(toolIdStart + 20, EnumClubType.DEUCE);
		//canes
		cane = new ItemCane(toolIdStart + 21, EnumCaneType.CANE);
		spearCane = new ItemCane(toolIdStart + 22, EnumCaneType.SPEAR);
		dragonCane = new ItemCane(toolIdStart + 23, EnumCaneType.DRAGON);
		//Spoons/forks
		crockerSpork = new ItemSpork(toolIdStart + 24, EnumSporkType.CROCKER);
		skaiaFork = new ItemSpork(toolIdStart + 25, EnumSporkType.SKAIA);

		//achievements
		getHammer = (new Achievement(413, "getHammer", 12, 15, Minestuck.clawHammer, (Achievement)null)).setIndependent().registerAchievement();

		//registers things for the client
		ClientProxy.registerSided();
		//server doesn't actually register any renderers for obvious reasons
		proxy.registerRenderers();
		//the client does, however
		ClientProxy.registerRenderers();
		//register blocks
		GameRegistry.registerBlock(chessTile, ItemChessTile.class, "chessTile");
		GameRegistry.registerBlock(gatePortal, "gatePortal");
		//metadata nonsense to conserve ids
		ItemStack blackChessTileStack = new ItemStack(chessTile, 1, 0);
		ItemStack whiteChessTileStack = new ItemStack(chessTile, 1, 1);
		ItemStack darkGreyChessTileStack = new ItemStack(chessTile, 1, 2);
		ItemStack lightGreyChessTileStack = new ItemStack(chessTile, 1, 3);
		//Give Items names to be displayed ingame

		LanguageRegistry.addName(clawHammer, "Claw Hammer");
		LanguageRegistry.addName(sledgeHammer, "Sledgehammer");
		LanguageRegistry.addName(pogoHammer, "Pogo Hammer");
		LanguageRegistry.addName(telescopicSassacrusher, "Telescopic Sassacrusher");
		LanguageRegistry.addName(fearNoAnvil, "Fear No Anvil");
		LanguageRegistry.addName(zillyhooHammer, "The Warhammer of Zillyhoo");
		LanguageRegistry.addName(popamaticVrillyhoo, "Pop-a-matic Vrillyhoo Hammer");
		LanguageRegistry.addName(scarletZillyhoo, "Scarlet Zillyhoo");
		LanguageRegistry.addName(sord, "Sord.....");
		LanguageRegistry.addName(ninjaSword, "Katana");
		LanguageRegistry.addName(katana, "Unbreakable Katana");
		LanguageRegistry.addName(caledscratch, "Caledscratch");
		LanguageRegistry.addName(royalDeringer, "Royal Deringer");
		LanguageRegistry.addName(regisword, "Regisword");
		LanguageRegistry.addName(scarletRibbitar, "Scarlet Ribbitar");
		LanguageRegistry.addName(doggMachete, "Snoop Dogg Snow Cone Machete");
		LanguageRegistry.addName(sickle, "Sickle");
		LanguageRegistry.addName(homesSmellYaLater, "Homes Smell Ya Later");
		LanguageRegistry.addName(regiSickle, "Regisickle");
		LanguageRegistry.addName(clawSickle, "Clawsickle");
		LanguageRegistry.addName(deuceClub, "Deuce Club");
		LanguageRegistry.addName(cane, "Cane");
		LanguageRegistry.addName(spearCane, "Spear Cane");
		LanguageRegistry.addName(dragonCane, "Dragon Cane");
		LanguageRegistry.addName(crockerSpork, "Junior Battlemaster's Bowlbuster Stirring/Poking Solution 50000");
		LanguageRegistry.addName(skaiaFork, "Skaia War Fork");
		//Same for blocks
		LanguageRegistry.addName(blackChessTileStack, "Black Chess Tile");
		LanguageRegistry.addName(whiteChessTileStack, "White Chess Tile");
		LanguageRegistry.addName(lightGreyChessTileStack, "Light Grey Chess Tile");
		LanguageRegistry.addName(darkGreyChessTileStack, "Dark Grey Chess Tile");
		LanguageRegistry.addName(gatePortal, "Gate");
		//set harvest information for blocks
		MinecraftForge.setBlockHarvestLevel(chessTile, "shovel", 0);

		//set translations for automatic names
		LanguageRegistry.instance().addStringLocalization("entity.Salamander.name", "Salamander");
		LanguageRegistry.instance().addStringLocalization("entity.Nakagator.name", "Nakagator");
		LanguageRegistry.instance().addStringLocalization("entity.Imp.name", "Imp");
		//Different names for different types of imps
		for(EntityImp.Type impType : EntityImp.Type.values())
			LanguageRegistry.instance().addStringLocalization("entity." + impType.getTypeString() + ".Imp.name", impType.getTypeString() + " Imp");
		LanguageRegistry.instance().addStringLocalization("entity.Ogre.name", "Ogre");
		for(EntityOgre.Type ogreType : EntityOgre.Type.values())
			LanguageRegistry.instance().addStringLocalization("entity." + ogreType.getTypeString() + ".Ogre.name", ogreType.getTypeString() + " Ogre");
		LanguageRegistry.instance().addStringLocalization("entity.Giclops.name", "Giclops");
		for(EntityGiclops.Type giclopsType : EntityGiclops.Type.values())
			LanguageRegistry.instance().addStringLocalization("entity." + giclopsType.getTypeString() + ".Giclops.name", giclopsType.getTypeString() + " Giclops");
		LanguageRegistry.instance().addStringLocalization("entity.dersitePawn.name", "Dersite Pawn");
		LanguageRegistry.instance().addStringLocalization("entity.prospitianPawn.name", "Prospitian Pawn");
		LanguageRegistry.instance().addStringLocalization("entity.dersiteBishop.name", "Dersite Bishop");
		LanguageRegistry.instance().addStringLocalization("entity.prospitianBishop.name", "Prospitian Bishop");

		LanguageRegistry.instance().addStringLocalization("achievement.getHammer", "Get Hammer");
		LanguageRegistry.instance().addStringLocalization("achievement.getHammer.desc", "Get the Claw Hammer");

		LanguageRegistry.instance().addStringLocalization("key.gristCache", "View Grist Cache");
		//register entities
		this.registerAndMapEntity(EntitySalamander.class, "Salamander", 0xffe62e, 0xfffb53);
		this.registerAndMapEntity(EntityNakagator.class, "Nakagator", 0xffe62e, 0xfffb53);
		this.registerAndMapEntity(EntityImp.class, "Imp", 0x000000, 0xffffff);
		this.registerAndMapEntity(EntityOgre.class, "Ogre", 0x000000, 0xffffff);
		this.registerAndMapEntity(EntityGiclops.class, "Giclops", 0x000000, 0xffffff);
		this.registerAndMapEntity(EntityBlackPawn.class, "dersitePawn", 0x0f0f0f, 0xf0f0f0);
		this.registerAndMapEntity(EntityWhitePawn.class, "prospitianPawn", 0xf0f0f0, 0x0f0f0f);
		this.registerAndMapEntity(EntityBlackBishop.class, "dersiteBishop", 0x000000, 0xc121d9);
		this.registerAndMapEntity(EntityWhiteBishop.class, "prospitianBishop", 0xffffff, 0xfde500);
		//register entities with fml
		EntityRegistry.registerModEntity(EntityGrist.class, "grist", currentEntityIdOffset, this, 512, 1, true);

		EntityRegistry.addSpawn(EntityImp.class, 3, 3, 20, EnumCreatureType.monster, WorldType.base12Biomes);
		EntityRegistry.addSpawn(EntityOgre.class, 2, 1, 1, EnumCreatureType.monster, WorldType.base12Biomes);
		EntityRegistry.addSpawn(EntityGiclops.class, 1, 1, 1, EnumCreatureType.monster, WorldType.base12Biomes);
		//register Tile Entities
		GameRegistry.registerTileEntity(TileEntityGatePortal.class, "gatePortal");
		//register world generators
		DimensionManager.registerProviderType(skaiaProviderTypeId, WorldProviderSkaia.class, true);
		DimensionManager.registerDimension(skaiaDimensionId, skaiaProviderTypeId);
		GameRegistry.registerPlayerTracker(new MinestuckPlayerTracker());

	}

	@PostInit
	public void postInit(FMLPostInitializationEvent event) 
	{
		
	}
	//registers entity with forge and minecraft, and increases currentEntityIdOffset by one in order to prevent id collision
	public void registerAndMapEntity(Class entityClass, String name, int eggColor, int eggSpotColor)
	{
		this.registerAndMapEntity(entityClass, name, eggColor, eggSpotColor, 80, 3, true);
	}
	public void registerAndMapEntity(Class entityClass, String name, int eggColor, int eggSpotColor, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates)
	{
		EntityList.addMapping(entityClass, name, entityIdStart + currentEntityIdOffset, eggColor, eggSpotColor);
		EntityRegistry.registerModEntity(entityClass, name, currentEntityIdOffset, this, trackingRange, updateFrequency, sendsVelocityUpdates);
		currentEntityIdOffset++;
	}
}
