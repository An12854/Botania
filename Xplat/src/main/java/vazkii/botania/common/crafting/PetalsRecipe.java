/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package vazkii.botania.common.crafting;

import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.NotNull;

import vazkii.botania.api.recipe.PetalApothecaryRecipe;
import vazkii.botania.common.block.BotaniaBlocks;

import java.util.ArrayList;
import java.util.List;

public class PetalsRecipe implements PetalApothecaryRecipe {
	private final ResourceLocation id;
	private final ItemStack output;
	private final Ingredient reagent;
	private final NonNullList<Ingredient> inputs;

	public PetalsRecipe(ResourceLocation id, ItemStack output, Ingredient reagent, Ingredient... inputs) {
		Preconditions.checkArgument(inputs.length <= 16, "Cannot have more than 16 ingredients");
		this.id = id;
		this.output = output;
		this.reagent = reagent;
		this.inputs = NonNullList.of(Ingredient.EMPTY, inputs);
	}

	@Override
	public Ingredient getReagent() {
		return reagent;
	}

	@Override
	public boolean matches(Container inv, @NotNull Level world) {
		List<Ingredient> ingredientsMissing = new ArrayList<>(inputs);

		for (int i = 0; i < inv.getContainerSize(); i++) {
			ItemStack input = inv.getItem(i);
			if (input.isEmpty()) {
				break;
			}

			int stackIndex = -1;

			for (int j = 0; j < ingredientsMissing.size(); j++) {
				Ingredient ingr = ingredientsMissing.get(j);
				if (ingr.test(input)) {
					stackIndex = j;
					break;
				}
			}

			if (stackIndex != -1) {
				ingredientsMissing.remove(stackIndex);
			} else {
				return false;
			}
		}

		return ingredientsMissing.isEmpty();
	}

	@NotNull
	@Override
	public final ItemStack getResultItem() {
		return output;
	}

	@NotNull
	@Override
	public ItemStack assemble(@NotNull Container inv) {
		return getResultItem().copy();
	}

	@NotNull
	@Override
	public NonNullList<Ingredient> getIngredients() {
		return inputs;
	}

	@NotNull
	@Override
	public ItemStack getToastSymbol() {
		return new ItemStack(BotaniaBlocks.defaultAltar);
	}

	@NotNull
	@Override
	public ResourceLocation getId() {
		return id;
	}

	@NotNull
	@Override
	public RecipeSerializer<?> getSerializer() {
		return BotaniaRecipeTypes.PETAL_SERIALIZER;
	}

	public static class Serializer extends RecipeSerializerBase<PetalsRecipe> {
		@NotNull
		@Override
		public PetalsRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
			ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));
			Ingredient reagent = Ingredient.fromJson(json.get("reagent"));
			JsonArray ingrs = GsonHelper.getAsJsonArray(json, "ingredients");
			List<Ingredient> inputs = new ArrayList<>();
			for (JsonElement e : ingrs) {
				inputs.add(Ingredient.fromJson(e));
			}
			return new PetalsRecipe(id, output, reagent, inputs.toArray(new Ingredient[0]));
		}

		@Override
		public PetalsRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buf) {
			Ingredient[] inputs = new Ingredient[buf.readVarInt()];
			for (int i = 0; i < inputs.length; i++) {
				inputs[i] = Ingredient.fromNetwork(buf);
			}
			Ingredient reagent = Ingredient.fromNetwork(buf);
			ItemStack output = buf.readItem();
			return new PetalsRecipe(id, output, reagent, inputs);
		}

		@Override
		public void toNetwork(@NotNull FriendlyByteBuf buf, @NotNull PetalsRecipe recipe) {
			buf.writeVarInt(recipe.getIngredients().size());
			for (Ingredient input : recipe.getIngredients()) {
				input.toNetwork(buf);
			}
			recipe.reagent.toNetwork(buf);
			buf.writeItem(recipe.getResultItem());
		}

	}

}
