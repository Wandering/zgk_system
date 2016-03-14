define(function(require, exports, module) {
	var Tool = require('../tools.js');

	function tip(ele, str) {
		var errorLable = ele.find('p');
		errorLable.html(str);
		errorLable.show(500);
		setTimeout(function() {
			errorLable.hide(500);
		}, 2000)
	}

	function validateForm(callback) {
		var name = $('#product_name').val();
		var reg = /^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]{2,10}$/;
		if (!reg.test(name)) {
			tip($('#product_name').parent().parent(), '产品名称输入格式错误，汉字、数字或者字母，2-10个字符');
			return;
		}
		var url = $('#product_url').val();
		if (!Tool.isURL(url)) {
			tip($('#product_url').parent().parent(), '输产品地址入格式错误');
			return;
		}
		var logo = $('#product_logo').val();
		if (!logo) {
			tip($('#product_logo').parent().parent(), '产品logo不能为空');
			return;
		}
		var describe = $('#product_describe').val();
		if (describe.length > 100) {
			tip($('#product_describe').parent().parent(), '描述不能超过100字符！');
			return;
		}
		callback(name, url, logo, describe);
	}
	module.exports = {
		validateTheFirstStep: function(callback) {
			validateForm(callback);
		},
		validateTheSecondStep: function(callback) {
			var params = [];
			var name = $('#product_name').val();
			var url = $('#product_url').val();
			var logo = $('#product_logo').val();
			var describe = $('#product_describe').val();

			params = [name, url, logo, describe];

			var realName = $('#real_name').val().trim();
			if (!realName) {
				tip($('#real_name').parent().parent(), '管理员的真实姓名不能为空');
				return;
			}

			params.push(realName);
			var userName = $('#user_name').val().trim();
			if (!/^[0-9a-zA-Z]{6,10}$/ig.test(userName)) {
				tip($('#real_name').parent().parent(), '管理员账号格式错误');
				return;
			}

			params.push(userName);

			var userPwd = $('#user_pwd').val().trim();
			if (!/^[0-9a-zA-Z]{6,10}$/ig.test(userPwd)) {
				tip($('#user_pwd').parent().parent(), '管理员密码格式错误');
				return;
			}

			require('md5');
			userPwd = $.md5(userPwd);
			params.push(userPwd);

			var userPhone = $('#user_phone').val().trim();
			if (!Tool.isMobile(userPhone) && !Tool.isTelephone(userPhone)) {
				tip($('#user_phone').parent().parent(), '管理员电话格式错误');
				return;
			}

			params.push(userPhone);

			var userEmail = $('#user_email').val().trim();
			if (!Tool.isEmail(userEmail)) {
				tip($('#user_email').parent().parent(), '管理员邮箱格式错误');
				return;
			}

			params.push(userEmail);

			callback(params);
		}
	}
});