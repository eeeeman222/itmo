package org.ifmo.laba4.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
public class Coordinate {
    @Column(name="x", nullable = false)
    private double x;
    @Column(name="y", nullable = false)
    private double y;
    @Column(name="r", nullable = false)
    private double r;
}
