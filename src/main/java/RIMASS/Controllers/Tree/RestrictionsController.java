/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RIMASS.Controllers.Tree;

import RIMASS.Domain.query.base.Restriction;
import RIMASS.Model.query.management.QueryManager;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class RestrictionsController {
    private final static int DEFAULT_NUMBER_OF_ELEMENTS = 5;

    @Autowired
    private QueryManager queryManager;

    public RestrictionsController(){}

    @RequestMapping(value="/query/getRestrictions.xml", method=RequestMethod.POST)
    public ModelAndView getRestrictions(@RequestParam("nodeId") String nodeId,
                                        @RequestParam("keyword") String key,
                                        @RequestParam(value="n",required=false) Integer nElements,
                                        ModelMap model){
        if (key == null) key = "";
        model.put("nodeID", nodeId);

        int numberToRet;
        if (nElements != null) numberToRet = nElements.intValue();
        else numberToRet = DEFAULT_NUMBER_OF_ELEMENTS;
        try{
            List<Restriction> rests = queryManager.getGraphicQueryEditor().getRestrictions(Long.parseLong(nodeId), key);
            model.put("nrest",rests.size());
            model.put("rest",rests.subList(0,(rests.size()<numberToRet)?rests.size():numberToRet));
            model.put("nshowed", (rests.size()<numberToRet)?rests.size():numberToRet);
        }catch(Exception e){
             Logger.getLogger(RestrictionsController.class.getName()).log(Level.SEVERE, null, e);
            model.put("nrest", 0);
            model.put("rest", null);
            model.put("nshowed", 0);
        }
        return new ModelAndView("listRest","model", model);
    }

    @RequestMapping(value="/query/addRestriction.html", method=RequestMethod.POST)
    public ModelAndView addRestriction(@RequestParam("nodeID") String nodeId,
                                        @RequestParam("rel") String rel,
                                        @RequestParam("term") String term,
                                        ModelMap model){

        queryManager.getGraphicQueryEditor().addRestriction(Long.parseLong(nodeId), Long.parseLong(rel), Long.parseLong(term));
        model.put("tree", queryManager.getGraphicQueryEditor().getCurrentQuery().getRoots());
        queryManager.getQueryExecuter().runQuery();
        return new ModelAndView("createTree","model",model);
    }

    @RequestMapping(value="/query/delNode.html", method=RequestMethod.POST)
    public ModelAndView delNode(@RequestParam("nodeId") String nodeId,
                                @RequestParam("relNodeId") String relNodeId,
                                @RequestParam("fatherId") String fatherId,
                                ModelMap model){
        queryManager.getGraphicQueryEditor().removeRestriction(Long.parseLong(fatherId),
                                                                Long.parseLong(relNodeId),
                                                                Long.parseLong(nodeId));
        model.put("tree", queryManager.getGraphicQueryEditor().getCurrentQuery().getRoots());
        queryManager.getQueryExecuter().runQuery();
        return new ModelAndView("createTree","model",model);
    }

    @Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public void setQueryManager(QueryManager qm){
        queryManager = qm;
    }
}
