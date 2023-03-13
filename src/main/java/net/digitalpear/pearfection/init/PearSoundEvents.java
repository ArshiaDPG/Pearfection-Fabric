package net.digitalpear.pearfection.init;

import net.digitalpear.pearfection.Pearfection;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class PearSoundEvents {
    public static final SoundEvent BLOCK_PEAR_BLOCK_BREAK = register("block.pear.break");
    public static final SoundEvent BLOCK_PEAR_BLOCK_STEP = register("block.pear.step");
    public static final SoundEvent BLOCK_PEAR_BLOCK_HIT = register("block.pear.hit");
    public static final SoundEvent BLOCK_PEAR_BLOCK_PLACE = register("block.pear.place");

    public static final BlockSoundGroup BLOCK_SOUND_PEAR = new BlockSoundGroup(2.0F, 1.0F, BLOCK_PEAR_BLOCK_BREAK, BLOCK_PEAR_BLOCK_STEP,
            BLOCK_PEAR_BLOCK_PLACE, BLOCK_PEAR_BLOCK_HIT, BLOCK_PEAR_BLOCK_HIT);



    private static SoundEvent register(String id) {
        return Registry.register(Registries.SOUND_EVENT, new Identifier(Pearfection.MOD_ID, id), SoundEvent.of(new Identifier(Pearfection.MOD_ID, id)));
    }
}
