package org.emeegeemee.ultima.terrain;

/**
 * Username: Justin
 * Date: 12/8/2014
 */
public interface TerrainGenerator {
    int[][] generateTerrain(double[][] map, double roughness, double min, double max);
}
