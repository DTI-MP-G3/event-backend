package com.event.event.enums;

public enum RewardPoints {
    REGISTER(10000),
    REFERRAL(5000),
    FIRST_TRANSACTION(2000);

    private final int points;

    RewardPoints(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }
}