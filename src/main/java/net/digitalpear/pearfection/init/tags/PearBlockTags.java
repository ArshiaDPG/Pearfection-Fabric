package net.digitalpear.pearfection.init.tags;

import net.digitalpear.pearfection.Pearfection;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class PearBlockTags {
    public static final TagKey<Block> CALLERY_STEMS = tag("callery_stems");
    public static final TagKey<Block> HUGE_PEAR_CANNOT_REPLACE = tag("huge_pear_cannot_replace");
    public static final TagKey<Block> PEAR_GROWABLE_ON = tag("pear_growable_on");
    public static final TagKey<Block> ENORMOUS_PEAR_GROWABLE_ON = tag("enormous_pear_growable_on");


    private static TagKey<Block> tag(String id) {
        return TagKey.of(RegistryKeys.BLOCK, new Identifier(Pearfection.MOD_ID, id));
    }
    private static TagKey<Block> commonTag(String mod,String id) {
        return TagKey.of(RegistryKeys.BLOCK, new Identifier(mod, id));
    }
}
