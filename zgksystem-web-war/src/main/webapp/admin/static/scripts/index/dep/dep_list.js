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
            title: '代理商名称'
        }, {
            data: 'departmentPhone',
            title: '代理商电话'
        }, {
            data: 'departmentFax',
            title: '代理商传真'
        }, {
            data: 'departmentPrincipal',
            title: '代理商负责人'
        }

        ];

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
            var cookieJson = JSON.parse($.cookie('userInfo'));
            var roleType = cookieJson.roleType;
            switch (roleType){
                case 1:
                    departmentJson.areaCode = formArry[4];
                    break;
                case 2:
                    departmentJson.areaCode = formArry[5];
                    break;
                case 3:
                    departmentJson.areaCode = formArry[6];
                    break;
                default:
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
                    console.log(data)
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
                            title: "新增代理商",
                            tmpl: tmpl,
                            onClose: function() {
                                $("#add_dep").dialog("destroy");
                            },
                            render: function() {
                                var cookieJson = JSON.parse($.cookie('userInfo'));
                                var roleType = cookieJson.roleType;

                                var  curProvincesCookieId = cookieJson.areaCode;

                                console.log(roleType)

                                switch (roleType){
                                    case 1:
                                        $('#dep_provinces_from').show();
                                        $('#dep_city_from,#dep_county_from').hide();
                                        // 省份
                                        $.getJSON('/system/dataDictionary/findProvinceList?token=' + token,function(res){
                                            console.log(res)
                                            for(var i=0;i<res.bizData.length;i++){
                                                $('#dep_provinces').append('<option value="'+ res.bizData[i].id +'">'+ res.bizData[i].provinceName +'</option>')
                                            }
                                        });
                                        break;
                                    case 2:
                                        curProvincesCookieId = curProvincesCookieId+"0000";
                                        $('#dep_city_from').show();
                                        $('#dep_provinces_from,#dep_county_from').hide();
                                        $.getJSON('/system/dataDictionary/findCityList?token=' + token + '&provinceId='+curProvincesCookieId,function(res){
                                            console.log(res)
                                            for(var i=0;i<res.bizData.length;i++){
                                                $('#dep_city').append('<option value="'+ res.bizData[i].id +'">'+ res.bizData[i].cityName +'</option>')
                                            }
                                        });
                                        break;
                                    case 3:
                                        curProvincesCookieId = curProvincesCookieId+"00";
                                        $('#dep_county_from').show();
                                        $('#dep_provinces_from,#dep_city_from').hide();
                                        // 市
                                        $.getJSON('/system/dataDictionary/findCountyList?token=' + token + '&cityId='+curProvincesCookieId,function(res){
                                            console.log(res)
                                            for(var i=0;i<res.bizData.length;i++){
                                                $('#dep_county').append('<option value="'+ res.bizData[i].id +'">'+ res.bizData[i].countyName +'</option>')
                                            }
                                        });
                                        break;
                                    default:
                                }
                            },
                            buttons: [{
                                text: "新增",
                                'class': "btn btn-primary",
                                click: function() {
                                    var vali = require('./dep_form.js');
                                    vali.validate(function(formArry) {
                                        addOrUpdateDepartment(formArry, function(ret) {
                                            tableObj.fnDraw();
                                            //console.log(ret)
                                            if ('0000000' === ret.rtnCode) {
                                                var cookieJson = JSON.parse($.cookie('userInfo'));
                                                if("-1"==cookieJson.departmentCode){

                                                    var node = {
                                                        id: ret.bizData.departmentCode,
                                                        name: formArry[0]
                                                    };
                                                    treeCallback({
                                                        type: 'add',
                                                        obj: node
                                                    });
                                                }
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
                        console.log(data)
                        if ('0000000' === data.rtnCode) {
                            $.get('../tmpl/dep/dep_form.html', function(tmpl) {
                                require('dialog');
                                $("#add_dep").dialog({
                                    title: "修改代理商",
                                    tmpl: tmpl,
                                    onClose: function() {
                                        $("#add_dep").dialog("destroy");
                                    },
                                    render: function() {
                                        $('#dep_name').val(data.bizData.departmentName);
                                        $('#dep_telephone').val(data.bizData.departmentPhone);
                                        $('#dep_fax').val(data.bizData.departmentFax);
                                        $('#dep_leading').val(data.bizData.departmentPrincipal);

                                        // 修改
                                        var roleType = data.bizData.roleType;

                                        var updateProvincesId = data.bizData.areaCode;



                                        switch (roleType){
                                            case "2":
                                                $('#dep_provinces_from').show();
                                                $('#dep_city_from,#dep_county_from').hide();
                                                // 省份
                                                $.getJSON('/system/dataDictionary/findProvinceList?token=' + token,function(res){
                                                    console.log(res)
                                                    for(var i=0;i<res.bizData.length;i++){
                                                        $('#dep_provinces').append('<option value="'+ res.bizData[i].id +'">'+ res.bizData[i].provinceName +'</option>')
                                                    }
                                                    $('#dep_provinces').find('option[value="'+ updateProvincesId +'0000"]').attr('selected',true);
                                                });
                                                break;
                                            case "3":
                                                var curProvincesId = updateProvincesId.substring(0,2)+"0000";
                                                $('#dep_city_from').show();
                                                $('#dep_provinces_from,#dep_county_from').hide();
                                                $.getJSON('/system/dataDictionary/findCityList?token=' + token + '&provinceId='+curProvincesId,function(res){
                                                    console.log(res)
                                                    for(var i=0;i<res.bizData.length;i++){
                                                        $('#dep_city').append('<option value="'+ res.bizData[i].id +'">'+ res.bizData[i].cityName +'</option>')
                                                    }
                                                    $('#dep_city').find('option[value="'+ updateProvincesId +'00"]').attr('selected',true);
                                                });
                                                break;
                                            case "4":
                                                var curProvincesCookieId = curProvincesCookieId+"00";
                                                $('#dep_county_from').show();
                                                $('#dep_provinces_from,#dep_city_from').hide();
                                                // 市
                                                $.getJSON('/system/dataDictionary/findCountyList?token=' + token + '&cityId='+curProvincesCookieId,function(res){
                                                    console.log(res)
                                                    for(var i=0;i<res.bizData.length;i++){
                                                        $('#dep_county').append('<option value="'+ res.bizData[i].id +'">'+ res.bizData[i].countyName +'</option>')
                                                    }
                                                });
                                                break;
                                            default:
                                        }
                                    },
                                    buttons: [{
                                        text: "修改",
                                        'class': "btn btn-primary",
                                        click: function() {
                                            var vali = require('./dep_form.js');
                                            vali.validate(function(formArry) {
                                                addOrUpdateDepartment(formArry, function(ret) {
                                                    tableObj.fnDraw();
                                                    if ('0000000' === ret.rtnCode) {
                                                        var cookieJson = JSON.parse($.cookie('userInfo'));
                                                        if("-1"==cookieJson.departmentCode){
                                                            var node = {
                                                                id: data.bizData.departmentCode,
                                                                name: formArry[0]
                                                            };
                                                            treeCallback({
                                                                type: 'update',
                                                                obj: node
                                                            });
                                                        }
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
                        var str = '确认删除代理商' + aData.departmentName;
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