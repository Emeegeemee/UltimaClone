package org.emeegeemee.ultima.tiles;

import java.util.*;

/**
 * Username: Justin
 * Date: 11/29/2014
 */
public class TileConfig implements Iterable<Integer> {
    private Set<Integer> valid;
    private Set<Integer> opaque;
    private Set<Integer> passable;

    private Map<Integer, Integer> heightMap;

    private TileConfig() {

    }

    public TileConfig(Set<Integer> valid, Set<Integer> opaque, Set<Integer> passable, Map<Integer, Integer> heightMap) {
        this.valid = valid;
        this.opaque = opaque;
        this.passable = passable;
        this.heightMap = heightMap;
    }

    public boolean isValid(int index) {
        return valid.contains(index);
    }

    public int getNumberValid() {
        return valid.size();
    }

    public boolean isOpaque(int index) {
        return opaque.contains(index);
    }

    public boolean isPassable(int index) {
        return passable.contains(index);
    }

    public int getTileIndex(int height) {
        ArrayList<Integer> list = new ArrayList<>(heightMap.keySet());
        Collections.sort(list);

        for (Integer tileIndex : list) {
            if (height <= heightMap.get(tileIndex)) {
                return tileIndex;
            }
        }

        return list.get(list.size() - 1);
    }

    public String toString() {
        return String.format("valid: %s%nopaque: %s%npassable: %s%nheightMap: %s", valid.toString(), opaque.toString(), passable.toString(), heightMap.toString());
    }

    @Override
    public Iterator<Integer> iterator() {
        return valid.iterator();
    }
}
