package org.emeegeemee.ultima.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Scaling;
import org.emeegeemee.ultima.Ultima;
import org.emeegeemee.ultima.terrain.DiamondSquare;
import org.emeegeemee.ultima.terrain.TerrainGenerator;
import org.emeegeemee.ultima.tiles.ITile;
import org.emeegeemee.ultima.tiles.TileFactory;

/**
 * Username: Justin
 * Date: 11/29/2014
 */
public class GameScreen extends ScreenAdapter {
    private static final int TILES_WIDE = 33, TILES_HIGH = 33;

    public static final float UPDATE_STEP = 1 / 60f;
    private float lag = 0f;

    private final Ultima game;
    private OrthographicCamera camera;

    private ITile[][] tiles;

    public GameScreen(final Ultima gam) {
        game = gam;
        TileFactory factory = game.getTileFactory();

        camera = new OrthographicCamera();
        tiles = new ITile[TILES_WIDE][TILES_HIGH];

        TerrainGenerator gen = new DiamondSquare();

        double[][] map = new double[TILES_WIDE][TILES_HIGH];
        map[0][0] = Math.random();
        map[0][TILES_HIGH - 1] = Math.random();
        map[TILES_WIDE - 1][TILES_HIGH - 1] = Math.random();
        map[TILES_WIDE - 1][0] = Math.random();

        int[][] heightMap = gen.generateTerrain(map, 1.0, 0, 255);

        for(int i = 0; i < TILES_WIDE; i++) {
            for(int j = 0; j < TILES_HIGH; j++) {
                tiles[i][j] = factory.getTileFromHeight(heightMap[i][j]);
            }
        }
    }

    @Override
    public void render(float delta) {
        lag += delta;

        while(lag >= UPDATE_STEP) {
            physics();	//updates the physics by one step
            logic();	//updates the game logic by one step

            lag -= UPDATE_STEP;
        }

        draw(/*lag / UPDATE_STEP*/); //draws the view with linear interpolation
    }


    public void physics() {

    }

    public void logic() {

    }

    private void draw(/*float alpha*/) {
        SpriteBatch batch = game.getSpriteBatch();

        int tileWidth = game.getTileWidth();
        int tileHeight = game.getTileHeight();

        float fixedWidth = tileWidth*TILES_WIDE; // your preferred viewWidth
        float fixedHeight = tileHeight*TILES_HIGH; // your preferred viewHeight
        //very useful and easy function to get preferred width and height and still keeping the same aspect ratio :)
        Vector2 size = Scaling.fill.apply(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), fixedWidth, fixedHeight);

        camera.setToOrtho(false, size.x, size.y);

        float shiftWidth = (fixedWidth - size.x)/2;
        float shiftHeight = (fixedHeight - size.y)/2;
        //shift the camera so it is in "center"
        camera.translate(shiftWidth, shiftHeight);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        for(int i = 0; i < TILES_WIDE; i++) {
            for(int j = 0; j < TILES_HIGH; j++) {
                tiles[i][j].draw(batch, i*tileWidth, j*tileHeight);
            }
        }

        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
