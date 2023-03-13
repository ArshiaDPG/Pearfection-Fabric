package net.digitalpear.pearfection.init.tags;

import net.digitalpear.pearfection.Pearfection;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class PearItemTags {
    public static final TagKey<Item> CALLERY_STEMS = of("callery_stems");

    private static TagKey<Item> of(String id) {
        return TagKey.of(RegistryKeys.ITEM, new Identifier(Pearfection.MOD_ID, id));
    }
}
