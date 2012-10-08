/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function clearSelection() {
    var sel ;
    if(document.selection && document.selection.empty){
        document.selection.empty();
    } else if(window.getSelection){
        sel=window.getSelection();
        if(sel && sel.removeAllRanges) sel.removeAllRanges() ;
    }
}

function checkJustNumbers(evt){
    var keyCode = (evt.which) ? evt.which : evt.keyCode;
    if (keyCode > 31 && (keyCode < 48 || keyCode > 57)) return false;
    return keyCode;
}

function createNewRootFromNode(){
    if (document.treeBox.dragging == undefined) return;
    var nodeTo = document.treeBox.dragging.treeNode;
    if (nodeTo.idFather == undefined) return;
    nodeTo.moveNodeToRoot(document.treeBox);
}

function createDraggingItem(container){
    var movel = document.createElement("div");
    movel.className = "draggedElement";
    movel.innerHTML = "<span class='modif'>" + (container.nameMod?container.nameMod+": ":"") +
                        "</span><span class='term'>" + container.nameEl + "</span>";
    return movel;
}

function moverDraggedOb(e){
    if (document.treeBox.draggingObj == null || document.treeBox.draggingObj == undefined) return;
    var posx = 0;
    var posy = 0;
    if (e.pageX || e.pageY) 	{
            posx = e.pageX;
            posy = e.pageY;
    }
    else if (e.clientX || e.clientY) 	{
            posx = e.clientX + document.body.scrollLeft
                    + document.documentElement.scrollLeft;
            posy = e.clientY + document.body.scrollTop
                    + document.documentElement.scrollTop;
    }

    document.treeBox.draggingObj.style.left = parseInt(posx +10) + "px";
    document.treeBox.draggingObj.style.top = parseInt(posy + 10) + "px";
    clearSelection();
}

function treeNode(idFather, thisId, thisName, thisModId, thisModName){
    this.idFather = idFather;
    this.idEl = thisId;
    this.nameEl = thisName;
    this.idMod = thisModId;
    this.nameMod = thisModName;
    this.remove = function(container){
        if (this.idFather == undefined){
            document.manager.removeTree("nodeId=" + this.idEl, container);
        }else{
            document.manager.removeNodeComplete("nodeId=" + this.idEl + "&relNodeId=" + this.idMod + "&fatherId=" + this.idFather, container);
        }
    };

    this.getGeneralizations = function(container, keyword, cant){
        if (keyword == undefined) keyword = "";
        var param = "nodeId=" + this.idEl + "&keyword=" + keyword;
        if (cant != undefined) param +="&n=" + cant;
        document.manager.getGeneralizations(param,function(XMLAnswer){
                            var generalizations = XMLAnswer.getElementsByTagName("generalization");
                            var nodeID = XMLAnswer.getAttribute("nodeID");
                            for (var i=0; i<generalizations.length; i++){
                                var res = new Generalization(nodeID, generalizations[i].getAttribute("termID"),
                                                        generalizations[i].getAttribute("termLabel"));
                                container.appendGeneralization(res.createDiv());
                            }
                            container.appendDivSize(XMLAnswer.getAttribute("shown"),
                                                    XMLAnswer.getAttribute("total"));
                        });
    };

    this.getRestrictions = function(container, keyword, cant){
        if (keyword == undefined) keyword = "";
        var param = "nodeId=" + this.idEl + "&keyword=" + keyword;
        if (cant != undefined) param +="&n=" + cant;
        document.manager.getRestrictions(param,function(XMLAnswer){
                            var restrictions = XMLAnswer.getElementsByTagName("restriction");
                            var nodeID = XMLAnswer.getAttribute("nodeID");
                            for (var i=0; i<restrictions.length; i++){
                                var res = new Restriction(nodeID, restrictions[i].getAttribute("modID"),
                                                        restrictions[i].getAttribute("termID"),
                                                        restrictions[i].getAttribute("modLabel"),
                                                        restrictions[i].getAttribute("termLabel"));
                                container.appendRestriction(res.createDiv());
                            }
                            container.appendDivSize(XMLAnswer.getAttribute("shown"),
                                                    XMLAnswer.getAttribute("total"));
                        });
    };

    this.getSpecializations = function(container, keyword, cant){
        if (keyword == undefined) keyword = "";
        var param = "nodeId=" + this.idEl + "&keyword=" + keyword;
        if (cant != undefined) param +="&n=" + cant;
        document.manager.getSpecializations(param,function(XMLAnswer){
                            var specializations = XMLAnswer.getElementsByTagName("specialization");
                            var nodeID = XMLAnswer.getAttribute("nodeID");
                            for (var i=0; i<specializations.length; i++){
                                var res = new Specialization(nodeID, specializations[i].getAttribute("termID"),
                                                        specializations[i].getAttribute("termLabel"));
                                container.appendSpecialization(res.createDiv());
                            }
                            container.appendDivSize(XMLAnswer.getAttribute("shown"),
                                                    XMLAnswer.getAttribute("total"));
                        });
    };

    this.moveNodeTo = function(container, idor, idormod, idorfather){
        if (idorfather == undefined || idorfather == null){
            document.manager.moveRootToNode("origenID=" + idor + "&toID=" + this.idEl,container);
        }else document.manager.moveNodeTo("origenID=" + idor + "&origenModID=" + idormod + "&origenFatherID=" + idorfather + "&toID=" + this.idEl,container);
    };

    this.moveNodeToRoot = function(container){
        document.manager.moveNodeToRoot("origenID=" + this.idEl + "&origenModID=" + this.idMod + "&fatherID=" + this.idFather,container);
    };
}

