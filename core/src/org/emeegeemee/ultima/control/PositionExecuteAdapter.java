package org.emeegeemee.ultima.control;

/**
 * Username: Justin
 * Date: 12/9/2014
 */
public abstract class PositionExecuteAdapter implements PositionExecute {
    private final int keycode;

    public PositionExecuteAdapter(int keycode) {
        this.keycode = keycode;
    }

    @Override
    public int getKeycode() {
        return keycode;
    }
}
