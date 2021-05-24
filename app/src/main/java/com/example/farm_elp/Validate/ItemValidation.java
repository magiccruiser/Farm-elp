package com.example.farm_elp.Validate;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.farm_elp.MyProduct_Activity.ItemDetail;
import com.example.farm_elp.R;

public class ItemValidation {

    AwesomeValidation awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

    public boolean validateItem(ItemDetail itemDetail) {
        awesomeValidation.addValidation(itemDetail, R.id.item_name, RegexTemplate.NOT_EMPTY,R.string.not_empty);

        awesomeValidation.addValidation(itemDetail, R.id.item_Description, RegexTemplate.NOT_EMPTY,R.string.not_empty);

        awesomeValidation.addValidation(itemDetail, R.id.item_quantity, "^[0-9]{1,3}$",R.string.invalid_quantity);

        return awesomeValidation.validate();
    }
}
