package net.digitalpear.pearfection.init.data;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class PearFoodComponents {

    public static final FoodComponent LAMPEAR = new FoodComponent.Builder().hunger(4).saturationModifier(0.3F)
            .statusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 5 * 20), 0.8f).build();

    public static final FoodComponent COPPER_LAMPEAR = new FoodComponent.Builder().hunger(6).saturationModifier(0.8F)
            .statusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 40 * 20), 1)
            .statusEffect(new StatusEffectInstance(StatusEffects.HASTE, 120 * 20, 3), 1)
            .statusEffect(new StatusEffectInstance(StatusEffects.LUCK, 600 * 20), 1)
            .alwaysEdible().build();

    public static final FoodComponent PEAR_TART = new FoodComponent.Builder().hunger(6).saturationModifier(0.5F)
            .statusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 5 * 20), 0.4f).build();
}
