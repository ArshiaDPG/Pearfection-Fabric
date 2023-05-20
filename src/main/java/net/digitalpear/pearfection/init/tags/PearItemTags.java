package net.digitalpear.pearfection.init.tags;

import net.digitalpear.pearfection.Pearfection;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class PearItemTags {
    public static final TagKey<Item> CALLERY_STEMS = tag("callery_stems");
    public static final TagKey<Item> C_FRUITS_PEARS = commonTag("fruits/pears");

    private static TagKey<Item> tag(String id) {
        return TagKey.of(RegistryKeys.ITEM, new Identifier(Pearfection.MOD_ID, id));
    }
    private static TagKey<Item> commonTag(String id) {
        return TagKey.of(RegistryKeys.ITEM, new Identifier("c", id));
    }
}
