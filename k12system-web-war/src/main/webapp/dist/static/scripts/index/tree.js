define("static/scripts/index/tree",["sea-modules/jquery/ztree/jquery.ztree.core.3.5.min","sea-modules/jquery/ztree/jquery.ztree.excheck-3.5.min"],function(e,r,n){var c=function(){var r=function(){return{view:{selectedMulti:!1},check:{enable:!1,chkboxType:{Y:"ps",N:"s"}},async:{enable:!0,url:"",autoParam:["id","name=n","level=lv"],otherParam:{otherParam:"zTreeAsyncTest"}},callback:{beforeClick:function(){},onAsyncSuccess:function(){}}}};return{treeObj:null,init:function(n){e("sea-modules/jquery/ztree/jquery.ztree.core.3.5.min"),e("sea-modules/jquery/ztree/jquery.ztree.excheck-3.5.min");var c=r();return n.url?c.async.url=n.url:c.async.enable=!1,c.check.enable=n.check||!1,n.beforeClick&&(c.callback.beforeClick=n.beforeClick),n.onAsyncSuccess&&(c.callback.onAsyncSuccess=n.onAsyncSuccess),!n.url&&n.nodes&&n.nodes.length>0?void(this.treeObj=$.fn.zTree.init($("#"+n.id),c,n.nodes)):void(this.treeObj=$.fn.zTree.init($("#"+n.id),c))},getCheckedNodes:function(){var e=this.treeObj.getCheckedNodes(!0);return e},structureCheckedTree:function(e){for(var r=0;r<e.length;r++){var n=e[r].id;if(e[r].isParent)for(var c=0;c<e.length;c++)n==e[c].pId&&(e[r].children=e[r].children||[],e[r].children.push(e[c]))}for(var t=[],s=0;s<e.length;s++)e[s].pId||t.push(e[s]);return t}}}();n.exports=c});