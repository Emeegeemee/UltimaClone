package org.emeegeemee.ultima.utils;

/**
 * Username: Justin
 * Date: 12/9/2014
 */
public class Point2 {
    public static final Point2 ZERO = new Point2();
    public static final Point2 X = new Point2(1, 0);
    public static final Point2 Y = new Point2(0, 1);

    public int x, y;

    public Point2() {
        x = 0;
        y = 0;
    }

    public Point2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point2(Point2 p) {
        x = p.x;
        y = p.y;
    }

    public Point2 set(int x, int y) {
        this.x = x;
        this.y = y;

        return this;
    }

    public Point2 set(Point2 p) {
        x = p.x;
        y = p.y;

        return this;
    }

    public Point2 add(int x, int y) {
        this.x += x;
        this.y += y;

        return this;
    }

    public Point2 add(Point2 p) {
        return add(p.x, p.y);
    }

    public Point2 sub(int x, int y) {
        return add(-x, -y);
    }

    public Point2 sub(Point2 p) {
        return sub(p.x, p.y);
    }

    public Point2 cpy() {
        return new Point2(this);
    }

    public float dst2(int x, int y) {
        int dx = this.x - x;
        int dy = this.y - y;

        return dx * dx + dy * dy;
    }

    public float dst2(Point2 p) {
        return dst2(p.x, p.y);
    }

    public float dst(int x, int y) {
        return (float)Math.sqrt(dst2(x, y));
    }

    public float dst(Point2 p) {
        return dst(p.x, p.y);
    }

    public float len2() {
        return x * x + y * y;
    }

    public float len() {
        return (float)Math.sqrt(len2());
    }

    public Point2 nor() {
        if(Math.abs(y) > Math.abs(x)) {
            y /= Math.abs(y);
            x = 0;
        }
        else {
            x /= Math.abs(x);
            y = 0;
        }

        return this;
    }

    public Point2 setZero() {
        x = 0;
        y = 0;

        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Point2 point2 = (Point2) o;

        return x == point2.x && y == point2.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