function specialize(capa, cat, nodeID){
    document.manager.specialize("cat=" + cat + "&nodeID=" + nodeID,capa);
}

function generalize(capa, cat, nodeID){
    document.manager.generalize("cat=" + cat + "&nodeID=" + nodeID,capa);
}

function initializeNode(container, idFather, thisId, thisName, thisModId, thisModName){
    if (container.treeNode != undefined) return;
    container.treeNode = new treeNode(idFather, thisId, thisName, thisModId, thisModName);

    container.opened = false;
    container.loaded = false;

    //ADDING BUTTONS
    //ADDING BUTTON RESTRICTIONS
    container.bRestrictions = document.createElement("span");
    container.bRestrictions.className = "buttonTreeRestrictions";
    container.bRestrictions.onclick = function(e){
        if (!container.opened) return;
        container.panel.titleBox.titleName.innerHTML = "Restrictions";
        container.panel.style.display = "block";
        container.panel.panelSelected.style.display ="none";
        container.panel.contentBoxRes.style.display ="block";
        container.panel.panelSelected = container.panel.contentBoxRes;
        e.stopPropagation();
    };
    container.appendChild(container.bRestrictions);

    //ADDING BUTTON SPECIALIZATIONS
    container.bSpecializations = document.createElement("span");
    container.bSpecializations.className = "buttonTreeSpecializations";
    container.bSpecializations.onclick = function(e){
        if (!container.opened) return;
        container.panel.titleBox.titleName.innerHTML = "Specializations";
        container.panel.style.display = "block";
        container.panel.panelSelected.style.display ="none";
        container.panel.contentBoxSp.style.display ="block";
        container.panel.panelSelected = container.panel.contentBoxSp;
        e.stopPropagation();
    };
    container.appendChild(container.bSpecializations);

    //ADDING BUTTON Generalizations
    container.bGeneralizations = document.createElement("span");
    container.bGeneralizations.className = "buttonTreeGeneralizations";
    container.bGeneralizations.onclick = function(e){
        if (!container.opened) return;
        container.panel.titleBox.titleName.innerHTML = "Generalizations";
        container.panel.style.display = "block";
        container.panel.panelSelected.style.display ="none";
        container.panel.contentBoxGen.style.display ="block";
        container.panel.panelSelected = container.panel.contentBoxGen;
        e.stopPropagation();
    };
    container.appendChild(container.bGeneralizations);

    //ADDING BUTTON Delete
    container.bDelete = document.createElement("span");
    container.bDelete.className = "buttonTreeDelete";
    container.bDelete.onclick = function(e){
        if (!confirm("Are you sure you want to delete this node?")) return;
        container.treeNode.remove(document.treeBox,container.bDelete);
        e.stopPropagation();
    };
    container.appendChild(container.bDelete);

    //ADDING BUTTON Close
    container.bClose = document.createElement("span");
    container.bClose.owner = container;
    container.bClose.className = "buttonTreeClose";
    container.bClose.onclick = function(){
        container.managePanelOptions();
    };
    container.appendChild(container.bClose);

    //ADDING PANEL
    container.panel = document.createElement("div");
    container.panel.className = "branchOptions";

    //TITLE BOX
    container.panel.titleBox = document.createElement("div");
    container.panel.titleBox.className = "branchOptionsTitle";
    container.panel.titleBox.titleName = document.createElement("span");
    container.panel.titleBox.titleName.innerHTML = "Restrictions";
    
    container.panel.titleBox.searchField = document.createElement("div");
    container.panel.titleBox.searchField.className = "searchButton";
    container.panel.titleBox.searchField.input = document.createElement("input");
    container.panel.titleBox.searchField.input.className = "inputSearch";
    container.panel.titleBox.searchField.input.type = "text";
    container.panel.titleBox.searchField.input.onfocus = function(){
        container.panel.titleBox.searchField.className = "searchButtonHovered";
    };

    container.panel.titleBox.searchField.input.onblur = function(){
        if (container.panel.titleBox.searchField.input.value != "") return;
        container.panel.titleBox.searchField.className = "searchButton";
    };
    
    container.panel.titleBox.searchField.input.onkeyup = function(){
        var written = container.panel.titleBox.searchField.input.value;
        if (written == undefined) written = "";
            if (container.panel.panelSelected == container.panel.contentBoxRes){
                container.panel.contentBoxRes.innerHTML = "";
                container.treeNode.getRestrictions(container.panel.contentBoxRes, written,
                                                container.panel.contentBoxRes.lastValue);
            }
            if (container.panel.panelSelected == container.panel.contentBoxSp){
                container.panel.contentBoxSp.innerHTML = "";
                container.treeNode.getSpecializations(container.panel.contentBoxSp, written,
                                                container.panel.contentBoxSp.lastValue);
            }
            if (container.panel.panelSelected == container.panel.contentBoxGen){
                container.panel.contentBoxGen.innerHTML = "";
                container.treeNode.getGeneralizations(container.panel.contentBoxGen, written,
                                                container.panel.contentBoxGen.lastValue);
            }
    };
    container.panel.titleBox.searchField.appendChild(container.panel.titleBox.searchField.input);
    container.panel.titleBox.appendChild(container.panel.titleBox.titleName);
    container.panel.titleBox.appendChild(container.panel.titleBox.searchField);

    //Appending the title box to divCont
    container.panel.appendChild(container.panel.titleBox);

    //CONTENTS BOX
    container.panel.contentBoxRes = document.createElement("div");
    container.panel.contentBoxRes.className = "branchOptionsContent";
    container.panel.contentBoxRes.style.display = "block";
    container.panel.contentBoxRes.appendRestriction = function(res){
        container.panel.contentBoxRes.appendChild(res);
    };

    container.panel.contentBoxRes.appendDivSize = function(shown, total){
        var divNum = document.createElement("div");
        divNum.className = "showingSize";
        var inputNum = document.createElement("input");
        inputNum.type = "text";
        inputNum.className = "inputSize";
        inputNum.value = shown;
        container.panel.contentBoxRes.originValue = shown;
        inputNum.onkeypress = checkJustNumbers;
        inputNum.onchange = function(){
            if (container.panel.contentBoxRes.originValue == inputNum.value) return;
            container.panel.contentBoxRes.innerHTML="";
            container.treeNode.getRestrictions(container.panel.contentBoxRes, 
                                    container.panel.titleBox.searchField.value,
                                    inputNum.value);
            container.panel.contentBoxRes.lastValue = inputNum.value;
        };
        divNum.appendChild(inputNum);
        divNum.appendChild(document.createTextNode(" of " + total));
        container.panel.contentBoxRes.numElements = inputNum;
        container.panel.contentBoxRes.appendChild(divNum);
    };

    container.panel.panelSelected = container.panel.contentBoxRes;
    //Appending the contents box to divCont
    container.panel.appendChild(container.panel.contentBoxRes);

    //CONTENTS BOX
    container.panel.contentBoxGen = document.createElement("div");
    container.panel.contentBoxGen.className = "branchOptionsContent";
    container.panel.contentBoxGen.appendGeneralization = function(gen){
        container.panel.contentBoxGen.appendChild(gen);
    };

    container.panel.contentBoxGen.appendDivSize = function(shown, total){
        var divNum = document.createElement("div");
        divNum.className = "showingSize";
        var inputNum = document.createElement("input");
        inputNum.type = "text";
        inputNum.className = "inputSize";
        inputNum.value = shown;
        container.panel.contentBoxGen.originValue = shown;
        inputNum.onkeypress = checkJustNumbers;
        inputNum.onchange = function(){
            if (container.panel.contentBoxGen.originValue == inputNum.value) return;
            container.panel.contentBoxGen.innerHTML="";
            container.treeNode.getGeneralizations(container.panel.contentBoxGen,
                                    container.panel.titleBox.searchField.value,
                                    inputNum.value);
            container.panel.contentBoxGen.lastValue = inputNum.value;
        };
        divNum.appendChild(inputNum);
        divNum.appendChild(document.createTextNode(" of " + total));
        container.panel.contentBoxGen.numElements = inputNum;
        container.panel.contentBoxGen.appendChild(divNum);
    };

    //Appending the contents box to divCont
    container.panel.appendChild(container.panel.contentBoxGen);

    //CONTENTS BOX
    container.panel.contentBoxSp = document.createElement("div");
    container.panel.contentBoxSp.className = "branchOptionsContent";
    container.panel.contentBoxSp.appendSpecialization = function(esp){
        container.panel.contentBoxSp.appendChild(esp);
    };

    container.panel.contentBoxSp.appendDivSize = function(shown, total){
        var divNum = document.createElement("div");
        divNum.className = "showingSize";
        var inputNum = document.createElement("input");
        inputNum.type = "text";
        inputNum.className = "inputSize";
        inputNum.value = shown;
        container.panel.contentBoxSp.originValue = shown;
        inputNum.onkeypress = checkJustNumbers;
        inputNum.onchange = function(){
            if (container.panel.contentBoxSp.originValue == inputNum.value) return;
            container.panel.contentBoxSp.innerHTML="";
            container.treeNode.getSpecializations(container.panel.contentBoxSp,
                                    container.panel.titleBox.searchField.value,
                                    inputNum.value);
            container.panel.contentBoxSp.lastValue = inputNum.value;
        };
        divNum.appendChild(inputNum);
        divNum.appendChild(document.createTextNode(" of " + total));
        container.panel.contentBoxSp.numElements = inputNum;
        container.panel.contentBoxSp.appendChild(divNum);
    };

    //Appending the contents box to divCont
    container.panel.appendChild(container.panel.contentBoxSp);

    container.parentNode.appendChild(container.panel);

    //CREATE DRAG AREA
    container.dragArea = document.createElement("span");
    container.dragArea.className = "treeDragArea";
    container.dragArea.onmousedown = function(){
        document.treeBox.dragging = container;
        document.body.style.cursor = "-moz-grabbing";
        document.treeBox.style.cursor = "-moz-grabbing";
        document.treeBox.draggingObj = document.createElement("div");
        document.treeBox.draggingObj.className = "draggedElement";
        document.treeBox.draggingObj.innerHTML =
            "<span class='modif'>" + (container.treeNode.nameMod?container.treeNode.nameMod+": ":"") +
            "</span><span class='term'>" + container.treeNode.nameEl + "</span>";
        document.treeBox.appendChild(document.treeBox.draggingObj);
        
        document.onmousemove = function(e){
            moverDraggedOb(e);
        };
        
        document.onmouseup = function(){
            if (document.treeBox.dragging != undefined){
                document.body.style.cursor = "default";
                document.treeBox.style.cursor = "default";
                try{
                    document.treeBox.removeChild(document.treeBox.draggingObj);
                }catch(err){
                    //Just to keep free of javascript errors
                }
                document.treeBox.draggingObj = undefined;
                document.onmousemove = undefined;
                document.onmouseup = undefined;
                document.treeBox.dragging = undefined;
            }
        };

        document.treeBox.draggingObj.style.display ="block";
    };
    container.appendChild(container.dragArea);

    //CREATE DROP AREA
    container.dropArea = document.createElement("span");
    container.dropArea.innerHTML = "Drop here...";
    container.dropArea.className = "treeDropArea";
    container.dropArea.onmouseup = function(){
        if (document.treeBox.dragging != undefined){
            if (document.treeBox.dragging.treeNode.idEl == container.treeNode.idEl) return;
            
            container.treeNode.moveNodeTo(document.treeBox, document.treeBox.dragging.treeNode.idEl,
                                        document.treeBox.dragging.treeNode.idMod,
                                        document.treeBox.dragging.treeNode.idFather);
        }
    };
    container.appendChild(container.dropArea);
    container.onclick = function(){
        if (!container.opened){
            if (!container.loaded){
                container.treeNode.getRestrictions(container.panel.contentBoxRes,"",undefined);
                container.treeNode.getGeneralizations(container.panel.contentBoxGen,"",undefined);
                container.treeNode.getSpecializations(container.panel.contentBoxSp,"",undefined);
                container.loaded = true;
            }
            container.panel.style.display = "block";
            container.opened = true;
            container.bDelete.style.display = "block";
            container.bRestrictions.style.display = "block";
            container.bGeneralizations.style.display = "block";
            container.bSpecializations.style.display = "block";
            container.className="branchTitleClicked";
            container.bClose.className = "buttonTreeCloseDown";
        }else{
            container.opened = false;
            container.panel.style.display ="none";
            container.bDelete.style.display = "none";
            container.bRestrictions.style.display = "none";
            container.bGeneralizations.style.display = "none";
            container.bSpecializations.style.display = "none";
            container.className = "branchTitleOver";
            container.bClose.className = "buttonTreeClose";
        }
    };

    container.onmouseover = function(){
        if (document.treeBox.dragging != undefined){
            if (!container.opened) container.className = "branchTitle";
            container.dropArea.style.display = "inline";
            container.style.cursor ="-moz-grabbing";
        }else{
            container.style.cursor ="pointer";
            if (!container.opened) container.className = "branchTitleOver";
            container.dragArea.style.display = "block";
        }
        container.bClose.style.display ="block";
    };

    container.onmouseout = function(){
        container.dropArea.style.display = "none";
        if (container.opened) return;
        container.className = "branchTitle";
        container.bDelete.style.display = "none";
        container.bRestrictions.style.display = "none";
        container.bGeneralizations.style.display = "none";
        container.bSpecializations.style.display = "none";
        container.dragArea.style.display = "none";
        container.bClose.style.display = "none";
    };

    if (!container.opened && document.treeBox.dragging == undefined){
      container.className = "branchTitleOver";
      container.bClose.style.display = "block";
      container.dragArea.style.display = "block";
    }
}

