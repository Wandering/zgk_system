define(function(require, exports, module) {
	var Tool = require('../tools.js');

	function validateForm(callback) {
		var menuName = $('#menu_name').val();
		if (!menuName) {
			Tool.tip($('#menu_name').parent().parent(), '菜单名称不能为空');
			return;
		}
		var menuIcon = $('#menu_icon').val();
		if (!menuIcon) {
			Tool.tip($('#menu_icon').parent().parent(), '菜单代码不能为空');
			return;
		}
		var menuDescribe = $('#menu_describe').val();
		var menuSort = $('#menu_sort').val();
		if (!/^[1-9][0-9]*$/ig.test(menuSort)) {
			Tool.tip($('#menu_sort').parent().parent(), '菜单排序只能输入数字');
			return;
		}
		callback(menuName, menuIcon, menuDescribe, menuSort);
	}
	module.exports = {
		validate: function(callback) {
			validateForm(callback);
		}
	}
});