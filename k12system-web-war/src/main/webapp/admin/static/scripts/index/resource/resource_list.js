/**
 * Created by kepeng on 15/9/9.
 */
define(function(require, exports, module) {

    module.exports = function(parentCode, treeObj) {
        require('bootstrap');
        require('cookie');
        var token = $.cookie('bizData');
        var message = require('../message.js');
        var vali = require('./resource_form.js');

        var Table = require('../datatable.js');
        var col = [{
            data: 'id'
        }, {
            data: 'resourceName',
            title: '资源名称'
        }, {
            data: 'resourceUrl',
            title: '资源代码'
        },{
            data: 'resourceIcon',
            title: '资源样式'
        }];

        var columnDefs = [{
            "bVisible": false,
            "aTargets": [0]
        }];

        Table.initTable({
            columns: col,
            tableContentId: 'menu_table_content',
            tableId: new Date().getTime() + '_table_body',
            columnDefs: columnDefs,
            searchHandle: null,
            buttonHandle: null,
            sAjaxSource: '/system/resource/getResourcePage?menuCode=' + parentCode.id + '&token=' + token
        });

        var tableObj = Table.dataTable;

        var addOrUpdateDepartment = function(formArry, succCallback, id) {
            var resourceJson = {
                resourceName: formArry[0] || '',
                resourceUrl: formArry[1] || '',
                resourceIcon: formArry[2] || ''
            };

            if (id) {
                resourceJson.resourceId = id;
            } else {
                resourceJson.menuCode = parentCode.id;
            }
            //console.log(JSON.stringify(resourceJson));
            $.ajax({
                type: 'post',
                url: '/system/resource/addOrEditResource?token=' + token,
                contentType: 'application/x-www-form-urlencoded;charset=UTF-8',
                data: {
                    resourceJson: JSON.stringify(resourceJson)
                },
                dataType: 'json',
                success: function(data) {
                    succCallback(data);
                },
                error: function(data) {

                }
            });
        };

        var close = function() {
            $("#add_resource").dialog("destroy");
        };
        var cancle = {
            text: "取消",
            'class': "btn btn-primary",
            click: function() {
                $("#add_resource").dialog("destroy");
            }
        };

        var ajaxEerror = function(data) {
            var clickHandle = null;
            if ('0100014' === data.rtnCode || '0100015' === data.rtnCode) {
                clickHandle = function() {
                    window.location.href = 'login.html';
                }
            }
            message({
                title: '温馨提示',
                msg: data.msg,
                type: 'alert',
                clickHandle: clickHandle
            });
        };

        var ButtonEvent = {
            add: function(elementId) {
                $('#' + elementId).off('click');
                $('#' + elementId).on('click', function(e) {
                    if (!tableObj) {
                        return;
                    }
                    $.get('../tmpl/resource/resource_form.html', function(tmpl) {
                        require('dialog');
                        $("#add_resource").dialog({
                            title: "新增资源",
                            tmpl: tmpl,
                            onClose: close,
                            render: function() {},
                            buttons: [{
                                text: "新增",
                                'class': "btn btn-primary",
                                click: function() {
                                    var that = this;
                                    vali.validate(function(formArry) {
                                        addOrUpdateDepartment(formArry, function(data) {
                                            if ('0000000' === data.rtnCode) {
                                                tableObj.fnDraw();
                                                $("#add_resource").dialog("destroy");
                                            } else {
                                                $("#add_resource").dialog("destroy");
                                                ajaxEerror(data);
                                            }
                                        });
                                    });
                                }
                            },
                                cancle
                            ]
                        });
                    })
                });
            },
            update: function(elementId) {
                $('#' + elementId).off('click');
                $('#' + elementId).on('click', function(e) {
                    if (!tableObj) {
                        return;
                    }
                    var anSelected = Table.selectedRecord();
                    if (anSelected.length === 0) {
                        return;
                    }
                    var aData = anSelected[0];

                    $.get('/system/resource/getResourcesDetail?resourceId=' + aData.resourceId + '&token=' + token, function(data) {
                        if ('0000000' === data.rtnCode) {
                            $.get('../tmpl/resource/resource_form.html', function(tmpl) {
                                require('dialog');
                                $("#add_resource").dialog({
                                    title: "修改资源",
                                    tmpl: tmpl,
                                    onClose: close,
                                    render: function() {
                                        $('#resource_name').val(data.bizData.resourceName);
                                        $('#resource_url').val(data.bizData.resourceUrl);
                                        $('#resource_icon').val(data.bizData.resourceIcon);
                                    },
                                    buttons: [{
                                        text: "修改",
                                        'class': "btn btn-primary",
                                        click: function() {

                                            vali.validate(function(formArry) {
                                                addOrUpdateDepartment(formArry, function(data) {
                                                    if ('0000000' === data.rtnCode) {
                                                        tableObj.fnDraw();
                                                        $("#add_resource").dialog("destroy");
                                                    } else {
                                                        $("#add_resource").dialog("destroy");
                                                        ajaxEerror(data);
                                                    }
                                                }, aData.resourceId);
                                            });
                                        }
                                    },
                                        cancle
                                    ]
                                });
                            })
                        }
                    });
                });
            },
            delete: function(elementId) {
                $('#' + elementId).off('click');
                $('#' + elementId).on('click', function(e) {
                    if (!tableObj) {
                        return;
                    }
                    var anSelected = Table.selectedRecord();
                    if (anSelected.length !== 0) {
                        var aData = anSelected[0];
                        var str = '确认删除资源' + aData.resourceName;
                        message({
                            title: '温馨提示',
                            msg: str,
                            type: 'alert',
                            clickHandle: function() {
                                $.ajax({
                                    type: 'post',
                                    url: '/system/resource/delResources?resourceId=' + aData.resourceId + '&token=' + token,
                                    dataType: 'json',
                                    success: function(data) {
                                        if ('0000000' === data.rtnCode) {
                                            tableObj.fnDraw();
                                        } else {
                                            ajaxEerror(data);
                                        }
                                    }
                                })
                            }
                        });
                    }
                });
            }
        };

        require.async('../renderResource', function(resource) {
            resource(ButtonEvent, token);
        });
    };
});