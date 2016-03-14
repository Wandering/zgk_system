/**
 * Created by kepeng on 15/9/17.
 */
define(function(require, exports, module) {

    var resource = {
        /**
         * 获取某个菜单下的资源
         * @param ButtonEvent
         * @param token
         */
        resourceListModel: function(ButtonEvent, token) {
            var that = this;
            var menuCode = $('.resource').attr('data-id');
            $('#form_button').html('');
            $.get('/system/resource/getAccountResources?menuCode=' + menuCode + '&token=' + token, function(data) {
                if ('0000000' === data.rtnCode) {
                    that.renderButton(data.bizData, ButtonEvent);
                }
            });
        },
        /**
         * 渲染资源
         * @param arry
         * @param ButtonEvent
         */
        renderButton: function(arry, ButtonEvent) {
            var html = [];
            for (var i = 0, len = arry.length; i < len; i++) {
                html.push('<button type="button" class="btn ' + arry[i].resourceIcon
                    + '" id="' + arry[i].resourceUrl + '">' + arry[i].resourceName + '</button>');
            }
            $('#form_button').html(html.join(''));

            for (var i = 0, len = arry.length; i < len; i++) {
                try{
                    if (typeof ButtonEvent[arry[i].resourceUrl] === 'function') {
                        ButtonEvent[arry[i].resourceUrl](arry[i].resourceUrl);
                    }
                } catch(e) {

                }
            }
        }
    };
    module.exports = function(ButtonEvent, token) {
        resource.resourceListModel(ButtonEvent, token);
    }
});