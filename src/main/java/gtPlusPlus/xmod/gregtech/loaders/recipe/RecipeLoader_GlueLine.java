package gtPlusPlus.xmod.gregtech.loaders.recipe;

import static gregtech.api.enums.Mods.GalacticraftCore;
import static gregtech.api.enums.Mods.NewHorizonsCoreMod;
import static gregtech.api.recipe.RecipeMaps.assemblerRecipes;
import static gregtech.api.recipe.RecipeMaps.chemicalReactorRecipes;
import static gregtech.api.recipe.RecipeMaps.multiblockChemicalReactorRecipes;
import static gregtech.api.util.GT_RecipeBuilder.MINUTES;
import static gregtech.api.util.GT_RecipeBuilder.SECONDS;
import static gregtech.api.util.GT_RecipeBuilder.TICKS;
import static gregtech.api.util.GT_RecipeConstants.UniversalChemical;
import static gtPlusPlus.api.recipe.GTPPRecipeMaps.chemicalDehydratorRecipes;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import gtPlusPlus.core.item.ModItems;
import gtPlusPlus.core.item.chemistry.AgriculturalChem;
import gtPlusPlus.core.item.chemistry.GenericChem;
import gtPlusPlus.core.lib.CORE;
import gtPlusPlus.core.material.MISC_MATERIALS;
import gtPlusPlus.core.recipe.common.CI;
import gtPlusPlus.core.util.minecraft.FluidUtils;
import gtPlusPlus.core.util.minecraft.ItemUtils;
import gtPlusPlus.core.util.minecraft.MaterialUtils;
import gtPlusPlus.plugin.agrichem.BioRecipes;

public class RecipeLoader_GlueLine {

    public static void generate() {
        createRecipes();
    }

    private static void createRecipes() {
        chemicalPlantRecipes();
        chemicalReactorRecipes();
        dehydratorRecipes();
        distillationTowerRecipes();
        fluidHeaterRecipes();
        mixerRecipes();

        glueUsageRecipes();
    }

