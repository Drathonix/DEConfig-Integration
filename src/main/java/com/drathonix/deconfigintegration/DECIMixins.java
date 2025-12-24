package com.drathonix.deconfigintegration;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.Level;

import com.gtnewhorizon.gtnhmixins.ILateMixinLoader;
import com.gtnewhorizon.gtnhmixins.LateMixin;

import cpw.mods.fml.common.FMLCommonHandler;

@LateMixin
public class DECIMixins implements ILateMixinLoader {

    @Override
    public String getMixinConfig() {
        return "mixins.deconfigintegration.late.json";
    }

    @Override
    public List<String> getMixins(Set<String> loadedMods) {
        try {
            List<String> mixins = new ArrayList<>();
            if (loadedMods.contains("DraconicEvolution")) {
                mixins.add("draconicevolution.MixinProfileHelper");
                if (FMLCommonHandler.instance()
                    .getSide()
                    .isClient()) {
                    mixins.add("draconicevolution.MixinComponentFieldButton");
                }

                if (loadedMods.contains("IC2")) {
                    mixins.add("ic2.MixinItemArmorJetpack");
                    mixins.add("ic2.MixinItemToolMiningLaser");
                    mixins.add("ic2.MixinItemArmorNanoSuit");
                    mixins.add("ic2.MixinItemArmorNightvisionGoggles");
                    mixins.add("ic2.MixinItemArmorQuantumSuit");
                }
                if (loadedMods.contains("GraviSuite")) {
                    mixins.add("gravisuite.MixinItemAdvancedJetpack");
                    mixins.add("gravisuite.MixinItemAdvancedLappack");
                    mixins.add("gravisuite.MixinItemAdvChainsaw");
                    mixins.add("gravisuite.MixinItemAdvDDrill");
                    mixins.add("gravisuite.MixinItemGraviChestPlate");
                    mixins.add("gravisuite.MixinItemVajra");
                }
                if (loadedMods.contains("thaumicboots")) {
                    mixins.add("thaumicboots.MixinItemBoots");
                }

                if (loadedMods.contains("EMT")) {
                    mixins.add("emt.MixinItemElectricBootsTraveller");
                    mixins.add("emt.MixinItemElectricGoggles");
                    mixins.add("emt.MixinItemNanoGoggles");
                    mixins.add("emt.MixinItemQuantumGoggles");
                }

                if (loadedMods.contains("Avaritia")) {
                    mixins.add("avaritia.MixinItemArmorInfinity");
                    mixins.add("avaritia.MixinItemArmorInfinityAbilityHandler");
                    if (loadedMods.contains("Thaumcraft")) {
                        mixins.add("avaritia.MixinItemArmorInfinityThaumcraft");
                    }
                }

                if (loadedMods.contains("WitchingGadgets")) {
                    mixins.add("witchinggadgets.MixinItemPrimordialArmor");
                }
            } else {
                DEConfigIntegration.LOG.log(Level.WARN, "Draconic evolution is not present. DECI has been disabled.");
            }
            return mixins;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
