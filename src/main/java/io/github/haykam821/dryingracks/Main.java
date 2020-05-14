package io.github.haykam821.dryingracks;

import io.github.haykam821.dryingracks.block.DryingRackBlock;
import io.github.haykam821.dryingracks.block.DryingRackBlockEntity;
import io.github.haykam821.dryingracks.recipe.DryingRecipe;
import io.github.haykam821.dryingracks.recipe.DryingRecipeSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Main implements ModInitializer {
	private static final String MOD_ID = "dryingracks";

	private static final Identifier OAK_DRYING_RACK_ID = new Identifier(MOD_ID, "oak_drying_rack");
	public static final Block OAK_DRYING_RACK = new DryingRackBlock(FabricBlockSettings.copy(Blocks.OAK_PLANKS));
	public static final BlockItem OAK_DRYING_RACK_ITEM = new BlockItem(OAK_DRYING_RACK, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));

	private static final Identifier SPRUCE_DRYING_RACK_ID = new Identifier(MOD_ID, "spruce_drying_rack");
	public static final Block SPRUCE_DRYING_RACK = new DryingRackBlock(FabricBlockSettings.copy(Blocks.SPRUCE_PLANKS));
	public static final BlockItem SPRUCE_DRYING_RACK_ITEM = new BlockItem(SPRUCE_DRYING_RACK, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));

	private static final Identifier BIRCH_DRYING_RACK_ID = new Identifier(MOD_ID, "birch_drying_rack");
	public static final Block BIRCH_DRYING_RACK = new DryingRackBlock(FabricBlockSettings.copy(Blocks.BIRCH_PLANKS));
	public static final BlockItem BIRCH_DRYING_RACK_ITEM = new BlockItem(BIRCH_DRYING_RACK, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));

	private static final Identifier JUNGLE_DRYING_RACK_ID = new Identifier(MOD_ID, "jungle_drying_rack");
	public static final Block JUNGLE_DRYING_RACK = new DryingRackBlock(FabricBlockSettings.copy(Blocks.JUNGLE_PLANKS));
	public static final BlockItem JUNGLE_DRYING_RACK_ITEM = new BlockItem(JUNGLE_DRYING_RACK, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));

	private static final Identifier ACACIA_DRYING_RACK_ID = new Identifier(MOD_ID, "acacia_drying_rack");
	public static final Block ACACIA_DRYING_RACK = new DryingRackBlock(FabricBlockSettings.copy(Blocks.ACACIA_PLANKS));
	public static final BlockItem ACACIA_DRYING_RACK_ITEM = new BlockItem(ACACIA_DRYING_RACK, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));

	private static final Identifier DARK_OAK_DRYING_RACK_ID = new Identifier(MOD_ID, "dark_oak_drying_rack");
	public static final Block DARK_OAK_DRYING_RACK = new DryingRackBlock(FabricBlockSettings.copy(Blocks.DARK_OAK_PLANKS));
	public static final BlockItem DARK_OAK_DRYING_RACK_ITEM = new BlockItem(DARK_OAK_DRYING_RACK, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));

	private static final Identifier DRYING_RACK_ID = new Identifier(MOD_ID, "drying_rack");
	public static final BlockEntityType<DryingRackBlockEntity> DRYING_RACK_BLOCK_ENTITY = BlockEntityType.Builder.create(DryingRackBlockEntity::new, OAK_DRYING_RACK, SPRUCE_DRYING_RACK, BIRCH_DRYING_RACK, JUNGLE_DRYING_RACK, DARK_OAK_DRYING_RACK, ACACIA_DRYING_RACK).build(null);

	private static final Identifier DRYING_ID = new Identifier(MOD_ID, "drying");
	public static final DryingRecipeSerializer DRYING_RECIPE_SERIALIZER = new DryingRecipeSerializer(DryingRecipe::new);
	public static final RecipeType<DryingRecipe> DRYING_RECIPE_TYPE = new RecipeType<DryingRecipe>() {
		public String toString() {
			return DRYING_ID.toString();
		}
	};

	@Override
	public void onInitialize() {
		Registry.register(Registry.BLOCK, OAK_DRYING_RACK_ID, OAK_DRYING_RACK);
		Registry.register(Registry.ITEM, OAK_DRYING_RACK_ID, OAK_DRYING_RACK_ITEM);

		Registry.register(Registry.BLOCK, SPRUCE_DRYING_RACK_ID, SPRUCE_DRYING_RACK);
		Registry.register(Registry.ITEM, SPRUCE_DRYING_RACK_ID, SPRUCE_DRYING_RACK_ITEM);

		Registry.register(Registry.BLOCK, BIRCH_DRYING_RACK_ID, BIRCH_DRYING_RACK);
		Registry.register(Registry.ITEM, BIRCH_DRYING_RACK_ID, BIRCH_DRYING_RACK_ITEM);

		Registry.register(Registry.BLOCK, JUNGLE_DRYING_RACK_ID, JUNGLE_DRYING_RACK);
		Registry.register(Registry.ITEM, JUNGLE_DRYING_RACK_ID, JUNGLE_DRYING_RACK_ITEM);

		Registry.register(Registry.BLOCK, ACACIA_DRYING_RACK_ID, ACACIA_DRYING_RACK);
		Registry.register(Registry.ITEM, ACACIA_DRYING_RACK_ID, ACACIA_DRYING_RACK_ITEM);

		Registry.register(Registry.BLOCK, DARK_OAK_DRYING_RACK_ID, DARK_OAK_DRYING_RACK);
		Registry.register(Registry.ITEM, DARK_OAK_DRYING_RACK_ID, DARK_OAK_DRYING_RACK_ITEM);

		Registry.register(Registry.BLOCK_ENTITY_TYPE, DRYING_RACK_ID, DRYING_RACK_BLOCK_ENTITY);

		Registry.register(Registry.RECIPE_SERIALIZER, DRYING_ID, DRYING_RECIPE_SERIALIZER);
		Registry.register(Registry.RECIPE_TYPE, DRYING_ID, DRYING_RECIPE_TYPE);
	}
}