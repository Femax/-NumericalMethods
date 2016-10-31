package ru.advantum.fedosov.numericmethods.model;

/**
 * Created by fedosov on 10/31/16.
 */

public class Point  {
    private  double x ;
    private  double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
