package net.digitalpear.pearfection.init.tags;

import net.digitalpear.pearfection.Pearfection;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class PearItemTags {
    public static final TagKey<Item> CALLERY_STEMS = tag("callery_stems");

    public static final TagKey<Item> EATABLE_ON_DISH = compatTag("bountifulfares", "eatable_on_dish");

    private static TagKey<Item> tag(String id) {
        return compatTag(Pearfection.MOD_ID, id);
    }
    private static TagKey<Item> commonTag(String id) {
        return compatTag("c", id);
    }

    private static TagKey<Item> compatTag(String namespace, String id){
        return TagKey.of(RegistryKeys.ITEM, Identifier.of(namespace, id));
    }
}
