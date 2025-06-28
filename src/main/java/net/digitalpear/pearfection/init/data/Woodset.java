package net.digitalpear.pearfection.init.data;

import net.digitalpear.pearfection.common.blocks.CalleryLeavesBlock;
import net.digitalpear.pearfection.init.PearParticleTypes;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityType;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.ChestBoatEntity;
import net.minecraft.item.*;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;


@SuppressWarnings("unused")
public class Woodset {
    public static final List<Woodset> WOODSETS = new ArrayList<>();

    private final List<Block> registeredBlocksList = new ArrayList<>();
    private final List<Item> registeredItemsList = new ArrayList<>();

    private static final List<Block> signBlocks = new ArrayList<>();
    private static final List<Block> hangingSignBlocks = new ArrayList<>();


    private final Identifier name;
    private final MapColor sideColor;
    private final MapColor topColor;
    private BlockSetType blockSetType;
    private WoodType woodType;
    private BlockSoundGroup leaveSounds;

    private Block log;
    private Block strippedLog;
    private Block wood;
    private Block strippedWood;
    private Block leaves;
    private Block planks;
    private Block stairs;
    private Block slab;
    private Block mosaic;
    private Block mosaicStairs;
    private Block mosaicSlab;
    private Block fence;
    private Block fenceGate;
    private Block pressurePlate;
    private Block button;
    private Block door;
    private Block trapDoor;
    private Block sign;
    private Block wallSign;
    private Block hangingSign;
    private Block wallHangingSign;

    private Item signItem;
    private Item hangingSignItem;
    private Item boatItem;
    private Item chestBoatItem;

    private EntityType<BoatEntity> boat;
    private EntityType<ChestBoatEntity> chestBoat;

    private BlockFamily.Builder blockFamily;
    private final Settings woodsetSettings;

    private void registerWood(){
        blockSetType = createBlockSetType();
        woodType = WoodTypeBuilder.copyOf(woodsetSettings.woodPreset.woodType).register(this.getNameID(), getBlockSetType());

        planks = createPlanks();

        log = createLog();
        strippedLog = createStrippedLog();
        StrippableBlockRegistry.register(log, strippedLog);

        if (this.getWoodPreset() != WoodPreset.BAMBOO){
            wood = createWood();
            strippedWood = createStrippedWood();
            StrippableBlockRegistry.register(wood, strippedWood);
        }
        if (woodsetSettings.hasMosaic()){
            mosaic = createMosaic();
            mosaicStairs = createMosaicStairs();
            mosaicSlab = createMosaicSlab();
        }
        if (this.isOverworldTreeWood()){
            leaves = createLeaves();
        }
        stairs = createStairs();
        slab = createSlab();
        fence = createFence();
        fenceGate = createFenceGate();
        pressurePlate = createPressurePlate();
        button = createButton();
        door = createDoor();
        trapDoor = createTrapDoor();
        sign = createSign();
        wallSign = createWallSign();
        hangingSign = createHangingSign();
        wallHangingSign = createWallHangingSign();
        signItem = createSignItem();
        hangingSignItem = createHangingSignItem();

        if (woodsetSettings.hasBoats){
            boat = createBoatEntity();
            chestBoat = createChestBoatEntity();
            boatItem = createBoatItem();
            chestBoatItem = createChestBoatItem();
        }

        blockFamily = new BlockFamily.Builder(planks).group("wooden").unlockCriterionName(hasPlanks());
        blockFamily.stairs(stairs);
        blockFamily.slab(slab);
        if (woodsetSettings.hasMosaic()){
            blockFamily.customFence(fence);
            blockFamily.customFenceGate(fenceGate);
        }else{
            blockFamily.fence(fence);
            blockFamily.fenceGate(fenceGate);
        }
        blockFamily.door(door);
        blockFamily.trapdoor(trapDoor);
        blockFamily.sign(sign, wallSign);
        blockFamily.button(button);
        blockFamily.pressurePlate(pressurePlate);


        signBlocks.add(sign);
        signBlocks.add(wallSign);

        hangingSignBlocks.add(hangingSign);
        hangingSignBlocks.add(wallHangingSign);

        var signs = (FabricBlockEntityType) BlockEntityType.SIGN;
        signs.addSupportedBlock(sign);
        signs.addSupportedBlock(wallSign);

        var hangingSigns = (FabricBlockEntityType) BlockEntityType.HANGING_SIGN;
        hangingSigns.addSupportedBlock(hangingSign);
        hangingSigns.addSupportedBlock(wallHangingSign);

        if (woodsetSettings.woodPreset != WoodPreset.NETHER){
            registerWoodsetFlammables();
        }
        registerFuels();
    }


