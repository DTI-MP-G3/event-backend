package com.event.event.infrastructure.coupons.util;


import org.springframework.stereotype.Service;

@Service
public  class CouponCodeService {

    public  String referralCouponGenerator (Long userId){
        return ("RE" + userId.toString());
    }
}
