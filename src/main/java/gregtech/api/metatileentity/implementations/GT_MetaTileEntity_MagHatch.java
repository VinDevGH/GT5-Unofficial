package gregtech.api.metatileentity.implementations;

import static gregtech.api.enums.GT_Values.AuthorFourIsTheNumber;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_EMS_HOUSING;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.ForgeDirection;

import org.apache.commons.lang3.ArrayUtils;

import com.gtnewhorizons.modularui.api.screen.ModularWindow;
import com.gtnewhorizons.modularui.api.screen.UIBuildContext;
import com.gtnewhorizons.modularui.common.widget.SlotWidget;

import gregtech.api.gui.modularui.GT_UIInfos;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.render.TextureFactory;
import gregtech.common.tileentities.machines.multi.GT_MetaTileEntity_IndustrialElectromagneticSeparator;

public class GT_MetaTileEntity_MagHatch extends GT_MetaTileEntity_Hatch {

    public GT_MetaTileEntity_MagHatch(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional, 5, 1, "Holds electromagnet for the Magnetic Flux Exhibitor");
    }

    public GT_MetaTileEntity_MagHatch(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, 1, aDescription[0], aTextures);
    }

    @Override
    public boolean isSimpleMachine() {
        return true;
    }

    @Override
    public boolean isFacingValid(ForgeDirection facing) {
        return true;
    }

    @Override
    public String[] getDescription() {
        return ArrayUtils.addAll(this.mDescriptionArray, AuthorFourIsTheNumber);
    }

    @Override
    public boolean useModularUI() {
        return true;
    }

    @Override
    public void addUIWidgets(ModularWindow.Builder builder, UIBuildContext buildContext) {
        builder.widget(
            new SlotWidget(inventoryHandler, 0)
                .setFilter(GT_MetaTileEntity_IndustrialElectromagneticSeparator::isValidElectromagnet)
                .setAccess(true, true)
                .setPos(79, 34));
    }

    @Override
    public boolean isAccessAllowed(EntityPlayer aPlayer) {
        return true;
    }

    @Override
    public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer) {
        GT_UIInfos.openGTTileEntityUI(aBaseMetaTileEntity, aPlayer);
        return true;
    }

    @Override
    public ITexture[] getTexturesActive(ITexture aBaseTexture) {
        return new ITexture[] { aBaseTexture, TextureFactory.of(OVERLAY_EMS_HOUSING) };
    }

    @Override
    public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
        return new ITexture[] { aBaseTexture, TextureFactory.of(OVERLAY_EMS_HOUSING) };
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_MagHatch(mName, mTier, mDescriptionArray, mTextures);
    }
}
