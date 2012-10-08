/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Función createCaller()
 *
 * @description
 * Crea un objeto sobre el que se harán las llamadas AJAX.
 */

function createCaller(){
     var objetoAjax=false;
     try {
          /*Para navegadores distintos a internet explorer*/
          objetoAjax = new ActiveXObject("Msxml2.XMLHTTP");
     } catch (e) {
          try {
               /*Para explorer*/
               objetoAjax = new ActiveXObject("Microsoft.XMLHTTP");
          }
               catch (E) {
               objetoAjax = false;
          }
     }

     if (!objetoAjax && typeof XMLHttpRequest!='undefined') {
        objetoAjax = new XMLHttpRequest();
     }
     return objetoAjax;
}
