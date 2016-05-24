define(function (require, exports, module) {
    module.exports = function (parentCode, treeCallback) {
        require('bootstrap');
        require('cookie');
        var token = $.cookie('bizData');
        var message = require('../message.js');
        var Table = require('../datatable.js');
        var cookieJson = JSON.parse($.cookie('userInfo'));
        var roleType = cookieJson.roleType;
        if (roleType == '1') {
            var col = [
                {
                    data: 'id',
                    title: '序列'
                }, {
                    data: 'departmentName',
                    title: '名称'
                }, {
                    data: 'roleType',
                    title: '类型'
                }, {
                    data: 'departmentPrincipal',
                    title: '负责人'
                }, {
                    data: 'departmentPhone',
                    title: '电话'
                }, {
                    data: 'goodsAddress',
                    title: '联系地址'
                }, {
                    data: 'products',
                    title: '种类',
                    "render": function (data, type, row) {
                        var listHtml = '';
                        for (var i = 0; i < row.products.length; i++) {
                            listHtml += '<div>' + row.products[i].productName + '</div>';
                        }
                        return listHtml;
                    }
                }, {
                    data: 'webPrice',
                    title: '进货价',
                    "render": function (data, type, row) {
                        var listHtml = '';
                        for (var i = 0; i < row.products.length; i++) {
                            listHtml += '<div>' + row.products[i].pickupPrice + '</div>';
                        }
                        return listHtml;
                    }
                }, {
                    data: 'salePrice',
                    title: '售价',
                    "render": function (data, type, row) {
                        var listHtml = '';
                        for (var i = 0; i < row.products.length; i++) {
                            listHtml += '<div>' + row.products[i].salePrice + '</div>';
                        }
                        return listHtml;
                    }
                }
            ];
            var columnDefs = [
                {
                    "bVisible": false,
                    "sClass": "center",
                    "aTargets": [0],
                    "render": function (data, type, row) {
                        console.log(data);
                        console.log(row);
                    }
                },
                {
                    "sClass": "line50 center",
                    "aTargets": [1]
                },
                {
                    "aTargets": [2],
                    "sClass": "line50 center",
                    "render": function (data, type, row) {
                        var dataTxt = ['管理员', '省级代理', '市级代理', '区县级代理'];
                        return dataTxt[data - 1];
                    }
                },
                {
                    "sClass": "line50 center",
                    "aTargets": [3]
                },
                {
                    "sClass": "line50 center",
                    "aTargets": [4]
                },
                {
                    "sClass": "line50 center",
                    "aTargets": [5]
                },
                {
                    "sClass": "center",
                    "aTargets": [6]
                },
                {
                    "sClass": "center",
                    "aTargets": [7]
                },
                {
                    "sClass": "center",
                    "aTargets": [8]
                }];
            Table.initTable({
                columns: col,
                tableContentId: 'menu_table_content',
                tableId: new Date().getTime() + '_table_body',
                columnDefs: columnDefs,
                sAjaxSource: '/system/department/queryDepartment?parentCode=' + parentCode + '&token=' + token
            });
            var tableObj = Table.dataTable;
        } else {
            var col = [
                {
                    data: 'id',
                    title: '序列'
                }, {
                    data: 'departmentName',
                    title: '名称'
                }, {
                    data: 'roleType',
                    title: '类型'
                }, {
                    data: 'departmentPrincipal',
                    title: '负责人'
                }, {
                    data: 'departmentPhone',
                    title: '电话'
                }, {
                    data: 'goodsAddress',
                    title: '联系地址'
                }, {
                    data: 'products',
                    title: '种类',
                    "render": function (data, type, row) {
                        var listHtml = '';
                        for (var i = 0; i < row.products.length; i++) {
                            listHtml += '<div>' + row.products[i].productName + '</div>';
                        }
                        return listHtml;
                    }
                }, {
                    data: 'webPrice',
                    title: '进货价',
                    "render": function (data, type, row) {
                        var listHtml = '';
                        for (var i = 0; i < row.products.length; i++) {
                            listHtml += '<div>' + row.products[i].pickupPrice + '</div>';
                        }
                        return listHtml;
                    }
                }
            ];
            var columnDefs = [
                {
                    "bVisible": false,
                    "sClass": "center",
                    "aTargets": [0],
                    "render": function (data, type, row) {
                        console.log(data);
                        console.log(row);
                    }
                },
                {
                    "sClass": "line50 center",
                    "aTargets": [1]
                },
                {
                    "sClass": "line50 center",
                    "aTargets": [2],
                    "render": function (data, type, row) {
                        var dataTxt = ['管理员', '省级代理', '市级代理', '区县级代理'];
                        return dataTxt[data - 1];
                    }
                },
                {
                    "sClass": "line50 center",
                    "aTargets": [3]
                },
                {
                    "sClass": "line50 center",
                    "aTargets": [4]
                },
                {
                    "sClass": "line50 center",
                    "aTargets": [5]
                },
                {
                    "sClass": "center",
                    "aTargets": [6]
                },
                {
                    "sClass": "center",
                    "aTargets": [7]
                }];
            Table.initTable({
                columns: col,
                tableContentId: 'menu_table_content',
                tableId: new Date().getTime() + '_table_body',
                columnDefs: columnDefs,
                sAjaxSource: '/system/department/queryDepartment?parentCode=' + parentCode + '&token=' + token
            });
            var tableObj = Table.dataTable;
        }

        var errorTip = function (msg) {
            $('#model_error_msg').html(msg);
            $('#model_error_msg').show();
            setTimeout(function () {
                if ($('#model_error_msg')) {
                    $('#model_error_msg').hide();
                }
            }, 2000);
        };

        var addDepartment = function (formArry, succCallback, id) {
            var departmentJson = {
                departmentName: formArry[0] || '', // 部门名称
                departmentPhone: formArry[1] || '', // 联系电话
                departmentFax: formArry[2] || '',  // 传真
                departmentPrincipal: formArry[3] || '', // 部门负责人
            };

            departmentJson.parentCode = parentCode;  // 添加节点
            departmentJson.goodsAddress = formArry[5] || '';


            switch (roleType) {
                case 1:
                    departmentJson.areaCode = formArry[4] || '';
                    var roleType1Arr = [
                        {
                            "salePrice": formArry[7] || '',
                            "pickupPrice": formArry[6] || '',
                            "productId": 1,
                            "productName": "金榜登科"
                        },
                        {
                            "salePrice": formArry[9] || '',
                            "pickupPrice": formArry[8] || '',
                            "productId": 3,
                            "productName": "金榜提名"
                        },
                        {
                            "salePrice": formArry[11] || '',
                            "pickupPrice": formArry[10] || '',
                            "productId": 2,
                            "productName": "状元及第"
                        }
                    ];
                    departmentJson.products = roleType1Arr;
                    break;
                case 2:
                    departmentJson.areaCode = formArry[4];
                    var roleType2Arr = [
                        {
                            "salePrice": "",
                            "pickupPrice": formArry[6],
                            "productId": 1,
                            "productName": "金榜登科"
                        },
                        {
                            "salePrice": "",
                            "pickupPrice": formArry[7],
                            "productId": 3,
                            "productName": "金榜提名"
                        },
                        {
                            "salePrice": "",
                            "pickupPrice": formArry[8],
                            "productId": 2,
                            "productName": "状元及第"
                        }
                    ];
                    departmentJson.products = roleType2Arr;
                    break;
                case 3:
                    departmentJson.areaCode = formArry[4];
                    var roleType3Arr = [
                        {
                            "salePrice": "",
                            "pickupPrice": formArry[6],
                            "productId": 1,
                            "productName": "金榜登科"
                        },
                        {
                            "salePrice": "",
                            "pickupPrice": formArry[7],
                            "productId": 3,
                            "productName": "金榜提名"
                        },
                        {
                            "salePrice": "",
                            "pickupPrice": formArry[8],
                            "productId": 2,
                            "productName": "状元及第"
                        }
                    ];
                    departmentJson.products = roleType3Arr;
                    break;
                default:
            }
            console.log(departmentJson)

            $.ajax({
                type: 'post',
                url: '/system/department/addDepartment?token=' + token,
                contentType: 'application/x-www-form-urlencoded;charset=UTF-8',
                data: {
                    departmentJson: JSON.stringify(departmentJson)
                },
                dataType: 'json',
                success: function (data) {
                    console.log(data)
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

        var updateDepartment = function (formArry, succCallback, id) {
            var departmentJson = {
                departmentName: formArry[0] || '', // 部门名称
                departmentPhone: formArry[1] || '', // 联系电话
                departmentFax: formArry[2] || '',  // 传真
                departmentPrincipal: formArry[3] || '', // 部门负责人
            };
            departmentJson.id = id;
            departmentJson.goodsAddress = formArry[5] || '';


            switch (roleType) {
                case 1:
                    departmentJson.areaCode = formArry[4] || '';
                    var roleType1Arr = [
                        {
                            "salePrice": formArry[7] || '',
                            "pickupPrice": formArry[6] || '',
                            "productId": 1,
                            "productName": "金榜登科"
                        },
                        {
                            "salePrice": formArry[9] || '',
                            "pickupPrice": formArry[8] || '',
                            "productId": 3,
                            "productName": "金榜提名"
                        },
                        {
                            "salePrice": formArry[11] || '',
                            "pickupPrice": formArry[10] || '',
                            "productId": 2,
                            "productName": "状元及第"
                        }
                    ];
                    departmentJson.products = roleType1Arr;
                    break;
                case 2:
                    departmentJson.areaCode = formArry[4];
                    var roleType2Arr = [
                        {
                            "salePrice": "",
                            "pickupPrice": formArry[6],
                            "productId": 1,
                            "productName": "金榜登科"
                        },
                        {
                            "salePrice": "",
                            "pickupPrice": formArry[7],
                            "productId": 3,
                            "productName": "金榜提名"
                        },
                        {
                            "salePrice": "",
                            "pickupPrice": formArry[8],
                            "productId": 2,
                            "productName": "状元及第"
                        }
                    ];
                    departmentJson.products = roleType2Arr;
                    break;
                case 3:
                    departmentJson.areaCode = formArry[4];
                    var roleType3Arr = [
                        {
                            "salePrice": "",
                            "pickupPrice": formArry[6],
                            "productId": 1,
                            "productName": "金榜登科"
                        },
                        {
                            "salePrice": "",
                            "pickupPrice": formArry[7],
                            "productId": 3,
                            "productName": "金榜提名"
                        },
                        {
                            "salePrice": "",
                            "pickupPrice": formArry[8],
                            "productId": 2,
                            "productName": "状元及第"
                        }
                    ];
                    departmentJson.products = roleType3Arr;
                    break;
                default:
            }
            console.log(departmentJson)
            $.ajax({
                type: 'post',
                url: '/system/department/updateDepartment?token=' + token,
                contentType: 'application/x-www-form-urlencoded;charset=UTF-8',
                data: {
                    departmentJson: JSON.stringify(departmentJson)
                },
                dataType: 'json',
                success: function (data) {
                    console.log(data)
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
        var ButtonEvent = {
            add: function (elementId) {
                $('#' + elementId).off('click');
                $('#' + elementId).on('click', function (e) {
                    if (!tableObj) {
                        return;
                    }
                    $.get('../tmpl/dep/dep_form.html', function (tmpl) {
                        require('dialog');
                        $("#add_dep").dialog({
                            title: "新增代理商",
                            tmpl: tmpl,
                            onClose: function () {
                                $("#add_dep").dialog("destroy");
                            },

                            render: function () {
                                var cookieJson = JSON.parse($.cookie('userInfo'));
                                var roleType = parseInt(cookieJson.roleType);

                                var curProvincesCookieId = cookieJson.areaCode;


                                console.log(curProvincesCookieId)

                                console.log(roleType)
                                switch (roleType) {
                                    case 1:
                                        $('#dep_provinces_from').show();
                                        $('#dep_city_from,#dep_county_from').hide();
                                        // 省份
                                        $.getJSON('/system/dataDictionary/findProvinceList?token=' + token, function (res) {
                                            console.log(res)
                                            for (var i = 0; i < res.bizData.length; i++) {
                                                $('#dep_provinces').append('<option value="' + res.bizData[i].id + '">' + res.bizData[i].provinceName + '</option>')
                                            }
                                        });
                                        break;
                                    case 2:
                                        curProvincesCookieId = curProvincesCookieId + "0000";
                                        $('#dep_city_from').show();
                                        $('#dep_provinces_from,#dep_county_from,#products1-control,#products2-control,#products3-control').hide();
                                        $.getJSON('/system/dataDictionary/findCityList?token=' + token + '&provinceId=' + curProvincesCookieId, function (res) {
                                            console.log(res)
                                            for (var i = 0; i < res.bizData.length; i++) {
                                                $('#dep_city').append('<option value="' + res.bizData[i].id + '">' + res.bizData[i].cityName + '</option>')
                                            }
                                        });
                                        break;
                                    case 3:
                                        curProvincesCookieId = curProvincesCookieId + "00";
                                        $('#dep_county_from').show();
                                        $('#dep_provinces_from,#dep_city_from,#products1-control,#products2-control,#products3-control').hide();
                                        // 市
                                        $.getJSON('/system/dataDictionary/findCountyList?token=' + token + '&cityId=' + curProvincesCookieId, function (res) {
                                            console.log(res)
                                            for (var i = 0; i < res.bizData.length; i++) {
                                                $('#dep_county').append('<option value="' + res.bizData[i].id + '">' + res.bizData[i].countyName + '</option>')
                                            }
                                        });
                                        break;
                                    default:
                                }
                            },

                            buttons: [{
                                text: "新增",
                                'class': "btn btn-primary single-buttons",
                                click: function () {
                                    var vali = require('./dep_form.js');
                                    vali.validate(function (formArry) {
                                        addDepartment(formArry, function (ret) {
                                            tableObj.fnDraw();
                                            //console.log(ret)
                                            if ('0000000' === ret.rtnCode) {
                                                var cookieJson = JSON.parse($.cookie('userInfo'));
                                                if ("-1" == cookieJson.departmentCode) {

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
                                                    clickHandle: function () {
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
                                click: function () {
                                    $("#add_dep").dialog("destroy");
                                }
                            }]
                        });
                    })
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
                    //console.log(aData)
                    $.get('/system/department/getDepartment?id=' + aData.id + '&token=' + token, function (data) {
                        console.log(data)
                        if ('0000000' === data.rtnCode) {
                            $.get('../tmpl/dep/dep_form.html', function (tmpl) {
                                require('dialog');
                                $("#add_dep").dialog({
                                    title: "修改代理商",
                                    tmpl: tmpl,
                                    onClose: function () {
                                        $("#add_dep").dialog("destroy");
                                    },
                                    render: function () {
                                        $('#dep_name').attr('departmentId',data.bizData.id);
                                        $('#dep_name').val(data.bizData.departmentName);
                                        $('#dep_telephone').val(data.bizData.departmentPhone);
                                        $('#dep_fax').val(data.bizData.departmentFax);
                                        $('#dep_leading').val(data.bizData.departmentPrincipal);
                                        $('#sale_Price').val(data.bizData.salePrice);
                                        $('#goods_Address').val(data.bizData.goodsAddress);

                                        // 修改
                                        var roleType = parseInt(data.bizData.roleType);

                                        var updateProvincesId = data.bizData.areaCode;

                                        console.log(updateProvincesId);



                                        // 1:金榜登科  2:状元及第  3:金榜题名

                                        for(var i=0;i<data.bizData.products.length;i++){
                                            if(data.bizData.products[i].productId==1){
                                                $('#products1-purchases').val(data.bizData.products[i].pickupPrice);
                                                $('#products1-price').val(data.bizData.products[i].salePrice);
                                            }
                                            if(data.bizData.products[i].productId==2){
                                                $('#products3-purchases').val(data.bizData.products[i].pickupPrice);
                                                $('#products3-price').val(data.bizData.products[i].salePrice);
                                            }
                                            if(data.bizData.products[i].productId==3){
                                                $('#products2-purchases').val(data.bizData.products[i].pickupPrice);
                                                $('#products2-price').val(data.bizData.products[i].salePrice);
                                            }
                                        }

                                        switch (roleType) {
                                            case 2:
                                                $('#dep_provinces_from').show();
                                                $('#dep_city_from,#dep_county_from').hide();
                                                // 省份
                                                $.getJSON('/system/dataDictionary/findProvinceList?token=' + token, function (res) {
                                                    console.log(res)
                                                    for (var i = 0; i < res.bizData.length; i++) {
                                                        $('#dep_provinces').append('<option value="' + res.bizData[i].id + '">' + res.bizData[i].provinceName + '</option>')
                                                    }
                                                    $('#dep_provinces').find('option[value="' + updateProvincesId + '0000"]').attr('selected', true);
                                                });
                                                break;
                                            case 3:
                                                var curProvincesId = updateProvincesId.substring(0, 2) + "0000";
                                                $('#dep_city_from').show();
                                                $('#dep_provinces_from,#dep_county_from,#products2-control,#products3-control').hide();
                                                $.getJSON('/system/dataDictionary/findCityList?token=' + token + '&provinceId=' + curProvincesId, function (res) {
                                                    console.log(res)
                                                    for (var i = 0; i < res.bizData.length; i++) {
                                                        $('#dep_city').append('<option value="' + res.bizData[i].id + '">' + res.bizData[i].cityName + '</option>')
                                                    }
                                                    $('#dep_city').find('option[value="' + updateProvincesId + '00"]').attr('selected', true);
                                                });
                                                break;
                                            case 4:
                                                var curProvincesCookieId = updateProvincesId.substring(0, 4) + "00";
                                                $('#dep_county_from').show();
                                                $('#dep_provinces_from,#dep_city_from,#products2-control,#products3-control').hide();
                                                // 市
                                                $.getJSON('/system/dataDictionary/findCountyList?token=' + token + '&cityId=' + curProvincesCookieId, function (res) {
                                                    console.log(res)
                                                    for (var i = 0; i < res.bizData.length; i++) {
                                                        $('#dep_county').append('<option value="' + res.bizData[i].id + '">' + res.bizData[i].countyName + '</option>')
                                                    }
                                                });
                                                break;
                                            default:
                                        }
                                    },
                                    buttons: [{
                                        text: "修改",
                                        'class': "btn btn-primary",
                                        click: function () {
                                            var vali = require('./dep_form.js');
                                            vali.validate(function (formArry) {
                                                updateDepartment(formArry, function (ret) {
                                                    tableObj.fnDraw();
                                                    if ('0000000' === ret.rtnCode) {
                                                        //var cookieJson = JSON.parse($.cookie('userInfo'));
                                                        //if ("-1" == cookieJson.departmentCode) {
                                                        //    var node = {
                                                        //        id: data.bizData.departmentCode,
                                                        //        name: formArry[0]
                                                        //    };
                                                        //    treeCallback({
                                                        //        type: 'update',
                                                        //        obj: node
                                                        //    });
                                                        //}
                                                        $("#add_dep").dialog("destroy");
                                                    } else {
                                                        $("#add_dep").dialog("destroy");
                                                        message({
                                                            title: '温馨提示',
                                                            msg: ret.msg,
                                                            type: 'alert',
                                                            clickHandle: function () {
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
                                        click: function () {
                                            $("#add_dep").dialog("destroy");
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
                        var str = '确认删除代理商' + aData.departmentName;
                        message({
                            title: '温馨提示',
                            msg: str,
                            type: 'alert',
                            clickHandle: function () {
                                $.get('/system/department/delDepartment?id=' + aData.id + '&token=' + token, function (data) {
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
        require.async('../renderResource', function (resource) {
            resource(ButtonEvent, token);
        });
    };

});


