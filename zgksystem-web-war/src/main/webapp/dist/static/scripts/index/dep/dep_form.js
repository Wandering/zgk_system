define("static/scripts/index/dep/dep_form",["static/scripts/index/tools"],function(e,t,i){function n(e,t){var i=e.find("p");i.html(t),i.show(500),setTimeout(function(){i.hide(500)},2e3)}function r(e){var t=$("#dep_name").val().trim(),i=/^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]{1,10}$/;if(!i.test(t))return void n($("#dep_name").parent().parent(),"代理商名称输入格式错误");var r=$("#dep_telephone").val().trim();if(!p.isMobile(r)&&!p.isTelephone(r))return void n($("#dep_telephone").parent().parent(),"代理商电话输入格式错误");var a=$("#dep_fax").val().trim();if(!p.isFax(a))return void n($("#dep_fax").parent().parent(),"代理商传真输入格式错误");var o=$("#dep_leading").val().trim();if(!o)return void n($("#dep_leading").parent().parent(),"代理商负责人不能为空");var d=$("#dep_provinces").find("option:selected").val(),s=$("#dep_city").find("option:selected").val(),f=$("#dep_county").find("option:selected").val();e([t,r,a,o,d,s,f])}var p=e("static/scripts/index/tools");"function"!=typeof String.prototype.trim&&(String.prototype.trim=function(){return this.replace(/(^\s*)|(\s*$)/g,"")}),i.exports={validate:function(e){r(e)}}});