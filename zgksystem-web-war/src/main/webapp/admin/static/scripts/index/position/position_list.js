/**
 * Created by kepeng on 15/9/9.
 */
define(function(require, exports, module) {

    module.exports = function(parentCode, treeObj) {

        require('bootstrap');
        require('cookie');
        require('dialog');
        var token = $.cookie('bizData');
        var message = require('../message.js');

        var Table = require('../datatable.js');

        var Page = {
            constant: {
                COLUMN:[{
                    data: 'id'
                }, {
                    data: 'postName',
                    title: '岗位名称'
                }, {
                    data: 'description',
                    title: '岗位描述'
                }],
                COLUMN_DEFS:[{
                    "bVisible": false,
                    "aTargets": [0]
                }]
            },
            initTable: function() {
                Table.initTable({
                    columns: this.constant.COLUMN,
                    tableContentId: 'menu_table_content',
                    tableId: (+new Date()) + '_table_body',
                    columnDefs: this.constant.COLUMN_DEFS,
                    sAjaxSource: '/system/post/queryPost?departmentCode=' + parentCode.id + '&token=' + token
                });
            }
        }

        Page.initTable();

        var tableObj = Table.dataTable;

        var addOrUpdateDepartment = function(formArry, succCallback, id, postCode) {
            var postJson = {
                postName: formArry[0] || '',
                description: formArry[1] || '',
                areaIds: formArry[2] || '',
                schoolIds: formArry[3] || ''
            };

            if (id) {
                postJson.id = id;
            } else {
                postJson.departmentCode = parentCode.id;
            }

            if (postCode) {
                postJson.postCode = postCode;
            }

            $.ajax({
                type: 'post',
                url: '/system/post/addOrEditPost?token=' + token,
                contentType: 'application/x-www-form-urlencoded;charset=UTF-8',
                data: {
                    postJson: JSON.stringify(postJson)
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

        var ajaxError = function(data) {
            message({
                title: '温馨提示',
                msg: data.msg,
                type: 'alert',
                clickHandle: function() {
                    if ('0100014' === data.rtnCode) {
                        window.location.href = 'login.html';
                    }
                }
            });
        };

        var close = function() {
            $("#add_position").dialog("destroy");
        }

        var cancle =  {
            text: "取消",
            'class': "btn btn-primary",
            click: close
        };

        function getSchoolByArea(areaId, checkedSchool) {
            $.get('/system/dataDictionary/findSchoolList?areaId=' + areaId + '&token=' + token, function(ret) {
                if ('0000000' === ret.rtnCode) {
                    var list = ret.bizData;
                    var checkbox = [];
                    var schoolStr = checkedSchool ? checkedSchool.join('%') : '';
                    checkbox.push('<p><input name="school-checked-all" type="checkbox">&nbsp;全部</p>');
                    for (var i = 0, len = list.length; i < len; i++) {
                        if (schoolStr.indexOf(list[i].id) > -1) {
                            checkbox.push('<p><input name="school" checked="checked" type="checkbox" value="' + list[i].id + '">&nbsp;' + list[i].schoolName + '</p>');
                        } else {
                            checkbox.push('<p><input name="school" type="checkbox" value="' + list[i].id + '">&nbsp;' + list[i].schoolName + '</p>');
                        }
                    }
                    $('#school_checkbox').html(checkbox.join(''));
                    $('#school_checkbox input[name="school-checked-all"]').off('click');
                    $('#school_checkbox input[name="school-checked-all"]').on('click', function(e) {
                        var all = $(this)[0].checked;
                        var school = document.getElementsByName('school');
                        $.each(school, function(i, v) {
                            v.checked = all;
                        })
                    });

                    $('#school_checkbox input[name="school"]').off('click');
                    $('#school_checkbox input[name="school"]').on('click', function(e) {
                        var checked = $(this)[0].checked;
                        if (!checked) {
                            $('#school_checkbox input[name="school-checked-all"]').attr('checked', false);
                        }
                    });
                }
            })
        }

        function getArea(selectAreaId, checkedSchool) {
            $.get('/system/dataDictionary/findAreaList?token=' + token, function(ret) {
                if ('0000000' === ret.rtnCode) {
                    var option = [];
                    var selectStr = selectAreaId ? selectAreaId.join('%') : '';
                    option.push('<option value="0">全部</option>');
                    for (var i = 0, len = ret.bizData.length; i < len; i++) {
                        if (selectStr.indexOf(ret.bizData[i].id) > -1) {
                            option.push('<option selected="selected" value="' + ret.bizData[i].id + '">' + ret.bizData[i].areaName + '</option>');
                        } else {
                            option.push('<option value="' + ret.bizData[i].id + '">' + ret.bizData[i].areaName + '</option>');
                        }
                    }
                    $('#area').html(option.join(''));
                    if ($('#area').val() !== '0') {
                        $('#manager_school').show();
                        getSchoolByArea(selectAreaId ? selectAreaId[0] : ret.bizData[0].id, checkedSchool);
                    }
                }
            });
        }

        var ButtonEvent = {
            add: function(elementId) {
                $('#' + elementId).off('click');
                $('#' + elementId).on('click', function(e) {
                    if (!tableObj) {
                        return;
                    }
                    $.get('../tmpl/position/position_form.html', function(tmpl) {
                        $("#add_position").dialog({
                            title: "新增岗位",
                            tmpl: tmpl,
                            onClose: close,
                            render: function() {
                                getArea();
                                $('#area').change(function() {
                                    var areaId = $(this).val();
                                    if (areaId !== '0') {
                                        $('#manager_school').show();
                                        getSchoolByArea(areaId);
                                    } else {
                                        $('#manager_school').hide();
                                        $('#school_checkbox').html('');
                                    }
                                });
                            },
                            buttons: [{
                                text: "新增",
                                'class': "btn btn-primary",
                                click: function() {
                                    var vali = require('./position_form.js');
                                    vali.validate(function(formArry) {
                                        addOrUpdateDepartment(formArry, function(data) {
                                            if ('0000000' === data.rtnCode) {
                                                tableObj.fnDraw();
                                                close();
                                            } else {
                                                close();
                                                ajaxError(data);
                                            }
                                        });
                                    });
                                }
                            },cancle]
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
                    $.get('/system/post/getPost?id=' + aData.id + '&token=' + token, function(data) {
                        if ('0000000' === data.rtnCode) {
                            $.get('../tmpl/position/position_form.html', function(tmpl) {
                                $("#add_position").dialog({
                                    title: "修改岗位",
                                    tmpl: tmpl,
                                    onClose: close,
                                    render: function() {
                                        $('#position_name').val(data.bizData.postName);
                                        $('#position_describe').val(data.bizData.description);
                                        getArea(data.bizData.areaIds, data.bizData.schoolIds);
                                        $('#area').change(function() {
                                            var areaId = $(this).val();
                                            if (areaId !== '0') {
                                                $('#manager_school').show();
                                                getSchoolByArea(areaId, data.bizData.schoolIds);;
                                            } else {
                                                $('#manager_school').hide();
                                                $('#school_checkbox').html('');
                                            }
                                        });
                                    },
                                    buttons: [{
                                        text: "修改",
                                        'class': "btn btn-primary",
                                        click: function() {
                                            var vali = require('./position_form.js');
                                            vali.validate(function(formArry) {
                                                addOrUpdateDepartment(formArry, function(data) {
                                                    if ('0000000' === data.rtnCode) {
                                                        tableObj.fnDraw();
                                                        close();
                                                    } else {
                                                        close();
                                                        ajaxError(data);
                                                    }
                                                }, aData.id, data.bizData.postCode);
                                            });
                                        }
                                    },cancle]
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
                        var str = '确认删除岗位' + aData.postName;
                        message({
                            title: '温馨提示',
                            msg: str,
                            type: 'alert',
                            clickHandle: function() {
                                $.get('/system/post/delPost?id=' + aData.id + '&token=' + token, function(data) {
                                    if ('0000000' === data.rtnCode) {
                                        tableObj.fnDraw();
                                    } else {
                                        ajaxError(data);
                                    }
                                });
                            }
                        });
                    }
                });
            },
            distributionRight: function(elementId) {
                $('#' + elementId).off('click');
                $('#' + elementId).on('click', function(e) {
                    var selectData = Table.selectedRecord();
                    if (selectData.length != 1) {
                        ajaxError({
                            msg:'每次只能选择一项分配权限'
                        });
                        return;
                    }
                    var postCode = selectData[0].postCode;
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
                                            ajaxError({
                                                msg:'请添加相关角色信息'
                                            });
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
                                            ajaxError({
                                                msg:'请添加相关角色信息'
                                            });
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
                                            ajaxError(data);
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
    };
});