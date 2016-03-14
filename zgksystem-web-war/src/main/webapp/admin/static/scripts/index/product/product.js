define(function(require, exports, module) {
	module.exports = function() {
		require('bootstrap');
		require('cookie');
		var token = $.cookie('bizData');
		var message = require('../message.js');
		var vali = require('./product_form.js');
		var Table = require('../datatable.js');
		var col = [{
			data: 'productId'
		}, {
			data: 'productName',
			title: '产品名称'
		}, {
			data: 'productIndexPage',
			title: '产品地址'
		}, {
			data: 'productLogo',
			title: '产品logo'
		}, {
			data: 'description',
			title: '产品描述'
		}, {
			data: 'loginNumber',
			title: '管理员'
		},{
			data:'userName',
			title:'真实姓名'
		}];

		var columnDefs = [{
			"bVisible": false,
			"aTargets": [0]
		},{
			"aTargets": [2],
			"render":function(data, type, row) {
				return '<a style="color:#fff;text-decoration:underline" href="' + data + '" target="_blank">' + data + '</a>';
			}
		},{
			"aTargets": [3],
			"render":function(data, type, row) {
				return '<img src="' + data + '" width="18" height="18" />';
			}
		},{
			"aTargets": [4],
			"render":function(data, type, row) {
				var shortStr = data;
				if (shortStr.length > 40) {
					shortStr = data.substring(0, 40) + '...<a class="row-details row-details-close">更多</a>';
				}
				return shortStr;
			}
		}];

		Table.initTable({
			columns: col,
			tableContentId: 'table_content',
			tableId: 'product_table_body',
			columnDefs: columnDefs,
			sAjaxSource: '/system/product/queryProduct?token=' + token
		});

		var tableObj = Table.dataTable;

		var productUploadify = function() {
			var uploadify = require('../uploadify.js');
			uploadify(function(file, data, response) {
				//获取到data处理
				var obj = JSON.parse(data);
				if (obj.code == 200) {
					$('#product_logo_img').attr('src', obj.data.url);
					$('#product_logo').val(obj.data.url);
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
					$.get('../tmpl/product/add_product.html', function(tmpl) {
						require('dialog');
						$("#add_product").dialog({
							title: "新增产品",
							tmpl: tmpl,
							onClose: function() {
								$("#add_product").dialog("destroy");
							},
							render: function() {
								setTimeout(function() {
									productUploadify();
								}, 100);
							},
							buttons: [{
								text: "下一步",
								'class': "btn btn-success step-1",
								click: function() {
									vali.validateTheFirstStep(function() {
										var button = $('.modal-footer button.btn-success');
										var addButton = $('.modal-footer button.add');
										if (button.hasClass('step-1')) {
											button.removeClass('step-1');
											button.addClass('step-2');
											button.html('上一步');
											$('#step_title_1').removeClass('focus');
											$('#step_title_2').addClass('focus');
											var left = $('#page_body').width() / 2;
											addButton.show();
											$('#page_body').animate({
												left: -left + 'px'
											});
										} else if (button.hasClass('step-2')) {
											button.removeClass('step-2');
											button.addClass('step-1');
											button.html('下一步');
											$('#step_title_2').removeClass('focus');
											$('#step_title_1').addClass('focus');
											addButton.hide();
											$('#page_body').animate({
												left: 0 + 'px'
											});
										}
									});
								}
							},{
								text: "新增",
								'class': "btn btn-primary add custom-hide",
								click: function() {
									vali.validateTheSecondStep(function(params) {
										var productJson = {
											productName: params[0],
											productIndexPage: params[1],
											addressJson: params[1],
											productLogo: params[2],
											description: params[3],
											userName:params[4],
											loginNumber:params[5],
											password:params[6],
											phone:params[7],
											email:params[8]
										};
										$.ajax({
											type: 'post',
											url: '/system/product/addOrEditProduct?token=' + token,
											contentType: 'application/x-www-form-urlencoded;charset=UTF-8',
											data: {
												productJson: JSON.stringify(productJson)
											},
											dataType: 'json',
											success: function(data) {
												if ('0000000' === data.rtnCode) {
													tableObj.fnDraw();
													$("#add_product").dialog("destroy");
												} else {
													$("#add_product").dialog("destroy");
													message({
														title: '温馨提示',
														msg: data.msg,
														type: 'alert',
														clickHandle: function() {
															window.location.href = 'login.html';
														}
													});
												}
											},
											beforeSend: function(xhr) {},
											error: function(data) {

											}
										});
									});
								}
							}, {
								text: "取消",
								'class': "btn btn-primary",
								click: function() {
									$("#add_product").dialog("destroy");
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
					$.get('/system/product/getProduct?id=' + aData.productId + '&token=' + token, function(data) {
						if ('0000000' === data.rtnCode) {
							$.get('../tmpl/product/update_product.html', function(tmpl) {
								require('dialog');
								$("#add_product").dialog({
									title: "修改产品",
									tmpl: tmpl,
									onClose: function() {
										$("#add_product").dialog("destroy");
									},
									render: function() {
										setTimeout(function() {
											productUploadify();
										}, 100);
										$('#product_name').val(data.bizData.productName);
										$('#product_url').val(data.bizData.productIndexPage);
										$('#product_logo').val(data.bizData.productLogo);
										$('#product_logo_img').attr('src', data.bizData.productLogo);
										$('#product_describe').val(data.bizData.description);
									},
									buttons: [{
										text: "修改",
										'class': "btn btn-primary",
										click: function() {
											vali.validateTheFirstStep(function(sysName, sysUrl, sysLogo, sysDescribe) {
												var productJson = {
													productId: aData.id,
													addressJson: sysUrl,
													description: sysDescribe,
													productIndexPage: sysUrl,
													productLogo: sysLogo,
													productName: sysName
												};
												$.ajax({
													type: 'post',
													url: '/system/product/addOrEditProduct?token=' + token,
													contentType: 'application/x-www-form-urlencoded;charset=UTF-8',
													data: {
														productJson: JSON.stringify(productJson)
													},
													dataType: 'json',
													success: function(data) {
														if ('0000000' === data.rtnCode) {
															tableObj.fnDraw();
															$("#add_product").dialog("destroy");
														} else {
															$("#add_product").dialog("destroy");
															message({
																title: '温馨提示',
																msg: data.msg,
																type: 'alert',
																clickHandle: function() {
																	window.location.href = 'login.html';
																}
															});
														}
													},
													beforeSend: function(xhr) {},
													error: function(data) {

													}
												});
											});
										}
									}, {
										text: "取消",
										'class': "btn btn-primary",
										click: function() {
											$("#add_product").dialog("destroy");
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
						var str = '确认删除产品' + aData.productName;
						message({
							title: '温馨提示',
							msg: str,
							clickHandle: function() {
								$.get('/system/product/delProduct?id=' + aData.productId + '&token=' + token, function(data) {
									if ('0000000' === data.rtnCode) {
										tableObj.fnDraw();
									} else {
										message({
											title:'错误提示',
											msg:data.msg,
											type:'alert'
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