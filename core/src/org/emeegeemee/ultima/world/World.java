package org.emeegeemee.ultima.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import org.emeegeemee.ultima.screen.GameScreen;
import org.emeegeemee.ultima.terrain.TerrainGenerator;
import org.emeegeemee.ultima.tiles.ITile;
import org.emeegeemee.ultima.tiles.TileFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Username: Justin
 * Date: 12/8/2014
 */
public class World {
    private ITile[][] tiles;
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

        int[][] heightMap = generator.generateTerrain(map, 0.5, 0, 255);

        tiles = new ITile[size][size];

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new File("testing"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                tiles[i][j] = factory.getTileFromHeight(heightMap[i][j]);
                if(writer != null)
                    writer.printf("%3d ", heightMap[i][j]);
            }
            if(writer != null)
                writer.println();
        }

        if(writer != null) {
            writer.flush();
            writer.close();
        }
    }

    public int getWidth() {
        return tiles.length;
    }

    public int getHeight() {
        return tiles[0].length;
    }

    public void draw(SpriteBatch batch, Vector2 pos) {
        int x = (int)pos.x - radius;
        int y = (int)pos.y - radius;

        for(int i = 0; i < GameScreen.TILES_WIDE; i++) {
            for (int j = 0; j < GameScreen.TILES_HIGH; j++) {
                int xi = x+i;
                int yj = y+j;

                if(xi >= 0 && yj >= 0 && xi < tiles.length && yj < tiles.length)
                    tiles[x + i][y + j].draw(batch, i * tileWidth, j * tileHeight);
            }
        }
    }

    public boolean isPassable(Vector2 pos) {
        return tiles[(int)pos.x][(int)pos.y].isPassable();
    }
}

