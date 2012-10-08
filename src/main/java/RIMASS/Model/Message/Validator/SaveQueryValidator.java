/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RIMASS.Model.Message.Validator;

import RIMASS.Model.Message.SaveQueryMessage;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;



/**
 *
 * @author hadriw
 */
@Component
public class SaveQueryValidator implements Validator{

    public boolean supports(Class<?> clazz) {
        return SaveQueryMessage.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        SaveQueryMessage msg = (SaveQueryMessage) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.notblank", "Name can't be empty.");
    }

}
