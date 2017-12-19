package com.codekul.AutoSmsIntegration.controller;

import com.codekul.AutoSmsIntegration.model.User;
import com.codekul.AutoSmsIntegration.repo.OtpRepo;
import com.codekul.AutoSmsIntegration.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pooja on 15/12/17.
 */

@RestController
@RequestMapping("/User")
public class UserController {

    public static final String STATUS="status";
    public static final String FAIL="Failure";
    public static final String SUCCESS="Success";
    public static final String MESSAGE="msg";
    public static final String TIMESATMP="timestamp";
    public static final String CODE="otp";
    Integer code;

   @Autowired
   private UserRepo userRepo;

   @Autowired
   private OtpRepo otpRepo;

   @PostMapping("/RegisterUser")
    public ResponseEntity<?> saveUser(@RequestBody User user){
       User user1=userRepo.findByMobileNo(user.getMobileNo());
       if(user1==null) {
           Map<String, Object> map = new HashMap<>();
           ResponseEntity<Map<String, Object>> entity = null;
           userRepo.save(user);
           map.put(MESSAGE, "User Inserted Successfully");
           map.put("userId", user.getId());
           map.put(STATUS, SUCCESS);
           map.put(TIMESATMP, LocalTime.now());
           entity = new ResponseEntity<>(map, HttpStatus.OK);
           return entity;
       }
       else {
           Map<String, Object> map = new HashMap<>();
           ResponseEntity<Map<String, Object>> entity = null;
           map.put(MESSAGE, "User Already Exist");
           map.put(STATUS, FAIL);
           map.put(TIMESATMP, LocalTime.now());
           entity = new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
           return entity;
       }
   }

   @GetMapping("/getUser")
   public List<User> getUser(){
        List<User> list= userRepo.findAll();
        return list;
   }


    @GetMapping("/getOtp/{mobileNo}")
    public ResponseEntity<?> getOtp(@PathVariable String mobileNo){
       User user=userRepo.findByMobileNo(mobileNo);
       if (user!=null) {
           code = otpRepo.generateOtp();
           Map<String, Object> map = new HashMap<>();
           ResponseEntity<Map<String, Object>> entity = null;
//           user.setOtpNo(code);
           userRepo.save(user);
           map.put(MESSAGE, "OTP Generated Successfully");
           map.put(CODE,code);
           map.put("MobileNo", user.getMobileNo());
           map.put(STATUS, SUCCESS);
           map.put(TIMESATMP, LocalTime.now());
           entity = new ResponseEntity<>(map, HttpStatus.OK);
           return entity;
       }
       else {
           Map<String, Object> map = new HashMap<>();
           ResponseEntity<Map<String, Object>> entity = null;
           map.put(MESSAGE, "MobileNo Not Exist");
           map.put(STATUS, FAIL);
           map.put(TIMESATMP, LocalTime.now());
           entity = new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
           return entity;
       }
    }

}
