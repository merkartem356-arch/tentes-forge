package com.tentnets;

import net.minecraftforge.event.TickEvent;

/** Грузится только на клиенте. */
public final class ClientHooks {
    private ClientHooks() {}

    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            ThermalClient.tick();
        }
    }
}
