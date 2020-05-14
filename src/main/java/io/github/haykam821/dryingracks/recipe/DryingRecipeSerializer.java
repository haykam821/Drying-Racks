package io.github.haykam821.dryingracks.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.registry.Registry;

public class DryingRecipeSerializer implements RecipeSerializer<DryingRecipe> {
	private final DryingRecipeFactory recipeFactory;

	public DryingRecipeSerializer(DryingRecipeFactory recipeFactory) {
		this.recipeFactory = recipeFactory;
	}

	@Override
	public DryingRecipe read(Identifier identifier, JsonObject jsonObject) {
		String group = JsonHelper.getString(jsonObject, "group", "");

		JsonElement ingredientElement = JsonHelper.hasArray(jsonObject, "ingredient") ? JsonHelper.getArray(jsonObject, "ingredient") : JsonHelper.getObject(jsonObject, "ingredient");
		Ingredient ingredient = Ingredient.fromJson(ingredientElement);

		String result = JsonHelper.getString(jsonObject, "result");
		Identifier resultID = new Identifier(result);
		ItemStack resultStack = new ItemStack(Registry.ITEM.getOrEmpty(resultID).orElseThrow(() -> {
			return new IllegalStateException("Item: " + result + " does not exist");
		}));
	
		return this.recipeFactory.create(identifier, group, ingredient, resultStack);
	}

	@Override
	public DryingRecipe read(Identifier identifier, PacketByteBuf packet) {
		String group = packet.readString(32767);
		Ingredient ingredient = Ingredient.fromPacket(packet);
		ItemStack resultStack = packet.readItemStack();

		return this.recipeFactory.create(identifier, group, ingredient, resultStack);
	}

	@Override
	public void write(PacketByteBuf packet, DryingRecipe dryingRecipe) {
		packet.writeString(dryingRecipe.getGroup());
		dryingRecipe.getInput().write(packet);
		packet.writeItemStack(dryingRecipe.getOutput());
	}

	public interface DryingRecipeFactory {
		DryingRecipe create(Identifier identifier, String string, Ingredient ingredient, ItemStack itemStack);
	}
}