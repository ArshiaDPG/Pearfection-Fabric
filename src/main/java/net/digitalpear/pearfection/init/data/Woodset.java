package net.digitalpear.pearfection.init.data;


import com.terraformersmc.terraform.sign.block.TerraformHangingSignBlock;
import com.terraformersmc.terraform.sign.block.TerraformSignBlock;
import com.terraformersmc.terraform.sign.block.TerraformWallHangingSignBlock;
import com.terraformersmc.terraform.sign.block.TerraformWallSignBlock;
import net.digitalpear.pearfection.Pearfection;
import net.digitalpear.pearfection.common.blocks.CalleryLeavesBlock;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;


public record Woodset(Identifier id, MapColor topColor, MapColor sideColor, WoodType woodType) {

    public static LeavesBlock createLeavesBlock(BlockSoundGroup soundGroup) {
        return new CalleryLeavesBlock(false, AbstractBlock.Settings.create().mapColor(MapColor.DARK_GREEN).strength(0.2f).ticksRandomly().sounds(soundGroup).nonOpaque().allowsSpawning(Woodset::canSpawnOnLeaves).suffocates(Woodset::never).blockVision(Woodset::never));
    }
    public static LeavesBlock createFloweringLeavesBlock(BlockSoundGroup soundGroup, MapColor color) {
        return new CalleryLeavesBlock(true, AbstractBlock.Settings.create().mapColor(color).strength(0.2f).ticksRandomly().sounds(soundGroup).nonOpaque().allowsSpawning(Woodset::canSpawnOnLeaves).suffocates(Woodset::never).blockVision(Woodset::never));
    }
    public static LeavesBlock createFloweringLeavesBlock(BlockSoundGroup soundGroup) {
        return createFloweringLeavesBlock(soundGroup, MapColor.DARK_GREEN);
    }


    public Woodset(String name, MapColor topColor, MapColor sideColor, WoodType woodType){
        this(new Identifier(Pearfection.MOD_ID, name), topColor, sideColor, woodType);
    }
    public Woodset(Identifier id, MapColor topColor, MapColor sideColor){
        this(id, topColor, sideColor, WoodType.OAK);
    }

    @Override
    public Identifier id() {
        return id;
    }

    public String name(){
        return id().getPath();
    }
    public String namespace(){
        return id().getNamespace();
    }

    private static boolean never(BlockState state, net.minecraft.world.BlockView blockView, BlockPos blockPos) {
        return false;
    }

    private static boolean canSpawnOnLeaves(BlockState state, net.minecraft.world.BlockView blockView, BlockPos blockPos, EntityType<?> entityType) {
        return entityType == EntityType.OCELOT || entityType == EntityType.PARROT;
    }


    public BlockItem createBlockItem(String blockID, Block block){
        return Registry.register(Registries.ITEM, new Identifier(namespace(), blockID), new BlockItem(block, new Item.Settings()));
    }

    public Block createBlockWithItem(String blockID, Block block){
        createBlockItem(blockID, block);
        return Registry.register(Registries.BLOCK, new Identifier(namespace(), blockID), block);
    }

    public Block createBlockWithoutItem(String blockID, Block block){
        return Registry.register(Registries.BLOCK, new Identifier(namespace(), blockID), block);
    }

    private PillarBlock createLogBlock(MapColor topMapColor, MapColor sideMapColor) {
        return new PillarBlock(AbstractBlock.Settings.create().mapColor((state) -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? topMapColor : sideMapColor).strength(2.0F).sounds(this.woodType().soundType()));
    }

    private ButtonBlock createWoodenButtonBlock() {
        return new ButtonBlock(AbstractBlock.Settings.copy(Blocks.OAK_BUTTON), this.woodType().setType(), 30, true);
    }


