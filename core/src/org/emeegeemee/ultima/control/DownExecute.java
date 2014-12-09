package org.emeegeemee.ultima.control;

import org.emeegeemee.ultima.utils.Point2;
import org.emeegeemee.ultima.world.World;

/**
 * Username: Justin
 * Date: 12/9/2014
 */
public class DownExecute extends PositionExecuteAdapter {
    public DownExecute(int keycode) {
        super(keycode);
    }

    @Override
    public void posUpdate(Point2 pos) {
        pos.y--;
    }

    @Override
    public void boundsUpdate(World world, Point2 pos) {
        if (pos.y < 0)
            pos.y = 0;
    }

    @Override
    public void posBacktrack(Point2 pos) {
        pos.y++;
    }
}