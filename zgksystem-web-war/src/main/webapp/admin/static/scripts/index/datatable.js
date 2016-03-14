define(function(require, exports, module) {
	require('dataTables');
	require('bootstrap');
	var message = require('./message.js');

	$.fn.dataTableExt.oStdClasses.sPaging = 'dataTables_paginate paging_bootstrap paging_custom';
	jQuery.fn.dataTableExt.oSort['string-case-asc'] = function(x, y) {
		return ((x < y) ? -1 : ((x > y) ? 1 : 0));
	};

	jQuery.fn.dataTableExt.oSort['string-case-desc'] = function(x, y) {
		return ((x < y) ? 1 : ((x > y) ? -1 : 0));
	};

	var Table = {
		dataTable: null,
		searchHandle: null,
		buttonHandle: null,
		resourceListHandle:null,
		resourceContentId:null,
		/**
		 * 展示右边内容的数据
		 * @param {Object} data  展示的数据
		 * @param {Object} columns  需要展示的列数
		 * @param {object} tableContentId 显示表数据的容器
		 */
		initTable: function(options) {
			this.searchHandle = options.searchHandle || null;
			this.buttonHandle = options.buttonHandle || null;
			this.resourceListHandle = options.resourceListHandle || null;
			this.tableId = options.tableId;
			this.resourceContentId = options.resourceContentId;
			$('#' + options.tableContentId).html('<table cellpadding="0" cellspacing="0" border="0" class="display" id="' + options.tableId + '"></table>');
			this.dataTable = $('#' + options.tableId).dataTable({
				"searching": false,
				"ordering": false,
				"language": {
					"sLengthMenu": "每页显示 _MENU_ 条记录",
					"sZeroRecords": "抱歉， 没有找到相关数据",
					"sInfo": "从 _START_ 到 _END_ / 共 _TOTAL_ 条数据",
					"sInfoEmpty": "没有数据",
					"sInfoFiltered": "(从 _MAX_ 条数据中检索)",
					"sProcessing": "正在加载数据...",
					"sProcessing": "<img src='/dist/static/images/loader.gif' />", //这里是给服务器发请求后到等待时间显示的 加载gif,
					"oPaginate": {
						"sFirst": "首页",
						"sPrevious": "前一页",
						"sNext": "后一页",
						"sLast": "尾页"
					}
				},
				"columns": options.columns,
				"columnDefs": options.columnDefs,
				"sZeroRecords": "没有检索到数据",
				"bProcessing": true,
				"bServerSide": true,
				"sAjaxSource": options.sAjaxSource,
				"fnServerData": this.retrieveData,
				"fnInitComplete": function(oSettings, json) { //表格初始化完成后调用 
					require('mCustomScrollbar');
					$("#content").mCustomScrollbar({
						theme: "minimal"
					});
				}
			}).removeClass('display').addClass('table table-datatable table-custom');
			this.eventHandle();
		},
		retrieveData: function(sSource, aoData, fnCallback) {
			var newData = {};
			for (var i = 0, len = aoData.length; i < len; i++) {
				if ('sEcho' === aoData[i].name ||
					'iDisplayStart' === aoData[i].name ||
					'iDisplayLength' === aoData[i].name) {
					newData[aoData[i].name] = aoData[i].value;
				}
			}
			newData.currentPageNo = newData.iDisplayStart / newData.iDisplayLength + 1;
			newData.pageSize = newData.iDisplayLength;
			if (Table.searchHandle && typeof Table.searchHandle === 'function') {
				Table.searchHandle(newData);
			}
			$.ajax({
				"type": "get",
				"contentType": "application/json",
				"url": sSource,
				"dataType": "json",
				"data": newData,
				"success": function(resp) {
					var data = resp;
					var resourceList = [];
					if ('0000000' === data.rtnCode) {
						var finallyData = {
							sEcho: newData.sEcho,
							iTotalRecords: data.bizData.count,
							iTotalDisplayRecords: data.bizData.count,
							aaData: data.bizData.list
						};
						fnCallback(finallyData);
						Table.eventHandle();
					} else if('0100014' === data.rtnCode || '0100015' === data.rtnCode) {
						message({
							title:'温馨提示',
							msg: data.msg,
							type:'alert',
							clickHandle: function() {
								window.location.href = 'login.html';
							}
						});
					} else {
						var finallyData = {
							sEcho: newData.sEcho,
							iTotalRecords: 0,
							iTotalDisplayRecords: 0,
							aaData: []
						};
						fnCallback(finallyData);
					}
					if (Table.resourceContentId) {

						if (Table.resourceListHandle && typeof Table.resourceListHandle === 'function') {
							Table.resourceListHandle();
						}
					}
				}
			});
		},
		eventHandle: function() {
			$('#' + this.tableId + ' tbody tr').off('click');
			$('#' + this.tableId + ' tbody tr').on('click', function(e) {
				if ($(this).hasClass('row_selected')) {
					$(this).removeClass('row_selected');
				} else {
					Table.dataTable.$('tr.row_selected').removeClass('row_selected');
					$(this).addClass('row_selected');
				}
				e.stopPropagation();
			});

			if (Table.buttonHandle && typeof Table.buttonHandle === 'function') {
				$('.td-button').off('click');
				$('.td-button').on('click', function(e) {
					Table.buttonHandle();
					e.stopPropagation();
				});
			}
		},
		fnGetSelected: function(oTable01Local) {
			return oTable01Local.$('tr.row_selected');
		},
		/**
		 * 处理选择的数据
		 * @returns {Array}
		 */
		selectedRecord:function() {
			var anSelected = this.fnGetSelected(this.dataTable);
			var aData = [];
			if (anSelected.length > 0) {
				for (var i = 0, len = anSelected.length; i < len; i++) {
					aData.push(this.dataTable.fnGetData(anSelected[i]));
				}
			}
			return aData;
		}
	}

	module.exports = Table;
});