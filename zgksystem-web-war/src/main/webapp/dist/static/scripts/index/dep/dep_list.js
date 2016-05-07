define("static/scripts/index/dep/dep_list",["sea-modules/bootstrap/bootstrap","sea-modules/jquery/cookie/jquery.cookie","static/scripts/index/message","static/scripts/index/datatable","sea-modules/jquery/dialog/jquery.dialog","static/scripts/index/dep/dep_form"],function(e,t,a){a.exports=function(t,a){e("sea-modules/bootstrap/bootstrap"),e("sea-modules/jquery/cookie/jquery.cookie");var o=$.cookie("bizData"),i=e("static/scripts/index/message"),n=e("static/scripts/index/datatable"),d=JSON.parse($.cookie("userInfo")),r=d.roleType;if("1"==r){var c=[{data:"id"},{data:"departmentName",title:"代理商名称"},{data:"roleType",title:"代理商类型"},{data:"wechatPrice",title:"微信售价"},{data:"webPrice",title:"web售价"},{data:"salePrice",title:"拿货价"},{data:"departmentPrincipal",title:"代理商负责人"},{data:"departmentPhone",title:"代理商电话"},{data:"goodsAddress",title:"联系地址"}],s=[{bVisible:!1,aTargets:[0]},{aTargets:[2],render:function(e){var t=["管理员","省级代理","市级代理","区县级代理"];return t[e-1]}}];n.initTable({columns:c,tableContentId:"menu_table_content",tableId:(new Date).getTime()+"_table_body",columnDefs:s,sAjaxSource:"/system/department/queryDepartment?parentCode="+t+"&token="+o});var l=n.dataTable}else{var c=[{data:"id"},{data:"departmentName",title:"代理商名称"},{data:"roleType",title:"代理商类型"},{data:"salePrice",title:"拿货价"},{data:"departmentPrincipal",title:"代理商负责人"},{data:"departmentPhone",title:"代理商电话"},{data:"goodsAddress",title:"联系地址"}],s=[{bVisible:!1,aTargets:[0]},{aTargets:[2],render:function(e){var t=["管理员","省级代理","市级代理","区县级代理"];return t[e-1]}}];n.initTable({columns:c,tableContentId:"menu_table_content",tableId:(new Date).getTime()+"_table_body",columnDefs:s,sAjaxSource:"/system/department/queryDepartment?parentCode="+t+"&token="+o});var l=n.dataTable}var p=function(e,a,i){var n={departmentName:e[0]||"",departmentPhone:e[1]||"",departmentFax:e[2]||"",departmentPrincipal:e[3]||"",salePrice:e[7]||"",goodsAddress:e[8]||""};switch(i?n.id=i:n.parentCode=t,r){case 1:n.webPrice=e[9]||"",n.wechatPrice=e[10]||"",n.areaCode=e[4];break;case 2:n.areaCode=e[5];break;case 3:n.areaCode=e[6]}$.ajax({type:"post",url:"/system/department/addOrEditDepartment?token="+o,contentType:"application/x-www-form-urlencoded;charset=UTF-8",data:{departmentJson:JSON.stringify(n)},dataType:"json",success:function(e){console.log(e),a(e)},beforeSend:function(){$(".single-buttons").attr("disabled","disabled")},complete:function(){$(".single-buttons").removeAttr("disabled")},error:function(){}})},m={add:function(t){$("#"+t).off("click"),$("#"+t).on("click",function(){l&&$.get("../tmpl/dep/dep_form.html",function(t){e("sea-modules/jquery/dialog/jquery.dialog"),$("#add_dep").dialog({title:"新增代理商",tmpl:t,onClose:function(){$("#add_dep").dialog("destroy")},render:function(){var e=JSON.parse($.cookie("userInfo")),t=parseInt(e.roleType),a=e.areaCode;switch(console.log(a),console.log(t),t){case 1:$("#dep_provinces_from").show(),$("#dep_city_from,#dep_county_from").hide(),$.getJSON("/system/dataDictionary/findProvinceList?token="+o,function(e){console.log(e);for(var t=0;t<e.bizData.length;t++)$("#dep_provinces").append('<option value="'+e.bizData[t].id+'">'+e.bizData[t].provinceName+"</option>")});break;case 2:a+="0000",$("#dep_city_from").show(),$("#dep_provinces_from,#dep_county_from,#web-control-group,#wechat-control-group").hide(),$.getJSON("/system/dataDictionary/findCityList?token="+o+"&provinceId="+a,function(e){console.log(e);for(var t=0;t<e.bizData.length;t++)$("#dep_city").append('<option value="'+e.bizData[t].id+'">'+e.bizData[t].cityName+"</option>")});break;case 3:a+="00",$("#dep_county_from").show(),$("#dep_provinces_from,#dep_city_from,#web-control-group,#wechat-control-group").hide(),$.getJSON("/system/dataDictionary/findCountyList?token="+o+"&cityId="+a,function(e){console.log(e);for(var t=0;t<e.bizData.length;t++)$("#dep_county").append('<option value="'+e.bizData[t].id+'">'+e.bizData[t].countyName+"</option>")})}},buttons:[{text:"新增","class":"btn btn-primary single-buttons",click:function(){var t=e("static/scripts/index/dep/dep_form");t.validate(function(e){p(e,function(t){if(l.fnDraw(),"0000000"===t.rtnCode){var o=JSON.parse($.cookie("userInfo"));if("-1"==o.departmentCode){var n={id:t.bizData.departmentCode,name:e[0]};a({type:"add",obj:n})}$("#add_dep").dialog("destroy")}else $("#add_dep").dialog("destroy"),i({title:"温馨提示",msg:t.msg,type:"alert",clickHandle:function(){window.location.href="login.html"}})})})}},{text:"取消","class":"btn btn-primary",click:function(){$("#add_dep").dialog("destroy")}}]})})})},update:function(t){$("#"+t).off("click"),$("#"+t).on("click",function(){if(l){var t=n.fnGetSelected(l);if(0!==t.length){var d=l.fnGetData(t[0]);$.get("/system/department/getDepartment?id="+d.id+"&token="+o,function(t){console.log(t),"0000000"===t.rtnCode&&$.get("../tmpl/dep/dep_form.html",function(n){e("sea-modules/jquery/dialog/jquery.dialog"),$("#add_dep").dialog({title:"修改代理商",tmpl:n,onClose:function(){$("#add_dep").dialog("destroy")},render:function(){$("#dep_name").val(t.bizData.departmentName),$("#dep_telephone").val(t.bizData.departmentPhone),$("#dep_fax").val(t.bizData.departmentFax),$("#dep_leading").val(t.bizData.departmentPrincipal),$("#sale_Price").val(t.bizData.salePrice),$("#goods_Address").val(t.bizData.goodsAddress),$("#webPrice").val(t.bizData.webPrice),$("#wechatPrice").val(t.bizData.wechatPrice);var e=parseInt(t.bizData.roleType),a=t.bizData.areaCode;switch(console.log(a),e){case 2:$("#dep_provinces_from").show(),$("#dep_city_from,#dep_county_from").hide(),$.getJSON("/system/dataDictionary/findProvinceList?token="+o,function(e){console.log(e);for(var t=0;t<e.bizData.length;t++)$("#dep_provinces").append('<option value="'+e.bizData[t].id+'">'+e.bizData[t].provinceName+"</option>");$("#dep_provinces").find('option[value="'+a+'0000"]').attr("selected",!0)});break;case 3:var i=a.substring(0,2)+"0000";$("#dep_city_from").show(),$("#dep_provinces_from,#dep_county_from").hide(),$.getJSON("/system/dataDictionary/findCityList?token="+o+"&provinceId="+i,function(e){console.log(e);for(var t=0;t<e.bizData.length;t++)$("#dep_city").append('<option value="'+e.bizData[t].id+'">'+e.bizData[t].cityName+"</option>");$("#dep_city").find('option[value="'+a+'00"]').attr("selected",!0)});break;case 4:var n=a.substring(0,4)+"00";$("#dep_county_from").show(),$("#dep_provinces_from,#dep_city_from").hide(),$.getJSON("/system/dataDictionary/findCountyList?token="+o+"&cityId="+n,function(e){console.log(e);for(var t=0;t<e.bizData.length;t++)$("#dep_county").append('<option value="'+e.bizData[t].id+'">'+e.bizData[t].countyName+"</option>")})}},buttons:[{text:"修改","class":"btn btn-primary",click:function(){var o=e("static/scripts/index/dep/dep_form");o.validate(function(e){p(e,function(o){if(l.fnDraw(),"0000000"===o.rtnCode){var n=JSON.parse($.cookie("userInfo"));if("-1"==n.departmentCode){var d={id:t.bizData.departmentCode,name:e[0]};a({type:"update",obj:d})}$("#add_dep").dialog("destroy")}else $("#add_dep").dialog("destroy"),i({title:"温馨提示",msg:o.msg,type:"alert",clickHandle:function(){window.location.href="login.html"}})},d.id)})}},{text:"取消","class":"btn btn-primary",click:function(){$("#add_dep").dialog("destroy")}}]})})})}}})},"delete":function(e){$("#"+e).off("click"),$("#"+e).on("click",function(){if(l){var e=n.fnGetSelected(l);if(0!==e.length){var t=l.fnGetData(e[0]),d="确认删除代理商"+t.departmentName;i({title:"温馨提示",msg:d,type:"alert",clickHandle:function(){$.get("/system/department/delDepartment?id="+t.id+"&token="+o,function(e){"0000000"===e.rtnCode?(l.fnDraw(),a({type:"delete",id:t.departmentCode})):i({title:"错误提示",msg:e.msg,type:"alert"})})}})}}})}};e.async(["static/scripts/index/renderResource"],function(e){e(m,o)})}});