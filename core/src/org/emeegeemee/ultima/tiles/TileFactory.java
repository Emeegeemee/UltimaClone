package org.emeegeemee.ultima.tiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

/**
 * Username: Justin
 * Date: 11/29/2014
 */
public class TileFactory {
    private final TextureRegion[] regions;
    private final TileConfig config;
    private final Map<Integer, ITile> tiles;//cache to enable flyweight

    public TileFactory(Texture tileset, TileConfig config, int tileWidth, int tileHeight) {
        TextureRegion[][] regions = TextureRegion.split(tileset, tileWidth, tileHeight);
        this.regions = new TextureRegion[regions.length * regions[0].length];

        int pos = 0;
        for (TextureRegion[] region : regions) {
            for (TextureRegion aRegion : region) {
                this.regions[pos] = aRegion;
                pos++;
            }
        }

        tiles = new HashMap<>();
        this.config = config;
    }

    public ITile getTile(int index) {
        assert index >= 0 && index < regions.length : String.format("index must be [0-%d)", regions.length);

        ITile tile = tiles.get(index);

        if (tile == null) {
            tile = new Tile(regions[index], config.isOpaque(index), config.isPassable(index));
            tiles.put(index, tile);
        }

        return tile;
    }

    public ITile getTileFromHeight(int height) {
        return getTile(config.getTileIndex(height));
    }

    public void clearCache() {
        tiles.clear();
    }
}
