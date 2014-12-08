package org.emeegeemee.ultima.tiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Username: Justin
 * Date: 11/29/2014
 */
public interface ITile {
    void draw(SpriteBatch batch, int x, int y);

    boolean isOpaque();

    boolean isPassable();
}
