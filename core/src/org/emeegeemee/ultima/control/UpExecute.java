package org.emeegeemee.ultima.control;

import org.emeegeemee.ultima.utils.Point2;
import org.emeegeemee.ultima.world.World;

/**
 * Username: Justin
 * Date: 12/9/2014
 */
public class UpExecute extends PositionExecuteAdapter {
    public UpExecute(int keycode) {
        super(keycode);
    }

    @Override
    public void posUpdate(Point2 pos) {
        pos.y++;
    }

    @Override
    public void boundsUpdate(World world, Point2 pos) {
        if (pos.y > world.getHeight() - 1)
            pos.y = world.getHeight() - 1;
    }

    @Override
    public void posBacktrack(Point2 pos) {
        pos.y--;
    }
}
