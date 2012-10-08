/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RIMASS.Controllers.Tree;

import RIMASS.Model.query.management.QueryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author hadriw
 */
@Controller
public class HistoryController {
    private final static int DEFAULT_NUMBER_OF_ELEMENTS = 5;

    @Autowired
    private QueryManager queryManager;

    public HistoryController(){}

    @RequestMapping(value="/query/undo.html", method=RequestMethod.POST)
    public ModelAndView undo(ModelMap model){
        queryManager.getGraphicQueryEditor().getHistory().undoOperation();
        model.put("tree", queryManager.getGraphicQueryEditor().getCurrentQuery().getRoots());
        queryManager.getQueryExecuter().runQuery();
        return new ModelAndView("createTree","model",model);
    }

    @RequestMapping(value="/query/redo.html", method=RequestMethod.POST)
    public ModelAndView redo(ModelMap model){
        queryManager.getGraphicQueryEditor().getHistory().redoOperation();
        model.put("tree", queryManager.getGraphicQueryEditor().getCurrentQuery().getRoots());
        queryManager.getQueryExecuter().runQuery();
        return new ModelAndView("createTree","model",model);
    }

    @Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public void setQueryManager(QueryManager qm){
        queryManager = qm;
    }
}
