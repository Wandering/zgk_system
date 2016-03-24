/**
 * Created by kepeng on 15/9/10.
 */

define(function(require, exports, module) {
    var Tool = require('../tools.js');
    function tip(ele, str) {
        var errorLable = ele.find('p');
        errorLable.html(str);
        errorLable.fadeIn(500);
        setTimeout(function() {
            errorLable.fadeOut(500);
        }, 2000)
    }

    if (typeof String.prototype.trim !== 'function') {
        String.prototype.trim = function() {
            return this.replace(/(^\s*)|(\s*$)/g,'');
        };
    }

    function validateForm(callback, flag) {
        var position_name = $('#position_name').val();
        if (!position_name) {
            tip($('#position_name').parent().parent(), '请选择代理商名称');
            return;
        }
        var name = $('#employee_name').val().trim();
        var reg = /^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]{1,10}$/;
        if (!reg.test(name)) {
            tip($('#employee_name').parent().parent(), '人员姓名输入格式错误');
            return;
        }

        var login_name = '', login_pwd = '';

        if ('update' !== flag) {
            login_name = $('#login_name').val().trim();
            var regLogin = /^(\w|[\u4E00-\u9FA5]){6,10}$/ig;
            if (!regLogin.test(login_name)) {
                tip($('#login_name').parent().parent(), '账户格式输入错误');
                return;
            }

            login_pwd = $('#login_pwd').val().trim();
            var regPWD = /^(\w){6,10}$/ig;
            if (!regPWD.test(login_pwd)) {
                tip($('#login_pwd').parent().parent(), '密码格式输入错误，只能输入字母和数字，长度6-10');
                return;
            }
            var isExist = $('#login_name').attr('data-flag');
            if ('isExist' === isExist) {
                tip($('#login_name').parent().parent(), '账户已存在');
                return;
            }
        }

        var telephone = $('#employee_telephone').val().trim();
        if (!Tool.isMobile(telephone) && !Tool.isTelephone(telephone)) {
            tip($('#employee_telephone').parent().parent(), '联系电话格式错误');
            return;
        }

        var eMail = $('#employee_email').val().trim();
        if (!Tool.isEmail(eMail)) {
            tip($('#employee_email').parent().parent(), '邮箱地址格式错误');
            return;
        }

        var employee_describe = $('#employee_describe').val().trim();
        if (employee_describe.length > 100) {
            tip($('#employee_describe').parent().parent(), '描述不能超过100字符！');
            return;
        }
        //if (school_checkbox.length <= 0) {
        //    tip($('#school_checkbox').parent(), '请选择管辖学校');
        //    return;
        //}

        callback([position_name, name, login_name, login_pwd, telephone, eMail, employee_describe]);
    }
    module.exports = {
        validate: function(callback, flag) {
            validateForm(callback, flag);
        }
    }
});
