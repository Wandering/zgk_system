define(function(require, exports, module) {
	module.exports = function(productCode, treeCallback) {
		require('bootstrap');
		require('cookie');
		require('dialog');
		var token = $.cookie('bizData');
		var message = require('../message.js');
		var Table = require('../datatable.js');
		//{
		//	data: 'id',
		//		title: '<input type="checkbox" />'
		//},
		var col = [{
			data: 'menuName',
			title: '菜单名称'
		}, {
			data: 'menuIcon',
			title: '菜单代码'
		},{
			data:'seqSort',
			title:'菜单排序'
		}];

		//var columnDefs = [{
		//	"sClass": "center",
		//	"sWidth": "30px",
		//	"render": function(data, type, row) {
		//		return '<input type="checkbox"  data-id="' + data + '"  />';
		//	},
		//	"aTargets": [0]
		//}]

		Table.initTable({
			columns: col,
			tableContentId: 'menu_table_content',
			tableId: 'menu_table_body',
			sAjaxSource: '/system/menu/queryPage?token=' + token + '&menuCode=' + productCode
		});
		var tableObj = Table.dataTable;

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

		var ajax = function(menuJson, callback) {
			$.ajax({
				type: 'post',
				url: '/system/menu/addOrEditMenu?token=' + token,
				contentType: 'application/x-www-form-urlencoded;charset=utf-8',
				data: {
					menuJson: JSON.stringify(menuJson)
				},
				dataType: 'json',
				success: function(data) {
					//console.log(data)
					if ('0000000' === data.rtnCode) {
						window.location.reload();
						tableObj.fnDraw();
						callback(data);
						$("#add_menu").dialog("destroy");
					} else if ('0100014' === data.rtnCode) {
						$("#add_menu").dialog("destroy");
						ajaxEerror(data);
					}
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
					$.get('../tmpl/menu/menu_dialog.tmpl', function(tmpl) {
						$("#add_menu").dialog({
							title: "新增菜单",
							tmpl: tmpl,
							onClose: function() {
								$("#add_menu").dialog("destroy");
							},
							render: function() {},
							buttons: [{
								text: "新增",
								'class': "btn btn-primary",
								click: function() {
									var vali = require('./menu_form.js');
									vali.validate(function(menuName, menuIcon, menuDescribe, menuSort) {
										var menuJson = {
											menuName: menuName,
											menuIcon: menuIcon,
											description: menuDescribe,
											parentCode: productCode,
											seqSort:menuSort
										};
										//console.log(JSON.stringify(menuJson))
										ajax(menuJson, function(data) {
											var node = {
												id: data.bizData.menuCode,
												name: menuName
											};
											treeCallback({
												type: 'add',
												obj: node
											});
										});
									});
								}
							}, {
								text: "取消",
								'class': "btn btn-primary",
								click: function() {
									$("#add_menu").dialog("destroy");
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
					var anSelected = Table.selectedRecord();
					if (anSelected.length != 1) {
						ajaxEerror({
							msg:'请选择一条记录修改！'
						});
						return;
					}
					var aData = anSelected[0];

					$.get('/system/menu/getMenuDetail?menuId=' + aData.id + '&token=' + token, function(ret) {
						if ('0000000' === ret.rtnCode) {
							var menuObj = ret.bizData;
							$.get('../tmpl/menu/menu_dialog.tmpl', function(tmpl) {
								require('dialog');
								$("#add_menu").dialog({
									title: "修改菜单",
									tmpl: tmpl,
									onClose: function() {
										$("#add_menu").dialog("destroy");
									},
									render: function() {
										$('#menu_name').val(menuObj.menuName);
										$('#menu_icon').val(menuObj.menuIcon);
										$('#menu_describe').val(menuObj.description);
										$('#menu_sort').val(menuObj.seqSort);
									},
									buttons: [{
										text: "修改",
										'class': "btn btn-primary",
										click: function() {
											var vali = require('./menu_form.js');
											vali.validate(function(menuName, menuIcon, menuDescribe, menuSort) {
												var menuJson = {
													menuName: menuName,
													menuIcon: menuIcon,
													description: menuDescribe,
													parentCode: productCode,
													menuId: menuObj.menuId,
													seqSort:menuSort
												};
												ajax(menuJson, function(data) {
													var node = {
														id: menuObj.menuId,
														name: menuName
													};
													treeCallback({
														type: 'update',
														obj: node
													});
												});
											});
										}
									}, {
										text: "取消",
										'class': "btn btn-primary",
										click: function() {
											$("#add_menu").dialog("destroy");
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
					var anSelected = Table.selectedRecord();
					if (anSelected.length <= 0) {
						ajaxEerror({
							msg:'请选择至少一条记录删除！'
						});
						return;
					}
					var aData = anSelected[0];
					var str = '确认删除这条数据' + aData.menuName;
					message({
						title: '温馨提示',
						msg: str,
						type: 'alert',
						clickHandle: function() {
							$.ajax({
								type: 'post',
								url: '/system/menu/delMenu?menuId=' + aData.id + '&token=' + token,
								dataType: 'json',
								success: function(data) {
									if ('0000000' === data.rtnCode) {
										tableObj.fnDraw();
										treeCallback({
											type: 'delete',
											id: aData.menuCode
										});
									} else {
										ajaxEerror(data);
									}
								}
							});
						}
					});
				});
			}
		};

		require.async('../renderResource', function(resource) {
			resource(ButtonEvent, token);
		});
	}
});