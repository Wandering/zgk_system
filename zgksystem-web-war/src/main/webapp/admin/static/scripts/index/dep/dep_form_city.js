/**
 * Created by kepeng on 15/9/9.
 */
define(function (require, exports, module) {
    var Tool = require('../tools.js');


    function tip(ele, str) {
        var errorLable = ele.find('p');
        errorLable.html(str);
        errorLable.show(500);
        setTimeout(function () {
            errorLable.hide(500);
        }, 2000)
    }

    if (typeof String.prototype.trim !== 'function') {
        String.prototype.trim = function () {
            return this.replace(/(^\s*)|(\s*$)/g, '');
        };
    }
    //验证用户账号是否存在
    var token = $.cookie('bizData');

    function checkLoginNameIsExist(userName) {
        $.ajax({
            type: 'post',
            url: '/system/department/checkDepartmentNameIsExist?token=' + token,
            contentType: 'application/x-www-form-urlencoded;charset=UTF-8',
            data: {
                departmentName: userName
            },
            dataType: 'json',
            success: function (data) {
                if ('0000000' === data.rtnCode) {
                    if ('0' == data.bizData) {
                        tip($('#dep_name').parent().parent(), '账户已存在');
                        $('#dep_name').focus();
                        $('#dep_name').attr('data-flag', 'isExist');
                        return;
                    }
                }
                $('#dep_name').attr('data-flag', '');
            },
            beforeSend: function (xhr) {
            },
            error: function (data) {
                $('#dep_name').attr('data-flag', '');
            }
        });
    }

    function validateForm(callback) {
        var name = $('#dep_name').val().trim();
        var reg = /^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]{1,10}$/;
        if (!reg.test(name)) {
            tip($('#dep_name').parent().parent(), '代理商名称输入格式错误');
            return;
        }
        checkLoginNameIsExist(name);
        var telephone = $('#dep_telephone').val().trim();
        if (!Tool.isMobile(telephone) && !Tool.isTelephone(telephone)) {
            tip($('#dep_telephone').parent().parent(), '代理商电话输入格式错误');
            return;
        }
        var fax = $('#dep_fax').val().trim();
        if (!Tool.isFax(fax)) {
            tip($('#dep_fax').parent().parent(), '代理商传真输入格式错误');
            return;
        }
        var leading = $('#dep_leading').val().trim();
        if (!leading) {
            tip($('#dep_leading').parent().parent(), '代理商负责人不能为空');
            return;
        }

        var provinces = $("#dep_provinces").find("option:selected").val();
        var city = $("#dep_city").find("option:selected").val();
        var county = $("#dep_county").find("option:selected").val();
        var salePrice = $.trim($('#sale_Price').val());
        var webPrice = $.trim($('#webPrice').val());
        var wechatPrice = $.trim($('#wechatPrice').val());
        if (!salePrice) {
            tip($('#sale_Price').parent().parent(), '零售价格不能为空');
            return;
        }
        if (salePrice.length > 6 || isNaN(salePrice)) {
            tip($('#sale_Price').parent().parent(), '零售价格输入长度或格式错误');
            return;
        }
        var goodsAddress = $('#goods_Address').val().trim();
        if (!goodsAddress) {
            tip($('#goods_Address').parent().parent(), '取货地址不能为空');
            return;
        }
        if (!webPrice) {
            tip($('#webPrice').parent().parent(), 'web售价不能为空');
            return;
        }
        if (webPrice.length > 6 || isNaN(webPrice)) {
            tip($('#webPrice').parent().parent(), 'web售价输入长度或格式错误');
            return;
        }
        if (!wechatPrice) {
            tip($('#wechatPrice').parent().parent(), '微信售价不能为空');
            return;
        }
        if (wechatPrice.length > 6 || isNaN(wechatPrice)) {
            tip($('#wechatPrice').parent().parent(), '微信售价输入长度或格式错误');
            return;
        }
        callback(
            [
                name,    // 部门名称
                telephone, // 联系电话
                fax,  // 传真
                leading, //
                provinces, // 省份
                city,   // 市
                county, // 城市
                salePrice, // 拿货价
                goodsAddress, // 取货地址
                webPrice,  // web售价
                wechatPrice // 微信售价
            ]);
    }

    module.exports = {
        validate: function (callback) {
            validateForm(callback);
        }
    }
});