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

    public TileFactory(Texture tileset, TileConfig config, int tileWidth, int tileHeight, boolean preBuild) {
        TextureRegion[][] regions = TextureRegion.split(tileset, tileWidth, tileHeight);
        this.regions = new TextureRegion[regions.length * regions[0].length];

        int pos = 0;
        for (TextureRegion[] region : regions) {
            for (TextureRegion aRegion : region) {
                this.regions[pos] = aRegion;
                pos++;
            }
        }

        this.config = config;
        tiles = new HashMap<>(config.getNumberValid());

        if(preBuild) {
            buildCache();
        }
    }

    public void buildCache() {
        for(Integer i : config) {
            if(tiles.get(i) == null) {
                ITile tile = new Tile(regions[i], config.isOpaque(i), config.isPassable(i));
                tiles.put(i, tile);
            }
        }
    }

    public ITile getTile(int index) {
        assert config.isValid(index) && index >= 0 && index < regions.length :
                String.format("index must be [0-%d) and be in the valid config list", regions.length);

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
