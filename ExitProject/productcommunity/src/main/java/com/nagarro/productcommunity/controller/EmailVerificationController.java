package com.nagarro.productcommunity.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import java.util.Random;

/**
 * This function sends mail to the user for verification
 * @author pranshusahu
 *
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class EmailVerificationController
{
	 @Autowired
	 private JavaMailSender javaMailSender;
	    
	 @GetMapping(value = "/send-verification-email")
	 public ResponseEntity<String> sendEmail(@RequestParam String userEmail)
	 {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(userEmail);
	    msg.setSubject("Verification code for product community");
	    Random rnd = new Random();
	    String number=String.format("%06d", rnd.nextInt(999999));
        msg.setText("Your verification code is "+number);
        javaMailSender.send(msg);
		return new ResponseEntity<String>(number,HttpStatus.OK);
	 }
}
