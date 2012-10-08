/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RIMASS.Model.Message.Validator;


import RIMASS.Model.Message.ChangePasswordMessage;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author hadriw
 */
@Component
public class ChangePasswordValidator implements Validator{

    public boolean supports(Class<?> clazz) {
        return ChangePasswordMessage.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "oldpassword", "oldpassword.notblank", "The old password can't be blank.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newpassword", "newpassword.notblank", "The old password can't be blank.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmnewpassword", "confirmnewpassword.notblank", "The old password can't be blank.");
        if (errors.hasErrors()) return;
        ChangePasswordMessage cpm = (ChangePasswordMessage) target;
        if (!cpm.getNewpassword().equals(cpm.getConfirmnewpassword())){
            errors.rejectValue("confirmnewpassword", "confirmnewpassword.notmatch", "The new passwords do not match.");
            return;
        }
        if (!cpm.getRealpassword().equals(cpm.getOldpassword())){
            errors.rejectValue("oldpassword", "oldpassword.notmatch","The old password does not match. Please re-type it.");
        }
    }
}
