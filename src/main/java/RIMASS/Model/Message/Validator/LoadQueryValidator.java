/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RIMASS.Model.Message.Validator;

import RIMASS.Model.Message.LoadQueryMessage;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author hadriw
 */
@Component
public class LoadQueryValidator implements Validator{

    public boolean supports(Class<?> clazz) {
        return LoadQueryMessage.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "selected", "selected.notblank", "You must select one query to load!!");
    }

}
