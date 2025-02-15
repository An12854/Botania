package vazkii.botania.fabric.integration.emi;

import dev.emi.emi.api.EmiApi;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiCraftingRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.recipe.VanillaEmiRecipeCategories;
import dev.emi.emi.api.stack.Comparison;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;

import net.minecraft.core.Registry;
import net.minecraft.world.item.ItemStack;

import vazkii.botania.api.recipe.BotanicalBreweryRecipe;
import vazkii.botania.api.recipe.ElvenTradeRecipe;
import vazkii.botania.api.recipe.ManaInfusionRecipe;
import vazkii.botania.api.recipe.OrechidRecipe;
import vazkii.botania.api.recipe.PetalApothecaryRecipe;
import vazkii.botania.api.recipe.PureDaisyRecipe;
import vazkii.botania.api.recipe.RunicAltarRecipe;
import vazkii.botania.api.recipe.TerrestrialAgglomerationRecipe;
import vazkii.botania.client.core.handler.CorporeaInputHandler;
import vazkii.botania.common.block.BotaniaBlocks;
import vazkii.botania.common.block.BotaniaFlowerBlocks;
import vazkii.botania.common.crafting.BotaniaRecipeTypes;
import vazkii.botania.common.item.BotaniaItems;
import vazkii.botania.common.item.equipment.tool.terrasteel.TerraShattererItem;
import vazkii.botania.common.item.lens.LensItem;
import vazkii.botania.common.lib.BotaniaTags;

import java.util.List;
import java.util.function.Function;
import java.util.stream.StreamSupport;

import static vazkii.botania.common.lib.ResourceLocationHelper.prefix;

public class BotaniaEmiPlugin implements EmiPlugin {
	public static EmiRecipeCategory PETAL_APOTHECARY = new EmiRecipeCategory(prefix("petal_apothecary"),
			EmiStack.of(BotaniaBlocks.defaultAltar));
	public static EmiRecipeCategory MANA_INFUSION = new EmiRecipeCategory(prefix("mana_infusion"),
			EmiStack.of(BotaniaBlocks.manaPool));
	public static EmiRecipeCategory RUNIC_ALTAR = new EmiRecipeCategory(prefix("runic_altar"),
			EmiStack.of(BotaniaBlocks.runeAltar));
	public static EmiRecipeCategory TERRESTRIAL_AGGLOMERATION = new EmiRecipeCategory(prefix("terrestrial_agglomeration"),
			EmiStack.of(BotaniaBlocks.terraPlate));
	public static EmiRecipeCategory ELVEN_TRADE = new EmiRecipeCategory(prefix("elven_trade"),
			EmiStack.of(BotaniaBlocks.alfPortal));
	public static EmiRecipeCategory BOTANICAL_BREWERY = new EmiRecipeCategory(prefix("botanical_brewery"),
			EmiStack.of(BotaniaBlocks.brewery));
	public static EmiRecipeCategory PURE_DAISY = new EmiRecipeCategory(prefix("pure_daisy"),
			EmiStack.of(BotaniaFlowerBlocks.pureDaisy));
	public static EmiRecipeCategory ORECHID = new EmiRecipeCategory(prefix("orechid"),
			EmiStack.of(BotaniaFlowerBlocks.orechid));
	public static EmiRecipeCategory ORECHID_IGNEM = new EmiRecipeCategory(prefix("orechid_ignem"),
			EmiStack.of(BotaniaFlowerBlocks.orechidIgnem));
	public static EmiRecipeCategory MARIMORPHOSIS = new EmiRecipeCategory(prefix("marimorphosis"),
			EmiStack.of(BotaniaFlowerBlocks.marimorphosis));

