define("static/scripts/index/menuManager/menu_form",["static/scripts/index/tools"],function(n,t,e){function i(n){var t=$("#menu_name").val();if(!t)return void r.tip($("#menu_name").parent().parent(),"菜单名称不能为空");var e=$("#menu_icon").val();if(!e)return void r.tip($("#menu_icon").parent().parent(),"菜单代码不能为空");var i=$("#menu_describe").val(),a=$("#menu_sort").val();return/^[1-9][0-9]*$/gi.test(a)?void n(t,e,i,a):void r.tip($("#menu_sort").parent().parent(),"菜单排序只能输入数字")}var r=n("static/scripts/index/tools");e.exports={validate:function(n){i(n)}}});