    public Block createPlanks(){
        return createBlockWithItem(this.name() + "_planks", new Block(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).mapColor(this.topColor()).sounds(this.woodType().soundType())));
    }

    public Block createStairs(){
        return createBlockWithItem(this.name() + "_stairs", new StairsBlock(Blocks.CHERRY_PLANKS.getDefaultState(), AbstractBlock.Settings.copy(Blocks.OAK_STAIRS).sounds(this.woodType().soundType()).mapColor(this.topColor())));
    }
    public Block createSlab(){
        return createBlockWithItem(this.name() + "_slab", new SlabBlock(AbstractBlock.Settings.copy(Blocks.OAK_SLAB).mapColor(this.topColor()).sounds(this.woodType().soundType())));
    }
    public Block createFence(){
        return createBlockWithItem(this.name() + "_fence", new FenceBlock(AbstractBlock.Settings.copy(Blocks.OAK_FENCE).mapColor(this.topColor()).sounds(this.woodType().soundType())));
    }
    public Block createFenceGate(){
        return createBlockWithItem(this.name() + "_fence_gate", new FenceGateBlock(AbstractBlock.Settings.copy(Blocks.OAK_FENCE_GATE).sounds(this.woodType().soundType()).mapColor(this.topColor()), this.woodType()));
    }
    public Block createPressurePlate(){
        return createBlockWithItem(this.name() + "_pressure_plate", new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, AbstractBlock.Settings.copy(Blocks.OAK_PRESSURE_PLATE).sounds(this.woodType().soundType()).mapColor(this.topColor()), this.woodType().setType()));
    }
    public Block createButton(){
        return createBlockWithItem(this.name() + "_button", createWoodenButtonBlock());
    }
    public Block createDoor(){
        return createBlockWithItem(this.name() + "_door", new DoorBlock(AbstractBlock.Settings.copy(Blocks.OAK_DOOR).mapColor(this.topColor()), this.woodType().setType()));
    }
    public Block createTrapDoor(){
        return createBlockWithItem(this.name() + "_trapdoor", new TrapdoorBlock(AbstractBlock.Settings.copy(Blocks.OAK_TRAPDOOR).mapColor(this.topColor()), this.woodType().setType()));
    }
    public Block createLog(boolean mushroom, boolean stripped, boolean wood) {
        String name = this.name();
        MapColor topColor = this.topColor();
        MapColor sideColor = this.sideColor();

        if (stripped) {
            name = "stripped_" + name;
            sideColor = this.topColor();
        }
        if (wood) {
            name += mushroom ? "_hyphae" : "_wood";
            topColor = stripped ? this.topColor() : this.sideColor();
        } else {
            name += mushroom ? "_stem" : "_log";
        }
        return createBlockWithItem(name, createLogBlock(topColor, sideColor));
    }
    public Block createLeaves(){
        return createBlockWithItem(this.name() + "_leaves", createLeavesBlock(BlockSoundGroup.AZALEA_LEAVES));
    }
    public Block createFloweringLeaves(MapColor flowerColor){
        return createBlockWithItem("flowering_" + this.name() + "_leaves", createFloweringLeavesBlock(BlockSoundGroup.AZALEA_LEAVES, flowerColor));
    }
    public Block createSign(){
        return createBlockWithoutItem(this.name() + "_sign", new TerraformSignBlock(new Identifier(namespace(), "entity/signs/" + this.name()), AbstractBlock.Settings.copy(Blocks.ACACIA_SIGN).mapColor(this.topColor())));
    }
    public Block createWallSign(){
        return createBlockWithoutItem(this.name() + "_wall_sign", new TerraformWallSignBlock(new Identifier(namespace(), "entity/signs/" + this.name()), AbstractBlock.Settings.copy(Blocks.ACACIA_WALL_SIGN).mapColor(this.topColor())));
    }
    public Block createHangingSign(){
        return createBlockWithoutItem(this.name() + "_hanging_sign", new TerraformHangingSignBlock(new Identifier(namespace(), "entity/signs/hanging/" + this.name()), new Identifier(Pearfection.MOD_ID, "textures/gui/hanging_signs/" + this.name()), AbstractBlock.Settings.copy(Blocks.ACACIA_HANGING_SIGN).mapColor(this.topColor())));
    }
    public Block createWallHangingSign(){
        return createBlockWithoutItem(this.name() + "_wall_hanging_sign", new TerraformWallHangingSignBlock(new Identifier(namespace(), "entity/signs/hanging/" + this.name()), new Identifier(Pearfection.MOD_ID, "textures/gui/hanging_signs/" + this.name()), AbstractBlock.Settings.copy(Blocks.ACACIA_WALL_HANGING_SIGN).mapColor(this.topColor())));
    }
}
