package com.drathonix.deconfigintegration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.drathonix.deconfigintegration.proxy.CommonProxy;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;

@Mod(
    modid = DEConfigIntegration.MODID,
    version = Tags.VERSION,
    name = "Draconic Evolution Config Integration",
    acceptedMinecraftVersions = "[1.7.10]")
public class DEConfigIntegration {

    public static final String MODID = "deconfigintegration";
    public static final Logger LOG = LogManager.getLogger(MODID);
    public static final boolean gravisuiteneo = Loader.isModLoaded("gravisuiteneo");
    public static final boolean thaumcraft = Loader.isModLoaded("Thaumcraft");

    @SidedProxy(
        clientSide = "com.drathonix.deconfigintegration.proxy.ClientProxy",
        serverSide = "com.drathonix.deconfigintegration.proxy.CommonProxy")
    public static CommonProxy proxy;
}
