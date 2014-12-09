package org.emeegeemee.ultima.screen;

import com.badlogic.gdx.*;
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
import org.emeegeemee.ultima.world.World;

/**
 * Username: Justin
 * Date: 11/29/2014
 */
public class GameScreen extends ScreenAdapter {
    public static final int TILES_WIDE = 33, TILES_HIGH = 33;
    public static final int RADIUS = 16;

    private static final float UPDATE_STEP = 1 / 60f;
    private float lag = 0f;

    private final Vector2 pos = new Vector2(RADIUS, RADIUS);
    private final ITile player;

    private final Ultima game;
    private final OrthographicCamera camera;

    private final World world;

    public GameScreen(final Ultima gam) {
        game = gam;
        TileFactory factory = game.getTileFactory();

        player = factory.getTile(31);
        camera = new OrthographicCamera();

        TerrainGenerator gen = new DiamondSquare();

        world = new World(game.getWorldSize(), gen, factory, game.getTileWidth(), game.getTileHeight());
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


    private void physics() {

    }

    private void logic() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            pos.y++;
            if (pos.y > world.getHeight() - 1)
                pos.y = world.getHeight() - 1;

            if(!world.isPassable(pos))
                pos.y--;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            pos.x--;
            if (pos.x < 0)
                pos.x = 0;

            if(!world.isPassable(pos))
                pos.x++;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            pos.x++;
            if (pos.x > world.getWidth() - 1)
                pos.x = world.getWidth() - 1;

            if(!world.isPassable(pos))
                pos.x--;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            pos.y--;
            if (pos.y < 0)
                pos.y = 0;

            if(!world.isPassable(pos))
                pos.y++;
        }
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

        world.draw(batch, pos);
        player.draw(batch, RADIUS * tileWidth, RADIUS * tileWidth);

        batch.end();
    }
/*
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
*/
}
