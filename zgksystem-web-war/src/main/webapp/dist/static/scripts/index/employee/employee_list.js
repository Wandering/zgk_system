define("static/scripts/index/employee/employee_list",["sea-modules/bootstrap/bootstrap","sea-modules/jquery/cookie/jquery.cookie","static/scripts/index/message","static/scripts/index/datatable","sea-modules/jquery/md5/jQuery.md5","sea-modules/jquery/dialog/jquery.dialog","static/scripts/index/employee/employee_form"],function(e,t,o){o.exports=function(t){function o(e,t){var o=e.find("p");o.html(t),o.fadeIn(500),setTimeout(function(){o.fadeOut(500)},2e3)}function n(e){$.ajax({type:"post",url:"/system/userInfo/checkLoginNumberIsExist?token="+a,contentType:"application/x-www-form-urlencoded;charset=UTF-8",data:{loginNumber:e},dataType:"json",success:function(e){return"0000000"===e.rtnCode&&"0"==e.bizData?(o($("#login_name").parent().parent(),"账户已存在"),$("#login_name").focus(),void $("#login_name").attr("data-flag","isExist")):void $("#login_name").attr("data-flag","")},beforeSend:function(){},error:function(){$("#login_name").attr("data-flag","")}})}e("sea-modules/bootstrap/bootstrap"),e("sea-modules/jquery/cookie/jquery.cookie");var a=$.cookie("bizData"),i=e("static/scripts/index/message"),s=e("static/scripts/index/datatable"),l=[{data:"id"},{data:"userName",title:"用户名称"},{data:"phone",title:"联系电话"},{data:"email",title:"邮箱"},{data:"description",title:"人员描述"}],d=[{bVisible:!1,aTargets:[0]},{aTargets:[3],render:function(e){return'<a style="color:#fff" href="Mailto:'+e+'">'+e+"</a>"}}];s.initTable({columns:l,tableContentId:"menu_table_content",tableId:(new Date).getTime()+"_table_body",columnDefs:d,sAjaxSource:"/system/userInfo/queryUserInfo?departmentCode="+t.id+"&token="+a});var r=s.dataTable,c=function(o,n,i){var s={postCode:o[0]||"",userName:o[1]||"",loginNumber:o[2]||"",password:o[3]||"",phone:o[4]||"",email:o[5]||"",description:o[6]||""};i?(s.id=i,s.departmentCode=t.id):(s.departmentCode=t.id,e("sea-modules/jquery/md5/jQuery.md5"),s.password=$.md5(s.password)),$.ajax({type:"post",url:"/system/userInfo/addOrEditUserInfo?token="+a,contentType:"application/x-www-form-urlencoded;charset=UTF-8",data:{userPojoJson:JSON.stringify(s)},dataType:"json",success:function(e){n(e)},beforeSend:function(){$(".single-buttons").attr("disabled","disabled")},complete:function(){$(".single-buttons").removeAttr("disabled")},error:function(){}})},m={add:function(o){$("#"+o).off("click"),$("#"+o).on("click",function(){r&&$.get("/system/post/queryComboxPost?departmentCode="+t.id+"&token="+a,function(o){if("0000000"===o.rtnCode){var a=o.bizData;if(a)$.get("../tmpl/employee/employee_form.html",function(t){e("sea-modules/jquery/dialog/jquery.dialog"),$("#add_employee").dialog({title:"新增账号",tmpl:t,onClose:function(){$("#add_employee").dialog("destroy")},render:function(){var e=[];e.push("<option>请选择代理商...</option>");for(var t in a)e.push('<option value="'+t+'">'+a[t]+"</option>");$("#position_name").html(e.join("")),$("#login_name").off("blur"),$("#login_name").on("blur",function(){n($(this).val())})},buttons:[{text:"新增","class":"btn btn-primary single-buttons",click:function(){var t=e("static/scripts/index/employee/employee_form");t.validate(function(e){c(e,function(e){"0000000"===e.rtnCode?(r.fnDraw(),$("#add_employee").dialog("destroy")):($("#add_employee").dialog("destroy"),i({title:"温馨提示",msg:e.msg,type:"alert",clickHandle:function(){}}))})})}},{text:"取消","class":"btn btn-primary",click:function(){$("#add_employee").dialog("destroy")}}]})});else{var s="代理商【"+t.name+"】下没有岗位，请先添加该代理商下的岗位信息";i({title:"温馨提示",msg:s,type:"alert"})}}else{var s="代理商【"+t.name+"】下没有岗位，请先添加该代理商下的岗位信息";i({title:"温馨提示",msg:s,type:"alert"})}})})},update:function(o){$("#"+o).off("click"),$("#"+o).on("click",function(){if(r){var o=s.fnGetSelected(r);if(0!==o.length){var n=r.fnGetData(o[0]);$.get("/system/userInfo/getUserInfo?id="+n.id+"&token="+a,function(o){"0000000"===o.rtnCode&&$.get("../tmpl/employee/employee_form.html",function(s){e("sea-modules/jquery/dialog/jquery.dialog"),$("#add_employee").dialog({title:"修改账号",tmpl:s,onClose:function(){$("#add_employee").dialog("destroy")},render:function(){$.get("/system/post/queryComboxPost?departmentCode="+t.id+"&token="+a,function(e){if("0000000"===e.rtnCode){var t=e.bizData,n=[];n.push("<option>请选择岗位...</option>");for(var a in t)n.push('<option value="'+a+'">'+t[a]+"</option>");$("#position_name").html(n.join("")),$("#position_name").val(o.bizData.postCode)}}),$("#employee_name").val(o.bizData.userName),$("#login_name").val(o.bizData.loginNumber),$("#login_pwd").val(o.bizData.password),$("#login_name_group").hide(),$("#login_pwd_group").hide(),$("#employee_telephone").val(o.bizData.phone),$("#employee_email").val(o.bizData.email),$("#employee_describe").val(o.bizData.description)},buttons:[{text:"修改","class":"btn btn-primary",click:function(){var t=e("static/scripts/index/employee/employee_form");t.validate(function(e){c(e,function(e){"0000000"===e.rtnCode?(r.fnDraw(),$("#add_employee").dialog("destroy")):($("#add_employee").dialog("destroy"),i({title:"温馨提示",msg:e.msg,type:"alert",clickHandle:function(){window.location.href="login.html"}}))},n.id)},"update")}},{text:"取消","class":"btn btn-primary",click:function(){$("#add_employee").dialog("destroy")}}]})})})}}})},"delete":function(e){$("#"+e).off("click"),$("#"+e).on("click",function(){if(r){var e=s.fnGetSelected(r);if(0!==e.length){var t=r.fnGetData(e[0]),o="确认删除账号"+t.userName;i({title:"温馨提示",msg:o,type:"alert",clickHandle:function(){$.get("/system/userInfo/delUserInfo?id="+t.id+"&token="+a,function(e){"0000000"===e.rtnCode?r.fnDraw():i({title:"错误提示",msg:e.msg,type:"alert"})})}})}}})}};e.async(["static/scripts/index/renderResource"],function(e){e(m,a)})}});