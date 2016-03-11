define("static/scripts/index/role/role",["sea-modules/bootstrap/bootstrap","sea-modules/jquery/cookie/jquery.cookie","sea-modules/jquery/dialog/jquery.dialog","static/scripts/index/message","static/scripts/index/datatable","static/scripts/index/tree"],function(e,t,o){o.exports=function(){function t(e){for(var t=[],o=[],n=0,r=e.length;r>n;n++)e[n].isResources?o.push(e[n].id):t.push(e[n].id);return{menuCodeList:t,resourceList:o}}function o(e,t){for(var o=0,n=e.length;n>o;o++)if(e[o]==t)return!0;return!1}function n(e,t,r){for(var i=0,a=e.length;a>i;i++){var s=e[i].children&&e[i].children.length>0;e[i].isResources?o(r,e[i].id)&&(e[i].checked=!0):o(t,e[i].id)&&(e[i].checked=!0,e[i].open=!0),s&&n(e[i].children,t,r)}}function r(e){var t=new RegExp("(^|&)"+e+"=([^&]*)(&|$)"),o=window.location.search.substr(1).match(t);return null!=o?unescape(o[2]):null}function i(t,o){var i=r("systemCode");$.get("/system/menu/getMenuTree?token="+c+"&hasResource=true&systemCode="+i,function(r){if("0000000"===r.rtnCode){var i=r.bizData.treeBeanList;t&&o&&n(i,t,o),f=e("static/scripts/index/tree"),f.init({id:"tree_resource",check:!0,nodes:i}),m=f.treeObj}else y(r)})}function a(e,o){var n=$("#role_name").val().trim();if(!n){var r=$("#role_name").parent().parent().find("p");return r.html("角色名称不能为空！"),r.show(),void setTimeout(function(){r.hide()},2e3)}var i=$("#role_description").val().trim(),a=f.getCheckedNodes(),s=t(a);if(a.length<=0)return void $("#model_error_msg").html("请选择菜单和资源分配");var l={roleName:n,description:i,menuCodeList:s.menuCodeList,resourceList:s.resourceList};e&&(l.rolesId=e),o&&(l.roleCode=o);var d=!1;d||(d=!0,$.ajax({type:"post",url:"/system/role/addOrEditRoles?token="+c,contentType:"application/x-www-form-urlencoded;charset=utf-8",data:{rolesPojoJson:JSON.stringify(l)},dataType:"json",success:function(e){d=!1,"0000000"===e.rtnCode?(g.fnDraw(),$("#add_role").dialog("destroy")):($("#add_role").dialog("destroy"),y(e))},error:function(){d=!1}}))}e("sea-modules/bootstrap/bootstrap"),e("sea-modules/jquery/cookie/jquery.cookie"),e("sea-modules/jquery/dialog/jquery.dialog");var s=e("static/scripts/index/message"),l=e("static/scripts/index/datatable"),c=$.cookie("bizData"),d=[{data:"id"},{data:"roleName",title:"角色名称"},{data:"description",title:"角色描述"}],u=[{bVisible:!1,aTargets:[0]}],f=null,m=null,p={text:"取消","class":"btn btn-primary",click:function(){$("#add_role").dialog("destroy")}};$("#form_search").html(""),l.initTable({columns:d,tableContentId:"table_content",tableId:"role_table_body",columnDefs:u,sAjaxSource:"/system/role/queryRoles?token="+c});var g=l.dataTable,y=function(e){var t=null;("0100014"===e.rtnCode||"0100015"===e.rtnCode)&&(t=function(){window.location.href="login.html"}),s({title:"温馨提示",msg:e.msg,type:"alert",clickHandle:t})},b={add:function(e){$("#"+e).off("click"),$("#"+e).on("click",function(){g&&$.get("../tmpl/role/role.tmpl",function(e){$("#add_role").dialog({title:"新增角色",tmpl:e,onClose:function(){$("#add_role").dialog("destroy")},render:function(){i()},buttons:[{text:"新增","class":"btn btn-primary",click:function(){a()}},p]})})})},update:function(e){$("#"+e).off("click"),$("#"+e).on("click",function(){if(g){var e=l.fnGetSelected(g);if(0!==e.length){var t=g.fnGetData(e[0]);$.get("/system/role/getRoles?id="+t.id+"&token="+c,function(e){"0000000"===e.rtnCode&&$.get("../tmpl/role/role.tmpl",function(o){$("#add_role").dialog({title:"修改角色",tmpl:o,onClose:function(){$("#add_role").dialog("destroy")},render:function(){i(e.bizData.menuCodeList,e.bizData.resourceList),$("#role_name").val(e.bizData.roleName),$("#role_description").val(e.bizData.description)},buttons:[{text:"修改","class":"btn btn-primary",click:function(){a(t.id,t.roleCode)}},p]})})})}}})},"delete":function(e){$("#"+e).off("click"),$("#"+e).on("click",function(){if(g){var e=l.fnGetSelected(g);if(0!==e.length){var t=g.fnGetData(e[0]),o="确认删除角色【"+t.roleName+"】，将会删除此角色下所有的菜单权限和资源权限且不可恢复，请谨慎操作！";s({title:"温馨提示",msg:o,type:"alert",clickHandle:function(){$.get("/system/role/delRoles?rolesId="+t.id+"&token="+c,function(e){"0000000"===e.rtnCode?g.fnDraw():s({title:"温馨提示",msg:e.msg,type:"alert"})})}})}}})}};e.async(["static/scripts/index/renderResource"],function(e){e(b,c)})}});