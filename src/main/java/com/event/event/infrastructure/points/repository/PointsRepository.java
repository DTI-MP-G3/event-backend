package com.event.event.infrastructure.points.repository;

import com.event.event.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PointsRepository extends JpaRepository<Point, Long> {


    Optional<Point> findByUserIdAndId(Long userId, Long pointId);
}
