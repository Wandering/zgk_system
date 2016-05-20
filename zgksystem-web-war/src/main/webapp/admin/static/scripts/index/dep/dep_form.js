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


    function tip2(ele, str) {
        ele.html(str);
        ele.show(500);
        setTimeout(function () {
            ele.hide(500);
        }, 2000)
    }

    if (typeof String.prototype.trim !== 'function') {
        String.prototype.trim = function () {
            return this.replace(/(^\s*)|(\s*$)/g, '');
        };
    }
    //验证用户账号是否存在
    var token = $.cookie('bizData');
    function checkLoginNameIsExist(userName,id) {
        $.ajax({
            type: 'post',
            url: '/system/department/checkDepartmentNameIsExist?token=' + token,
            contentType: 'application/x-www-form-urlencoded;charset=UTF-8',
            data: {
                departmentName: userName,
                departmentId:id
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
        //============名称
        var name = $('#dep_name').val().trim();
        var reg = /^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]{1,10}$/;
        if (!reg.test(name)) {
            tip($('#dep_name').parent().parent(), '代理商名称输入格式错误');
            return;
        }
        var id = $('#dep_name').attr('departmentId');
        if(!id){
            checkLoginNameIsExist(name,"0");
        }else{
            checkLoginNameIsExist(name,id);
        }

        //============电话
        var telephone = $('#dep_telephone').val().trim();
        if (!Tool.isMobile(telephone) && !Tool.isTelephone(telephone)) {
            tip($('#dep_telephone').parent().parent(), '代理商电话输入格式错误');
            return;
        }

        //============传真
        var fax = $('#dep_fax').val().trim();
        if (!Tool.isFax(fax)) {
            tip($('#dep_fax').parent().parent(), '代理商传真输入格式错误');
            return;
        }

        //============负责人
        var leading = $('#dep_leading').val().trim();
        if (!leading) {
            tip($('#dep_leading').parent().parent(), '代理商负责人不能为空');
            return;
        }

        var provinces = $("#dep_provinces").find("option:selected").val();
        var city = $("#dep_city").find("option:selected").val();
        var county = $("#dep_county").find("option:selected").val();


        // 金榜登科 进货价
        var products1Purchases = $.trim($('#products1-purchases').val());
        // 金榜登科 售价
        var products1Price = $.trim($('#products1-price').val());


        // 金榜提名 进货价
        var products2Purchases = $.trim($('#products2-purchases').val());
        // 金榜提名 售价
        var products2Price = $.trim($('#products2-price').val());


        // 状元及第 进货价
        var products3Purchases = $.trim($('#products3-purchases').val());
        // 状元及第 售价
        var products3Price = $.trim($('#products3-price').val());

        var cookieJson = JSON.parse($.cookie('userInfo'));
        var roleType = parseInt(cookieJson.roleType);
        console.log(roleType)


        switch (roleType) {
            case 1:
                //============省份
                //if(!provinces){
                //    tip($("#dep_provinces").parent().parent(), '请选择省份');
                //    return;
                //}
                //============金榜登科
                if (!products1Purchases) {
                    tip2($('#form-error-price'), '金榜登科进货价不能为空');
                    return;
                }
                if (products1Purchases.length > 6 || isNaN(products1Purchases)) {
                    tip2($('#form-error-price'), '金榜登科进货价输入长度或格式错误');
                    return;
                }
                if (!products1Price) {
                    tip2($('#form-error-price'), '金榜登科售价不能为空');
                    return;
                }
                if (products1Price.length > 6 || isNaN(products1Price)) {
                    tip2($('#form-error-price'), '金榜登科售价输入长度或格式错误');
                    return;
                }

                //============金榜提名
                if (!products2Purchases) {
                    tip2($('#form-error-price'), '金榜提名进货价不能为空');
                    return;
                }
                if (products2Purchases.length > 6 || isNaN(products2Purchases)) {
                    tip2($('#form-error-price'), '金榜提名进货价输入长度或格式错误');
                    return;
                }
                if (!products2Price) {
                    tip2($('#form-error-price'), '金榜提名售价不能为空');
                    return;
                }
                if (products2Price.length > 6 || isNaN(products2Price)) {
                    tip2($('#form-error-price'), '金榜提名售价输入长度或格式错误');
                    return;
                }

                //============状元及第
                if (!products3Purchases) {
                    tip2($('#form-error-price'), '状元及第进货价不能为空');
                    return;
                }
                if (products3Purchases.length > 6 || isNaN(products3Purchases)) {
                    tip2($('#form-error-price'), '状元及第进货价输入长度或格式错误');
                    return;
                }
                if (!products3Price) {
                    tip2($('#form-error-price'), '状元及第售价不能为空');
                    return;
                }
                if (products3Price.length > 6 || isNaN(products3Price)) {
                    tip2($('#form-error-price'), '状元及第售价输入长度或格式错误');
                    return;
                }
                //============取货地址
                var goodsAddress = $('#goods_Address').val().trim();
                if (!goodsAddress) {
                    tip($('#goods_Address').parent().parent(), '取货地址不能为空');
                    return;
                }


                callback(
                    [
                        name,    // 部门名称
                        telephone, // 联系电话
                        fax,  // 传真
                        leading, // 部门负责人
                        provinces, // 省份
                        goodsAddress, // 取货地址
                        products1Purchases,//金榜登科进货价
                        products1Price,//金榜登科售价
                        products2Purchases,//金榜提名进货价
                        products2Price,//金榜提名售价
                        products3Purchases,//状元及第进货价
                        products3Price//状元及第售价
                    ]);
                break;
            case 2:
                //============省份
                //if(!city){
                //    tip($("#dep_provinces").parent().parent(), '请选择城市');
                //    return;
                //}
                //============金榜登科
                if (!products1Purchases) {
                    tip2($('#form-error-price'), '金榜登科进货价不能为空');
                    return;
                }
                if (products1Purchases.length > 6 || isNaN(products1Purchases)) {
                    tip2($('#form-error-price'), '金榜登科进货价输入长度或格式错误');
                    return;
                }

                //============金榜提名
                if (!products2Purchases) {
                    tip2($('#form-error-price'), '金榜提名进货价不能为空');
                    return;
                }
                if (products2Purchases.length > 6 || isNaN(products2Purchases)) {
                    tip2($('#form-error-price'), '金榜提名进货价输入长度或格式错误');
                    return;
                }

                //============状元及第
                if (!products3Purchases) {
                    tip2($('#form-error-price'), '状元及第进货价不能为空');
                    return;
                }
                if (products3Purchases.length > 6 || isNaN(products3Purchases)) {
                    tip2($('#form-error-price'), '状元及第进货价输入长度或格式错误');
                    return;
                }
                //============取货地址
                var goodsAddress = $('#goods_Address').val().trim();
                if (!goodsAddress) {
                    tip($('#goods_Address').parent().parent(), '取货地址不能为空');
                    return;
                }

                callback(
                    [
                        name,    // 部门名称
                        telephone, // 联系电话
                        fax,  // 传真
                        leading, // 部门负责人
                        city, // 城市
                        goodsAddress, // 取货地址
                        products1Purchases,//金榜登科进货价
                        products2Purchases,//金榜提名进货价
                        products3Purchases//状元及第进货价
                    ]);
                break;
            case 3:
                //============区县
                //if(!county){
                //    tip($("#dep_provinces").parent().parent(), '请选择区县');
                //    return;
                //}
                //============金榜登科
                if (!products1Purchases) {
                    tip2($('#form-error-price'), '金榜登科进货价不能为空');
                    return;
                }
                if (products1Purchases.length > 6 || isNaN(products1Purchases)) {
                    tip2($('#form-error-price'), '金榜登科进货价输入长度或格式错误');
                    return;
                }

                //============金榜提名
                if (!products2Purchases) {
                    tip2($('#form-error-price'), '金榜提名进货价不能为空');
                    return;
                }
                if (products2Purchases.length > 6 || isNaN(products2Purchases)) {
                    tip2($('#form-error-price'), '金榜提名进货价输入长度或格式错误');
                    return;
                }

                //============状元及第
                if (!products3Purchases) {
                    tip2($('#form-error-price'), '状元及第进货价不能为空');
                    return;
                }
                if (products3Purchases.length > 6 || isNaN(products3Purchases)) {
                    tip2($('#form-error-price'), '状元及第进货价输入长度或格式错误');
                    return;
                }
                //============取货地址
                var goodsAddress = $('#goods_Address').val().trim();
                if (!goodsAddress) {
                    tip($('#goods_Address').parent().parent(), '取货地址不能为空');
                    return;
                }

                callback(
                    [
                        name,    // 部门名称
                        telephone, // 联系电话
                        fax,  // 传真
                        leading, // 部门负责人
                        county, // 区县
                        goodsAddress, // 取货地址
                        products1Purchases,//金榜登科进货价
                        products2Purchases,//金榜提名进货价
                        products3Purchases//状元及第进货价
                    ]);
                break;
            default:
        }
    }

    module.exports = {
        validate: function (callback) {
            console.log(callback);
            validateForm(callback);
        }
    }
});