define("static/scripts/index/common/initTreeGrid",["static/scripts/index/tree"],function(e,t,i){i.exports=function(t){$.get(t.getTreeDataURL,function(i){if("0000000"===i.rtnCode){var r=[];if(r="[object Array]"===Object.prototype.toString.call(i.bizData)?i.bizData:i.bizData.treeBeanList,r.length<=0)return i.msg||(i.msg="请添加相关信息"),void t.err(i);t.succ();var n=e("static/scripts/index/tree");n.init({id:"tree_menu",url:"",beforeClick:function(e,i){t.listGridModule&&"function"==typeof t.listGridModule&&t.listGridModule(i,n.treeObj)},onAsyncSuccess:function(){},nodes:r});var o=r[0],s=n.treeObj.getNodeByParam("id",o.id,null);n.treeObj.selectNode(s),n.treeObj.expandNode(s,!0,!1,!0),t.listGridModule(o,n.treeObj)}else t.err(i)})}});