define(function(require, exports, module) {

    module.exports = function(parentCode, treeCallback) {
        require('bootstrap');
        require('cookie');
        var token = $.cookie('bizData');
        var message = require('../message.js');

        var Table = require('../datatable.js');
        var col = [{
            data: 'id'
        }, {
            data: 'departmentName',
            title: '部门名称'
        }, {
            data: 'departmentPhone',
            title: '部门电话'
        }, {
            data: 'departmentFax',
            title: '部门传真'
        }, {
            data: 'departmentPrincipal',
            title: '部门负责人'
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
            sAjaxSource: '/system/department/queryDepartment?parentCode=' + parentCode + '&token=' + token
        });

        var tableObj = Table.dataTable;

        var errorTip = function(msg) {
            $('#model_error_msg').html(msg);
            $('#model_error_msg').show();
            setTimeout(function() {
                if ($('#model_error_msg')) {
                    $('#model_error_msg').hide();
                }
            }, 2000);
        };

        var addOrUpdateDepartment = function(formArry, succCallback, id) {
            var departmentJson = {
                departmentName: formArry[0] || '',
                departmentPhone: formArry[1] || '',
                departmentFax: formArry[2] || '',
                departmentPrincipal: formArry[3] || ''
            };

            if (id) {
                departmentJson.id = id;
            } else {
                departmentJson.parentCode = parentCode;
            }

            $.ajax({
                type: 'post',
                url: '/system/department/addOrEditDepartment?token=' + token,
                contentType: 'application/x-www-form-urlencoded;charset=UTF-8',
                data: {
                    departmentJson: JSON.stringify(departmentJson)
                },
                dataType: 'json',
                success: function(data) {
                    succCallback(data);
                },
                beforeSend: function(xhr) {},
                error: function(data) {

                }
            });
        };

        var ButtonEvent = {
            add: function(elementId) {
                $('#' + elementId).off('click');
                $('#' + elementId).on('click', function(e) {
                    if (!tableObj) {
                        return;
                    }
                    $.get('../tmpl/dep/dep_form.html', function(tmpl) {
                        require('dialog');
                        $("#add_dep").dialog({
                            title: "新增部门",
                            tmpl: tmpl,
                            onClose: function() {
                                $("#add_dep").dialog("destroy");
                            },
                            render: function() {
                            },
                            buttons: [{
                                text: "新增",
                                'class': "btn btn-primary",
                                click: function() {
                                    var vali = require('./dep_form.js');
                                    vali.validate(function(formArry) {
                                        addOrUpdateDepartment(formArry, function(ret) {
                                            //console.log(ret)
                                            if ('0000000' === ret.rtnCode) {
                                                tableObj.fnDraw();
                                                var node = {
                                                    id: ret.bizData.departmentCode,
                                                    name: formArry[0]
                                                };
                                                treeCallback({
                                                    type: 'add',
                                                    obj: node
                                                });
                                                $("#add_dep").dialog("destroy");
                                            } else {
                                                $("#add_dep").dialog("destroy");
                                                message({
                                                    title: '温馨提示',
                                                    msg: ret.msg,
                                                    type: 'alert',
                                                    clickHandle: function() {
                                                        window.location.href = 'login.html';
                                                    }
                                                });
                                            }
                                        });
                                    });
                                }
                            }, {
                                text: "取消",
                                'class': "btn btn-primary",
                                click: function() {
                                    $("#add_dep").dialog("destroy");
                                }
                            }]
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
                    var anSelected = Table.fnGetSelected(tableObj);
                    if (anSelected.length === 0) {
                        return;
                    }
                    var aData = tableObj.fnGetData(anSelected[0]);
                    //console.log(aData)
                    $.get('/system/department/getDepartment?id=' + aData.id + '&token=' + token, function(data) {
                        if ('0000000' === data.rtnCode) {
                            $.get('../tmpl/dep/dep_form.html', function(tmpl) {
                                require('dialog');
                                $("#add_dep").dialog({
                                    title: "修改部门",
                                    tmpl: tmpl,
                                    onClose: function() {
                                        $("#add_dep").dialog("destroy");
                                    },
                                    render: function() {
                                        $('#dep_name').val(data.bizData.departmentName);
                                        $('#dep_telephone').val(data.bizData.departmentPhone);
                                        $('#dep_fax').val(data.bizData.departmentFax);
                                        $('#dep_leading').val(data.bizData.departmentPrincipal);
                                    },
                                    buttons: [{
                                        text: "修改",
                                        'class': "btn btn-primary",
                                        click: function() {
                                            var vali = require('./dep_form.js');
                                            vali.validate(function(formArry) {
                                                addOrUpdateDepartment(formArry, function(ret) {
                                                    if ('0000000' === ret.rtnCode) {
                                                        tableObj.fnDraw();
                                                        var node = {
                                                            id: data.bizData.departmentCode,
                                                            name: formArry[0]
                                                        };
                                                        treeCallback({
                                                            type: 'update',
                                                            obj: node
                                                        });
                                                        $("#add_dep").dialog("destroy");
                                                    } else {
                                                        $("#add_dep").dialog("destroy");
                                                        message({
                                                            title: '温馨提示',
                                                            msg: ret.msg,
                                                            type: 'alert',
                                                            clickHandle: function() {
                                                                window.location.href = 'login.html';
                                                            }
                                                        });
                                                    }
                                                }, aData.id);
                                            });
                                        }
                                    }, {
                                        text: "取消",
                                        'class': "btn btn-primary",
                                        click: function() {
                                            $("#add_dep").dialog("destroy");
                                        }
                                    }]
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
                    var anSelected = Table.fnGetSelected(tableObj);
                    if (anSelected.length !== 0) {
                        var aData = tableObj.fnGetData(anSelected[0]);
                        var str = '确认删除部门' + aData.departmentName;
                        message({
                            title: '温馨提示',
                            msg: str,
                            type: 'alert',
                            clickHandle: function() {
                                $.get('/system/department/delDepartment?id=' + aData.id + '&token=' + token, function(data) {
                                    if ('0000000' === data.rtnCode) {
                                        tableObj.fnDraw();
                                        treeCallback({
                                            type: 'delete',
                                            id: aData.departmentCode
                                        });
                                    } else {
                                        message({
                                            title: "错误提示",
                                            msg: data.msg,
                                            type: 'alert'
                                        });
                                    }
                                });
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