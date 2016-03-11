/**
 * Created by kepeng on 15/9/11.
 */
define(function(require, exports, module) {
    module.exports = function(options) {
        require('dialog');
        var messageId = 'message_' + (+new Date());
        var buttons = [{
            text: "确认",
            'class': "btn btn-default",
            click: function() {
                $('#' + messageId).dialog("destroy");
                if (options.clickHandle && typeof options.clickHandle === 'function') {
                    options.clickHandle();
                }
            }
        },{
            text: "取消",
            'class': "btn btn-error",
            click: function() {
                $('#' + messageId).dialog("destroy");
            }
        }];

        if ('alert' === options.type) {
            buttons.length = 1;
        }

        $('#' + messageId).dialog({
            title: options.title,
            tmpl: '<div id="' + messageId + '">' + options.msg + '</div>',
            onClose: function() {
                $('#' + messageId).dialog("destroy");
            },
            buttons: buttons
        });
    };
});
