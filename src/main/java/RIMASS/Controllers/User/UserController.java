/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RIMASS.Controllers.User;

import RIMASS.Domain.user.User;
import RIMASS.Model.Message.ChangePasswordMessage;
import RIMASS.Model.Message.Validator.ChangePasswordValidator;
import RIMASS.Model.Message.LoginMessage;
import RIMASS.Model.Message.Validator.LoginValidator;
import RIMASS.Model.query.management.UserManager;
import RIMASS.Model.Message.NewUserMessage;
import RIMASS.Model.Message.Validator.NewUserValidator;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author hadriw
 */
@Controller
public class UserController implements Serializable{

    @Autowired
    private UserManager userManager;

    @Autowired
    private LoginValidator loginValidator;

    @Autowired
    private NewUserValidator newUserValidator;

    @Autowired
    private ChangePasswordValidator changePasswordValidator;

    public UserController(){
    }

    @Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public void setUserManager(UserManager userManager){
        this.userManager = userManager;
    }

    public void setLoginValidator(LoginValidator loginValidator){
        this.loginValidator = loginValidator;
    }

    public void setNewUserValidator(NewUserValidator newUserValidator){
        this.newUserValidator = newUserValidator;
    }

    public void setChangePasswordValidator(ChangePasswordValidator changePasswordValidator){
        this.changePasswordValidator = changePasswordValidator;
    }

    @RequestMapping(value="/user/formNewUser.html", method=RequestMethod.GET)
    public ModelAndView newFormUser(){
        return new ModelAndView("newUserForm", "newUserMessage", new NewUserMessage());
    }
    
    @RequestMapping(value="/user/newUser.html", method=RequestMethod.POST)
    public ModelAndView newUser(@ModelAttribute("newUserMessage") NewUserMessage newuser,
                                BindingResult result, ModelMap model){
        newUserValidator.validate(newuser, result);
        if (result.hasErrors()){
            return new ModelAndView("newUserForm");
        }

        if (!userManager.createNewUser(new User(newuser.getEmail(),newuser.getPassword()))){
            model.put("error","Can't insert the user now. Please try again later.");
            return new ModelAndView("newUserForm");
        }else return new ModelAndView("thanksForRegistering");
    }

    @RequestMapping(value="/user/formChangePass.html", method=RequestMethod.GET)
    public ModelAndView getChangePassForm(){
        return new ModelAndView("changePasswordForm", "changePasswordMessage", new ChangePasswordMessage());
    }
    
    @RequestMapping(value="/user/changePass.html", method=RequestMethod.POST)
    public ModelAndView changePassword(@ModelAttribute("changePasswordMessage") ChangePasswordMessage passmsg,
                                       BindingResult result, ModelMap model){
        if (userManager.getUserData() == null) return new ModelAndView("changePasswordForm", "changePasswordMessage", new ChangePasswordMessage());
        passmsg.setRealpassword(userManager.getUserData().getPassword());
        changePasswordValidator.validate(passmsg, result);
        if (result.hasErrors()){
            return new ModelAndView("changePasswordForm","changePasswordMessage", passmsg);
        }
        userManager.changePassword(passmsg.getNewpassword());
        return new ModelAndView("passwordChanged");
    }

    @RequestMapping(value="/user/getMenu.html", method=RequestMethod.GET)
    public ModelAndView getMenu(ModelMap model){
        if (userManager.getUserData() == null){
            model.put("logged", false);
        }else{
            model.put("logged", true);
            model.put("email", userManager.getUserData().getEmail());
        }
        return new ModelAndView("menuNavigation", "loginMessage", new LoginMessage());
    }

    @RequestMapping(value="/user/logout.html", method=RequestMethod.GET)
    public ModelAndView logout(ModelMap model){
        userManager.logout();
        model.put("logged", false);
        return new ModelAndView("menuNavigation", "loginMessage", new LoginMessage());
    }

    @RequestMapping(value="/user/login.html", method=RequestMethod.POST)
    public ModelAndView login(@ModelAttribute("loginMessage") LoginMessage login,
                              BindingResult result, ModelMap model){
        loginValidator.validate(login, result);
        if (result.hasErrors()){
            model.put("logged", false);
            return new ModelAndView("menuNavigation","loginMessage",login);
        }
        if (userManager.logInUser(login.getEmail(),login.getPassword())){
            model.put("logged", true);
            model.put("email", userManager.getUserData().getEmail());
        }else{
            model.put("error", "Either email or password are incorrect. Please try again.");
            model.put("logged",false);
        }
        return new ModelAndView("menuNavigation", "login", login);
    }
}
