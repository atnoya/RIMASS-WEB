/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RIMASS.Controllers.Tree;

import java.io.Serializable;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import RIMASS.Domain.query.base.GraphicQuery;
import RIMASS.Domain.query.base.QueryNode;
import RIMASS.Domain.query.base.TextualQuery;
import RIMASS.Model.query.management.QueryManager;
import RIMASS.Model.query.management.TermNotFoundException;

/**
 *
 * @author root
 */

@Controller
public class TreeController implements Serializable{

    @Autowired
    private QueryManager queryManager;

    TreeController(){
    }

    @RequestMapping(value="/query/getDocs.html",method=RequestMethod.POST)
    public ModelAndView getDocs(ModelMap model){

            model.put("docs", queryManager.getAllDocuments());
            return new ModelAndView("listDocs","model",model);
    }

    @RequestMapping(value="/query/getRels.html", method=RequestMethod.POST)
    public ModelAndView getRels(ModelMap model){
    	
        GraphicQuery currentQuery = queryManager.getGraphicQueryEditor().getCurrentQuery();
		model.put("tree", currentQuery == null ? new ArrayList<QueryNode>() : currentQuery.getRoots());
        
        return new ModelAndView("createTree","model",model);
    }

    @RequestMapping(value="/query/delTree.html", method=RequestMethod.POST)
    public ModelAndView delTree(@RequestParam("nodeId") String nodeId, ModelMap model){
        queryManager.getGraphicQueryEditor().removeRoot(Long.parseLong(nodeId));
        queryManager.getQueryExecuter().runQuery();
        return getRels(model);
    }

    @RequestMapping(value="/query/moveNode.html", method=RequestMethod.POST)
    public ModelAndView moveNode(@RequestParam("origenID") String nodeId,
                                    @RequestParam("origenModID") String nodeModId,
                                    @RequestParam("origenFatherID") String nodeFatherId,
                                    @RequestParam("toID") String toId,
                                    ModelMap model){

        queryManager.getGraphicQueryEditor().moveNode(Long.parseLong(nodeFatherId),
                            Long.parseLong(nodeModId), Long.parseLong(nodeId),Long.parseLong(toId));
        return getRels(model);
    }

    @RequestMapping(value="/query/moveRoot.html", method=RequestMethod.POST)
    public ModelAndView moveRoot(@RequestParam("origenID") String nodeId,
                                    @RequestParam("toID") String toId,
                                    ModelMap model){

        queryManager.getGraphicQueryEditor().moveRootToNode(Long.parseLong(nodeId),Long.parseLong(toId));
        queryManager.getQueryExecuter().runQuery();
        return getRels(model);
    }

    @RequestMapping(value="/query/moveNodeRoot.html", method=RequestMethod.POST)
    public ModelAndView moveNodeRoot(@RequestParam("origenID") String nodeId,
                                     @RequestParam("origenModID") String nodeModId,
                                     @RequestParam("fatherID") String fatherId,
                                     ModelMap model){
        queryManager.getGraphicQueryEditor().moveNodeToRoot(Long.parseLong(nodeModId),
                            Long.parseLong(nodeId), Long.parseLong(fatherId));
        queryManager.getQueryExecuter().runQuery();
        return getRels(model);
    }


    @RequestMapping(value="/query/addRoot.html", method=RequestMethod.POST)
    public ModelAndView addRoot(@RequestParam("term") String term, ModelMap model){
        try {
            queryManager.getGraphicQueryEditor().addRoot(term);
        } catch (TermNotFoundException ex) {
            //TODO SOMETHING COOL
        }
        queryManager.getQueryExecuter().runQuery();
        return getRels(model);
    }

    @RequestMapping(value="/index.html", method=RequestMethod.GET)
    public String startSearch(@RequestParam(value="phrase", required=false) String phrase, ModelMap model) throws Exception{
        if (phrase !=null){
            queryManager.createGraphicQuery(new TextualQuery(1,phrase));
            queryManager.getQueryExecuter().runQuery();
        }
        return "index";
    }

    @Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public void setQueryManager(QueryManager qm){
        queryManager = qm;
    }
}
