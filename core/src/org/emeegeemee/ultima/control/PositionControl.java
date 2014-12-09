package org.emeegeemee.ultima.control;

import com.badlogic.gdx.Input;
import org.emeegeemee.ultima.utils.Point2;
import org.emeegeemee.ultima.world.World;

/**
 * Username: Justin
 * Date: 12/9/2014
 */
public class PositionControl {
    private final PositionExecute execute;

    public PositionControl(final PositionExecute execute) {
        this.execute = execute;
    }

    public void updatePos(final Input input, final World world, final Point2 pos) {
        if(input.isKeyPressed(execute.getKeycode())) {
            execute.posUpdate(pos);
            execute.boundsUpdate(world, pos);

            if(!world.isPassable(pos))
                execute.posBacktrack(pos);
        }
    }
}
