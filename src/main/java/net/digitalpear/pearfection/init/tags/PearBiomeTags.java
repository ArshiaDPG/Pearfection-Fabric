package net.digitalpear.pearfection.init.tags;

import net.digitalpear.pearfection.Pearfection;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public class PearBiomeTags {

    private static TagKey<Biome> of(String id) {
        return TagKey.of(RegistryKeys.BIOME, new Identifier(Pearfection.MOD_ID, id));
    }

}
