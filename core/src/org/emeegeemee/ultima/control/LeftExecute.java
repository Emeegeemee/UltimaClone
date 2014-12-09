package org.emeegeemee.ultima.control;

import org.emeegeemee.ultima.utils.Point2;
import org.emeegeemee.ultima.world.World;

/**
 * Username: Justin
 * Date: 12/9/2014
 */
public class LeftExecute extends PositionExecuteAdapter {
    public LeftExecute(int keycode) {
        super(keycode);
    }

    @Override
    public void posUpdate(Point2 pos) {
        pos.x--;
    }

    @Override
    public void boundsUpdate(World world, Point2 pos) {
        if (pos.x < 0)
            pos.x = 0;
    }

    @Override
    public void posBacktrack(Point2 pos) {
        pos.x++;
    }
}
