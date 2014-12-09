package org.emeegeemee.ultima.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import org.emeegeemee.ultima.screen.GameScreen;
import org.emeegeemee.ultima.tiles.ITile;
import org.emeegeemee.ultima.utils.Point2;
import org.emeegeemee.ultima.world.World;

/**
 * Created by jemge on 12/9/2014.
 */
public class Player {
    private final Point2 pos;
    private final ITile picture;
    private final Point2 screen;
    private int radius = GameScreen.RADIUS;

    public Player(World world, ITile pic, Point2 screenPos) {
        picture = pic;

        int width = world.getWidth();
        int height = world.getHeight();

        pos = new Point2(MathUtils.random(width), MathUtils.random(height));
        while(!world.isPassable(pos)) {
            pos.set(MathUtils.random(width), MathUtils.random(height));
        }

        screen = screenPos;
    }

    public void draw(SpriteBatch batch) {
        picture.draw(batch, screen.x, screen.y);
    }

    public Point2 getPos() {
        return pos;
    }

    public int getRadius() {
        return radius;
    }
}
