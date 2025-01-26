package point;

import java.io.Serializable;
import java.util.Objects;

public class PointManager implements Serializable {
    private final double x;
    private final double y;
    private final double r;
    private final boolean inside;


    public PointManager(double x, double y, double r) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.inside = isInside(x,y,r);
    }

    public boolean isInside(double x, double y, double R) {
        double normalizedX = x / R;
        double normalizedY = y / R;

        // Проверка для треугольника
        boolean isInTriangle = (normalizedX >= 0 && normalizedY >= 0 && normalizedX + normalizedY <= 1);

        // Проверка для квадрата слева сверху
        boolean isInSquare = (normalizedX <= 0 && normalizedY >= 0 && normalizedX >= -1 && normalizedY <= 1);

        // Проверка для четверти окружности слева снизу
        boolean isInCircle = (normalizedX <= 0 && normalizedY <= 0 && normalizedX * normalizedX + normalizedY * normalizedY <= 1);

        return isInTriangle || isInSquare || isInCircle;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getR() {
        return r;
    }

    public boolean isInArea() {
        return inside;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PointManager that = (PointManager) o;
        return Double.compare(x, that.x) == 0 && Double.compare(y, that.y) == 0 && Double.compare(r, that.r) == 0 && inside == that.inside;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, r, inside);
    }

    @Override
    public String toString() {
        return "PointManager{" +
                "x=" + x +
                ", y=" + y +
                ", r=" + r +
                ", inside=" + inside +
                '}';
    }
}