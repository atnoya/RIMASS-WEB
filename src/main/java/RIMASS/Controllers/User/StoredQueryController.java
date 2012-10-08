/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RIMASS.Controllers.User;

import RIMASS.Domain.user.UserStoredQuery;
import RIMASS.Model.Message.LoadQueryMessage;
import RIMASS.Model.Message.Validator.LoadQueryValidator;
import RIMASS.Model.Message.SaveQueryMessage;
import RIMASS.Model.Message.Validator.SaveQueryValidator;
import RIMASS.Model.query.management.QueryManager;
import RIMASS.Model.query.management.UserManager;
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
public class StoredQueryController {

    @Autowired
    private UserManager userManager;

    @Autowired
    private QueryManager queryManager;
    
    @Autowired
    private SaveQueryValidator newUserQueryValidator;

    @Autowired
    private LoadQueryValidator loadQueryValidator;

    public StoredQueryController(){}
    /**
     * @param newUserQueryValidator the newUserQueryValidator to set
     */
    public void setNewUserQueryValidator(SaveQueryValidator newUserQueryValidator) {
        this.newUserQueryValidator = newUserQueryValidator;
    }

    @Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public void setUserManager(UserManager userManager){
        this.userManager = userManager;
    }
    
    @Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public void setQueryManager(QueryManager qm){
        queryManager = qm;
    }

    @RequestMapping(value="/user/formNewQuery.html", method=RequestMethod.GET)
    public ModelAndView storeQueryForm(){
        if (userManager.getUserData()==null) return new ModelAndView("sessionTimeOut");
        SaveQueryMessage sq = new SaveQueryMessage();
        sq.setQueries(userManager.getStoredQueries());
        return new ModelAndView("saveQueryForm", "saveQueryMessage", sq);
    }

    @RequestMapping(value="/user/storeQuery.html", method=RequestMethod.POST)
    public ModelAndView storeQuery(@ModelAttribute("saveQueryMessage") SaveQueryMessage newquery,
                                   BindingResult results, ModelMap model){
        if (userManager.getUserData() == null) return new ModelAndView("sessionTimeOut");
        newUserQueryValidator.validate(newquery, results);
        if (results.hasErrors()){
            SaveQueryMessage sq = new SaveQueryMessage();
            sq.setQueries(userManager.getStoredQueries());
            return new ModelAndView("saveQueryForm", "saveQueryMessage", sq);
        }

        if (userManager.checkQueryNameExists(newquery.getName())){
            return new ModelAndView("saveQueryOverwriteForm", "saveQueryMessage", newquery);
        }


        if (!userManager.storeQuery(newquery.getName(), newquery.getDescription(), queryManager.getAllDocuments().size(), queryManager.getCurrentQuery())){
            model.put("error","Can't save the query now. Please try again later.");
            SaveQueryMessage sq = new SaveQueryMessage();
            sq.setQueries(userManager.getStoredQueries());
            return new ModelAndView("saveQueryForm", "saveQueryMessage", sq);
        }else{
            return new ModelAndView("querySaveSuccess");
        }
    }

    @RequestMapping(value="/user/overwriteQuery.html", method=RequestMethod.POST)
    public ModelAndView overwriteQuery(@ModelAttribute("saveQueryMessage") SaveQueryMessage newquery,
                                   BindingResult results, ModelMap model){
        //TODO
        if (userManager.getUserData() == null) return new ModelAndView("sessionTimeOut");
        newUserQueryValidator.validate(newquery, results);
        if (results.hasErrors()){
            SaveQueryMessage sq = new SaveQueryMessage();
            sq.setQueries(userManager.getStoredQueries());
            return new ModelAndView("saveQueryForm", "saveQueryMessage", sq);
        }

        if (!userManager.overwriteQuery(newquery.getName(), newquery.getDescription(), queryManager.getAllDocuments().size(), queryManager.getCurrentQuery())){
            model.put("error","Can't save the query now. Please try again later.");
            SaveQueryMessage sq = new SaveQueryMessage();
            sq.setQueries(userManager.getStoredQueries());
            return new ModelAndView("saveQueryForm", "saveQueryMessage", sq);
        }else{
            return new ModelAndView("querySaveSuccess");
        }
    }

    @RequestMapping(value="/user/loadQueryForm.html", method=RequestMethod.GET)
    public ModelAndView loadQueryForm(){
        if (userManager.getUserData()==null) return new ModelAndView("sessionTimeOut");
        LoadQueryMessage lq = new LoadQueryMessage();
        lq.setQueries(userManager.getStoredQueries());
        return new ModelAndView("loadQueryForm", "loadQueryMessage", lq);
    }

    @RequestMapping(value="/user/loadQuery.html", method=RequestMethod.POST)
    public ModelAndView loadQuery(@ModelAttribute("loadQueryMessage") LoadQueryMessage query,
                                   BindingResult results, ModelMap model){
        if (userManager.getUserData() == null) return new ModelAndView("sessionTimeOut");
        loadQueryValidator.validate(query, results);
        if (results.hasErrors()){
            LoadQueryMessage lq = new LoadQueryMessage();
            lq.setQueries(userManager.getStoredQueries());
            return new ModelAndView("loadQueryForm","loadQueryMessage",lq);
        }
        UserStoredQuery usq = userManager.loadQuery(query.getSelected());
        if (usq == null){
            model.put("error","A mistake has just happened. It seems the query that you have selected is not avaliable. Sorry.");
            LoadQueryMessage lq = new LoadQueryMessage();
            lq.setQueries(userManager.getStoredQueries());
            return new ModelAndView("loadQueryForm","loadQueryMessage",lq);
        }else{
            this.queryManager.loadGraphicQuery(usq.getQuery());
            return new ModelAndView("loadQuerySuccess");
        }
    }
}
