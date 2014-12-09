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
import org.emeegeemee.ultima.tiles.TileFactory;
import org.emeegeemee.ultima.world.World;

/**
 * Username: Justin
 * Date: 11/29/2014
 */
public class GameScreen extends ScreenAdapter {
    public static final int TILES_WIDE = 65, TILES_HIGH = 65;
    public static final int RADIUS = 32;

    private static final float UPDATE_STEP = 1 / 60f;
    private float lag = 0f;

    private final Vector2 pos = new Vector2(RADIUS, RADIUS);

    private final Ultima game;
    private final OrthographicCamera camera;

    private final World world;

    private final InputProcessorQueue input;

    public GameScreen(final Ultima gam) {
        game = gam;
        TileFactory factory = game.getTileFactory();

        camera = new OrthographicCamera();

        TerrainGenerator gen = new DiamondSquare();

        world = new World(game.getWorldSize(), gen, factory, game.getTileWidth(), game.getTileHeight());

        input = new InputProcessorQueue(new InputAdapter() {
            @Override
            public boolean keyUp(int keycode) {
                switch (keycode) {
                    case Input.Keys.UP:
                        pos.y++;
                        if(pos.y >= Math.pow(2, game.getWorldSize()) + 1 - RADIUS)
                            pos.y = (float)(Math.pow(2, game.getWorldSize()) + 1 - RADIUS);
                        return true;
                    case Input.Keys.LEFT:
                        pos.x--;
                        if(pos.x < RADIUS)
                            pos.x = RADIUS;
                        return true;
                    case Input.Keys.RIGHT:
                        pos.x++;
                        if(pos.x >= Math.pow(2, game.getWorldSize()) + 1 - RADIUS)
                            pos.x = (float)(Math.pow(2, game.getWorldSize()) + 1 - RADIUS);
                        return true;
                    case Input.Keys.DOWN:
                        pos.y--;
                        if(pos.y < RADIUS)
                            pos.y = RADIUS;
                        return true;
                }

                return super.keyUp(keycode);
            }
        });

        Gdx.input.setInputProcessor(input);
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
        input.drain();
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
