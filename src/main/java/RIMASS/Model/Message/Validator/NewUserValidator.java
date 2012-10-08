/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RIMASS.Model.Message.Validator;

import RIMASS.Model.Message.NewUserMessage;
import RIMASS.Domain.user.dao.UserDao;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author hadriw
 */
@Component
public class NewUserValidator implements Validator{

    @Autowired
    private UserDao userDao;

    public NewUserValidator(){
        super();
    }

    public boolean supports(Class<?> clazz) {
        return NewUserMessage.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        NewUserMessage u = (NewUserMessage) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "email.notblank", "Email is required.");
        if (errors.getFieldError("email") == null){
            Pattern p = Pattern.compile("^[A-Za-z0-9\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\_\\`\\{\\|\\}\\~]+(.[A-Za-z0-9\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\_\\`\\{\\|\\}\\~]+)*@[A-Za-z0-9]+(\\-[A-Za-z0-9]+)*(.[A-Za-z0-9]+(\\-[A-Za-z0-9]+)*)*$");
            Matcher m = p.matcher(u.getEmail());
            if (!m.find()){
                errors.rejectValue("email","email.badformat", "Email format is wrong.");
            }

            if (errors.getFieldError("email") == null && !u.getEmail().equals(u.getConfirmemail()))
                errors.rejectValue("confirmemail", "confirmemail.notmatch", "Email and Confirm Email are not equals");

            if (errors.getFieldError("email") == null && userDao.getUserByEmail(u.getEmail()) != null){
                errors.rejectValue("email", "email.used", "Email is already registered. Please select a different one.");
            }
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.notblank", "Password is required.");
        if (errors.getFieldError("password") == null && !u.getPassword().equals(u.getConfirmpassword()))
                errors.rejectValue("password", "password.notmatch", "Password and Confirm Password are not equals");
    }

    /**
     * @param userDao the userDao to set
     */
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

}
