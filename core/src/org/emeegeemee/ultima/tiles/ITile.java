package org.emeegeemee.ultima.tiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Username: Justin
 * Date: 11/29/2014
 */
public interface ITile {
    public void draw(SpriteBatch batch, int x, int y);
    public boolean isOpaque();
    public boolean isPassable();
}
