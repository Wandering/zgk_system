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

    if (typeof String.prototype.trim !== 'function') {
        String.prototype.trim = function() {
            return this.replace(/(^\s*)|(\s*$)/g,'');
        };
    }

    function validateForm(callback) {
        var name = $('#company_name').val().trim();
        var reg = /^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]{1,10}$/;
        if (!reg.test(name)) {
            tip($('#company_name').parent().parent(), '公司名称输入格式错误,长度为1-10');
            return;
        }

        var abbreviation = $('#company_abbreviation').val().trim();
        if (!reg.test(abbreviation)) {
            tip($('#company_abbreviation').parent().parent(), '公司简称输入格式错误，不能为空');
            return;
        }

        var address = $('#company_address').val().trim();
        if (!address) {
            tip($('#company_address').parent().parent(), '公司地址不能为空');
            return;
        }
        var logo = $('#company_logo').val();
        if (!logo) {
            tip($('#company_logo').parent().parent(), 'logo不能为空');
            return;
        }

        var postCode = $('#post_code').val().trim();
        if (!Tool.isPostCode(postCode)) {
            tip($('#post_code').parent().parent(), '邮政编码格式错误');
            return;
        }

        var telephone = $('#telephone').val().trim();
        if (!Tool.isMobile(telephone) && !Tool.isTelephone(telephone)) {
            tip($('#telephone').parent().parent(), '联系电话格式错误');
            return;
        }

        var fax = $('#fax').val().trim();
        if (!Tool.isFax(fax)) {
            tip($('#fax').parent().parent(), '传真格式错误');
            return;
        }

        var contacts = $('#contacts').val().trim();
        if (!contacts) {
            tip($('#contacts').parent().parent(), '联系人不能为空');
            return;
        }

        var eMail = $('#e_mail').val().trim();
        if (!Tool.isEmail(eMail)) {
            tip($('#e_mail').parent().parent(), '邮箱地址格式错误');
            return;
        }
        callback([name, abbreviation, address, logo, postCode, telephone, fax, contacts, eMail]);
    }
    module.exports = {
        validateTheFirstStep: function(callback) {
            validateForm(callback);
        },
        validateTheSecondStep: function(callback) {
            var params = [];
            var name = $('#company_name').val().trim();
            var abbreviation = $('#company_abbreviation').val().trim();
            var address = $('#company_address').val().trim();
            var logo = $('#company_logo').val();
            var postCode = $('#post_code').val().trim();
            var telephone = $('#telephone').val().trim();
            var fax = $('#fax').val().trim();
            var contacts = $('#contacts').val().trim();
            var eMail = $('#e_mail').val().trim();

            params = [name, abbreviation, address, logo, postCode, telephone, fax, contacts, eMail];

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