    private static void chemicalPlantRecipes() {
        // CO + C3H6O2 = C4H6O3
        CORE.RA.addChemicalPlantRecipe(
            new ItemStack[] {  GT_Utility.getIntegratedCircuit(17), ItemUtils.getSimpleStack(GenericChem.mBlueCatalyst, 0) },
            new FluidStack[] { FluidUtils.getFluidStack("carbonmonoxide", 1000),
                FluidUtils.getFluidStack("methylacetate", 1000), },
            new ItemStack[] {},
            new FluidStack[] { MISC_MATERIALS.ACETIC_ANHYDRIDE.getFluidStack(1000) },
            10 * 20,
            500,
            3);

        CORE.RA.addChemicalPlantRecipe(
            new ItemStack[] {  GT_Utility.getIntegratedCircuit(18) },
            new FluidStack[] { FluidUtils.getFluidStack("aceticacid", 1000), FluidUtils.getFluidStack("chlorine", 1000),
                MISC_MATERIALS.ACETIC_ANHYDRIDE.getFluidStack(1000) },
            new ItemStack[] {},
            new FluidStack[] { MISC_MATERIALS.CHLOROACETIC_MIXTURE.getFluidStack(1000),
                MISC_MATERIALS.ACETIC_ANHYDRIDE.getFluidStack(950) },
            150 * 20,
            1000,
            4);

        // Na2CO3 + NaCN + C2H3O2Cl + 2HCl = C3H3NO2 + 3NaCl + CO2 + H2O
        CORE.RA.addChemicalPlantRecipe(
            new ItemStack[] {  GT_Utility.getIntegratedCircuit(19), ItemUtils.getSimpleStack(AgriculturalChem.mSodiumCarbonate, 6),
                MISC_MATERIALS.SODIUM_CYANIDE.getDust(3) },
            new FluidStack[] { MISC_MATERIALS.CHLOROACETIC_ACID.getFluidStack(1000),
                FluidUtils.getFluidStack("hydrochloricacid_gt5u", 2000) },
            new ItemStack[] { MISC_MATERIALS.CYANOACETIC_ACID.getDust(9), Materials.Salt.getDust(6) },
            new FluidStack[] { Materials.CarbonDioxide.getGas(1000), GT_ModHandler.getWater(1000) },
            20 * 20,
            1000,
            4);

        // CuSO4 + 5C3H3NO2 + 5C2H6O = CuSO4·5(H2O) + 5C5H7NO2
        CORE.RA.addChemicalPlantRecipe(
            new ItemStack[] {  GT_Utility.getIntegratedCircuit(20), ItemUtils.getSimpleStack(GenericChem.mSolidAcidCatalyst, 0),
                MISC_MATERIALS.COPPER_SULFATE.getDust(6), MISC_MATERIALS.CYANOACETIC_ACID.getDust(45) },
            new FluidStack[] { Materials.Ethanol.getFluid(5000) },
            new ItemStack[] { MISC_MATERIALS.COPPER_SULFATE_HYDRATED.getDust(11) },
            new FluidStack[] { MISC_MATERIALS.ETHYL_CYANOACETATE.getFluidStack(5000) },
            500 * 20,
            6000,
            5);

        // C3H3NO2 + C2H6O = C5H7NO2 + H2O
        CORE.RA.addChemicalPlantRecipe(
            new ItemStack[] {  GT_Utility.getIntegratedCircuit(21), MISC_MATERIALS.CYANOACETIC_ACID.getDust(9) },
            new FluidStack[] { Materials.Ethanol.getFluid(1000) },
            new ItemStack[] {},
            new FluidStack[] { MISC_MATERIALS.ETHYL_CYANOACETATE.getFluidStack(1000) },
            1000 * 20,
            6000,
            5);

        BioRecipes.mFormaldehyde = FluidUtils.getFluidStack("fluid.formaldehyde", 1)
            .getFluid();

        CORE.RA.addChemicalPlantRecipe(
            new ItemStack[] {  GT_Utility.getIntegratedCircuit(22), ItemUtils.getSimpleStack(GenericChem.mSolidAcidCatalyst, 0) },
            new FluidStack[] { MISC_MATERIALS.ETHYL_CYANOACETATE.getFluidStack(100),
                FluidUtils.getFluidStack(BioRecipes.mFormaldehyde, 100) },
            new ItemStack[] {},
            new FluidStack[] { MISC_MATERIALS.CYANOACRYLATE_POLYMER.getFluidStack(100), FluidUtils.getWater(1000) },
            10 * 20,
            8000,
            5);

        // CH4 + NH3 + 3O = HCN + 3H2O
        CORE.RA.addChemicalPlantRecipe(
            new ItemStack[] {  GT_Utility.getIntegratedCircuit(23), ItemUtils.getSimpleStack(GenericChem.mPinkCatalyst, 0) },
            new FluidStack[] { FluidUtils.getFluidStack("methane", 2000), FluidUtils.getFluidStack("ammonia", 2000),
                FluidUtils.getFluidStack("oxygen", 6000) },
            new ItemStack[] {},
            new FluidStack[] { MISC_MATERIALS.HYDROGEN_CYANIDE.getFluidStack(2000), FluidUtils.getWater(6000) },
            10 * 20,
            500,
            3);
    }

