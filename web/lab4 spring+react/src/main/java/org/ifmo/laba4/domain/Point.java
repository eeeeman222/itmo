package org.ifmo.laba4.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static com.zaxxer.hikari.util.ClockSource.toMillis;

@Data
@Entity
@Table(name="records")
@NoArgsConstructor
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private Coordinate coordinate;
    @Column(name="in_area", nullable = false)
    private boolean isHit;
    @Column(name="start_time", nullable = false)
    private ZonedDateTime createDateTime;
    @Column(name="processed_time", nullable = false)
    private double processDateTime;

    public Point(Coordinate coordinate, boolean inArea, ZonedDateTime startTime) {
        this.coordinate = coordinate;
        this.isHit = inArea;
        this.createDateTime = startTime;
        this.processDateTime = Duration.between(startTime, ZonedDateTime.now(ZoneOffset.UTC)).toMillis();
    }
}
