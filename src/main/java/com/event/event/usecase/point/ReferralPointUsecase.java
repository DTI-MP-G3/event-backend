package com.event.event.usecase.point;

import com.event.event.entity.Point;

import java.util.Optional;

public interface ReferralPointUsecase {
    Point addReferralPointByRegister(Long userId);

    Optional<Point> getPointByIdAndUserId (Long userId, Long pointId);
}
