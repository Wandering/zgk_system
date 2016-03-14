/**
 * Created by kepeng on 15/9/15.
 */

define(function(require, exports, module) {

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
        var resourceName = $('#resource_name').val().trim();
        var reg = /^[\u4e00-\u9fa5]{1,5}$/ig;
        if (!reg.test(resourceName)) {
            tip($('#resource_name').parent().parent(), '资源名称输入格式错误');
            return;
        }

        var resourceUrl = $('#resource_url').val().trim();
        if (!/^[A-Za-z]+$/ig.test(resourceIcon)) {
            tip($('#resource_url').parent().parent(), '资源代码不能为空，且只能为字母');
            return;
        }

        var resourceIcon = $('#resource_icon').val().trim();

        callback([resourceName, resourceUrl, resourceIcon]);
    }
    module.exports = {
        validate: function(callback) {
            validateForm(callback);
        }
    }
});

