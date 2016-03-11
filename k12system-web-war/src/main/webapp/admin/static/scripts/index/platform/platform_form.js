define(function(require, exports, module) {
	var Tool = require('../tools.js');

	function validateForm(callback) {
		var sysName = $('#platform_name').val().trim();
		var reg = /^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]{2,10}$/;
		if (!reg.test(sysName)) {
			Tool.tip($('#platform_name').parent().parent(), '平台名称格式输入错误');
			return;
		}
		var sysUrl = $('#platform_url').val().trim();
		if (!Tool.isURL(sysUrl)) {
			Tool.tip($('#platform_url').parent().parent(), '平台地址格式输入错误');
			return;
		}
		var sysLogo = $('#platform_logo').val();
		if (!sysLogo) {
			Tool.tip($('#platform_logo').parent().parent(), '平台logo不能为空');
			return;
		}
		var sysDescribe = $('#platform_describe').val().trim();
		if (sysDescribe.length > 100) {
			Tool.tip($('#platform_describe').parent().parent(), '描述长度不能超过100字符！');
			return;
		}
		callback(sysName, sysUrl, sysLogo, sysDescribe);
	};

	module.exports = {
		validateTheFirstStep: function(callback) {
			validateForm(callback);
		},
		validateTheSecondStep: function(callback) {
			var realName = $('#platform_real_name').val().trim();
			if (!realName) {
				Tool.tip($('#platform_real_name').parent().parent(), '管理员真实姓名不能为空');
				return;
			}
			var userName = $('#platform_user_name').val().trim();
			var reg = /^[0-9a-zA-Z]{6,10}$/ig;
			if (!reg.test(userName)) {
				Tool.tip($('#platform_user_name').parent().parent(), '管理员名称格式不符');
				return;
			}

			var pwd = $('#platform_user_pwd').val().trim();
			if (!/^[0-9a-zA-Z]{6,10}$/ig.test(pwd)) {
				Tool.tip($('#platform_user_pwd').parent().parent(), '管理员，密码格式不符');
				return;
			}
			require('md5');
			pwd = $.md5(pwd);

			var phone = $('#platform_user_phone').val().trim();
			if (!Tool.isMobile(phone) && !Tool.isTelephone(phone)) {
				Tool.tip($('#platform_user_phone').parent().parent(), '管理员电话格式不符');
				return;
			}

			var email = $('#platform_user_email').val().trim();
			if (!Tool.isEmail(email)) {
				Tool.tip($('#platform_user_email').parent().parent(), '管理员邮箱格式不符');
				return;
			}
			var sysName = $('#platform_name').val().trim();
			var sysUrl = $('#platform_url').val().trim();
			var sysLogo = $('#platform_logo').val();
			var sysDescribe = $('#platform_describe').val().trim();

			callback(sysName, sysUrl, sysLogo, sysDescribe, userName, pwd, realName, phone, email);
		}
	}
});