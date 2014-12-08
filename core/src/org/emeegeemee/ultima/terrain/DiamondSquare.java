package org.emeegeemee.ultima.terrain;

import java.util.DoubleSummaryStatistics;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Username: Justin
 * Date: 12/4/2014
 */
public class DiamondSquare {
    public int[][] generateTerrain(double[][] map, double roughness, double min, double max) {
        roughness = Math.min(1.0, Math.max(0.0, roughness));
        double ratio = Math.pow(2, -roughness);

        Queue<Square> queue = new LinkedList<>();

        queue.add(new Square(0, 0, map.length - 1, 1.0));

        while(queue.size() > 0) {
            Square s = queue.remove();

            double range = s.range;
            int len = s.length;
            int x1 = s.x;
            int x2 = s.x + len;
            int y1 = s.y;
            int y2 = y1 + len;

            int xMid = x1 + len / 2;
            int yMid = y1 + len / 2;

            double botLeft = map[x1][y1];
            double topLeft = map[x1][y2];
            double botRight = map[x2][y1];
            double topRight = map[x2][y2];

            map[xMid][yMid] = (botLeft + botRight + topLeft + topRight) / 4 + (Math.random()*range - range/2);

            map[xMid][y1] = (botLeft + botRight + map[xMid][yMid] + ((y1-len/2) >= 0 ? map[xMid][y1-len/2] : 0)) / ((y1-len/2) >= 0 ? 4 : 3) + (Math.random()*range - range/2);
            map[xMid][y2] = (topLeft + topRight + map[xMid][yMid] + ((y2+len/2) < map.length ? map[xMid][y2+len/2] : 0)) / ((y2+len/2) >= 0 ? 4 : 3) + (Math.random()*range - range/2);

            map[x1][yMid] = (botLeft + topLeft + map[xMid][yMid] + ((x1-len/2) >= 0 ? map[x1-len/2][yMid] : 0)) / ((x1-len/2) >= 0 ? 4 : 3) + (Math.random()*range - range/2);
            map[x2][yMid] = (botRight + topRight + map[xMid][yMid] + ((x2+len/2) < map.length ? map[x2+len/2][yMid] : 0)) / ((x2+len/2) >= 0 ? 4 : 3) + (Math.random()*range - range/2);

            if(len > 2) {
                range *= ratio;

                queue.add(new Square(x1, y1, len / 2, range));
                queue.add(new Square(x1, yMid, len / 2, range));
                queue.add(new Square(xMid, yMid, len / 2, range));
                queue.add(new Square(xMid, y1, len / 2, range));
            }
        }

        normalizeMap(map, min, max);

        return roundMap(map);
    }

    //normalizes the map to the give min and max
    private void normalizeMap(double[][] map, double min, double max) {
        double originalMin = Double.MAX_VALUE;
        double originalMax = -Double.MAX_VALUE;

        for (double[] aMap : map) {
            for (double val : aMap) {
                if (val > originalMax) {
                    originalMax = val;
                }
                if (val < originalMin) {
                    originalMin = val;
                }
            }
        }

        double originalRange = originalMax - originalMin;
        double newRange = max - min;

        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[i].length; j++) {
                map[i][j] = ((map[i][j] - originalMin) / originalRange) * newRange + min;
            }
        }
    }

    private int[][] roundMap(double[][] map) {
        int[][] newMap = new int[map.length][];

        for (int i = 0; i < newMap.length; i++) {
            newMap[i] = new int[map[i].length];

            for(int j = 0; j < newMap[i].length; j++) {
                newMap[i][j] = (int)Math.round(map[i][j]);
            }
        }

        return newMap;
    }

    private class Square {
        int x, y;
        int length;
        double range;

        public Square(int x, int y, int length, double range) {
            this.x = x;
            this.y = y;
            this.length = length;
            this.range = range;
        }
    }

    public static void main(String... args) {
        DiamondSquare ds = new DiamondSquare();

        double[][] map = new double[9][9];
        map[0][0] = Math.random();
        map[map.length - 1][0] = Math.random();
        map[0][map.length - 1] = Math.random();
        map[map.length - 1][map.length - 1] = Math.random();

        int[][] newMap = ds.generateTerrain(map, .2, 0, 255);

        for(int y = 0; y < newMap.length; y++) {
            for (int x = 0; x < newMap.length; x++) {
                System.out.printf("%3d ", newMap[x][y]);
            }
            System.out.println();
        }
    }
}
