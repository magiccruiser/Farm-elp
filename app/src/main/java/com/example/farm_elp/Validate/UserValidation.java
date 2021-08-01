package com.example.farm_elp.Validate;

import android.util.Patterns;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.farm_elp.Activity.LoginRegistration.Login;
import com.example.farm_elp.Activity.LoginRegistration.Registration;
import com.example.farm_elp.R;

public class UserValidation {

    AwesomeValidation awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

    public boolean validateUser(Registration registration){
        awesomeValidation.addValidation(registration, R.id.fullName, RegexTemplate.NOT_EMPTY,R.string.invalid_name);

        awesomeValidation.addValidation(registration, R.id.address, RegexTemplate.NOT_EMPTY,R.string.invalid_address);

        awesomeValidation.addValidation(registration, R.id.adhaarID, "^[0-9]{12}$",R.string.invalid_adhaar);

        awesomeValidation.addValidation(registration, R.id.phone_number, Patterns.PHONE,R.string.invalid_phone);

        awesomeValidation.addValidation(registration, R.id.email, Patterns.EMAIL_ADDRESS
                ,R.string.invalid_email);

        awesomeValidation.addValidation(registration, R.id.confirmpass,R.id.createpass, R.string.invalid_confirmpass);

        awesomeValidation.addValidation(registration, R.id.profession, RegexTemplate.NOT_EMPTY, R.string.validProfesssion);

        awesomeValidation.addValidation(registration, R.id.createpass, "^.{8,}$",R.string.invalid_pass);

        return awesomeValidation.validate();
    }

    public boolean checkLoginCredentials(Login login){

        awesomeValidation.addValidation(login, R.id.login_email, Patterns.EMAIL_ADDRESS
                ,R.string.invalid_email);
        awesomeValidation.addValidation(login, R.id.createpass, "^.{8,}$",R.string.invalid_pass);

        return awesomeValidation.validate();
    }
}