    private static void chemicalReactorRecipes() {
        // NaOH + HCN = NaCN + H2O
        GT_Values.RA.stdBuilder()
            .itemInputs(
                 GT_Utility.getIntegratedCircuit(17),
                ItemUtils.getItemStackOfAmountFromOreDict("dustSodiumHydroxide", 3)
            )
            .itemOutputs(MISC_MATERIALS.SODIUM_CYANIDE.getDust(3))
            .fluidInputs(MISC_MATERIALS.HYDROGEN_CYANIDE.getFluidStack(1000))
            .fluidOutputs(FluidUtils.getWater(1000))
            .duration(10*SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        // #UniversalChemical recipemap won't generate LCR recipe if config >= 10
        GT_Values.RA.stdBuilder()
            .itemInputs(
                 GT_Utility.getIntegratedCircuit(17),
                ItemUtils.getItemStackOfAmountFromOreDict("dustSodiumHydroxide", 3)
            )
            .itemOutputs(MISC_MATERIALS.SODIUM_CYANIDE.getDust(3))
            .fluidInputs(MISC_MATERIALS.HYDROGEN_CYANIDE.getFluidStack(1000))
            .fluidOutputs(FluidUtils.getWater(1000))
            .duration(10*SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(multiblockChemicalReactorRecipes);


        // CaCO3 + 2HCl = CaCl2 + CO2 + H2O
        GT_Values.RA.stdBuilder()
            .itemInputs(ItemUtils.getSimpleStack(ModItems.dustCalciumCarbonate, 5), GT_Utility.getIntegratedCircuit(8))
            .itemOutputs(ItemUtils.getItemStackFromFQRN("bartworks:gt.bwMetaGenerateddust:63", 3))
            .fluidInputs(Materials.HydrochloricAcid.getFluid(2000L))
            .fluidOutputs(Materials.CarbonDioxide.getGas(1000))
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        // Cu + 2H2SO4 = CuSO4 + SO2 + 2H2O
        // SO2 + 2H2O -> diluted sulfuric acid
        GT_Values.RA.stdBuilder()
            .itemInputs(
                 GT_Utility.getIntegratedCircuit(19),
                ItemUtils.getItemStackOfAmountFromOreDict("dustCopper", 1)
            )
            .itemOutputs(MISC_MATERIALS.COPPER_SULFATE.getDust(6))
            .fluidInputs(FluidUtils.getFluidStack("sulfuricacid", 2000))
            .fluidOutputs(FluidUtils.getFluidStack("dilutedsulfuricacid", 1000))
            .duration(5*SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);


        // #UniversalChemical won't generate LCR recipe if config >= 10
        GT_Values.RA.stdBuilder()
            .itemInputs(
                 GT_Utility.getIntegratedCircuit(19),
                ItemUtils.getItemStackOfAmountFromOreDict("dustCopper", 1)
            )
            .itemOutputs(MISC_MATERIALS.COPPER_SULFATE.getDust(6))
            .fluidInputs(FluidUtils.getFluidStack("sulfuricacid", 2000))
            .fluidOutputs(FluidUtils.getFluidStack("dilutedsulfuricacid", 1000))
            .duration(5*SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(multiblockChemicalReactorRecipes);
    }

    private static void dehydratorRecipes() {
        GT_Values.RA.stdBuilder()
            .itemInputs( MISC_MATERIALS.COPPER_SULFATE_HYDRATED.getDust(11))
            .itemOutputs( MISC_MATERIALS.COPPER_SULFATE.getDust(6) )
            .fluidOutputs(GT_ModHandler.getWater(5000))
            .eut(10)
            .duration(5*MINUTES)
            .addTo(chemicalDehydratorRecipes);
    }

    private static void distillationTowerRecipes() {
        GT_Values.RA.addDistillationTowerRecipe(
            MISC_MATERIALS.CHLOROACETIC_MIXTURE.getFluidStack(1000),
            new FluidStack[] { MISC_MATERIALS.CHLOROACETIC_ACID.getFluidStack(100),
                MISC_MATERIALS.DICHLOROACETIC_ACID.getFluidStack(450),
                MISC_MATERIALS.TRICHLOROACETIC_ACID.getFluidStack(450) },
            null,
            4 * 20,
            (int) TierEU.RECIPE_IV);
    }

    private static void fluidHeaterRecipes() {

        CORE.RA.addFluidHeaterRecipe(
             GT_Utility.getIntegratedCircuit(16),
            MISC_MATERIALS.CYANOACRYLATE_POLYMER.getFluidStack(100),
            MISC_MATERIALS.ETHYL_CYANOACRYLATE.getFluidStack(100),
            30 * 30,
            500);
    }

    private static void mixerRecipes() {
        GT_Values.RA.addMixerRecipe(
             GT_Utility.getIntegratedCircuit(1),
            MISC_MATERIALS.DICHLOROACETIC_ACID.getCell(1),
            null,
            null,
            MISC_MATERIALS.TRICHLOROACETIC_ACID.getFluidStack(1000),
            MISC_MATERIALS.CHLOROACETIC_MIXTURE.getFluidStack(2000),
            CI.emptyCells(1),
            100,
            100);

        GT_Values.RA.addMixerRecipe(
            ItemUtils.getItemStackOfAmountFromOreDict("cellSulfurTrioxide", 1),
             GT_Utility.getIntegratedCircuit(2),
            null,
            null,
            FluidUtils.getFluidStack("sulfuricacid", 1000),
            MISC_MATERIALS.SOLID_ACID_MIXTURE.getFluidStack(1000),
            CI.emptyCells(1),
            100,
            40);
    }

    private static void glueUsageRecipes() {
        // Braintech Tape recipe, PBI and superglue make 16 tape at once
        GT_Values.RA.stdBuilder()
            .itemInputs(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Polybenzimidazole, 1L), GT_ModHandler.getIC2Item("carbonMesh", 1L),  GT_Utility.getIntegratedCircuit(10) }
            )
            .itemOutputs(
                ItemList.Duct_Tape.get(16L)
            )
            .fluidInputs(
                MISC_MATERIALS.ETHYL_CYANOACRYLATE.getFluidStack(100)
            )
            .duration(6 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(assemblerRecipes);
        // Maintenance Hatch recipe, using Braintech Tape
        GT_Values.RA.stdBuilder()
            .itemInputs(
                new ItemStack[] { ItemList.Hull_LV.get(1), ItemList.Duct_Tape.get(1),  GT_Utility.getIntegratedCircuit(1) }
            )
            .itemOutputs(
                ItemList.Hatch_Maintenance.get(1)
            )
            .fluidInputs(
                MISC_MATERIALS.ETHYL_CYANOACRYLATE.getFluidStack(100)
            )
            .duration(6 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(assemblerRecipes);
        // Graphene recipes from later wafer tiers, using superglue instead of the bronze age glue
        GT_Values.RA.stdBuilder()
            .itemInputs(
                new ItemStack[] { ItemUtils.getItemStackOfAmountFromOreDict("dustGraphite", 64), ItemList.Circuit_Silicon_Wafer4.get(2L),  GT_Utility.getIntegratedCircuit(2) }
            )
            .itemOutputs(
                ItemUtils.getItemStackOfAmountFromOreDict("dustGraphene", 64)
            )
            .fluidInputs(
                MISC_MATERIALS.ETHYL_CYANOACRYLATE.getFluidStack(500)
            )
            .duration(20 * SECONDS)
            .eut(TierEU.RECIPE_LuV)
            .addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
            .itemInputs(
                new ItemStack[] { ItemUtils.getItemStackOfAmountFromOreDict("dustGraphite", 64), ItemList.Circuit_Silicon_Wafer5.get(1L),  GT_Utility.getIntegratedCircuit(2) }
            )
            .itemOutputs(
                ItemUtils.getItemStackOfAmountFromOreDict("dustGraphene", 64)
            )
            .fluidInputs(
                MISC_MATERIALS.ETHYL_CYANOACRYLATE.getFluidStack(250)
            )
            .duration(4 * SECONDS)
            .eut(TierEU.RECIPE_ZPM)
            .addTo(assemblerRecipes);


        GT_Values.RA.addMixerRecipe(
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Sulfur, 1L),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Lithium, 1L),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 4L),
            GT_Values.NI,
            GT_Values.NI,
            GT_Utility.getIntegratedCircuit(1),
            MISC_MATERIALS.ETHYL_CYANOACRYLATE.getFluidStack(100),
            null,
            ItemList.SFMixture.get(32),
            1600,
            16);

        GT_Values.RA.addMixerRecipe(
            ItemList.GelledToluene.get(1),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Aluminium, 1L),
            GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Polybenzimidazole, 1L),
            GT_Values.NI,
            GT_Values.NI,
            GT_Utility.getIntegratedCircuit(1),
            MISC_MATERIALS.ETHYL_CYANOACRYLATE.getFluidStack(100),
            null,
            ItemList.SFMixture.get(64),
            1600,
            16);
        GT_Values.RA.stdBuilder()
            .itemInputs(
                GT_OreDictUnificator.get(OrePrefixes.foil, Materials.PolyvinylChloride, 8),
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Paper, 32)
            )
            .itemOutputs(
                new ItemStack(Items.book, 64, 0)
            )
            .fluidInputs(
                MISC_MATERIALS.ETHYL_CYANOACRYLATE.getFluidStack(200)
            )
            .duration(1 * SECONDS + 12 * TICKS)
            .eut(8)
            .addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
            .itemInputs(
                GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Naquadah, 2),
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.RedSteel, 18)
            )
            .itemOutputs(
                ItemUtils.getItemStackFromFQRN("gregtech:gt.metaitem.01:32505", 1)
            )
            .fluidInputs(
                MISC_MATERIALS.ETHYL_CYANOACRYLATE.getFluidStack(144)
            )
            .duration(15 * SECONDS)
            .eut(TierEU.RECIPE_IV)
            .addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
            .itemInputs(
                GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.NaquadahAlloy, 2),
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Europium, 6)
            )
            .itemOutputs(
                ItemUtils.getItemStackFromFQRN("gregtech:gt.metaitem.01:32506", 1)
            )
            .fluidInputs(
                MISC_MATERIALS.ETHYL_CYANOACRYLATE.getFluidStack(288)
            )
            .duration(10 * SECONDS)
            .eut(TierEU.RECIPE_LuV)
            .addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
            .itemInputs(
                GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.ElectrumFlux, 2),
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Americium, 18)
            )
            .itemOutputs(
                ItemUtils.getItemStackFromFQRN("gregtech:gt.metaitem.01:32507", 1)
            )
            .fluidInputs(
                MISC_MATERIALS.ETHYL_CYANOACRYLATE.getFluidStack(576)
            )
            .duration(15 * SECONDS)
            .eut(TierEU.RECIPE_ZPM)
            .addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
            .itemInputs(
                GT_OreDictUnificator.get(OrePrefixes.cableGt02, Materials.ElectrumFlux, 2),
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Naquadah, 24)
            )
            .itemOutputs(
                ItemUtils.getItemStackFromFQRN("gregtech:gt.metaitem.01:32561", 1)
            )
            .fluidInputs(
                MISC_MATERIALS.ETHYL_CYANOACRYLATE.getFluidStack(1152)
            )
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_UV)
            .addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
            .itemInputs(
                GT_OreDictUnificator.get(OrePrefixes.cableGt04, Materials.ElectrumFlux, 2),
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.NaquadahEnriched, 36)
            )
            .itemOutputs(
                ItemUtils.getItemStackFromFQRN("gregtech:gt.metaitem.01:32562", 1)
            )
            .fluidInputs(
                MISC_MATERIALS.ETHYL_CYANOACRYLATE.getFluidStack(2304)
            )
            .duration(10 * SECONDS)
            .eut(TierEU.RECIPE_UHV)
            .addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
            .itemInputs(
                GT_OreDictUnificator.get(OrePrefixes.cableGt08, Materials.ElectrumFlux, 2),
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.NaquadahAlloy, 48)
            )
            .itemOutputs(
                ItemUtils.getItemStackFromFQRN("gregtech:gt.metaitem.01:32563", 1)
            )
            .fluidInputs(
                MISC_MATERIALS.ETHYL_CYANOACRYLATE.getFluidStack(4608)
            )
            .duration(15 * SECONDS)
            .eut(TierEU.RECIPE_UHV)
            .addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
            .itemInputs(
                GT_OreDictUnificator.get(OrePrefixes.wireGt16, Materials.SuperconductorUHV, 2),
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Neutronium, 56)
            )
            .itemOutputs(
                ItemUtils.getItemStackFromFQRN("gregtech:gt.metaitem.01:32564", 1)
            )
            .fluidInputs(
                MISC_MATERIALS.ETHYL_CYANOACRYLATE.getFluidStack(9216)
            )
            .duration(30 * SECONDS)
            .eut(TierEU.RECIPE_UHV)
            .addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
            .itemInputs(
                GT_OreDictUnificator.get(OrePrefixes.wireGt16, Materials.SuperconductorUHV, 2),
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.DraconiumAwakened, 64)
            )
            .itemOutputs(
                ItemUtils.getItemStackFromFQRN("gregtech:gt.metaitem.01:32565", 1)
            )
            .fluidInputs(
                MISC_MATERIALS.ETHYL_CYANOACRYLATE.getFluidStack(18432)
            )
            .duration(60 * SECONDS)
            .eut(TierEU.RECIPE_UHV)
            .addTo(assemblerRecipes);

        if (NewHorizonsCoreMod.isModLoaded() && GalacticraftCore.isModLoaded()) {
            GT_Values.RA.stdBuilder()
                .itemInputs(
                    new ItemStack[] { ItemUtils.getItemStackFromFQRN("GalacticraftMars:item.itemBasicAsteroids:7", 1), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Titanium, 8), ItemUtils.getItemStackFromFQRN("dreamcraft:item.TungstenString", 8),  GT_Utility.getIntegratedCircuit(1) }
                )
                .itemOutputs(
                    ItemUtils.getItemStackFromFQRN("GalaxySpace:item.ThermalClothT2", 1)
                )
                .fluidInputs(
                    MISC_MATERIALS.ETHYL_CYANOACRYLATE.getFluidStack(576)
                )
                .duration(30 * SECONDS)
                .eut(1024)
                .addTo(assemblerRecipes);

        }
    }
}
