/**
 * Created by kepeng on 15/9/14.
 */
define(function(require, exports, module) {
    module.exports = function() {

        //获取页面所需组件
        require('bootstrap');
        require('cookie');
        require('dialog');
        var message = require('../message');
        var Table = require('../datatable');

        var token = $.cookie('bizData');
        var col = [{
            data: 'id'
        }, {
            data: 'roleName',
            title: '角色名称'
        }, {
            data: 'description',
            title: '角色描述'
        }];
        var columnDefs = [{
            "bVisible": false,
            "aTargets": [0]
        }];
        var Tree = null,
            treeObj = null;
        var cancle = {
            text: "取消",
            'class': "btn btn-primary",
            click: function() {
                $("#add_role").dialog("destroy");
            }
        };


        $('#form_search').html('');
        Table.initTable({
            columns: col,
            tableContentId: 'table_content',
            tableId: 'role_table_body',
            columnDefs: columnDefs,
            sAjaxSource: '/system/role/queryRoles?token=' + token
        });
        var tableObj = Table.dataTable;

        /**
         * ajax错误处理
         * @param data
         */
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

        /**
         * 将选中的资源进行数据格式化
         * @param nodes
         * @returns {{menuCodeList: Array, resourceList: Array}}
         */
        function checkedDataFormat(nodes) {
            var menuList = [], resourceList = [];
            for (var i = 0, len = nodes.length; i < len; i++) {
                if (nodes[i].isResources) {
                    resourceList.push(nodes[i].id);
                } else {
                    menuList.push(nodes[i].id);
                }
            }
            return {
                menuCodeList:menuList,
                resourceList:resourceList
            }
        }

        /**
         * 判断数组中是否存在一个值
         * @param arry
         * @param value
         * @returns {boolean}
         */
        function isExistArray(arry, value) {
            for (var i = 0, len = arry.length; i < len; i++) {
                if (arry[i] == value) {
                    return true;
                }
            }
            return false;
        }

        /**
         * 初始化角色拥有的菜单和资源
         * @param nodes
         * @param treeBeans
         */

        function addCheckedStatus(nodes, menuCodeList, resourceList) {
            for (var i = 0, len = nodes.length; i < len; i++) {
                var flag = (nodes[i].children && nodes[i].children.length > 0);
                if (!nodes[i].isResources) {
                    if (isExistArray(menuCodeList, nodes[i].id)) {
                        nodes[i].checked = true;
                        nodes[i].open = true;
                    }
                } else {
                    if (isExistArray(resourceList, nodes[i].id)) {
                        nodes[i].checked = true;
                    }
                }

                if (flag) {
                    addCheckedStatus(nodes[i].children, menuCodeList, resourceList);
                }
            }
        }

        function GetQueryString(name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if (r != null) return unescape(r[2]);
            return null;
        }

        /**
         * 渲染资源树
         * @param treeBeans
         */
        function renderResourceTree(menuCodeList, resourceList) {
            var systemCode = GetQueryString('systemCode');
            $.get('/system/menu/getMenuTree?token=' + token + '&hasResource=true&systemCode=' + systemCode, function(data) {
                if ('0000000' === data.rtnCode) {
                    var nodes = data.bizData.treeBeanList;
                    if (menuCodeList && resourceList) {
                        addCheckedStatus(nodes, menuCodeList, resourceList);
                    }
                    Tree = require('../tree.js');
                    Tree.init({
                        id: 'tree_resource',
                        check: true,
                        nodes: nodes
                    });
                    treeObj = Tree.treeObj;
                } else {
                    ajaxEerror(data);
                }
            });
        }

        function addOrUpdateRole(id, roleCode) {
            var roleName = $('#role_name').val().trim();
            if (!roleName) {
                var errorLable = $('#role_name').parent().parent().find('p');
                errorLable.html('角色名称不能为空！');
                errorLable.show();
                setTimeout(function() {
                    errorLable.hide();
                }, 2000)
                return;
            }

            var roleDescription = $('#role_description').val().trim();

            var nodes = Tree.getCheckedNodes();
            var list = checkedDataFormat(nodes);
            if (nodes.length <= 0) {
                $('#model_error_msg').html('请选择菜单和资源分配');
                return;
            }

            var rolesPojoJson = {
                roleName: roleName,
                description: roleDescription,
                menuCodeList: list.menuCodeList,
                resourceList: list.resourceList
            };
            if (id) {
                rolesPojoJson.rolesId = id;
            }

            if (roleCode) {
                rolesPojoJson.roleCode = roleCode;
            }
            var ajaxFlag = false;
            if (!ajaxFlag) {
                ajaxFlag = true;
                $.ajax({
                    type: 'post',
                    url: '/system/role/addOrEditRoles?token=' + token,
                    contentType: 'application/x-www-form-urlencoded;charset=utf-8',
                    data: {
                        rolesPojoJson: JSON.stringify(rolesPojoJson)
                    },
                    dataType: 'json',
                    success: function(data) {
                        ajaxFlag = false;
                        if ('0000000' === data.rtnCode) {
                            tableObj.fnDraw();
                            $("#add_role").dialog("destroy");
                        } else {
                            $("#add_role").dialog("destroy");
                            ajaxEerror(data);
                        }
                    },
                    error: function() {
                        ajaxFlag = false;
                    }
                });
            }
        }

        var ButtonEvent = {
            add: function(elementId) {
                $('#' + elementId).off('click');
                $('#' + elementId).on('click', function(e) {
                    if (!tableObj) {
                        return;
                    }
                    $.get('../tmpl/role/role.tmpl', function(tmpl) {
                        $("#add_role").dialog({
                            title: "新增角色",
                            tmpl: tmpl,
                            onClose: function() {
                                $("#add_role").dialog("destroy");
                            },
                            render: function() {
                                renderResourceTree();
                            },
                            buttons: [{
                                text: "新增",
                                'class': "btn btn-primary",
                                click: function() {
                                    addOrUpdateRole();
                                }
                            }, cancle]
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

                    $.get('/system/role/getRoles?id=' + aData.id + '&token=' + token, function(data) {
                        if ('0000000' === data.rtnCode) {
                            $.get('../tmpl/role/role.tmpl', function(tmpl) {
                                $("#add_role").dialog({
                                    title: "修改角色",
                                    tmpl: tmpl,
                                    onClose: function() {
                                        $("#add_role").dialog("destroy");
                                    },
                                    render: function() {
                                        renderResourceTree(data.bizData.menuCodeList, data.bizData.resourceList);
                                        $('#role_name').val(data.bizData.roleName);
                                        $('#role_description').val(data.bizData.description);
                                    },
                                    buttons: [{
                                        text: "修改",
                                        'class': "btn btn-primary",
                                        click: function() {
                                            addOrUpdateRole(aData.id, aData.roleCode);
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
                        var str = '确认删除角色【' + aData.roleName + '】，将会删除此角色下所有的菜单权限和资源权限且不可恢复，请谨慎操作！';
                        message({
                            title: '温馨提示',
                            msg: str,
                            type: 'alert',
                            clickHandle: function() {
                                $.get('/system/role/delRoles?rolesId=' + aData.id + '&token=' + token, function(data) {
                                    if ('0000000' === data.rtnCode) {
                                        tableObj.fnDraw();
                                    } else {
                                        message({
                                            title: '温馨提示',
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
    }
});