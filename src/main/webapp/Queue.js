/* 
 * Author: Adrian Tubio
 * Clase Queue: estructura de datos de tipo cola.
 */

function Queue(){var _1=[];var _2=0;this.getSize=function(){return _1.length-_2;};this.isEmpty=function(){return (_1.length==0);};this.enqueue=function(_3){_1.push(_3);};this.dequeue=function(){var _4=undefined;if(_1.length){_4=_1[_2];if(++_2*2>=_1.length){_1=_1.slice(_2);_2=0;}}return _4;};this.getOldestElement=function(){var _5=undefined;if(_1.length){_5=_1[_2];}return _5;};}
