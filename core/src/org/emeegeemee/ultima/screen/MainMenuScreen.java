package org.emeegeemee.ultima.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.emeegeemee.ultima.Ultima;

/**
 * Username: Justin
 * Date: 11/29/2014
 */
public class MainMenuScreen extends ScreenAdapter {
    private final Ultima game;

    private final OrthographicCamera camera;
    private final BitmapFont.TextBounds bounds;

    private static final String WELCOME = "Welcome to Ultima!";
    private static final String INSTRUCTIONS = "Click anywhere to start a new game";

    public MainMenuScreen(final Ultima gam) {
        game = gam;

        camera = new OrthographicCamera();
        bounds = new BitmapFont.TextBounds();
    }

    @Override
    public void render(float delta) {
        SpriteBatch batch = game.getSpriteBatch();
        BitmapFont font = game.getFont();

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        font.getBounds(WELCOME, bounds);
        font.draw(batch, WELCOME, -bounds.width/2, 15*10);

        font.getBounds(INSTRUCTIONS, bounds);
        font.draw(batch, INSTRUCTIONS, -bounds.width/2, 0);
        batch.end();

        if(Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        camera.translate(-width/2, -height/2);
    }

    public void finalize() throws Throwable {
        super.finalize();
        Gdx.app.debug("MainMenuScreen", "The Main Menu is being garbage collected");
    }
}
