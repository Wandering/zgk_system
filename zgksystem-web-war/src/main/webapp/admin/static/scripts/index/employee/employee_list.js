/**
 * Created by kepeng on 15/9/9.
 */
define(function (require, exports, module) {

    module.exports = function (parentCode, treeObj) {
        require('bootstrap');
        require('cookie');
        var token = $.cookie('bizData');
        var message = require('../message.js');

        var Table = require('../datatable.js');
        var col = [{
            data: 'id'
        }, {
            data: 'userName',
            title: '用户名称'
        }, {
            data: 'phone',
            title: '联系电话'
        }, {
            data: 'email',
            title: '邮箱'
        }, {
            data: 'description',
            title: '人员描述'
        }];

        var columnDefs = [{
            "bVisible": false,
            "aTargets": [0]
        }, {
            "aTargets": [3],
            "render": function (data, type, row) {
                return '<a style="color:#fff" href="Mailto:' + data + '">' + data + '</a>';
            }
        }];

        Table.initTable({
            columns: col,
            tableContentId: 'menu_table_content',
            tableId: new Date().getTime() + '_table_body',
            columnDefs: columnDefs,
            sAjaxSource: '/system/userInfo/queryUserInfo?departmentCode=' + parentCode.id + '&token=' + token
        });

        var tableObj = Table.dataTable;

        var addOrUpdateDepartment = function (formArry, succCallback, id) {
            var postJson = {
                postCode: formArry[0] || '',
                userName: formArry[1] || '',
                loginNumber: formArry[2] || '',
                password: formArry[3] || '',
                phone: formArry[4] || '',
                email: formArry[5] || '',
                description: formArry[6] || ''
            };

            if (id) {
                postJson.id = id;
                postJson.departmentCode = parentCode.id;
            } else {

                postJson.departmentCode = parentCode.id;
                require('md5');
                postJson.password = $.md5(postJson.password);
            }
            console.log(JSON.stringify(postJson))
            $.ajax({
                type: 'post',
                url: '/system/userInfo/addOrEditUserInfo?token=' + token,
                contentType: 'application/x-www-form-urlencoded;charset=UTF-8',
                data: {
                    userPojoJson: JSON.stringify(postJson)
                },
                dataType: 'json',
                success: function (data) {
                    succCallback(data);
                },
                beforeSend: function (xhr) {
                    $('.single-buttons').attr('disabled', 'disabled');
                },
                complete: function () {
                    $('.single-buttons').removeAttr('disabled');
                },
                error: function (data) {

                }
            });
        };

        function tip(ele, str) {
            var errorLable = ele.find('p');
            errorLable.html(str);
            errorLable.fadeIn(500);
            setTimeout(function () {
                errorLable.fadeOut(500);
            }, 2000)
        }

        function checkLoginNameIsExist(userName) {
            $.ajax({
                type: 'post',
                url: '/system/userInfo/checkLoginNumberIsExist?token=' + token,
                contentType: 'application/x-www-form-urlencoded;charset=UTF-8',
                data: {
                    loginNumber: userName
                },
                dataType: 'json',
                success: function (data) {
                    if ('0000000' === data.rtnCode) {
                        if ('0' == data.bizData) {
                            tip($('#login_name').parent().parent(), '账户已存在');
                            $('#login_name').focus();
                            $('#login_name').attr('data-flag', 'isExist');
                            return;
                        }
                    }
                    $('#login_name').attr('data-flag', '');
                },
                beforeSend: function (xhr) {
                },
                error: function (data) {
                    $('#login_name').attr('data-flag', '');
                }
            });
        }

        var ButtonEvent = {
            add: function (elementId) {
                $('#' + elementId).off('click');
                $('#' + elementId).on('click', function (e) {
                    if (!tableObj) {
                        return;
                    }
                    $.get('/system/post/queryComboxPost?departmentCode=' + parentCode.id + '&token=' + token, function (data) {
                        if ('0000000' === data.rtnCode) {
                            var comboxData = data.bizData;

                            if (comboxData) {
                                $.get('../tmpl/employee/employee_form.html', function (tmpl) {
                                    require('dialog');
                                    $("#add_employee").dialog({
                                        title: "新增账号",
                                        tmpl: tmpl,
                                        onClose: function () {
                                            $("#add_employee").dialog("destroy");
                                        },
                                        render: function () {
                                            var str = [];
                                            str.push('<option>请选择代理商...</option>');
                                            for (var key in comboxData) {
                                                str.push('<option value="' + key + '">' + comboxData[key] + '</option>');
                                            };
                                            $('#position_name').html(str.join(''));
                                            //验证用户账号是否存在
                                            $('#login_name').off('blur');
                                            $('#login_name').on('blur', function (e) {
                                                checkLoginNameIsExist($(this).val());
                                            });
                                        },
                                        buttons: [{
                                            text: "新增",
                                            'class': "btn btn-primary single-buttons",
                                            click: function () {
                                                var vali = require('./employee_form.js');
                                                vali.validate(function (formArry) {
                                                    addOrUpdateDepartment(formArry, function (data) {
                                                        if ('0000000' === data.rtnCode) {
                                                            tableObj.fnDraw();
                                                            $("#add_employee").dialog("destroy");
                                                        } else {
                                                            $("#add_employee").dialog("destroy");
                                                            message({
                                                                title: '温馨提示',
                                                                msg: data.msg,
                                                                type: 'alert',
                                                                clickHandle: function () {
                                                                    //window.location.href = 'login.html';
                                                                }
                                                            });
                                                        }
                                                    });
                                                });
                                            }
                                        }, {
                                            text: "取消",
                                            'class': "btn btn-primary",
                                            click: function () {
                                                $("#add_employee").dialog("destroy");
                                            }
                                        }]
                                    });
                                })
                            } else {
                                var tipOne = '代理商【' + parentCode.name + '】下只能存在一个代理商人员';
                                message({
                                    title: '温馨提示',
                                    msg: tipOne,
                                    type: 'alert'
                                });
                            }
                        } else {
                            var tipOne = '代理商【' + parentCode.name + '】下只能存在一个代理商人员';
                            message({
                                title: '温馨提示',
                                msg: tipOne,
                                type: 'alert'
                            });
                        }
                    });
                });
            },
            update: function (elementId) {
                $('#' + elementId).off('click');
                $('#' + elementId).on('click', function (e) {
                    if (!tableObj) {
                        return;
                    }
                    var anSelected = Table.fnGetSelected(tableObj);
                    if (anSelected.length === 0) {
                        return;
                    }
                    var aData = tableObj.fnGetData(anSelected[0]);
                    $.get('/system/userInfo/getUserInfo?id=' + aData.id + '&token=' + token, function (data) {
                        if ('0000000' === data.rtnCode) {
                            $.get('../tmpl/employee/employee_form.html', function (tmpl) {
                                require('dialog');
                                $("#add_employee").dialog({
                                    title: "修改账号",
                                    tmpl: tmpl,
                                    onClose: function () {
                                        $("#add_employee").dialog("destroy");
                                    },
                                    render: function () {
                                        $('#position_name').html('<option value="'+ data.bizData.postCode +'">'+ data.bizData.departmentName +'</option>');

                                        $('#employee_name').val(data.bizData.userName);
                                        $('#login_name').val(data.bizData.loginNumber);
                                        $('#login_pwd').val(data.bizData.password);
                                        $('#login_name_group').hide();
                                        $('#login_pwd_group').hide();
                                        $('#employee_telephone').val(data.bizData.phone);
                                        $('#employee_email').val(data.bizData.email);
                                        $('#employee_describe').val(data.bizData.description);
                                    },
                                    buttons: [{
                                        text: "修改",
                                        'class': "btn btn-primary",
                                        click: function () {
                                            var vali = require('./employee_form.js');
                                            vali.validate(function (formArry) {
                                                addOrUpdateDepartment(formArry, function (data) {
                                                    if ('0000000' === data.rtnCode) {
                                                        tableObj.fnDraw();
                                                        $("#add_employee").dialog("destroy");
                                                    } else {
                                                        $("#add_employee").dialog("destroy");
                                                        message({
                                                            title: '温馨提示',
                                                            msg: data.msg,
                                                            type: 'alert',
                                                            clickHandle: function () {
                                                                window.location.href = 'login.html';
                                                            }
                                                        });
                                                    }
                                                }, aData.id);
                                            }, 'update');
                                        }
                                    }, {
                                        text: "取消",
                                        'class': "btn btn-primary",
                                        click: function () {
                                            $("#add_employee").dialog("destroy");
                                        }
                                    }]
                                });
                            })
                        }
                    });
                });
            },
            delete: function (elementId) {
                $('#' + elementId).off('click');
                $('#' + elementId).on('click', function (e) {
                    if (!tableObj) {
                        return;
                    }
                    var anSelected = Table.fnGetSelected(tableObj);
                    if (anSelected.length !== 0) {
                        var aData = tableObj.fnGetData(anSelected[0]);
                        //console.log(aData);
                        var str = '确认删除账号' + aData.userName;
                        message({
                            title: '温馨提示',
                            msg: str,
                            type: 'alert',
                            clickHandle: function () {
                                $.get('/system/userInfo/delUserInfo?id=' + aData.id + '&token=' + token, function (data) {
                                    if ('0000000' === data.rtnCode) {
                                        tableObj.fnDraw();
                                    } else {
                                        message({
                                            title: '错误提示',
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
        require.async('../renderResource', function (resource) {
            resource(ButtonEvent, token);
        });
    };
});