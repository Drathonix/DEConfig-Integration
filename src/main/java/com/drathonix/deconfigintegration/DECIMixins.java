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
            System.out.println("Detected mods: " + loadedMods);
            if (loadedMods.contains("DraconicEvolution")) {
                mixins.add("draconicevolution.MixinProfileHelper");
                if (FMLCommonHandler.instance()
                    .getSide()
                    .isClient()) {
                    mixins.add("draconicevolution.MixinComponentFieldButton");
                }

                if (loadedMods.contains("IC2")) {
                    mixins.add("ic2.MixinIC2JetpackDEIntegration");
                    mixins.add("ic2.MixinIC2MiningLaserDEIntegration");
                    mixins.add("ic2.MixinIC2NanoSuitDEIntegration");
                    mixins.add("ic2.MixinIC2NightVisionGogglesDEIntegration");
                    mixins.add("ic2.MixinIC2QuantumDEIntegration");
                }
                if (loadedMods.contains("GraviSuite")) {
                    mixins.add("gravisuite.MixinGraviAdvancedJetPackDEIntegration");
                    mixins.add("gravisuite.MixinGraviAdvancedLappackDEIntegration");
                    mixins.add("gravisuite.MixinGraviAdvChainsawDEIntegration");
                    mixins.add("gravisuite.MixinGraviAdvDrillDEIntegration");
                    mixins.add("gravisuite.MixinGraviChestPlateDEIntegration");
                    mixins.add("gravisuite.MixinGraviVajraDEIntegration");
                }
            } else {
                DEConfigIntegration.LOG.log(Level.WARN, "Draconic evolution is not present. DECI has been disabled.");
            }
            System.out.println("Loading: " + mixins);
            return mixins;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
