/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function $(_1){
    return document.getElementById(_1);
}

function getSize() {
  var myWidth = 0, myHeight = 0;
  if( typeof( window.innerWidth ) == 'number' ) {
    //Non-IE
    myWidth = window.innerWidth;
    myHeight = window.innerHeight;
  } else if( document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight ) ) {
    //IE 6+ in 'standards compliant mode'
    myWidth = document.documentElement.clientWidth;
    myHeight = document.documentElement.clientHeight;
  } else if( document.body && ( document.body.clientWidth || document.body.clientHeight ) ) {
    //IE 4 compatible
    myWidth = document.body.clientWidth;
    myHeight = document.body.clientHeight;
  }
  return [myWidth, myHeight];
}

function actionMenuSearch(id){
    var el = $(id);
    if (el.opened == undefined || el.opened == false){
        el.opened = true;
        el.style.display = "block";
    }else{
        el.opened = false;
        el.style.display = "none";
    }
}

function actionMenu(id, idtitle){
    var elc = $(id);
    var elt = $(idtitle);
    var el = document.menuNavigation;
    if (el.opened == undefined || el.opened == true){
        el.opened = false;
        el.style.height = "30px";
        elc.style.display = "none";
        elt.className = "menuTitleMini";
    }else{
        el.opened = true;
        el.style.height = "auto";
        elc.style.display = "block";
        elt.className = "menuTitle";
    }
}

function backgroundNotifications(){
    this.div = document.createElement("div");
    this.div.className = "backgroundNotifications";
    this.getDisplay = function(){
        return this.div;
    };
}

function errorWindow(){
    this.backgroundNot = new backgroundNotifications();
    this.windowsOnAir = this.backgroundNot.getDisplay();
    this.errorWindow = document.createElement("div");
    this.errorWindow.className = "errorWindow";

    this.titleBox = document.createElement("div");
    this.titleBox.className = "title";
   
    this.contentsBox = document.createElement("div");
    this.contentsBox.className = "content";
    
    this.buttonBox = document.createElement("div");
    this.buttonBox.className = "button";

    this.buttonCancel = document.createElement("span");
    this.buttonCancel.className = "buttonCancel";
    this.buttonCancel.innerHTML = "Cancel";
    this.buttonCancel.target = this.windowsOnAir;
    this.buttonCancel.onclick = function(){
        document.getElementsByTagName('body')[0].removeChild(this.target);
    };
    
    this.buttonBox.appendChild(this.buttonCancel);

    this.display = function(title, message, width){
        this.titleBox.innerHTML = title;
        this.errorWindow.appendChild(this.titleBox);
        this.contentsBox.innerHTML = message;
        this.errorWindow.appendChild(this.contentsBox);
        this.errorWindow.appendChild(this.buttonBox);
        var innerSize = getSize();
        this.errorWindow.style.top = "150px";
        this.errorWindow.style.left = parseInt(innerSize[0]/2 - width/2) + "px";
        this.errorWindow.style.width = parseInt(width) + "px";
        this.windowsOnAir.appendChild(this.errorWindow);
        document.getElementsByTagName('body')[0].appendChild(this.windowsOnAir);
    };
}

function createLoadingOnTree(message){
    this.backgroundTree = document.createElement("div");
    this.backgroundTree.className = "backgroundTreeNotifications";

    this.boxLoading = document.createElement("div");
    this.boxLoading.className = "LoadingMessage";
    this.boxLoading.innerHTML = message;
    var innerSize = getSize();
    this.boxLoading.style.top = parseInt(innerSize[1]/2 - 50) + "px";
    
    this.close = function(){
        document.treeBox.removeChild(this.boxLoading);
        document.treeBox.removeChild(this.backgroundTree);
    };
    this.open = function(){
        document.treeBox.appendChild(this.boxLoading);
        document.treeBox.appendChild(this.backgroundTree);
    };
}

function onFocusTextBox(el){
    if (el.entered == undefined) el.value = "";
    el.entered = true;
}

function onBlurTextBox(el, text){
    if (el.value == "") {
        el.value = text;
        el.entered = undefined;
    }
}

function UserForm(){
    this.backgroundNot = new backgroundNotifications();
    this.windowOnAir = this.backgroundNot.getDisplay();
    
    this.userForm = document.createElement("div");
    this.userForm.className = "userForm";

    this.titleBox = document.createElement("div");
    this.titleBox.className = "title";

    this.contentsBox = document.createElement("div");
    this.contentsBox.className = "content";

    this.openLoading = function(message){
        this.contentsBox.innerHTML = message;
        if (this.userForm.opened != true){
            this.userForm.appendChild(this.contentsBox);
            this.userForm.className = "userFormLoading";
            this.userForm.style.top = "50px";
            this.windowOnAir.appendChild(this.userForm);
            document.getElementsByTagName('body')[0].appendChild(this.windowOnAir);
            this.userForm.opened = true;
        }
    };

    this.setContents = function(contents){
        this.titleBox.innerHTML = "User data...";
        this.userForm.insertBefore(this.titleBox,this.contentsBox);
        this.userForm.className = "userForm";
        this.contentsBox.innerHTML = contents;
        this.userForm.style.top = "50px";
    };

    this.close = function(){
        document.getElementsByTagName('body')[0].removeChild(this.windowOnAir);
        document.userForm = null;
    };
}


function QueryForm(){
    this.backgroundNot = new backgroundNotifications();
    this.windowOnAir = this.backgroundNot.getDisplay();

    this.queryForm = document.createElement("div");
    this.queryForm.className = "userForm";

    this.titleBox = document.createElement("div");
    this.titleBox.className = "title";

    this.contentsBox = document.createElement("div");
    this.contentsBox.className = "content";

    this.openLoading = function(message){
        this.contentsBox.innerHTML = message;
        if (this.queryForm.opened != true){
            this.queryForm.appendChild(this.contentsBox);
            this.queryForm.className = "userFormLoading";
            this.queryForm.style.top = "50px";
            this.windowOnAir.appendChild(this.queryForm);
            document.getElementsByTagName('body')[0].appendChild(this.windowOnAir);
            this.query.opened = true;
        }
    };

    this.setContents = function(contents){
        this.titleBox.innerHTML = "Query data...";
        this.queryForm.insertBefore(this.titleBox,this.contentsBox);
        this.queryForm.className = "userForm";
        this.contentsBox.innerHTML = contents;
        this.queryForm.style.top = "50px";
    };

    this.close = function(){
        document.getElementsByTagName('body')[0].removeChild(this.windowOnAir);
        document.queryForm = null;
    };
}