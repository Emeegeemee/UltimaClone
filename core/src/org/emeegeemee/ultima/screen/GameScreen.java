package org.emeegeemee.ultima.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Scaling;
import org.emeegeemee.ultima.Ultima;
import org.emeegeemee.ultima.control.*;
import org.emeegeemee.ultima.entities.Player;
import org.emeegeemee.ultima.terrain.DiamondSquare;
import org.emeegeemee.ultima.terrain.TerrainGenerator;
import org.emeegeemee.ultima.tiles.ITile;
import org.emeegeemee.ultima.tiles.TileFactory;
import org.emeegeemee.ultima.utils.Point2;
import org.emeegeemee.ultima.world.World;

/**
 * Username: Justin
 * Date: 11/29/2014
 */
public class GameScreen extends ScreenAdapter {
    public static final int TILES = (int)Math.pow(2, 5) + 1;
    public static final int RADIUS = (TILES - 1) / 2;

    private static final float UPDATE_STEP = 1 / 60f;
    private float lag = 0f;

    private final Player player;

    private final Ultima game;
    private final OrthographicCamera camera;

    private final World world;

    private final PositionControl[] controls;

    public GameScreen(final Ultima gam) {
        game = gam;
        TileFactory factory = game.getTileFactory();

        camera = new OrthographicCamera();

        TerrainGenerator gen = new DiamondSquare();

        world = new World(game.getWorldSize(), gen, factory, game.getTileWidth(), game.getTileHeight());

        controls = new PositionControl[4];
        controls[0] = new PositionControl(new UpExecute(Input.Keys.W));
        controls[1] = new PositionControl(new DownExecute(Input.Keys.S));
        controls[2] = new PositionControl(new RightExecute(Input.Keys.D));
        controls[3] = new PositionControl(new LeftExecute(Input.Keys.A));

        player = new Player(world, factory.getTile(31), new Point2(RADIUS * game.getTileWidth(), RADIUS * game.getTileWidth()));
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

    int count = 0;

    private void logic() {
        if(count == 0) {
            for (PositionControl pc : controls) {
                pc.updatePos(Gdx.input, world, player.getPos());
            }
        }
        count = (count + 1) % 3;

        world.light(player.getPos(), player.getRadius());
    }

    private void draw(/*float alpha*/) {
        SpriteBatch batch = game.getSpriteBatch();
        ShapeRenderer renderer = game.getRenderer();

        int tileWidth = game.getTileWidth();
        int tileHeight = game.getTileHeight();

        float fixedWidth = tileWidth* TILES; // your preferred viewWidth
        float fixedHeight = tileHeight* TILES; // your preferred viewHeight
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
        renderer.setProjectionMatrix(camera.combined);

        batch.begin();

        world.draw(batch, player.getPos());
        player.draw(batch);

        batch.end();

        renderer.begin(ShapeRenderer.ShapeType.Line);

        renderer.setColor(Color.YELLOW);
        renderer.ellipse((RADIUS- (2*tileWidth / 2f - 0.5f)) * tileWidth, (RADIUS- (2*tileHeight / 2f - 0.5f)) * tileHeight, 2*tileWidth*RADIUS, 2*tileHeight*RADIUS);
        renderer.rect(RADIUS * tileHeight, RADIUS * tileHeight, 16, 16);

        renderer.end();
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
