package net.digitalpear.pearfection.init;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;
import net.digitalpear.pearfection.Pearfection;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class PearBoatTypes {


    public static TerraformBoatType makeBoat(String name, ItemConvertible planks, Item boat, Item chestBoat){
        return Registry.register(TerraformBoatTypeRegistry.INSTANCE, new Identifier(Pearfection.MOD_ID, name),
                new TerraformBoatType.Builder()
                .planks(planks.asItem())
                .item(boat)
                .chestItem(chestBoat)
                .build());
    }

    public static final TerraformBoatType CALLERY = makeBoat("callery",
            PearBlocks.CALLERY_PLANKS.asItem(),
            PearItems.CALLERY_BOAT,
            PearItems.CALLERY_CHEST_BOAT);


    public static void init() {
    }
}
