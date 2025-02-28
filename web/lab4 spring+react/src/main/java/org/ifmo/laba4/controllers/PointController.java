package org.ifmo.laba4.controllers;


import org.ifmo.laba4.domain.Coordinate;
import org.ifmo.laba4.domain.Point;
import org.ifmo.laba4.services.PointService;
import org.ifmo.laba4.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("api/points")
@CrossOrigin(origins = "http://localhost:3000")
public class PointController {

    @Autowired
    private PointService pointService;


    @GetMapping()
    public List<Point> getAllPoints() {
        System.out.println("djncifdjcnifujcnd ijcknuicj staart");
        return pointService.getAllPoints();
    }

    @PostMapping()
    public Point savePoint(@RequestBody Coordinate coordinate) {
        try {

            return pointService.savePoint(coordinate, ZonedDateTime.now(ZoneOffset.UTC));
        }
        catch (IllegalArgumentException e) {
            return null;
        }
    }

    @DeleteMapping()
    public void deletePoints() {
        try{
            pointService.deletePoints();
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
