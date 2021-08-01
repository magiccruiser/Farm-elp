package com.example.farm_elp.Validate;

import android.util.Patterns;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.farm_elp.Activity.DrawerActivity.MyProduct.ItemDetail;
import com.example.farm_elp.R;
import com.example.farm_elp.model.Item;

public class ItemValidation {

    AwesomeValidation awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

    public boolean validateItem(ItemDetail itemDetail, Item item) {
        awesomeValidation.addValidation(itemDetail, R.id.item_name, RegexTemplate.NOT_EMPTY,R.string.not_empty);

        awesomeValidation.addValidation(itemDetail, R.id.item_Description, RegexTemplate.NOT_EMPTY,R.string.not_empty);

        awesomeValidation.addValidation(itemDetail, R.id.item_quantity, "^[0-9]{1,3}$",R.string.invalid_quantity);

        awesomeValidation.addValidation(itemDetail, R.id.itemImage, Patterns.WEB_URL,R.string.enter_image);

        return awesomeValidation.validate();
    }
}
