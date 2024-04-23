### Draconic Evolution Config GUI Integration
Keybinds suck. So let's unify everything to use one keybind: The draconic evolution tool and armor config GUI key! This mod was designed specifically for GTNH.

### Current Supported Mods and Items
IC2: Night Vision Goggles, Jetpack, Electric Jetpack, Nano Helm, Quantum Helm, Quantum Chest, Quantum Leggings, and Mining Laser.

Gravi Suite: GraviChest Plate, Advanced Jetpack, Advanced Nano Chestplate, Advanced Chainsaw, Advanced Drill, Vajra, and Battery packs.

Gravi Suite Neo: Chainsaw Treecapitator is supported.

Thaumic Boots: All boots speed, jump, and omni modulation values are supported.

EMT: All boots speed and jump boost configurable.

### Future Targets
EMT and Thaumic boots: Will implement speed and jump modulation and magic vision toggles.

### Other changes
IC2: Added an NBT tag to Quantum Leggings to toggle quantum sprint without changing the config. Can only be edited in the drac gui.

### How to integrate with DECI
DECI contains only two classes of its own that you need to be aware of:

[AdvancedItemConfigField](https://github.com/Drathonix/DEConfig-Integration/blob/main/src/main/java/com/drathonix/deconfigintegration/bridge/AdvancedItemConfigField.java)
* Supports adding fields that draconic evolution cannot handle normally, specifically 'integer booleans' and integer enums
* Can support representing a datatype as another.
* For good examples of this, see the [GraviSuite Mixins](https://github.com/Drathonix/DEConfig-Integration/tree/main/src/main/java/com/drathonix/deconfigintegration/mixins/gravisuite)

[DEConfigurableExt](https://github.com/Drathonix/DEConfig-Integration/blob/main/src/main/java/com/drathonix/deconfigintegration/bridge/DEConfigurableExt.java)
* Exposes an item's NBT to Draconic Evolution's config system. You must also implement DE's IConfigurableItem interface.
* By default, returns the itemstack's entire NBT tag. This is safe, but some mods may have sub NBT compounds specifically for item settings.
* To apply this to other mods, use mixins.

Absolutely feel free to contribute. No one is against better QOL.
