package point;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PointsArr implements Serializable
{
    private final List<PointManager> points = new ArrayList<>();

    public void addPoint(PointManager point)
    {
        points.add(point);
    }

    public List<PointManager> getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return "PointsArr{" +
                "points=" + points +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PointsArr pointsArr = (PointsArr) o;
        return Objects.equals(points, pointsArr.points);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(points);
    }
}