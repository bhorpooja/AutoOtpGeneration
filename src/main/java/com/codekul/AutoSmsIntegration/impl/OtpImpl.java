package com.codekul.AutoSmsIntegration.impl;

import com.codekul.AutoSmsIntegration.repo.OtpRepo;
import org.springframework.stereotype.Repository;

import java.util.Random;

/**
 * Created by pooja on 16/12/17.
 */
@Repository
public class OtpImpl implements OtpRepo {

    @Override
    public Integer generateOtp() {
        Integer MIN=100000;
        Integer MAX=999999;
        Random random=new Random();
        return random.nextInt(((MAX-MIN)+1)+MIN);
    }
}
