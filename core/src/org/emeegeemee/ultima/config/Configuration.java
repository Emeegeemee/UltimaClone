package org.emeegeemee.ultima.config;

import org.emeegeemee.ultima.tiles.TileConfig;

/**
 * Username: Justin
 * Date: 12/9/2014
 */
public class Configuration {
    TileConfig tileConfig;

    public TileConfig getTileConfig() {
        return tileConfig;
    }

    @Override
    public String toString() {
        return String.format("Configuration:%n%s%n", tileConfig.toString());
    }
}
