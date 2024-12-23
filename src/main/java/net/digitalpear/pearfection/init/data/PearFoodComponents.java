package net.digitalpear.pearfection.init.data;

import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;


public class PearFoodComponents {

    public static final FoodComponent LAMPEAR = new FoodComponent.Builder().nutrition(4).saturationModifier(0.3F).build();


    public static final FoodComponent COPPER_LAMPEAR = new FoodComponent.Builder().nutrition(6).saturationModifier(0.8F).alwaysEdible().build();

    public static final FoodComponent PEAR_TART = new FoodComponent.Builder().nutrition(8).saturationModifier(1.0F).build();
}
