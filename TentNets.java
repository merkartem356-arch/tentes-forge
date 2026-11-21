package com.tentnets;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@Mod(TentNets.MODID)
public class TentNets {
    public static final String MODID = "tentnets";

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, MODID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, MODID);
    private static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final RegistryObject<Block> ANTI_DRONE_NET = BLOCKS.register("anti_drone_net",
            () -> new NetBlock(NetBlock.props(MapColor.COLOR_LIGHT_GRAY), NetBlock.Kind.ANTI_DRONE));
    public static final RegistryObject<Block> CAMO_NET = BLOCKS.register("camo_net",
            () -> new NetBlock(NetBlock.props(MapColor.COLOR_GREEN), NetBlock.Kind.CAMO));

    public static final RegistryObject<Item> ANTI_DRONE_NET_ITEM = ITEMS.register("anti_drone_net",
            () -> new BlockItem(ANTI_DRONE_NET.get(), new Item.Properties()));
    public static final RegistryObject<Item> CAMO_NET_ITEM = ITEMS.register("camo_net",
            () -> new BlockItem(CAMO_NET.get(), new Item.Properties()));
    public static final RegistryObject<Item> THERMAL_VISION = ITEMS.register("thermal_vision", ThermalItem::new);

    public static final RegistryObject<CreativeModeTab> TAB = TABS.register("main",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tentnets"))
                    .icon(() -> new ItemStack(ANTI_DRONE_NET_ITEM.get()))
                    .displayItems((params, out) -> {
                        out.accept(ANTI_DRONE_NET_ITEM.get());
                        out.accept(CAMO_NET_ITEM.get());
                        out.accept(THERMAL_VISION.get());
                    })
                    .build());

    @SuppressWarnings("removal")
    public TentNets() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(modBus);
        ITEMS.register(modBus);
        TABS.register(modBus);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            MinecraftForge.EVENT_BUS.addListener(ClientHooks::onClientTick);
        }
    }
}
