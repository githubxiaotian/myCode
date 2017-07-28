// JavaScript Document

startList = function() {
if (document.all&&document.getElementById) {
navRoot = document.getElementById("nav");
for (i=0; i<navRoot.childNodes.length; i++) {
node = navRoot.childNodes[i];
if (node.nodeName=="LI") {
node.onmouseover=function() {
this.className+=" over";
  }
  node.onmouseout=function() {
  this.className=this.className.replace(" over", "");
   }
   }
  }


menuRoot = document.getElementById("side_menu");
for (i=0; i<menuRoot.childNodes.length; i++) {
menu = menuRoot.childNodes[i];
for (j=0; j<menu.childNodes.length; j++) {
menu2 = menu.childNodes[j];
if (menu2.nodeName=="LI") {
menu2.onmouseover=function() {
for (k=0; k<this.childNodes.length; k++) {
ul = this.childNodes[k];
if(ul.nodeName=="DIV")
ul.style.display="block";
}

}
  menu2.onmouseout=function() {
for (k=0; k<this.childNodes.length; k++) {
ul = this.childNodes[k];
if(ul.nodeName=="DIV")
ul.style.display="none";
}


   }
   }
  }
}




 }
}
window.onload=startList;