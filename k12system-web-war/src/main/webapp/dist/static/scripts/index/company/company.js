define("static/scripts/index/company/company",["sea-modules/jquery/cookie/jquery.cookie","static/scripts/index/tree","static/scripts/index/company/company_list"],function(e,i,t){t.exports=function(i){e("sea-modules/jquery/cookie/jquery.cookie");var t=$.cookie("bizData");$.get("/system/product/queryTreeProduct?token="+t,function(t){if("0000000"===t.rtnCode){var c=t.bizData;if(c.length<=0)return void i.err(t);i.succ();var o=e("static/scripts/index/tree");o.init({id:"tree_menu",url:"",beforeClick:function(i,t){var c=e("static/scripts/index/company/company_list");c(t.id)},onAsyncSuccess:function(){},nodes:c});var r=c[0],n=o.treeObj.getNodeByParam("id",r.id,null);o.treeObj.selectNode(n);var s=e("static/scripts/index/company/company_list");s(r.id)}else i.err(t)})}});