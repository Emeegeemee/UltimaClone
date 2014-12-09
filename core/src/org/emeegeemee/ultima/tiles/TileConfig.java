package org.emeegeemee.ultima.tiles;

import java.util.*;

/**
 * Username: Justin
 * Date: 11/29/2014
 */
public class TileConfig {
    private Set<Integer> opaque;
    private Set<Integer> passable;

    private Map<Integer, Integer> heightMap;

    public boolean isOpaque(int index) {
        return opaque.contains(index);
    }

    public boolean isPassable(int index) {
        return passable.contains(index);
    }

    public int getTileIndex(int height) {
        ArrayList<Map.Entry<Integer, Integer>> list = new ArrayList<>(heightMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                if(o1.getValue() < o2.getValue()) {
                    return -1;
                }
                else if(o1.getValue() > o2.getValue()) {
                    return 1;
                }
                else {
                    return 0;
                }
            }
        });

        for (Map.Entry<Integer, Integer> tileEntry : list) {
            if (height <= tileEntry.getValue()) {
                return tileEntry.getKey();
            }
        }

        return list.get(list.size() - 1).getKey();
    }

    public String toString() {
        return String.format("opaque: %s%npassable: %s%nheightMap: %s", opaque.toString(), passable.toString(), heightMap.toString());
    }
}
