package org.emeegeemee.ultima;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.emeegeemee.ultima.config.Configuration;
import org.emeegeemee.ultima.screen.MainMenuScreen;
import org.emeegeemee.ultima.tiles.TileFactory;

public class Ultima extends Game {
	private SpriteBatch batch;
	private Texture tileset;
	private BitmapFont font;

	private final String tilesetFilename;
	private final Configuration config;
	private final int tileWidth, tileHeight;
	private final int worldSize;
	private TileFactory tileFactory;

	public Ultima(String tilesetFilename, Configuration config, int tileWidth, int tileHeight, int worldSize) {
		this.tilesetFilename = tilesetFilename;
		this.config = config;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.worldSize = worldSize;
	}

	@Override
	public void create() {
		batch = new SpriteBatch();
		tileset = new Texture(tilesetFilename);
		font = new BitmapFont();

		tileFactory = new TileFactory(tileset, config.getTileConfig(), tileWidth, tileHeight);

		setScreen(new MainMenuScreen(this));

		Gdx.app.log("Ultima", "Application has been created");
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		Gdx.app.log("Ultima", String.format("Resizing to, width: %d, height: %d", width, height));
	}

	@Override
	public void pause() {
		super.pause();
		Gdx.app.log("Ultima", "Application is pausing");
	}

	@Override
	public void resume() {
		super.resume();
		Gdx.app.log("Ultima", "Application is resuming");
	}

	@Override
	public void dispose() {
		super.dispose();

		batch.dispose();
		tileset.dispose();
		font.dispose();
		tileFactory.clearCache();

		Gdx.app.log("Ultima", "Application is being disposed of");
	}

	public SpriteBatch getSpriteBatch() {
		return batch;
	}

	public BitmapFont getFont() {
		return font;
	}

	public int getTileWidth() {
		return tileWidth;
	}

	public int getTileHeight() {
		return tileHeight;
	}

	public int getWorldSize() {
		return worldSize;
	}

	public TileFactory getTileFactory() {
		return tileFactory;
	}
}
