/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function QueryManager(){
    this.queue = new Queue();
    this.block = false;
    this.addRestriction = function(param, handler){
        var q = new Query(param, handler, "query/addRestriction.html", "POST", "Adding selected restriction...", this.doCall);
        this.queue.enqueue(q);
        if (!this.block) this.fire();
    };

    this.removeNodeComplete = function(param, handler){
        var q = new Query(param, handler, "query/delNode.html", "POST","Deleting node...", this.doCall);
        this.queue.enqueue(q);
        if (!this.block) this.fire();
    };

    this.removeTree = function(param, handler){
        var q = new Query(param, handler, "query/delTree.html","POST", "Deleting node...", this.doCall);
        this.queue.enqueue(q);
        if (!this.block) this.fire();
    };

    this.getGeneralizations = function(param, handler){
        var q = new Query(param, handler, "query/getGeneralizations.xml","POST", "Getting generalizations...", this.doCallXML);
        this.queue.enqueue(q);
        if (!this.block) this.fire();
    };

    this.getRestrictions = function(param, handler){
        var q = new Query(param, handler, "query/getRestrictions.xml","POST", "Getting Restrictions...", this.doCallXML);
        this.queue.enqueue(q);
        if (!this.block) this.fire();
    };

    this.getSpecializations = function(param, handler){
        var q = new Query(param, handler, "query/getSpecializations.xml","POST", "Getting Specializations...", this.doCallXML);
        this.queue.enqueue(q);
        if (!this.block) this.fire();
    };

    this.moveNodeTo = function(param, handler){
        var q = new Query(param, handler, "query/moveNode.html", "POST","Moving node...", this.doCall);
        this.queue.enqueue(q);
        if (!this.block) this.fire();
    };

    this.moveRootToNode = function(param, handler){
        var q = new Query(param, handler, "query/moveRoot.html", "POST","Moving node...", this.doCall);
        this.queue.enqueue(q);
        if (!this.block) this.fire();
    };

    this.moveNodeToRoot = function(param, handler){
        var q = new Query(param, handler, "query/moveNodeRoot.html", "POST","Moving node...", this.doCall);
        this.queue.enqueue(q);
        if (!this.block) this.fire();
    };

    this.specialize = function(param, handler){
        var q = new Query(param, handler, "query/specialize.html", "POST","Specializing selected node...", this.doCall);
        this.queue.enqueue(q);
        if (!this.block) this.fire();
    };

    this.generalize = function(param, handler){
        var q = new Query(param, handler, "query/generalize.html", "POST","Generalizing selected node...", this.doCall);
        this.queue.enqueue(q);
        if (!this.block) this.fire();
    };

    this.getWholeTree = function(param, handler){
        var q = new Query(param, handler, "query/getRels.html", "POST","Obtaining Tree...", this.doCall);
        this.queue.enqueue(q);
        if (!this.block) this.fire();
    };

    this.getDocuments = function(param, handler){
        var q = new Query(param, handler, "query/getDocs.html", "POST","Obtaining documents...", this.doCall);
        this.queue.enqueue(q);
        if (!this.block) this.fire();
    };

    this.addNewRoot = function(param, handler){
        var q = new Query(param, handler, "query/addRoot.html", "POST","Creating new root...", this.doCall);
        this.queue.enqueue(q);
        if (!this.block) this.fire();
    };

    this.undo = function(param, handler){
        var q = new Query(param, handler, "query/undo.html", "POST","Undoing the last operation...", this.doCall);
        this.queue.enqueue(q);
        if (!this.block) this.fire();
    };

    this.redo = function(param, handler){
        var q = new Query(param, handler, "query/redo.html", "POST","Redoing the last operation...", this.doCall);
        this.queue.enqueue(q);
        if (!this.block) this.fire();
    };

    this.fire = function(){
        this.block = true;
        if (this.queue.isEmpty()){
            this.block = false;
            return;
        }
        var q = this.queue.dequeue();
        q.protocol(q);
    };

    this.doCall = function(q){
        var ajax=createCaller();
        ajax.open(q.method, q.address, true);
        var loadingForm = new createLoadingOnTree(q.message);
        var errorWin = new errorWindow();
        ajax.onreadystatechange = function(){
                if (ajax.readyState==1) {
                    loadingForm.open();
                }
                else if (ajax.readyState==4){
                      if(ajax.status==200){
                            q.handler.innerHTML=this.responseText;
                      }else{
                          if (ajax.status==400){
                                errorWin.display("Bad Request","Data forma incorrect. Please correct the data and try again.", 300);
                          }else if (ajax.status==403){
                                errorWin.display("Unauthorized","Access to this page is forbidden. Log in and try again.", 300);
                          }else if (ajax.status==404){
                                errorWin.display("Not found","The page doesn't exist.",300);
                          }else if (ajax.status==500){
                                errorWin.display("Server error","We apologize, the server has got an internal error. Sorry, please try again later.",300);
                          }else{
                              errorWin.display("Unknown error","There is an error that the server couldn't handle. Try again later.",300);
                          }
                      }
                      document.manager.fire();
                      loadingForm.close();
                }
        };
        ajax.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
        ajax.send(q.param);
    };

    this.doCallXML = function(q){
        var ajax=createCaller();
        ajax.open(q.method, q.address, true);
        var loadingForm = new createLoadingOnTree(q.message);
        var errorWin = new errorWindow();
        ajax.onreadystatechange = function(){
                if (ajax.readyState==1) {
                    loadingForm.open();
                }
                else if (ajax.readyState==4){
                      if(ajax.status==200){
                            q.handler(ajax.responseXML.documentElement);
                      }else{
                          if (ajax.status==400){
                                errorWin.display("Bad Request","Data forma incorrect. Please correct the data and try again.", 300);
                          }else if (ajax.status==403){
                                errorWin.display("Unauthorized","Access to this page is forbidden. Log in and try again.", 300);
                          }else if (ajax.status==404){
                                errorWin.display("Not found","The page doesn't exist.",300);
                          }else if (ajax.status==500){
                                errorWin.display("Server error","We apologize, the server has got an internal error. Sorry, please try again later.",300);
                          }else{
                              errorWin.display("Unknown error","There is an error that the server couldn't handle. Try again later.",300);
                          }
                      }
                      document.manager.fire();
                      loadingForm.close();
                }
        };
        ajax.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
        ajax.send(q.param);
    };
}
