define(function(require, exports, module) {
	var Tool = (function() {
		var tool = {
			/**
			 * 验证url
			 * @param {Object} str
			 */
			isURL: function(str) {
				return /^https?:\/\/(([a-zA-Z0-9_-])+(\.)?)*(:\d+)?(\/((\.)?(\?)?=?&?[a-zA-Z0-9_-](\?)?)*)*$/ig.test(str);
			},
			/**
			 * 邮政编码
			 * @param str
			 * @returns {boolean}
			 */
			isPostCode: function(str) {
				return /[1-9]\d{5}(?!\d)/ig.test(str);
			},
			/**
			 * 手机号码
			 * @param str
			 * @returns {boolean}
			 */
			isMobile: function(str) {
				var reg = /^1[3|4|5|7|8][0-9]\d{8}$/ig;
				return reg.test(str);
			},
			/**
			 * 固定电话
			 * @param str
			 * @returns {boolean}
			 */
			isTelephone: function(str) {
				var reg = /^([0-9]{3,4}-)?[0-9]{7,8}$/ig;
				return reg.test(str);
			},
			/**
			 * 邮箱地址
			 * @param str
			 * @returns {boolean}
			 */
			isEmail: function(str) {
				var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/ig;
				return reg.test(str);
			},
			/**
			 *  传真
			 * @param str
			 * @returns {boolean}
			 */
			isFax: function(str) {
				var reg = /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/ig;
				return reg.test(str);
			},
			/**
			 *  数字、字母、汉字
			 * @param str
			 * @returns {boolean}
			 */
			isName: function(str) {
				return /^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]{2,10}$/ig.test(str);
			},
			/**
			 * 时间格式化
			 * @param {Object} date
			 * @param {Object} format
			 */
			timeFormat: function(date, format) {
				var o = {
					"M+": date.getMonth() + 1, //month
					"d+": date.getDate(), //day
					"h+": date.getHours(), //hour
					"m+": date.getMinutes(), //minute
					"s+": date.getSeconds(), //second
					"q+": Math.floor((date.getMonth() + 3) / 3), //quarter
					"S": date.getMilliseconds() //millisecond
				}
				if (/(y+)/.test(format)) format = format.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
				for (var k in o)
					if (new RegExp("(" + k + ")").test(format))
						format = format.replace(RegExp.$1,
							RegExp.$1.length == 1 ? o[k] :
							("00" + o[k]).substr(("" + o[k]).length));
				return format;
			},
			tip: function(ele, str) {
				var errorLable = ele.find('p');
				errorLable.html(str);
				errorLable.show(500);
				setTimeout(function() {
					errorLable.hide(500);
				}, 2000)
			}
		};

		return tool;
	})();

	if (typeof String.prototype.trim !== 'function') {
		String.prototype.trim = function() {
			return this.replace(/(^\s*)|(\s*$)/g,'');
		};
	}

	module.exports = Tool;
});