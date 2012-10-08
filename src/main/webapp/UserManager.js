/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function UserManager(){
    this.queue = new Queue();
    this.block = false;
    this.newUserForm = function(){
        var q = new Query(null, null, "user/formNewUser.html", "GET","Loading user form...", this.doCall);
        this.queue.enqueue(q);
        if (!this.block) this.fire();
    };

    this.newUser = function(param){
        var q = new Query(param, null, "user/newUser.html", "POST","Checking data...", this.doCall);
        this.queue.enqueue(q);
        if (!this.block){
          this.fire();
        }
    };

    this.changePassForm = function(){
        var q = new Query(null, null, "user/formChangePass.html", "GET","Loading user form...", this.doCall);
        this.queue.enqueue(q);
        if (!this.block) this.fire();
    };

    this.changePass = function(param){
        var q = new Query(param, null, "user/changePass.html", "POST","Checking data...", this.doCall);
        this.queue.enqueue(q);
        if (!this.block){
          this.fire();
        }
    };

    this.getMenu = function(handler){
        var q = new Query(null, handler, "user/getMenu.html", "GET", null, this.doInvisibleCall);
        this.queue.enqueue(q);
        if (!this.block){
          this.fire();
        }
    };

    this.logout = function(handler){
        var q = new Query(null, handler, "user/logout.html", "GET", null, this.doInvisibleCall);
        this.queue.enqueue(q);
        if (!this.block){
          this.fire();
        }
    };

    this.login = function(param,handler){
        var q = new Query(param, handler, "user/login.html", "POST", null, this.doInvisibleCall);
        this.queue.enqueue(q);
        if (!this.block){
          this.fire();
        }
    };

    this.newUserQueryForm = function(){
        var q = new Query(null, null, "user/formNewQuery.html", "GET","Loading save query form...", this.doCall);
        this.queue.enqueue(q);
        if (!this.block) this.fire();
    };

    this.storeQuery = function(param){
        var q = new Query(param,null,"user/storeQuery.html","POST", "Storing query...", this.doCall);
        this.queue.enqueue(q);
        if (!this.block) this.fire();
    };

    this.doOverwriteQuery = function(param){
        var q = new Query(param,null,"user/overwriteQuery.html","POST", "Storing query...", this.doCall);
        this.queue.enqueue(q);
        if (!this.block) this.fire();
    };
    this.doCancelOverwriteQuery = function(){
        this.newUserQueryForm();
    };
    this.loadQueryForm = function(){
        var q = new Query(null, null, "user/loadQueryForm.html", "GET","Loading queries...", this.doCall);
        this.queue.enqueue(q);
        if (!this.block) this.fire();
    };

    this.loadQuery = function(param){
        var q = new Query(param,null,"user/loadQuery.html","POST", "Loading query...", this.doCall);
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

    this.doInvisibleCall = function(q){
        var ajax=createCaller();
        ajax.open(q.method, q.address, true);
        var errorWin = new errorWindow();
        ajax.onreadystatechange = function(){
                if (ajax.readyState==1) {
                }
                else if (ajax.readyState==4){
                      if(ajax.status==200){
                            q.handler.innerHTML = ajax.responseText;
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
                      document.usermanager.fire();
                }
        };
        ajax.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
        ajax.send(q.param);
    };

    this.doCall = function(q){
        var ajax=createCaller();
        ajax.open(q.method, q.address, true);
        var errorWin = new errorWindow();
        if (document.userForm == undefined) document.userForm = new UserForm();
        ajax.onreadystatechange = function(){
                if (ajax.readyState==1) {
                    document.userForm.openLoading(q.message,200);
                }
                else if (ajax.readyState==4){
                      if(ajax.status==200){
                            document.userForm.setContents(ajax.responseText,400);
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
                          document.userForm.close();
                      }
                      document.usermanager.fire();
                }
        };
        ajax.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
        ajax.send(q.param);
    };

    this.doCallQuery = function(q){
        var ajax=createCaller();
        ajax.open(q.method, q.address, true);
        var errorWin = new errorWindow();
        if (document.queryForm == undefined) document.queryForm = new QueryForm();
        ajax.onreadystatechange = function(){
                if (ajax.readyState==1) {
                    document.queryForm.openLoading(q.message,200);
                }
                else if (ajax.readyState==4){
                      if(ajax.status==200){
                            document.queryForm.setContents(ajax.responseText,400);
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
                          document.queryForm.close();
                      }
                      document.usermanager.fire();
                }
        };
        ajax.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
        ajax.send(q.param);
    };
}
