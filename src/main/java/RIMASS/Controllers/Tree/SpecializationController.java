/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RIMASS.Controllers.Tree;

import RIMASS.Domain.document.Term;
import RIMASS.Model.query.management.QueryManager;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author hadriw
 */

@Controller
public class SpecializationController {
    private final static int DEFAULT_NUMBER_OF_ELEMENTS = 5;
    
    @Autowired
    private QueryManager queryManager;

    public SpecializationController(){}

    @RequestMapping(value="/query/getSpecializations.xml", method=RequestMethod.POST)
    public ModelAndView getSpecializations(@RequestParam("nodeId") String nodeId,
                                           @RequestParam("keyword") String key,
                                           @RequestParam(value="n",required=false) Integer nElements,
                                           ModelMap model){
        if (key == null) key = "";
        model.put("nodeID", nodeId);

        int numberToRet;
        if (nElements != null) numberToRet = nElements.intValue();
        else numberToRet = DEFAULT_NUMBER_OF_ELEMENTS;
        //try{
            List<Term> esps = queryManager.getGraphicQueryEditor().getSpecializations(Long.parseLong(nodeId), key);
            model.put("nesp",esps.size());
            model.put("esps",esps.subList(0,(esps.size()<numberToRet)?esps.size():numberToRet));
            model.put("nshowed", (esps.size()<numberToRet)?esps.size():numberToRet);
        /*}catch(Exception e){
            model.put("nesp", 0);
            model.put("esps", null);
            model.put("nshowed", 0);
        }*/
        return new ModelAndView("listEsp","model", model);
    }

    @RequestMapping(value="/query/specialize.html", method=RequestMethod.POST)
    public ModelAndView specialize(@RequestParam("nodeID") String nodeId,
                                        @RequestParam("cat") String cat,
                                        ModelMap model){

        queryManager.getGraphicQueryEditor().specializeQueryNode(Long.parseLong(nodeId), Long.parseLong(cat));
        model.put("tree", queryManager.getGraphicQueryEditor().getCurrentQuery().getRoots());
        queryManager.getQueryExecuter().runQuery();
        return new ModelAndView("createTree","model",model);
    }

    @Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public void setQueryManager(QueryManager qm){
        queryManager = qm;
    }
}
