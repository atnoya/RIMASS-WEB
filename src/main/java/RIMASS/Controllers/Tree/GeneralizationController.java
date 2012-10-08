/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RIMASS.Controllers.Tree;

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

import RIMASS.Domain.document.Term;
import RIMASS.Model.query.management.QueryManager;

/**
 *
 * @author hadriw
 */
@Controller
public class GeneralizationController {
    private final static int DEFAULT_NUMBER_OF_ELEMENTS = 5;

    @Autowired
    private QueryManager queryManager;

    public GeneralizationController(){}

    @RequestMapping(value="/query/getGeneralizations.xml", method=RequestMethod.POST)
    public ModelAndView getGeneralizations(@RequestParam("nodeId") String nodeId,
                                           @RequestParam("keyword") String key,
                                           @RequestParam(value="n",required=false) Integer nElements,
                                           ModelMap model){
        if (key == null) key = "";
        model.put("nodeID", nodeId);

        int numberToRet;
        if (nElements != null) numberToRet = nElements.intValue();
        else numberToRet = DEFAULT_NUMBER_OF_ELEMENTS;
        try{
            List<Term> gens = queryManager.getGraphicQueryEditor().getGeneralizations(Long.parseLong(nodeId), key);
            model.put("ngen",gens.size());
            model.put("gens",gens.subList(0,(gens.size()<numberToRet)?gens.size():numberToRet));
            model.put("nshowed", (gens.size()<numberToRet)?gens.size():numberToRet);
        }catch(Exception e){
            model.put("ngen", 0);
            model.put("gens", null);
            model.put("nshowed", 0);
        }
        return new ModelAndView("listGen","model", model);
    }

    @RequestMapping(value="/query/generalize.html", method=RequestMethod.POST)
    public ModelAndView generalize(@RequestParam("nodeID") String nodeId,
                                        @RequestParam("cat") String cat,
                                        ModelMap model){

        queryManager.getGraphicQueryEditor().generalizeQueryNode(Long.parseLong(nodeId), Long.parseLong(cat));
        model.put("tree", queryManager.getGraphicQueryEditor().getCurrentQuery().getRoots());
        queryManager.getQueryExecuter().runQuery();
        return new ModelAndView("createTree","model",model);
    }

    @Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public void setQueryManager(QueryManager qm){
        queryManager = qm;
    }
}
