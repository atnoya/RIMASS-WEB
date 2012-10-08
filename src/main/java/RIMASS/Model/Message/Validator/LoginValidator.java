/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RIMASS.Model.Message.Validator;

import RIMASS.Model.Message.LoginMessage;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author hadriw
 */
@Component
public class LoginValidator implements Validator {

    public boolean supports(Class<?> clazz) {
        return LoginMessage.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "email.notblank", "Email is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.notblank", "Password is required");
    }

}