	@Override
	public void register(EmiRegistry registry) {
		var old = CorporeaInputHandler.hoveredStackGetter;
		CorporeaInputHandler.hoveredStackGetter = () -> {
			EmiIngredient ingr = EmiApi.getHoveredStack(true).getStack();
			if (ingr.getEmiStacks().size() > 0) {
				var stack = ingr.getEmiStacks().get(0).getItemStack();
				if (!stack.isEmpty()) {
					return stack;
				}
			}
			return old.get();
		};
		registry.addCategory(PETAL_APOTHECARY);
		registry.addCategory(MANA_INFUSION);
		registry.addCategory(RUNIC_ALTAR);
		registry.addCategory(TERRESTRIAL_AGGLOMERATION);
		registry.addCategory(ELVEN_TRADE);
		registry.addCategory(BOTANICAL_BREWERY);
		registry.addCategory(PURE_DAISY);
		registry.addCategory(ORECHID);
		registry.addCategory(ORECHID_IGNEM);
		registry.addCategory(MARIMORPHOSIS);

		registry.addWorkstation(VanillaEmiRecipeCategories.CRAFTING, EmiStack.of(BotaniaItems.craftingHalo));
		registry.addWorkstation(VanillaEmiRecipeCategories.CRAFTING, EmiStack.of(BotaniaItems.autocraftingHalo));

		registry.addWorkstation(PETAL_APOTHECARY, EmiStack.of(BotaniaBlocks.defaultAltar));
		registry.addWorkstation(PETAL_APOTHECARY, EmiStack.of(BotaniaBlocks.desertAltar));
		registry.addWorkstation(PETAL_APOTHECARY, EmiStack.of(BotaniaBlocks.forestAltar));
		registry.addWorkstation(PETAL_APOTHECARY, EmiStack.of(BotaniaBlocks.fungalAltar));
		registry.addWorkstation(PETAL_APOTHECARY, EmiStack.of(BotaniaBlocks.mesaAltar));
		registry.addWorkstation(PETAL_APOTHECARY, EmiStack.of(BotaniaBlocks.mossyAltar));
		registry.addWorkstation(PETAL_APOTHECARY, EmiStack.of(BotaniaBlocks.mountainAltar));
		registry.addWorkstation(PETAL_APOTHECARY, EmiStack.of(BotaniaBlocks.plainsAltar));
		registry.addWorkstation(PETAL_APOTHECARY, EmiStack.of(BotaniaBlocks.swampAltar));
		registry.addWorkstation(PETAL_APOTHECARY, EmiStack.of(BotaniaBlocks.taigaAltar));
		registry.addWorkstation(MANA_INFUSION, EmiStack.of(BotaniaBlocks.manaPool));
		registry.addWorkstation(MANA_INFUSION, EmiStack.of(BotaniaBlocks.dilutedPool));
		registry.addWorkstation(MANA_INFUSION, EmiStack.of(BotaniaBlocks.fabulousPool));
		registry.addWorkstation(RUNIC_ALTAR, EmiStack.of(BotaniaBlocks.runeAltar));
		registry.addWorkstation(TERRESTRIAL_AGGLOMERATION, EmiStack.of(BotaniaBlocks.terraPlate));
		registry.addWorkstation(ELVEN_TRADE, EmiStack.of(BotaniaBlocks.alfPortal));
		registry.addWorkstation(BOTANICAL_BREWERY, EmiStack.of(BotaniaBlocks.brewery));

		registry.addWorkstation(PURE_DAISY, EmiStack.of(BotaniaFlowerBlocks.pureDaisy));
		registry.addWorkstation(PURE_DAISY, EmiStack.of(BotaniaFlowerBlocks.pureDaisyFloating));
		registry.addWorkstation(ORECHID, EmiStack.of(BotaniaFlowerBlocks.orechid));
		registry.addWorkstation(ORECHID, EmiStack.of(BotaniaFlowerBlocks.orechidFloating));
		registry.addWorkstation(ORECHID_IGNEM, EmiStack.of(BotaniaFlowerBlocks.orechidIgnem));
		registry.addWorkstation(ORECHID_IGNEM, EmiStack.of(BotaniaFlowerBlocks.orechidIgnemFloating));
		registry.addWorkstation(MARIMORPHOSIS, EmiStack.of(BotaniaFlowerBlocks.marimorphosis));
		registry.addWorkstation(MARIMORPHOSIS, EmiStack.of(BotaniaFlowerBlocks.marimorphosisFloating));
		registry.addWorkstation(MARIMORPHOSIS, EmiStack.of(BotaniaFlowerBlocks.marimorphosisChibi));
		registry.addWorkstation(MARIMORPHOSIS, EmiStack.of(BotaniaFlowerBlocks.marimorphosisChibiFloating));

		Function<Comparison, Comparison> compareNbt = c -> c.copy().nbt(true).build();
		registry.setDefaultComparison(BotaniaItems.lexicon, compareNbt);
		registry.setDefaultComparison(BotaniaItems.brewFlask, compareNbt);
		registry.setDefaultComparison(BotaniaItems.brewVial, compareNbt);
		registry.setDefaultComparison(BotaniaItems.bloodPendant, compareNbt);
		registry.setDefaultComparison(BotaniaItems.incenseStick, compareNbt);
		// Disables the ability to see the no wings tiara recipe, probably an nbt mismatch
		//registry.setDefaultComparison(BotaniaItems.flightTiara, compareNbt);

		registry.addRecipe(new AncientWillEmiRecipe(EmiStack.of(BotaniaItems.terrasteelHelm), EmiIngredient.of(List.of(
				EmiStack.of(BotaniaItems.ancientWillAhrim),
				EmiStack.of(BotaniaItems.ancientWillDharok),
				EmiStack.of(BotaniaItems.ancientWillGuthan),
				EmiStack.of(BotaniaItems.ancientWillKaril),
				EmiStack.of(BotaniaItems.ancientWillTorag),
				EmiStack.of(BotaniaItems.ancientWillVerac)
		))));

		registry.addRecipe(new CompositeLensEmiRecipe(
				StreamSupport.stream(Registry.ITEM.getOrCreateTag(BotaniaTags.Items.LENS).spliterator(), false)
						.map(ItemStack::new)
						.filter(s -> !((LensItem) s.getItem()).isControlLens(s))
						.filter(s -> ((LensItem) s.getItem()).isCombinable(s))
						.map(EmiStack::of)
						.toList()));

		ItemStack tipped = new ItemStack(BotaniaItems.terraPick);
		TerraShattererItem.setTipped(tipped);
		registry.addRecipe(new EmiCraftingRecipe(List.of(EmiStack.of(BotaniaItems.terraPick),
				EmiStack.of(BotaniaItems.elementiumPick)), EmiStack.of(tipped), null));

		for (PetalApothecaryRecipe recipe : registry.getRecipeManager().getAllRecipesFor(BotaniaRecipeTypes.PETAL_TYPE)) {
			registry.addRecipe(new PetalApothecaryEmiRecipe(recipe));
		}
		for (ManaInfusionRecipe recipe : registry.getRecipeManager().getAllRecipesFor(BotaniaRecipeTypes.MANA_INFUSION_TYPE)) {
			registry.addRecipe(new ManaInfusionEmiRecipe(recipe));
		}
		for (RunicAltarRecipe recipe : registry.getRecipeManager().getAllRecipesFor(BotaniaRecipeTypes.RUNE_TYPE)) {
			registry.addRecipe(new RunicAltarEmiRecipe(recipe));
		}
		for (TerrestrialAgglomerationRecipe recipe : registry.getRecipeManager().getAllRecipesFor(BotaniaRecipeTypes.TERRA_PLATE_TYPE)) {
			registry.addRecipe(new TerrestrialAgglomerationEmiRecipe(recipe));
		}
		for (ElvenTradeRecipe recipe : registry.getRecipeManager().getAllRecipesFor(BotaniaRecipeTypes.ELVEN_TRADE_TYPE)) {
			registry.addRecipe(new ElvenTradeEmiRecipe(recipe));
		}
		List<ItemStack> containers = List.of(BotaniaItems.vial, BotaniaItems.flask, BotaniaItems.incenseStick, BotaniaItems.bloodPendant)
				.stream().map(ItemStack::new).toList();
		for (BotanicalBreweryRecipe recipe : registry.getRecipeManager().getAllRecipesFor(BotaniaRecipeTypes.BREW_TYPE)) {
			for (ItemStack container : containers) {
				if (!recipe.getOutput(container.copy()).isEmpty()) {
					registry.addRecipe(new BotanicalBreweryEmiRecipe(recipe, container));
				}
			}
		}
		for (PureDaisyRecipe recipe : registry.getRecipeManager().getAllRecipesFor(BotaniaRecipeTypes.PURE_DAISY_TYPE)) {
			registry.addRecipe(new PureDaisyEmiRecipe(recipe));
		}

		EmiIngredient flower = EmiStack.of(BotaniaFlowerBlocks.orechid);
		for (OrechidRecipe recipe : registry.getRecipeManager().getAllRecipesFor(BotaniaRecipeTypes.ORECHID_TYPE)) {
			registry.addRecipe(new OrechidEmiRecipe(ORECHID, recipe, flower));
		}
		flower = EmiStack.of(BotaniaFlowerBlocks.orechidIgnem);
		for (OrechidRecipe recipe : registry.getRecipeManager().getAllRecipesFor(BotaniaRecipeTypes.ORECHID_IGNEM_TYPE)) {
			registry.addRecipe(new OrechidEmiRecipe(ORECHID_IGNEM, recipe, flower));
		}
		flower = EmiStack.of(BotaniaFlowerBlocks.marimorphosis);
		for (OrechidRecipe recipe : registry.getRecipeManager().getAllRecipesFor(BotaniaRecipeTypes.MARIMORPHOSIS_TYPE)) {
			registry.addRecipe(new OrechidEmiRecipe(MARIMORPHOSIS, recipe, flower));
		}
	}

	public static int rotateXAround(int x, int y, int cx, int cy, double degrees) {
		double rad = Math.toRadians(degrees);
		return (int) (Math.cos(rad) * (x - cx) - Math.sin(rad) * (y - cy) + cx);
	}

	public static int rotateYAround(int x, int y, int cx, int cy, double degrees) {
		double rad = Math.toRadians(degrees);
		return (int) (Math.sin(rad) * (x - cx) - Math.cos(rad) * (y - cy) + cy);
	}
}
