package org.ifmo.laba4.services;

import org.ifmo.laba4.domain.Coordinate;
import org.ifmo.laba4.domain.Point;
import org.ifmo.laba4.repositories.PointsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class PointService {
    @Autowired
    private PointsRepository pointsRepository;

    public List<Point> getAllPoints(){
        return pointsRepository.findAll();
    }

    public void deletePoints(){
        pointsRepository.deleteAll();
    }

    public Point savePoint(Coordinate coordinate, ZonedDateTime time){
        try{
            System.out.println("saved");
            return pointsRepository.save(new Point(
                    coordinate,
                    checkHit(coordinate),
                    time
                    )
            );
        }
        catch(IllegalArgumentException e){
            throw new IllegalArgumentException(e);
        }
    }

    private boolean checkHit(Coordinate cord){
        double x = cord.getX();
        double y = cord.getY();
        double r = cord.getR();

        if(x >= 0 && y >= 0){
            return x <= r/2 && y <= r - 2*x;
        }
        if(x >= 0 && y <= 0){
            return x <= r && y >= -r/2;
        }
        if(x <= 0 && y >= 0){
            return (x*x + y*y <=r*r/4);
        }

        return false;
    }

    private void validateCord(Coordinate cord) throws IllegalArgumentException {
        if(cord == null || cord.getR() < -3 || cord.getR() > 5){
            throw new IllegalArgumentException("Invalid coordinate");
        }
    }
}
