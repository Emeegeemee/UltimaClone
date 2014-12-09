package org.emeegeemee.ultima.control;

import org.emeegeemee.ultima.utils.Point2;
import org.emeegeemee.ultima.world.World;

/**
 * Username: Justin
 * Date: 12/9/2014
 */
public interface PositionExecute {
    public int getKeycode();
    public void posUpdate(Point2 pos);
    public void boundsUpdate(World world, Point2 pos);
    public void posBacktrack(Point2 pos);
}
