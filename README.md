### Draconic Evolution Config GUI Integration
Keybinds suck. So let's unify everything to use one keybind: The draconic evolution tool and armor config GUI key!

### Current Supported Mods and Items
IC2: Night Vision Goggles, Nano Helm, Quantum Helm, Quantum Chest, Quantum Leggings, and Mining Laser.

Gravi Suite: GraviChest Plate, Advanced Chainsaw, Advanced Drill, Vajra, and Battery packs.

### How to integrate with DECI
DECI contains only two classes of its own that you need to be aware of:

[AdvancedItemConfigField](https://github.com/Drathonix/DEConfig-Integration/blob/main/src/main/java/com/drathonix/deconfigintegration/bridge/AdvancedItemConfigField.java)
* Supports adding fields that draconic evolution cannot handle normally.
* Can support representing a datatype as another.
* For good examples of this, see the [GraviSuite Mixins](https://github.com/Drathonix/DEConfig-Integration/tree/main/src/main/java/com/drathonix/deconfigintegration/mixins/gravisuite)
[DEConfigurableExt](https://github.com/Drathonix/DEConfig-Integration/blob/main/src/main/java/com/drathonix/deconfigintegration/bridge/DEConfigurableExt.java)
* Exposes an item to Draconic Evolution's config system.
* By default, returns the itemstack's entire NBT tag. This is safe, but some mods may have sub NBT compounds specifically for item settings.
