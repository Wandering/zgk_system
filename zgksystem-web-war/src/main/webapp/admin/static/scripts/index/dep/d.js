define("static/scripts/index/dep/dep_form", ["static/scripts/index/tools"], function (e, t, r) {
    function a(e, t) {
        var r = e.find("p");
        r.html(t), r.show(500), setTimeout(function () {
            r.hide(500)
        }, 2e3)
    }

    function n(e) {
        $.ajax({
            type: "post",
            url: "/system/department/checkDepartmentNameIsExist?token=" + p,
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            data: {departmentName: e},
            dataType: "json",
            success: function (e) {
                return "0000000" === e.rtnCode && "0" == e.bizData ? (a($("#dep_name").parent().parent(), "账户已存在"), $("#dep_name").focus(), void $("#dep_name").attr("data-flag", "isExist")) : void $("#dep_name").attr("data-flag", "")
            },
            beforeSend: function () {
            },
            error: function () {
                $("#dep_name").attr("data-flag", "")
            }
        })
    }

    function i(e) {
        var t = $("#dep_name").val().trim(), r = /^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]{1,10}$/;
        if (!r.test(t))return void a($("#dep_name").parent().parent(), "代理商名称输入格式错误");
        n(t);
        var i = $("#dep_telephone").val().trim();
        if (!o.isMobile(i) && !o.isTelephone(i))return void a($("#dep_telephone").parent().parent(), "代理商电话输入格式错误");
        var p = $("#dep_fax").val().trim();
        if (!o.isFax(p))return void a($("#dep_fax").parent().parent(), "代理商传真输入格式错误");
        var d = $("#dep_leading").val().trim();
        if (!d)return void a($("#dep_leading").parent().parent(), "代理商负责人不能为空");
        var s = $("#dep_provinces").find("option:selected").val(), c = $("#dep_city").find("option:selected").val(), l = $("#dep_county").find("option:selected").val(), f = $.trim($("#sale_Price").val()), v = $.trim($("#webPrice").val()), u = $.trim($("#wechatPrice").val());
        if (!f)return void a($("#sale_Price").parent().parent(), "零售价格不能为空");
        if (f.length > 6 || isNaN(f))return void a($("#sale_Price").parent().parent(), "零售价格输入长度或格式错误");
        var m = $("#goods_Address").val().trim();
        if (!m)return void a($("#goods_Address").parent().parent(), "取货地址不能为空");
        var _ = JSON.parse($.cookie("userInfo")), h = parseInt(_.roleType);
        if (console.log(h), "1" == h) {
            if (!v)return void a($("#webPrice").parent().parent(), "web售价不能为空");
            if (v.length > 6 || isNaN(v))return void a($("#webPrice").parent().parent(), "web售价输入长度或格式错误");
            if (!u)return void a($("#wechatPrice").parent().parent(), "微信售价不能为空");
            if (u.length > 6 || isNaN(u))return void a($("#wechatPrice").parent().parent(), "微信售价输入长度或格式错误");
            e([t, i, p, d, s, c, l, f, m, v, u])
        } else e([t, i, p, d, s, c, l, f, m])
    }

    var o = e("static/scripts/index/tools");
    "function" != typeof String.prototype.trim && (String.prototype.trim = function () {
        return this.replace(/(^\s*)|(\s*$)/g, "")
    });
    var p = $.cookie("bizData");
    r.exports = {
        validate: function (e) {
            i(e)
        }
    }
});