function Restriction(nodeID, idMod, idTerm, nameMod, nameTerm){
    this.idMod = idMod;
    this.nameMod = nameMod;
    this.idTerm = idTerm;
    this.nameTerm = nameTerm;
    this.nodeID = nodeID;

    this.createDiv = function(){
        var span = document.createElement("div");
        span.className = "optionsPanelElement";
        span.nodeID = this.nodeID;
        span.idMod = this.idMod;
        span.nameMod = this.nameMod;
        span.idTerm = this.idTerm;
        span.nameTerm = this.nameTerm;
        
        span.mod = document.createElement("span");
        span.mod.innerHTML = span.nameMod;
        span.mod.className = "modrestriction";
        span.appendChild(span.mod);

        var textNode = document.createTextNode(" ");
        span.appendChild(textNode);
        span.term = document.createElement("span");
        span.term.innerHTML = span.nameTerm;
        span.term.className = "termrestriction";
        span.appendChild(span.term);

        span.onclick = function(){
            document.manager.addRestriction("rel=" + span.idMod + "&term=" + span.idTerm + "&nodeID=" + span.nodeID,document.treeBox);
        };
        return span;
    };
}

function Generalization(nodeID, idTerm, nameTerm){
    this.idTerm = idTerm;
    this.nameTerm = nameTerm;
    this.nodeID = nodeID;

    this.createDiv = function(){
        var span = document.createElement("div");
        span.className = "optionsPanelElement";
        span.nodeID = this.nodeID;
        span.idTerm = this.idTerm;
        span.nameTerm = this.nameTerm;

        var textNode = document.createTextNode(" ");
        span.appendChild(textNode);
        span.term = document.createElement("span");
        span.term.innerHTML = span.nameTerm;
        span.term.className = "labelWord";
        span.appendChild(span.term);

        span.onclick = function(){
            document.manager.generalize("cat=" + span.idTerm + "&nodeID=" + span.nodeID,document.treeBox);
        };
        return span;
    };
}

function Specialization(nodeID, idTerm, nameTerm){
    this.idTerm = idTerm;
    this.nameTerm = nameTerm;
    this.nodeID = nodeID;

    this.createDiv = function(){
        var span = document.createElement("div");
        span.className = "optionsPanelElement";
        span.nodeID = this.nodeID;
        span.idTerm = this.idTerm;
        span.nameTerm = this.nameTerm;

        var textNode = document.createTextNode(" ");
        span.appendChild(textNode);
        span.term = document.createElement("span");
        span.term.innerHTML = span.nameTerm;
        span.term.className = "labelWord";
        span.appendChild(span.term);

        span.onclick = function(){
            document.manager.specialize("cat=" + span.idTerm + "&nodeID=" + span.nodeID,document.treeBox);
        };
        return span;
    };
}