package com.gmail.everloser12.fishingweather.prework;

import com.squareup.otto.Bus;

/**
 * Created by al-ev on 02.05.2016.
 */
public final class BusProvider {
    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
        return BUS;
    }

    private BusProvider() {
        // No instances.
    }
}
