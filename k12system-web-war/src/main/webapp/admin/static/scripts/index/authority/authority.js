/**
 * Created by kepeng on 15/9/16.
 */
define(function(require, exports, module) {
    module.exports = function() {
        require('bootstrap');
        require('cookie');
        var token = $.cookie('bizData');
        var message = require('../message.js');
        var Table = require('../datatable.js');
        var col = [{
            data: 'postCode'
        }, {
            data: 'postName',
            title: '岗位名称'
        }];

        var columnDefs = [{
            "bVisible": false,
            "aTargets": [0]
        }];

        var tip = function(msg) {
            message({
                title: '温馨提示',
                msg: msg,
                type: 'alert'
            });
        };

        Table.initTable({
            columns: col,
            tableContentId: 'table_content',
            tableId: 'table_body_' + (+new Date()),
            columnDefs: columnDefs,
            sAjaxSource: '/system/post/getManagerPost?token=' + token
        });

        var ButtonEvent = {
            distributionRight: function(elementId) {
                $('#' + elementId).off('click');
                $('#' + elementId).on('click', function(e) {
                    var selectData = Table.selectedRecord();
                    if (selectData.length != 1) {
                        tip('每次只能选择一项分配权限');
                        return;
                    }
                    var postCode = selectData[0].postCode;
                    require('dialog');
                    $("#role_radio").dialog({
                        title: "分配权限",
                        tmpl: '<div id="role_radio"></div>',
                        onClose: function() {
                            $("#role_radio").dialog("destroy");
                        },
                        render: function() {
                            $.get('/system/role/getPostRoles?token=' + token + '&postCode=' + postCode, function(ret) {
                                var currentCode;
                                if ('0000000' === ret.rtnCode) {
                                    currentCode = ret.bizData.roleCode
                                }
                                $.get('/system/role/queryComboxRoles?token=' + token, function(data) {
                                    if ('0000000' === data.rtnCode) {
                                        var role = data.bizData;
                                        if (!role) {
                                            $("#role_radio").dialog("destroy");
                                            tip('请添加相关角色信息');
                                            return;
                                        }
                                        var html = [];
                                        for (var key in role) {
                                            html.push('<div class="radio"><label>');
                                            var check = '';
                                            if (key == currentCode) {
                                                check = 'checked';
                                            }
                                            html.push('<input type="radio" name="roleRadios" value="' + key + '" ' + check + '>');
                                            html.push(role[key]);
                                            html.push('</label></div>');
                                        }
                                        html.push('<div class="radio" id="error_tip" style="color: #f00;"></div>');
                                        $("#role_radio").html(html.join(''));
                                    } else {
                                        $("#role_radio").dialog("destroy");
                                        if ('0100010' === data.rtnCode) {
                                            tip('请添加相关角色信息');
                                        }
                                    }
                                });
                            });
                        },
                        buttons: [{
                            text: "保存",
                            'class': "btn btn-primary",
                            click: function() {
                                var roleCode = $("input[name='roleRadios']:checked").val();
                                if (!roleCode) {
                                    $('#error_tip').html('请选择角色权限');
                                    return;
                                }
                                var rolePostJson = {
                                    postCode:postCode,
                                    roleCode:roleCode
                                };
                                $.ajax({
                                    type: 'post',
                                    url: '/system/role/distributionRoles?token=' + token,
                                    contentType: 'application/x-www-form-urlencoded;charset=utf-8',
                                    data: {
                                        rolePostJson: JSON.stringify(rolePostJson)
                                    },
                                    dataType: 'json',
                                    success: function(data) {
                                        if ('0000000' === data.rtnCode) {
                                            $("#role_radio").dialog("destroy");
                                        } else {
                                            $("#role_radio").dialog("destroy");
                                            message({
                                                title: '温馨提示',
                                                msg: data.msg,
                                                type: 'alert',
                                                clickHandle: function() {
                                                    window.location.href = 'login.html';
                                                }
                                            });
                                        }
                                    }
                                });
                            }
                        }, {
                            text: "取消",
                            'class': "btn btn-primary",
                            click: function() {
                                $("#role_radio").dialog("destroy");
                            }
                        }]
                    });
                });
            }
        };

        require.async('../renderResource', function(resource) {
            resource(ButtonEvent, token);
        });
    }
});
