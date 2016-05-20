define("static/scripts/index/dep/dep_form",["static/scripts/index/tools"],function(r,e,i){function t(r,e){var i=r.find("p");i.html(e),i.show(500),setTimeout(function(){i.hide(500)},2e3)}function o(r,e){r.html(e),r.show(500),setTimeout(function(){r.hide(500)},2e3)}function n(r){$.ajax({type:"post",url:"/system/department/checkDepartmentNameIsExist?token="+d,contentType:"application/x-www-form-urlencoded;charset=UTF-8",data:{departmentName:r},dataType:"json",success:function(r){return"0000000"===r.rtnCode&&"0"==r.bizData?(t($("#dep_name").parent().parent(),"账户已存在"),$("#dep_name").focus(),void $("#dep_name").attr("data-flag","isExist")):void $("#dep_name").attr("data-flag","")},beforeSend:function(){},error:function(){$("#dep_name").attr("data-flag","")}})}function a(r){var e=$("#dep_name").val().trim(),i=/^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]{1,10}$/;if(!i.test(e))return void t($("#dep_name").parent().parent(),"代理商名称输入格式错误");n(e);var a=$("#dep_telephone").val().trim();if(!p.isMobile(a)&&!p.isTelephone(a))return void t($("#dep_telephone").parent().parent(),"代理商电话输入格式错误");var d=$("#dep_fax").val().trim();if(!p.isFax(d))return void t($("#dep_fax").parent().parent(),"代理商传真输入格式错误");var s=$("#dep_leading").val().trim();if(!s)return void t($("#dep_leading").parent().parent(),"代理商负责人不能为空");var f=$("#dep_provinces").find("option:selected").val(),c=$("#dep_city").find("option:selected").val(),u=$("#dep_county").find("option:selected").val(),v=$.trim($("#products1-purchases").val()),m=$.trim($("#products1-price").val()),l=$.trim($("#products2-purchases").val()),_=$.trim($("#products2-price").val()),g=$.trim($("#products3-purchases").val()),h=$.trim($("#products3-price").val()),N=JSON.parse($.cookie("userInfo")),x=parseInt(N.roleType);switch(console.log(x),x){case 1:if(!f)return void t($("#dep_provinces").parent().parent(),"请选择省份");if(!v)return void o($("#form-error-price"),"金榜登科进货价不能为空");if(v.length>6||isNaN(v))return void o($("#form-error-price"),"金榜登科进货价输入长度或格式错误");if(!m)return void o($("#form-error-price"),"金榜登科售价不能为空");if(m.length>6||isNaN(m))return void o($("#form-error-price"),"金榜登科售价输入长度或格式错误");if(!l)return void o($("#form-error-price"),"金榜提名进货价不能为空");if(l.length>6||isNaN(l))return void o($("#form-error-price"),"金榜提名进货价输入长度或格式错误");if(!_)return void o($("#form-error-price"),"金榜提名售价不能为空");if(_.length>6||isNaN(_))return void o($("#form-error-price"),"金榜提名售价输入长度或格式错误");if(!g)return void o($("#form-error-price"),"状元及第进货价不能为空");if(g.length>6||isNaN(g))return void o($("#form-error-price"),"状元及第进货价输入长度或格式错误");if(!h)return void o($("#form-error-price"),"状元及第售价不能为空");if(h.length>6||isNaN(h))return void o($("#form-error-price"),"状元及第售价输入长度或格式错误");var y=$("#goods_Address").val().trim();if(!y)return void t($("#goods_Address").parent().parent(),"取货地址不能为空");r([e,a,d,s,f,y,v,m,l,_,g,h]);break;case 2:if(!c)return void t($("#dep_provinces").parent().parent(),"请选择城市");if(!v)return void o($("#form-error-price"),"金榜登科进货价不能为空");if(v.length>6||isNaN(v))return void o($("#form-error-price"),"金榜登科进货价输入长度或格式错误");if(!l)return void o($("#form-error-price"),"金榜提名进货价不能为空");if(l.length>6||isNaN(l))return void o($("#form-error-price"),"金榜提名进货价输入长度或格式错误");if(!g)return void o($("#form-error-price"),"状元及第进货价不能为空");if(g.length>6||isNaN(g))return void o($("#form-error-price"),"状元及第进货价输入长度或格式错误");var y=$("#goods_Address").val().trim();if(!y)return void t($("#goods_Address").parent().parent(),"取货地址不能为空");r([e,a,d,s,c,y,v,l,g]);break;case 3:if(!u)return void t($("#dep_provinces").parent().parent(),"请选择区县");if(!v)return void o($("#form-error-price"),"金榜登科进货价不能为空");if(v.length>6||isNaN(v))return void o($("#form-error-price"),"金榜登科进货价输入长度或格式错误");if(!l)return void o($("#form-error-price"),"金榜提名进货价不能为空");if(l.length>6||isNaN(l))return void o($("#form-error-price"),"金榜提名进货价输入长度或格式错误");if(!g)return void o($("#form-error-price"),"状元及第进货价不能为空");if(g.length>6||isNaN(g))return void o($("#form-error-price"),"状元及第进货价输入长度或格式错误");var y=$("#goods_Address").val().trim();if(!y)return void t($("#goods_Address").parent().parent(),"取货地址不能为空");r([e,a,d,s,u,y,v,l,g])}}var p=r("static/scripts/index/tools");"function"!=typeof String.prototype.trim&&(String.prototype.trim=function(){return this.replace(/(^\s*)|(\s*$)/g,"")});var d=$.cookie("bizData");i.exports={validate:function(r){console.log(r),a(r)}}});