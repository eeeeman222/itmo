package se.ifmo.utils;

/**
 * Utility class to check if a point lies within a defined area.
 */
public class AreaChecker {

    public static boolean isInArea(double x, double y, double R) {

        if (isXInArea(x) && isYInArea(y) && isRInArea(R)){
            if (x >= 0 && y >= 0 && y <= R && x <= R/2) {
                return true;
            }

            if (x <= 0 && y >= 0 && y <= R/2 + x) {
                return true;
            }

            if (x >= 0 && y <= 0 && x * x + y * y <= R * R) {
                return true;
            }
        }
        else{
            return false;
        }
        return false;
    }



    private static boolean isXInArea(double x){
        return x >= -5 && x <= 3;
    }

    private static boolean isYInArea(double y){
        return y >= -5 && y <= 5;
    }

    private static boolean isRInArea(double r){
        return r >=1 && r <= 3;
    }
}