define("static/scripts/index/dep/dep_form",["static/scripts/index/tools"],function(e,t,a){function r(e,t){var a=e.find("p");a.html(t),a.show(500),setTimeout(function(){a.hide(500)},2e3)}function n(e){$.ajax({type:"post",url:"/system/department/checkDepartmentNameIsExist?token="+o,contentType:"application/x-www-form-urlencoded;charset=UTF-8",data:{departmentName:e},dataType:"json",success:function(e){return"0000000"===e.rtnCode&&"0"==e.bizData?(r($("#dep_name").parent().parent(),"账户已存在"),$("#dep_name").focus(),void $("#dep_name").attr("data-flag","isExist")):void $("#dep_name").attr("data-flag","")},beforeSend:function(){},error:function(){$("#dep_name").attr("data-flag","")}})}function i(e){var t=$("#dep_name").val().trim(),a=/^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]{1,10}$/;if(!a.test(t))return void r($("#dep_name").parent().parent(),"代理商名称输入格式错误");n(t);var i=$("#dep_telephone").val().trim();if(!p.isMobile(i)&&!p.isTelephone(i))return void r($("#dep_telephone").parent().parent(),"代理商电话输入格式错误");var o=$("#dep_fax").val().trim();if(!p.isFax(o))return void r($("#dep_fax").parent().parent(),"代理商传真输入格式错误");var d=$("#dep_leading").val().trim();if(!d)return void r($("#dep_leading").parent().parent(),"代理商负责人不能为空");var s=$("#dep_provinces").find("option:selected").val(),c=$("#dep_city").find("option:selected").val(),l=$("#dep_county").find("option:selected").val(),v=$.trim($("#sale_Price").val()),f=$.trim($("#webPrice").val()),u=$.trim($("#wechatPrice").val());if(!v)return void r($("#sale_Price").parent().parent(),"零售价格不能为空");if(v.length>6||isNaN(v))return void r($("#sale_Price").parent().parent(),"零售价格输入长度或格式错误");var m=$("#goods_Address").val().trim();return m?f?f.length>6||isNaN(f)?void r($("#webPrice").parent().parent(),"web售价输入长度或格式错误"):u?u.length>6||isNaN(u)?void r($("#wechatPrice").parent().parent(),"微信售价输入长度或格式错误"):void e([t,i,o,d,s,c,l,v,m,f,u]):void r($("#wechatPrice").parent().parent(),"微信售价不能为空"):void r($("#webPrice").parent().parent(),"web售价不能为空"):void r($("#goods_Address").parent().parent(),"取货地址不能为空")}var p=e("static/scripts/index/tools");"function"!=typeof String.prototype.trim&&(String.prototype.trim=function(){return this.replace(/(^\s*)|(\s*$)/g,"")});var o=$.cookie("bizData");a.exports={validate:function(e){i(e)}}});