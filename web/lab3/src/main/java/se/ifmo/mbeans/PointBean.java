package se.ifmo.mbeans;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.transaction.UserTransaction;
import lombok.Getter;
import lombok.Setter;
import se.ifmo.db.Point;
import se.ifmo.utils.AreaChecker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

@Named("pointBean")
@SessionScoped

public class PointBean implements Serializable {

    private double x;
    private double y;
    private double r;
    private boolean result;

    @Setter
    @Getter
    private String error;
    private List<Point> points = new ArrayList<>(); // List to hold session points

    @PersistenceContext
    private EntityManager em;

    @Resource
    private UserTransaction userTransaction;

    @PostConstruct
    public void init() {
        loadPointsFromDatabase();
    }

    @Transactional
    public void loadPointsFromDatabase() {
        points = em.createQuery("SELECT p FROM Point p", Point.class).getResultList();
    }
    // Method to validate, persist, and add point to session list
    @Transactional
    public void checkAndAddPoint() {
        System.out.println("in checkAndAddPoint()\n");

        if (r == 0){
            System.out.println(" r is null(0), returning");
            return;
        }
        try {
            result = AreaChecker.isInArea(x, y, r); // Perform validation
        } catch (Exception e) {
            error = "введены неверные данные!!!";
        }

        // Create a new Point object with validated data
        Point point = new Point(x, y, r, result);

        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(point);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
        points.add(point);
    }

    @Transactional
    public void clearPoints() {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            points.clear();
            transaction.commit();
        }catch (Exception e) {
            e.printStackTrace();
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    // Method to get points for display in the table
    public List<Point> getPoints() {
        return points;
    }

    // Getters and setters for x, y, r
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

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

}
