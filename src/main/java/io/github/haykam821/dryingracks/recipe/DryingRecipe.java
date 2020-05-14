package io.github.haykam821.dryingracks.recipe;

import io.github.haykam821.dryingracks.Main;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;

public class DryingRecipe extends AbstractCookingRecipe {
	public DryingRecipe(Identifier id, String group, Ingredient input, ItemStack output) {
		super(Main.DRYING_RECIPE_TYPE, id, group, input, output, 0, 1200);
	}

	public Ingredient getInput() {
		return this.input;
	}

	@Override
	@Environment(EnvType.CLIENT)
	public ItemStack getRecipeKindIcon() {
		return new ItemStack(Main.OAK_DRYING_RACK_ITEM);
	}

	@Override
	public RecipeSerializer<DryingRecipe> getSerializer() {
		return Main.DRYING_RECIPE_SERIALIZER;
	}
}
