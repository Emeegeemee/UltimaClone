package org.emeegeemee.ultima.tiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Username: Justin
 * Date: 11/29/2014
 */
public class VisibilityTile implements ITile {
    private final ITile tile;
    private boolean lit;

    public VisibilityTile(ITile tile) {
        this.tile = tile;
    }

    @Override
    public void draw(SpriteBatch batch, int x, int y) {
        if(lit)
            tile.draw(batch, x, y);
    }

    @Override
    public boolean isOpaque() {
        return tile.isOpaque();
    }

    @Override
    public boolean isPassable() {
        return tile.isPassable();
    }

    public void setLit(boolean lit) {
        this.lit = lit;
    }
}
