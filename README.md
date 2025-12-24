### Draconic Evolution Config GUI Integration v1.0.0
Keybinds suck. So let's unify everything to use one keybind: The draconic evolution tool and armor config GUI key! This mod was designed specifically for GTNH.

[Demonstration Video](https://www.youtube.com/watch?v=3MJpmwVm7-U)

### Current Supported Mods and Items
IC2: Night Vision Goggles, Jetpack, Electric Jetpack, Nano Helm, Quantum Helm, Quantum Chest, Quantum Leggings, and Mining Laser.

Gravi Suite: GraviChest Plate, Advanced Jetpack, Advanced Nano Chestplate, Advanced Chainsaw, Advanced Drill, Vajra, and Battery packs.

Gravi Suite Neo: Chainsaw Treecapitator is supported.

Thaumic Boots: All boots speed, jump, and omni modulation values are supported.

EMT: All boots speed and jump boost configurable.

Avaritia: All infinity armor movement stats are controllable as well as thaumcraft node vision.

### Other changes
IC2: Added an NBT tag to Quantum Leggings to toggle quantum sprint without changing the config. Can only be edited in the drac gui.
EMT: Added an NBT tag to Electric Goggles to disable Magic Vision
Avaritia: Added Night Vision to the helmet.

### How to integrate with DECI
DECI contains only two classes of its own that you need to be aware of:

[EnumerableItemConfigField](https://github.com/Drathonix/DEConfig-Integration/blob/main/src/main/java/com/drathonix/deconfigintegration/bridge/EnumerableItemConfigField.java)
* Supports adding fields that draconic evolution cannot handle normally, specifically 'integer booleans' and integer enums
* Can support representing a datatype as another.
* For good examples of this, see the [GraviSuite Mixins](https://github.com/Drathonix/DEConfig-Integration/tree/main/src/main/java/com/drathonix/deconfigintegration/mixins/gravisuite)

[DEConfigurableExt](https://github.com/Drathonix/DEConfig-Integration/blob/main/src/main/java/com/drathonix/deconfigintegration/bridge/DEConfigurableExt.java)
* Exposes an item's NBT to Draconic Evolution's config system. You must also implement DE's IConfigurableItem interface.
* By default, returns the itemstack's entire NBT tag. This is safe, but some mods may have sub NBT compounds specifically for item settings.
* To apply this to other mods, use mixins.

Absolutely feel free to contribute. No one is against better QOL.

### Future Plans
Ultimately the GTNH Dev team would prefer if something like this did not use mixins and although I doubt my mixins will break in the future its obvious that the real issue is with how mods implement gear configuration. Draconic evolution makes configuring its gear very easy in comparison to others, and I figured why reinvent the wheel. Well I'm going to reinvent the wheel next time. GTNH needs a universal API that allows configuring armor to be simple and easy without needing 100 different keybinds, this endeavor will require a giant number of changes to be made to the mods I've mixin'd here as well as a well defined API for devs to hook into. I'll keep maintaining DECI but be aware that DECI may be deprecated in the future.