    public Woodset(Identifier name, MapColor sideColor, MapColor topColor, Settings.Builder settings){
        this.woodsetSettings = settings.build();
        this.name = name;
        this.sideColor = sideColor;
        this.topColor = topColor;
        registerWood();
        WOODSETS.add(this);
    }

    public Woodset(Identifier name, MapColor sideColor, MapColor topColor){
        this(name, sideColor, topColor, new Settings.Builder());
    }
    private RegistryKey<Item> itemKey(String id) {
        return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(this.getNamespace(), id));
    }
    private RegistryKey<Block> blockKey(String id) {
        return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(this.getNamespace(), id));
    }
    private Block createBlockWithItem(String blockID, AbstractBlock.Settings settings){
        return createBlockWithItem(blockID, Block::new, settings);
    }
    private Block createBlockWithItem(String blockID, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings){
        Block block = Blocks.register(blockKey(blockID), factory, settings);
        registeredBlocksList.add(block);
        Items.register(block);
        return block;
    }
    private Block createBlockWithoutItem(String blockID, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings){
        Block block = Blocks.register(blockKey(blockID), factory, settings);
        registeredBlocksList.add(block);
        return block;
    }
    public Item createItem(String blockID, Function<Item.Settings, Item> factory, Item.Settings settings){
        Item item = Items.register(itemKey(blockID), factory, settings);
        registeredItemsList.add(item);
        return item;
    }
    private RegistryKey<EntityType<?>> entityKey(String id) {
        return RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(this.getNamespace(), id));
    }
    public <T extends Entity> EntityType<T> register(String name, EntityType.Builder<T> type){
        return register(entityKey(name), type);
    }
    public <T extends Entity> EntityType<T> register(RegistryKey<EntityType<?>> name, EntityType.Builder<T> type){
        return Registry.register(Registries.ENTITY_TYPE, name, type.build(name));
    }
    private static EntityType.EntityFactory<BoatEntity> boatFactory(Item item) {
        return (entityType, world) -> new BoatEntity(entityType, world, () -> item);
    }
    private static EntityType.EntityFactory<ChestBoatEntity> chestBoatFactory(Item item) {
        return (entityType, world) -> new ChestBoatEntity(entityType, world, () -> item);
    }

    private AbstractBlock.Settings createLogBlock(MapColor topMapColor, MapColor sideMapColor) {
        return AbstractBlock.Settings.create().mapColor(state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? topMapColor : sideMapColor).strength(2.0F).sounds(this.getWoodType().soundType());
    }

    public Settings getWoodsetSettings() {
        return woodsetSettings;
    }

    public static List<WoodType> getAllWoodTypes(){
        if (WOODSETS.isEmpty()){
            return null;
        }
        List<WoodType> types = new ArrayList<>();
        for (Woodset set : WOODSETS){
            types.add(set.getWoodType());
        }
        return types;
    }
    public Identifier getNameID() {
        return name;
    }
    public String getName() {
        return name.getPath();
    }
    public String getNamespace() {
        return name.getNamespace();
    }

    public BlockSetType getBlockSetType() {
        return blockSetType;
    }

    public WoodPreset getWoodPreset() {
        return woodsetSettings.woodPreset;
    }

    public MapColor getSideColor() {
        return sideColor;
    }

    public MapColor getTopColor() {
        return topColor;
    }

    public WoodType getWoodType() {
        return woodType;
    }

    public Block getButton() {
        return button;
    }

    public Block getFence() {
        return fence;
    }

    public Block getPlanks() {
        return planks;
    }

    public Block getSlab() {
        return slab;
    }

    public Block getFenceGate() {
        return fenceGate;
    }

    public Block getStairs() {
        return stairs;
    }

    public Block getDoor() {
        return door;
    }

    public Block getHangingSign() {
        return hangingSign;
    }

    public Block getWallHangingSign() {
        return wallHangingSign;
    }

    public Block getPressurePlate() {
        return pressurePlate;
    }

    public Block getSign() {
        return sign;
    }

    public Block getTrapDoor() {
        return trapDoor;
    }

    public Block getWallSign() {
        return wallSign;
    }

    public Item getHangingSignItem() {
        return hangingSignItem;
    }

    public Item getSignItem() {
        return signItem;
    }

    public Block getLog() {
        return log;
    }

    public Block getStrippedLog() {
        return strippedLog;
    }

    public Block getWood() {
        return wood;
    }

    public Block getStrippedWood() {
        return strippedWood;
    }

    public Block getMosaic() {
        return mosaic;
    }

    public Block getMosaicStairs() {
        return mosaicStairs;
    }

    public Block getMosaicSlab() {
        return mosaicSlab;
    }

    public Block getLeaves() {
        return leaves;
    }

    public EntityType<BoatEntity> getBoat() {
        return boat;
    }

    public EntityType<ChestBoatEntity> getChestBoat() {
        return chestBoat;
    }

    public Item getBoatItem() {
        return boatItem;
    }

    public Item getChestBoatItem() {
        return chestBoatItem;
    }

    public List<Block> getRegisteredBlocksList() {
        return registeredBlocksList;
    }

    public List<Item> getRegisteredItemsList() {
        return registeredItemsList;
    }

    public static List<Block> getAllSigns(){
        return signBlocks;
    }
    public static List<Block> getAllHangingSigns(){
        return hangingSignBlocks;
    }

    public BlockFamily getBlockFamily() {
        return blockFamily.build();
    }
    private Block createLog() {
        return createBlockWithItem(this.getName() + "_" + woodsetSettings.getLogName(), PillarBlock::new, createLogBlock(this.getSideColor(), this.getTopColor()));
    }
    private Block createStrippedLog() {
        return createBlockWithItem("stripped_" + this.getName() + "_" + woodsetSettings.getLogName(), PillarBlock::new, createLogBlock(this.getSideColor(), this.getTopColor()));
    }
    private Block createWood() {
        return createBlockWithItem(this.getName() + "_" +woodsetSettings.getWoodName(), PillarBlock::new, createLogBlock(this.getSideColor(), this.getSideColor()));
    }
    private Block createStrippedWood() {
        return createBlockWithItem("stripped_" + this.getName() + "_" +woodsetSettings.getWoodName(), PillarBlock::new, createLogBlock(this.getTopColor(), this.getTopColor()));
    }
    private Block createLeaves() {
        return createBlockWithItem(this.getName() + "_leaves", settings -> new CalleryLeavesBlock(0.01f, settings, false), createLeavesBlock());
    }
    private Block createPlanks(){
        return createBlockWithItem(this.getName() + "_planks", AbstractBlock.Settings.copy(getBase()).sounds(getBlockSetType().soundType()).mapColor(getTopColor()));
    }
    private Block createStairs(){
        return createBlockWithItem(this.getName() + "_stairs", settings -> new StairsBlock(getBase().getDefaultState(), settings), AbstractBlock.Settings.copy(getBase()).sounds(getBlockSetType().soundType()).mapColor(getTopColor()));
    }
    private Block createSlab(){
        return createBlockWithItem(this.getName() + "_slab", SlabBlock::new, AbstractBlock.Settings.copy(getBase()).sounds(getBlockSetType().soundType()).mapColor(getTopColor()));
    }
    private Block createMosaic(){
        return createBlockWithItem(this.getName() + "_mosaic", AbstractBlock.Settings.copy(getBase()).sounds(getBlockSetType().soundType()).mapColor(getTopColor()));
    }
    private Block createMosaicStairs(){
        return createBlockWithItem(this.getName() + "_mosaic_stairs", settings -> new StairsBlock(getBase().getDefaultState(), settings), AbstractBlock.Settings.copy(getBase()).sounds(getBlockSetType().soundType()).mapColor(getTopColor()));
    }
    private Block createMosaicSlab(){
        return createBlockWithItem(this.getName() + "_mosaic_slab", SlabBlock::new, AbstractBlock.Settings.copy(getBase()).sounds(getBlockSetType().soundType()).mapColor(getTopColor()));
    }
    private Block createFence(){
        return createBlockWithItem(this.getName() + "_fence", FenceBlock::new, AbstractBlock.Settings.copy(getBase()).sounds(getBlockSetType().soundType()).mapColor(getTopColor()));
    }
    private Block createFenceGate(){
        return createBlockWithItem(this.getName() + "_fence_gate", settings -> new FenceGateBlock(this.getWoodType(), settings), AbstractBlock.Settings.copy(getBase()).sounds(getBlockSetType().soundType()).mapColor(getTopColor()));
    }
    private Block createPressurePlate(){
        return createBlockWithItem(this.getName() + "_pressure_plate", settings -> new PressurePlateBlock(this.getBlockSetType(), settings), AbstractBlock.Settings.copy(getBase()).sounds(getBlockSetType().soundType()).mapColor(getTopColor()));
    }
    private Block createButton(){
        return createBlockWithItem(this.getName() + "_button", settings -> new ButtonBlock(this.getBlockSetType(), 30, settings), AbstractBlock.Settings.copy(getBase()).sounds(getBlockSetType().soundType()).mapColor(getTopColor()));
    }
    private Block createDoor(){
        return createBlockWithItem(this.getName() + "_door", settings -> new DoorBlock(this.getBlockSetType(), settings), AbstractBlock.Settings.copy(getBase()).sounds(getBlockSetType().soundType()).mapColor(getTopColor()));
    }
    private Block createTrapDoor(){
        return createBlockWithItem(this.getName() + "_trapdoor", settings -> new TrapdoorBlock(this.getBlockSetType(), settings), AbstractBlock.Settings.copy(getBase()).sounds(getBlockSetType().soundType()).mapColor(getTopColor()));
    }
    private Block createSign(){
        return createBlockWithoutItem(this.getName() + "_sign", settings -> new SignBlock(
                        this.woodType, settings),
                AbstractBlock.Settings.copy(getSignBase()).mapColor(this.getTopColor()));
    }
    private Block createWallSign(){
        return createBlockWithoutItem(this.getName() + "_wall_sign", settings -> new WallSignBlock(
                        this.woodType, settings),
                AbstractBlock.Settings.copy(getSignBase()).mapColor(this.getTopColor()).lootTable(sign.getLootTableKey()));
    }

    private Block createHangingSign(){
        return createBlockWithoutItem(this.getName() + "_hanging_sign", settings -> new HangingSignBlock(
                        this.woodType, settings),
                AbstractBlock.Settings.copy(getHangingSignBase()).mapColor(this.getTopColor()));
    }
    private Block createWallHangingSign(){
        return createBlockWithoutItem(this.getName() + "_wall_hanging_sign", settings -> new WallHangingSignBlock(
                        this.woodType, settings),
                AbstractBlock.Settings.copy(getHangingSignBase()).mapColor(this.getTopColor()).lootTable(hangingSign.getLootTableKey()));
    }

    private Item createSignItem(){
        return createItem(this.getName() + "_sign", settings -> new SignItem(this.getSign(), this.getWallSign(), settings), new Item.Settings().maxCount(16));
    }
    private Item createHangingSignItem(){
        return createItem(this.getName() + "_hanging_sign", settings -> new HangingSignItem(this.getHangingSign(), this.getWallHangingSign(), settings), new Item.Settings().maxCount(16));
    }

    private EntityType<BoatEntity> createBoatEntity(){
        return register(this.getName() + "_" + woodsetSettings.getBoatName(), EntityType.Builder.create(boatFactory(boatItem), SpawnGroup.MISC).dropsNothing().dimensions(1.375F, 0.5625F).eyeHeight(0.5625F).maxTrackingRange(10));
    }
    private EntityType<ChestBoatEntity> createChestBoatEntity(){
        return register(this.getName() + "_chest_" + woodsetSettings.getBoatName(), EntityType.Builder.create(chestBoatFactory(chestBoatItem), SpawnGroup.MISC).dropsNothing().dimensions(1.375F, 0.5625F).eyeHeight(0.5625F).maxTrackingRange(10));
    }
    private Item createBoatItem(){
        return createItem(this.getName() + "_" + woodsetSettings.getBoatName(), settings -> new BoatItem(boat, settings), new Item.Settings().maxCount(1));
    }
    private Item createChestBoatItem(){
        return createItem(this.getName() + "_chest_" + woodsetSettings.getBoatName(), settings -> new BoatItem(chestBoat, settings), new Item.Settings().maxCount(1));
    }

    public void registerFuels(){
        FuelRegistryEvents.BUILD.register((builder, context) -> {
            builder.add(log, 300);
            builder.add(strippedLog, 300);
            if (woodsetSettings.woodPreset == WoodPreset.BAMBOO){
                builder.add(mosaic, 300);
                builder.add(mosaicSlab, 150);
                builder.add(mosaicStairs, 300);
            }
            else{
                builder.add(wood, 300);
                builder.add(strippedWood, 300);
            }
            builder.add(pressurePlate, 300);
            builder.add(button, 100);
            builder.add(trapDoor, 300);
            builder.add(door, 300);
            builder.add(fence, 300);
            builder.add(fenceGate, 300);
            builder.add(signItem, 300);
            builder.add(hangingSignItem, 800);

            if (woodsetSettings.hasBoats()){
                builder.add(boatItem, 1200);
                builder.add(chestBoatItem, 1200);
            }
        });
    }
    public void registerWoodsetFlammables(){
        addFlammable(getLog(), 5, 5);
        addFlammable(getStrippedLog(), 5, 5);

        if (getWoodPreset() != WoodPreset.BAMBOO){
            addFlammable(getWood(), 5, 5);
            addFlammable(getStrippedWood(), 5, 5);
        }
        if (woodsetSettings.hasMosaic()){
            addFlammable(getMosaic(), 5, 20);
            addFlammable(getMosaicStairs(), 5, 20);
            addFlammable(getMosaicSlab(), 5, 20);
        }
        if (isOverworldTreeWood()){
            addFlammable(getLeaves(), 30, 60);
        }

        addFlammable(getPlanks(), 5, 20);
        addFlammable(getSlab(), 5, 20);
        addFlammable(getStairs(), 5, 20);
        addFlammable(getFence(), 5, 20);
        addFlammable(getFenceGate(), 5, 20);

        addFlammable(getSign(), 5, 20);
        addFlammable(getWallSign(), 5, 20);

        addFlammable(getHangingSign(), 5, 20);
        addFlammable(getWallHangingSign(), 5, 20);
    }
    public static void addFlammable(Block block, int burn, int spread){
        FlammableBlockRegistry.getDefaultInstance().add(block, burn, spread);
    }

    public void fullWoodset(BlockStateModelGenerator blockStateModelGenerator){
        BlockStateModelGenerator.BlockTexturePool pool = blockStateModelGenerator.registerCubeAllModelTexturePool(getPlanks());
        pool.family(getBlockFamily());

        if (isOverworldTreeWood()){
            blockStateModelGenerator.registerSimpleCubeAll(getLeaves());
        }

        if (notBambooVariant()){
            blockStateModelGenerator.createLogTexturePool(getLog()).log(getLog()).wood(getWood());
            blockStateModelGenerator.createLogTexturePool(getStrippedLog()).log(getStrippedLog()).wood(getStrippedWood());
        }
        if (woodsetSettings.hasMosaic()){
            blockStateModelGenerator.createLogTexturePool(getLog()).uvLockedLog(getLog());
            blockStateModelGenerator.createLogTexturePool(getStrippedLog()).uvLockedLog(getStrippedLog());
        }

        blockStateModelGenerator.registerHangingSign(strippedLog, hangingSign, wallHangingSign);

        blockStateModelGenerator.registerItemModel(getBoatItem());
        blockStateModelGenerator.registerItemModel(getChestBoatItem());
    }

    private Block getBase(){
        switch (getWoodPreset()){
            case NETHER -> {
                return Blocks.CRIMSON_PLANKS;
            }
            case BAMBOO -> {
                return Blocks.BAMBOO_PLANKS;
            }
            case FANCY -> {
                return Blocks.CHERRY_PLANKS;
            }
            default -> {
                return Blocks.OAK_PLANKS;
            }
        }
    }
    private Block getSignBase(){
        switch (getWoodPreset()){
            case NETHER -> {
                return Blocks.CRIMSON_SIGN;
            }
            case BAMBOO -> {
                return Blocks.BAMBOO_SIGN;
            }
            case FANCY -> {
                return Blocks.CHERRY_SIGN;
            }
            default -> {
                return Blocks.OAK_SIGN;
            }
        }
    }
    private Block getHangingSignBase(){
        switch (getWoodPreset()){
            case NETHER -> {
                return Blocks.CRIMSON_HANGING_SIGN;
            }
            case BAMBOO -> {
                return Blocks.BAMBOO_HANGING_SIGN;
            }
            case FANCY -> {
                return Blocks.CHERRY_HANGING_SIGN;
            }
            default -> {
                return Blocks.OAK_HANGING_SIGN;
            }
        }
    }

    private BlockSetType createBlockSetType(){
        return getWoodPreset().blockSetType();
    }

    public boolean isOverworldTreeWood(){
        return this.getWoodPreset().isOverworldTree();
    }
    public boolean notBambooVariant(){
        return this.getWoodPreset() != WoodPreset.BAMBOO;
    }

    public AbstractBlock.Settings createLeavesBlock() {
        return createLeavesBlock(MapColor.DARK_GREEN);
    }
    public AbstractBlock.Settings createLeavesBlock(MapColor color) {
        return AbstractBlock.Settings.create().mapColor(color).strength(0.2F).ticksRandomly().sounds(woodsetSettings.leaveSoundGroup).nonOpaque().allowsSpawning(Blocks::canSpawnOnLeaves).suffocates(Blocks::never).blockVision(Blocks::never).burnable().pistonBehavior(PistonBehavior.DESTROY).solidBlock(Blocks::never);
    }



    public static void addToBuildingTab(Item proceedingItem, Woodset woodset){
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
            entries.addAfter(proceedingItem, woodset.getPlanks(), woodset.getStairs(), woodset.getSlab(),
                    woodset.getFence(), woodset.getFenceGate(),
                    woodset.getDoor(), woodset.getTrapDoor(),
                    woodset.getPressurePlate(), woodset.getButton());
            if (woodset.notBambooVariant()){
                entries.addAfter(proceedingItem, woodset.getWood(), woodset.getStrippedWood());
            }
            if (woodset.getWoodsetSettings().hasMosaic()){
                entries.addAfter(proceedingItem, woodset.getMosaic(), woodset.getMosaicStairs(), woodset.getMosaicSlab());
            }
            entries.addAfter(proceedingItem, woodset.getLog(), woodset.getStrippedLog());
        });
    }
    private String hasPlanks(){
        return RecipeGenerator.hasItem(getPlanks());
    }

    public void generateRecipes(RecipeGenerator recipeGenerator, RegistryEntryLookup<Item> lookup, RecipeExporter exporter, TagKey<Item> logs){
        recipeGenerator.offerPlanksRecipe(getPlanks(), logs, 4);
        recipeGenerator.createStairsRecipe(this.getStairs(), Ingredient.ofItems(this.getPlanks())).criterion(hasPlanks(), recipeGenerator.conditionsFromItem(this.getPlanks())).offerTo(exporter);
        recipeGenerator.offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, this.getSlab(), this.getPlanks());
        recipeGenerator.generateFamily(getBlockFamily(), FeatureSet.empty());
        if (notBambooVariant()){
            recipeGenerator.offerBarkBlockRecipe(getWood(), getLog());
            recipeGenerator.offerBarkBlockRecipe(getStrippedWood(), getStrippedLog());
        }
        if (getWoodsetSettings().hasMosaic()){
            recipeGenerator.offerMosaicRecipe(RecipeCategory.BUILDING_BLOCKS, this.getMosaic(), this.getSlab());
            recipeGenerator.createStairsRecipe(this.getMosaicStairs(), Ingredient.ofItems(this.getPlanks())).criterion(hasPlanks(), recipeGenerator.conditionsFromItem(this.getMosaic())).offerTo(exporter);
            recipeGenerator.offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, this.getMosaicStairs(), this.getPlanks());
        }
        recipeGenerator.createFenceRecipe(fence, Ingredient.ofItems(planks)).criterion(hasPlanks(), recipeGenerator.conditionsFromItem(planks)).offerTo(exporter);
        recipeGenerator.createFenceGateRecipe(fenceGate, Ingredient.ofItems(planks)).criterion(hasPlanks(), recipeGenerator.conditionsFromItem(planks)).offerTo(exporter);
        recipeGenerator.createDoorRecipe(door, Ingredient.ofItems(planks)).criterion(hasPlanks(), recipeGenerator.conditionsFromItem(planks)).offerTo(exporter);
        recipeGenerator.createTrapdoorRecipe(trapDoor, Ingredient.ofItems(planks)).criterion(hasPlanks(), recipeGenerator.conditionsFromItem(planks)).offerTo(exporter);

        recipeGenerator.createSignRecipe(signItem, Ingredient.ofItems(planks)).criterion(hasPlanks(), recipeGenerator.conditionsFromItem(planks)).offerTo(exporter);
        recipeGenerator.offerHangingSignRecipe(getHangingSignItem(), getStrippedLog());

        recipeGenerator.offerBoatRecipe(getBoatItem(), getPlanks());
        recipeGenerator.offerChestBoatRecipe(getChestBoatItem(), getBoatItem());
    }

    public enum WoodPreset {
        DEFAULT(WoodType.OAK),
        FANCY(WoodType.CHERRY),
        NETHER(WoodType.CRIMSON),
        BAMBOO(WoodType.BAMBOO);

        private final WoodType woodType;

        WoodPreset(WoodType type){
            this.woodType = type;
        }

        public WoodType getWoodType() {
            return woodType;
        }

        boolean isOverworldTree(){
            return this == DEFAULT || this == FANCY;
        }
        public BlockSetType blockSetType(){
            return woodType.setType();
        }
    }
    public static class Settings{
        public enum BoatType {BOAT, RAFT}
        private String logName = null;
        private String woodName = null;
        private BoatType boatType = null;

        private boolean hasBoats = true;
        private boolean hasMosaic = false;
        private BlockSoundGroup leaveSoundGroup = BlockSoundGroup.GRASS;
        private WoodPreset woodPreset = WoodPreset.DEFAULT;

        private Settings() {
        }

        public BoatType getBoatType() {
            return boatType;
        }

        public BlockSoundGroup getLeaveSoundGroup() {
            return leaveSoundGroup;
        }

        public String getLogName() {
            return logName;
        }

        public String getWoodName() {
            return woodName;
        }

        public boolean hasMosaic() {
            return hasMosaic;
        }

        public boolean hasBoats() {
            return hasBoats;
        }

        public WoodPreset getWoodPreset() {
            return woodPreset;
        }

        private String getBoatName(){
            return this.getBoatType() == BoatType.RAFT ? "raft" : "boat";
        }


        public static class Builder {
            private final Settings settings = new Settings();
            public Settings build(){
                settings.boatType = getBoatType();
                settings.woodName = getWoodName();
                settings.logName = getLogName();
                return settings;
            }

            public Builder woodName(String woodName) {
                settings.woodName = woodName;
                return this;
            }

            public Builder woodName(boolean hasBoats) {
                settings.hasBoats = hasBoats;
                return this;
            }

            public Builder logName(String logName) {
                settings.logName = logName;
                return this;
            }

            public Builder setBoatType(BoatType type) {
                settings.boatType = type;
                return this;
            }

            public Builder woodPreset(WoodPreset woodPreset) {
                settings.woodPreset = woodPreset;
                return this;
            }

            public Builder leaveSoundGroup(BlockSoundGroup soundGroup){
                settings.leaveSoundGroup = soundGroup;
                return this;
            }

            public Builder hasMosaic(){
                settings.hasMosaic = true;
                return this;
            }

            private String getLogName(){
                if (settings.logName != null){
                    return settings.logName;
                }
                switch (settings.woodPreset){
                    case NETHER -> {
                        return "stem";
                    }
                    case BAMBOO -> {
                        return "block";
                    }
                    default -> {
                        return "log";
                    }
                }
            }
            public BoatType getBoatType() {
                return Objects.requireNonNullElseGet(settings.boatType, () -> settings.woodPreset == WoodPreset.BAMBOO ? BoatType.RAFT : BoatType.BOAT);
            }

            private String getWoodName(){
                return Objects.requireNonNullElseGet(settings.woodName, () -> settings.woodPreset == WoodPreset.NETHER ? "hyphae" : "wood");
            }
        }
    }
}

