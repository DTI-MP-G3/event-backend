package com.event.event.usecase.point.impl;

import com.event.event.entity.Point;
import com.event.event.enums.RewardPoints;
import com.event.event.infrastructure.points.dto.PointsRepository;
import com.event.event.usecase.point.ReferralPointUsecase;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReferralPointUsecaseImpl implements ReferralPointUsecase {

    PointsRepository pointsRepository;

    public ReferralPointUsecaseImpl(PointsRepository pointsRepository) {
        this.pointsRepository = pointsRepository;
    }

    @Override
    @Transactional
    public Point addReferralPointByRegister(Long userId) {
        Point newPoint = new Point();
        newPoint.setUserId(userId);
        newPoint.setPoints(RewardPoints.REGISTER.getPoints());

        try{
            log.info("User with ID {} was refer someone", userId);
            pointsRepository.save(newPoint);
        }catch (Exception e){
            e.printStackTrace();
        }
        return newPoint;
    }
}
