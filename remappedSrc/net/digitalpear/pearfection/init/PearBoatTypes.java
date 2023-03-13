package net.digitalpear.pearfection.init;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;
import net.digitalpear.pearfection.Pearfection;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class PearBoatTypes {

    public static TerraformBoatType makeBoat(String name, TerraformBoatType boatType){
        return Registry.register(TerraformBoatTypeRegistry.INSTANCE, new Identifier(Pearfection.MOD_ID, name), boatType);
    }

    public static final TerraformBoatType CALLERY = makeBoat("callery", new TerraformBoatType.Builder().planks(PearBlocks.CALLERY_PLANKS.asItem())
            .item(PearItems.CALLERY_BOAT).chestItem(PearItems.CALLERY_CHEST_BOAT).build());


    public static void init() {
    }
}
