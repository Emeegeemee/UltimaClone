package org.emeegeemee.ultima.tiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Username: Justin
 * Date: 11/29/2014
 */
/*
public class Tile
-----------------------------------------------------------------------------------------
            Tile(String code)        // Create a new tile based on a String code
    boolean getLit()                 // Return whether this Tile is lit or not
       void setLit(boolean value)    // Change the lit status of this Tile
       void draw(int x, int y)       // Draw at index position (x, y)
    boolean isOpaque()               // Does this type of Tile block light?
    boolean isPassable()             // Can the Avatar walk on this Tile?
 */
public class Tile implements ITile {

    private final TextureRegion texture;
    private final boolean opaque, passable;

    public Tile(TextureRegion texture, boolean opaque, boolean passable) {
        this.texture = texture;
        this.opaque = opaque;
        this.passable = passable;
    }

    @Override
    public void draw(SpriteBatch batch, int x, int y) {
        batch.draw(texture, x, y);
    }

    @Override
    public boolean isOpaque() {
        return opaque;
    }

    @Override
    public boolean isPassable() {
        return passable;
    }
}
