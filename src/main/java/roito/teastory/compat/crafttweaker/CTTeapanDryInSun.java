package roito.teastory.compat.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import roito.teastory.TeaStory;
import roito.teastory.api.recipe.ITeaMakingRecipe;
import roito.teastory.api.recipe.TeaMakingRecipe;
import roito.teastory.common.RecipeRegister;
import roito.teastory.helper.CraftTweakerHelper;
import roito.teastory.helper.LogHelper;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import javax.annotation.Nonnull;
import java.util.List;

@ZenClass("mods.teastory.Teapan.DryInSun")
@ZenRegister
public class CTTeapanDryInSun
{
	@ZenMethod
	public static void add(IIngredient input, IItemStack output)
	{
		ItemStack out = CraftTweakerMC.getItemStack(output);
		RecipeRegister.actions.add(new Addition(CraftTweakerHelper.getItemStacks(input), out));
	}

	private static final class Addition implements IAction
	{
		private final NonNullList<ItemStack> inputs;
		private final ItemStack output;

		private Addition(NonNullList<ItemStack> inputs, ItemStack output)
		{
			this.inputs = inputs;
			this.output = output;
		}

		@Override
		public void apply()
		{
			RecipeRegister.managerTeapanInSun.add(new TeaMakingRecipe(inputs, output));
		}

		@Override
		public String describe()
		{
			return String.format("Add Teastory Teapan dry-in-sun recipe: input %s -> output %s", inputs, output);
		}
	}

	@ZenMethod
	public static void remove(IIngredient input, IItemStack output)
	{
		ItemStack out = CraftTweakerMC.getItemStack(output);
		RecipeRegister.actions.add(new Removal(CraftTweakerHelper.getItemStacks(input), out));
	}

	private static final class Removal implements IAction
	{
		private final NonNullList<ItemStack> inputs;
		private final ItemStack output;

		private Removal(NonNullList<ItemStack> inputs, ItemStack output)
		{
			this.inputs = inputs;
			this.output = output;
		}

		@Override
		public void apply()
		{
			RecipeRegister.managerTeapanInSun.remove(inputs, output);
		}

		@Override
		public String describe()
		{
			return null;
		}
	}

	@ZenMethod
	public static void removeAll()
	{
		RecipeRegister.actions.add(new Clear());
	}

	private static final class Clear implements IAction
	{
		@Override
		public void apply()
		{
			RecipeRegister.managerTeapanInSun.removeAll();
		}

		@Override
		public String describe()
		{
			return "Removing all dry-in-sun recipes from Teapan";
		}
	}
}
