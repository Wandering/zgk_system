define(function(require, exports, module) {
	module.exports = function() {
		require('bootstrap');
		require('cookie');
		var token = $.cookie('bizData');
		var message = require('../message.js');
		var vali = require('./platform_form.js');

		//$.get('../tmpl/platform/platform_search_header.tmpl', function(tmpl) {
		//	$('#form_search').html(tmpl);
		//	require('datetimepicker');
		//	require('datetimepickerCN');
		//	$('.form_date').datetimepicker({
		//		language: 'zh-CN',
		//		weekStart: 1,
		//		todayBtn: 1,
		//		autoclose: 1,
		//		todayHighlight: 1,
		//		startView: 2,
		//		minView: 2,
		//		forceParse: 0
		//	});
		//});
		var Table = require('../datatable.js');
		var col = [{
			data: 'id'
		}, {
			data: 'systemName',
			title: '平台名称'
		}, {
			data: 'systemUrl',
			title: '平台地址'
		}, {
			data: 'systemLogo',
			title: '平台logo'
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
				return '<a target="_blank" href="' + data + '" style="color:#fff">' + data + '</a>';
			}
		},{
			"aTargets": [3],
			"render":function(data, type, row) {
				return '<img src="' + data + '" width="18" height="18" />';
			}
		}];

		Table.initTable({
			columns: col,
			tableContentId: 'table_content',
			tableId: 'table_body',
			columnDefs: columnDefs,
			sAjaxSource: '/system/system/querySystemPage?token=' + token
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

		var platformUploadify = function() {
			var uploadify = require('../uploadify.js');
			uploadify(function(file, data, response) {
				//获取到data处理
				var obj = JSON.parse(data);
				if (obj.code == 200) {
					$('#platform_logo_img').attr('src', obj.data.url);
					$('#platform_logo').val(obj.data.url);
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
					$.get('../tmpl/platform/add_platform.html', function(tmpl) {
						require('dialog');
						$("#add_platform").dialog({
							title: "新增平台",
							tmpl: tmpl,
							onClose: function() {
								$("#add_platform").dialog("destroy");
							},
							render: function() {
								setTimeout(function() {
									platformUploadify();
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
									vali.validateTheSecondStep(function(systemName, systemUrl, systemLogo,
																		description, userName, pwd, realName,
																		phone, email) {
										var systemJson = {
											systemName: systemName,
											systemUrl: systemUrl,
											systemLogo: systemLogo,
											description: description,
											loginNumber: userName,
											password:pwd,
											userName:realName,
											email:email,
											phone:phone
										};
										//console.log(JSON.stringify(systemJson))
										$.ajax({
											type: 'post',
											url: '/system/system/addOrEditSytem?token=' + token,
											contentType: 'application/x-www-form-urlencoded;charset=utf-8',
											data: {
												systemJson: JSON.stringify(systemJson)
											},
											dataType: 'json',
											success: function(data) {
												if ('0000000' === data.rtnCode) {
													tableObj.fnDraw();
													$("#add_platform").dialog("destroy");
												} else {
													$("#add_platform").dialog("destroy");
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
									$("#add_platform").dialog("destroy");
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
					$.get('/system/system/getK12System?systemId=' + aData.systemId + '&token=' + token, function(data) {
						if ('0000000' === data.rtnCode) {
							$.get('../tmpl/platform/update_platform.html', function(tmpl) {
								require('dialog');
								$("#add_platform").dialog({
									title: "修改平台",
									tmpl: tmpl,
									onClose: function() {
										$("#add_platform").dialog("destroy");
									},
									render: function() {
										setTimeout(function() {
											platformUploadify();
										}, 100);
										$('#platform_name').val(data.bizData.systemName);
										$('#platform_url').val(data.bizData.systemUrl);
										$('#platform_logo').val(data.bizData.systemLogo);
										$('#platform_logo_img').attr('src', data.bizData.systemLogo);
										$('#platform_describe').val(data.bizData.description);
									},
									buttons: [{
										text: "修改",
										'class': "btn btn-primary",
										click: function() {
											vali.validateTheFirstStep(function(sysName, sysUrl, sysLogo, sysDescribe) {
												var systemJson = {
													systemId: aData.systemId,
													systemName: sysName,
													systemUrl: sysUrl,
													systemLogo: sysLogo,
													description: sysDescribe
												};
												$.ajax({
													type: 'post',
													url: '/system/system/addOrEditSytem?token=' + token,
													contentType: 'application/x-www-form-urlencoded;charset=utf-8',
													data: {
														systemJson: JSON.stringify(systemJson)
													},
													dataType: 'json',
													success: function(data) {
														//console.log(data)
														if ('0000000' === data.rtnCode) {
															tableObj.fnDraw();
															$("#add_platform").dialog("destroy");
														} else {
															$("#add_platform").dialog("destroy");
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
											$("#add_platform").dialog("destroy");
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
						var str = '确认删除平台' + aData.systemName;
						message({
							title: '温馨提示',
							msg: str,
							type: 'alert',
							clickHandle: function() {
								$.ajax({
									type: 'post',
									url: '/system/system/delSystem?systemId=' + aData.systemId + '&token=' + token,
									contentType: 'application/x-www-form-urlencoded;charset=utf-8',
									dataType: 'json',
									success: function(data) {
										if ('0000000' === data.rtnCode) {
											tableObj.fnDraw();
										} else {
											message({
												title:'温馨提示',
												msg:data.msg,
												type:'alert'
											});
										}
									},
									beforeSend: function(xhr) {},
									error: function(data) {

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