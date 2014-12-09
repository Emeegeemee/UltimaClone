package org.emeegeemee.ultima.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.emeegeemee.ultima.screen.GameScreen;
import org.emeegeemee.ultima.terrain.TerrainGenerator;
import org.emeegeemee.ultima.tiles.ITile;
import org.emeegeemee.ultima.tiles.TileFactory;
import org.emeegeemee.ultima.tiles.VisibilityTile;
import org.emeegeemee.ultima.utils.Point2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Username: Justin
 * Date: 12/8/2014
 */
public class World {
    private VisibilityTile[][] tiles;
    private final int[][] heightMap;
    private int tileWidth, tileHeight;
    private int radius;

    public World(int n, TerrainGenerator generator, TileFactory factory, int tileWidth, int tileHeight) {
        this(n, generator, factory, tileWidth, tileHeight, GameScreen.RADIUS);
    }

    public World(int n, TerrainGenerator generator, TileFactory factory, int tileWidth, int tileHeight, int radius) {
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.radius = radius;

        int size = (int)Math.pow(2, n) + 1;

        double[][] map = new double[size][size];
        map[0][0] = Math.random();
        map[0][size - 1] = Math.random();
        map[size - 1][size - 1] = Math.random();
        map[size - 1][0] = Math.random();

        heightMap = generator.generateTerrain(map, 0.5, 0, 255);

        tiles = new VisibilityTile[size][size];

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                tiles[i][j] = new VisibilityTile(factory.getTileFromHeight(heightMap[i][j]));
            }
        }
    }

    public int getWidth() {
        return tiles.length;
    }

    public int getHeight() {
        return tiles[0].length;
    }

    public void draw(SpriteBatch batch, Point2 pos) {
        int x = pos.x - radius;
        int y = pos.y - radius;

        for(int i = 0; i < GameScreen.TILES; i++) {
            for (int j = 0; j < GameScreen.TILES; j++) {
                int xi = x+i;
                int yj = y+j;

                if(xi >= 0 && yj >= 0 && xi < tiles.length && yj < tiles.length)
                    tiles[xi][yj].draw(batch, i * tileWidth, j * tileHeight);
            }
        }
    }

    public void light(Point2 pos, int range) {
        int x = pos.x - radius;
        int y = pos.y - radius;

        for(int i = 0; i < GameScreen.TILES; i++) {
            for (int j = 0; j < GameScreen.TILES; j++) {
                int xi = x+i;
                int yj = y+j;

                if(xi >= 0 && yj >= 0 && xi < tiles.length && yj < tiles.length)
                    tiles[xi][yj].setLit(true);
            }
        }

        light(pos, pos.x, pos.y, range * range);
    }

    private void light(Point2 pos, int x, int y, int range) {
        if(x < 0 || x >= tiles.length || y < 0 || y >= tiles.length || pos.dst2(x, y) > range)
            return;

        VisibilityTile tile = tiles[x][y];

        if(tile.isLit())
            return;

        tile.setLit(true);

        if(tile.isOpaque()) {
            if(pos.equals(x, y)) {
                int val = (int)Math.sqrt(range) / 2;
                light(pos, x - 1, y, val * val);
                light(pos, x + 1, y, val * val);
                light(pos, x, y - 1, val * val);
                light(pos, x, y + 1, val * val);
            }
            return;
        }

        light(pos, x - 1, y, range);
        light(pos, x + 1, y, range);
        light(pos, x, y + 1, range);
        light(pos, x, y - 1, range);
    }

    public boolean isPassable(Point2 pos) {
        return tiles[pos.x][pos.y].isPassable();
    }
}

