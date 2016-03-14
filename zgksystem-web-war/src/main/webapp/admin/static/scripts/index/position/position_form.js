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
        var name = $('#position_name').val().trim();
        var reg = /^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]{1,10}$/;
        if (!reg.test(name)) {
            tip($('#position_name').parent().parent(), '岗位名称输入格式错误');
            return;
        }

        var describe = $('#position_describe').val().trim();
        if (describe.length > 100) {
            tip($('#position_describe').parent().parent(), '描述不能超过100字符！');
            return;
        }


        var areaIdNum = parseInt($('#area').val(), 10);

        var areaId = [areaIdNum];
        var school_checkbox = [];

        if (areaIdNum != 0) {
            $('#school_checkbox input[name="school"]:checked').each(function() {
                school_checkbox.push($(this).val());
            });

            if ($('#school_checkbox input[name="school-checked-all"]')[0].checked) {
                school_checkbox = [];
            }
        }
        callback([name, describe, areaId, school_checkbox]);
    }
    module.exports = {
        validate: function(callback) {
            validateForm(callback);
        }
    }
});
