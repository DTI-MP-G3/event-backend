package com.event.event.infrastructure.points.dto;

import com.event.event.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointsRepository extends JpaRepository<Point, Long> {

}
