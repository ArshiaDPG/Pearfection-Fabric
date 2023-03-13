package net.digitalpear.pearfection.init.tags;

import net.digitalpear.pearfection.Pearfection;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class PearBlockTags {
    public static final TagKey<Block> CALLERY_STEMS = of("callery_stems");
    public static final TagKey<Block> HUGE_PEAR_CANNOT_REPLACE = of("huge_pear_cannot_replace");
    public static final TagKey<Block> PEAR_GROWABLE_ON = of("pear_growable_on");
    public static final TagKey<Block> HOLLOWED_LOGS = of("wilderwild","hollowed_logs");
    public static final TagKey<Block> TERMITE_BREAKABLE = of("wilderwild","termite_breakable");

    private static TagKey<Block> of(String id) {
        return TagKey.of(RegistryKeys.BLOCK, new Identifier(Pearfection.MOD_ID, id));
    }
    private static TagKey<Block> of(String mod,String id) {
        return TagKey.of(RegistryKeys.BLOCK, new Identifier(mod, id));
    }
}
