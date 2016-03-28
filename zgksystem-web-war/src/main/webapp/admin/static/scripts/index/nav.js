define(function(require, exports, module) {

	require('cookie');
	var token = $.cookie('bizData');

	require('bootstrap');
	var message = require('./message.js');

	var widthLess1024 = function() {
		if ($(window).width() < 1024) {
			$("#sidebar, #navbar").addClass("collapsed");
			$("#navigation").find(".dropdown.open").removeClass("open");
			$("#navigation").find(".dropdown-menu.animated").removeClass("animated");
			if ($("#sidebar").hasClass("collapsed")) {
				$("#content").animate({
					left: "0px",
					paddingLeft: "55px"
				}, 150)
			} else {
				$("#content").animate({
					paddingLeft: "55px"
				}, 150)
			}
		} else {
			$("#sidebar, #navbar").removeClass("collapsed");
			if ($("#sidebar").hasClass("collapsed")) {
				$("#content").animate({
					left: "210px",
					paddingLeft: "265px"
				}, 150)
			} else {
				$("#content").animate({
					paddingLeft: "265px"
				}, 150)
			}
		}
	};
	var widthLess768 = function() {
		if ($(window).width() < 768) {
			if ($(".collapsed-content .search").length === 1) {
				$("#main-search").appendTo(".collapsed-content .search")
			}
			if ($(".collapsed-content li.user").length === 0) {
				$(".collapsed-content li.search").after($("#current-user"))
			}
		} else {
			$("#current-user").show();
			if ($(".collapsed-content .search").length === 2) {
				$(".nav.refresh").after($("#main-search"))
			}
			if ($(".collapsed-content li.user").length === 1) {
				$(".quick-actions >li:last-child").before($("#current-user"))
			}
		}
	};

	function GetQueryString(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null) return unescape(r[2]);
		return null;
	}
	/**
	 * 处理左边菜单
	 */
	var Nav = {
		navStr: [],
		menuCode: {
			/**
			 * 权限管理
			 */
			authorityManager:function() {
				$('#page_0').html('');
				Nav.changeSection('tmpl_1');
				try {
					require.async('./authority/authority', function(module) {
						module();
					});
				} catch (e) {}
			},
			/**
			 * 资源管理
			 */
			resourceManager:function() {
				$('.mask').fadeIn();
				setTimeout(function() {
					$('.mask').fadeOut();
				}, 5000);
				$('#page_0').html('');
				var systemCode = GetQueryString('systemCode');
				require.async(['./common/initTreeGrid', './resource/resource_list'], function(module, listGridModule) {
					var options = {
						succ: Nav.getTreeDataSuccess,
						err: function(ret) {
							Nav.getTreeDataError(ret, '请联系系统管理员添加菜单信息');
						},
						getTreeDataURL: '/system/menu/getMenuTree?token=' + token + '&systemCode=' + systemCode,
						listGridModule: listGridModule
					};
					module(options);
				});
			},
			/**
			 * 角色管理
			 */
			roleManager: function() {
				$('#page_0').html('');
				Nav.changeSection('tmpl_1');
				try {
					require.async('./role/role', function(module) {
						module();
					});
				} catch (e) {}
			},
			/**
			 * 部门管理
			 */
			departmentManager: function() {
				$('#page_0').html('');
				require.async('./dep/dep', function(module) {
					Nav.treeMenuEntry(module, '请联系管理员添加代理公司');
				});
			},
			/**
			 * 菜单管理
			 */
			menuManager: function() {
				$('#page_0').html('');
				var module = require('./menuManager/menu_manager');
				Nav.treeMenuEntry(module);
			},
			/**
			 * 平台管理
			 */
			platformManager: function() {
				$('#page_0').html('');
				Nav.changeSection('tmpl_1');
				try {
					require.async('./platform/platform', function(module) {
						module();
					});
				} catch (e) {}
			},
			/**
			 *  产品管理
			 */
			productManager: function() {
				$('#page_0').html('');
				Nav.changeSection('tmpl_1');
				try {
					require.async('./product/product', function(module) {
						module();
					});
				} catch (e) {}
			},
			/**
			 * 公司管理
			 */
			companyManager: function() {
				$('#page_0').html('');
				require.async('./company/company', function(module) {
					Nav.treeMenuEntry(module, '请联系产品管理员添加产品');
				});
			},
			/**
			 * 岗位管理
			 */
			positionManager: function() {
				$('.mask').fadeIn();
				setTimeout(function() {
					$('.mask').fadeOut();
				}, 5000);
				$('#page_0').html('');
				require.async(['./common/initTreeGrid', './position/position_list'], function(module, listGridModule) {
					var options = {
						succ: Nav.getTreeDataSuccess,
						err: function(ret) {
							Nav.getTreeDataError(ret, '请联系代理公司管理员添加部门信息');
						},
						getTreeDataURL: '/system/department/queryTreeDepartmentAll?token=' + token,
						listGridModule: listGridModule
					};
					module(options);
				});
			},
			/**
			 * 人员管理
			 */
			employeeManager: function() {
				$('.mask').fadeIn();
				setTimeout(function() {
					$('.mask').fadeOut();
				}, 5000);
				$('#page_0').html('');
				require.async(['./common/initTreeGrid','./employee/employee_list'], function(module, listGridModule) {
					var options = {
						succ: Nav.getTreeDataSuccess,
						err: function(ret) {
							Nav.getTreeDataError(ret, '请联系部门管理员添加岗位信息');
						},
						getTreeDataURL: '/system/department/queryTreeDepartmentAll?token=' + token + "&navType=2",
						listGridModule: listGridModule
					};
					module(options);
				});
			}
		},
		getTreeDataSuccess: function() {
			$('.mask').fadeOut();
			Nav.changeSection('tmpl_0');
		},
		getTreeDataError: function(ret, msg) {
			$('.mask').fadeOut();
			var tipMsg = '';
			var clickHandle = null;
			if ('0100010' === ret.rtnCode) {
				tipMsg = msg;
			} else {
				tipMsg = ret.msg || '抱歉，发生错误信息';
			}
			if ('0100014' === ret.rtnCode) {
				clickHandle = function() {
					window.location.href = 'login.html';
				}
			}

			message({
				title: '温馨提示',
				msg: tipMsg,
				type: 'alert',
				clickHandle: clickHandle
			});
		},
		treeMenuEntry: function(module, msg, getTreeDataURL, listGridModule) {
			try {
				$('.mask').fadeIn();
				setTimeout(function() {
					$('.mask').fadeOut();
				}, 5000);
				var options = {
					succ: this.getTreeDataSuccess,
					err: function(ret) {
						Nav.getTreeDataError(ret, msg);
					},
					getTreeDataURL: getTreeDataURL || '',
					listGridModule: listGridModule || null
				};
				module(options);
			} catch (e) {}
		},
		changeSection: function(tmplId) {
			$('#page_0').html($('#' + tmplId).html());
		},
		/**
		 * 获取菜单数据并初始化
		 * @param {Object} contentId
		 * @param {Object} rootClassName
		 */
		getNavData: function(contentId, rootClassName) {
			var that = this;
			var systemCode = GetQueryString('systemCode');
			$.get('/system/menu/getMenuTree?token=' + token + '&systemCode=' + systemCode, function(data) {
				if ('0000000' === data.rtnCode) {
					var menuData = data.bizData.treeBeanList;
					var htmlStr = that.initNav(menuData, rootClassName);
					$('#' + contentId).html(htmlStr);
					that.initFisrt();
					that.addEvent();
				} else {
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
				}
			});
		},
		initNav: function(data, className, flag) {
			this.navStr.push('<ul class="' + className + '">');
			var classNames = ['fa-tachometer', 'fa-list', 'fa-pencil', 'fa-th-large', 'fa-desktop', 'fa-play-circle',
				'fa-bar-chart-o', 'fa-tint', 'fa-th', 'fa-star'
			];
			for (var i = 0, len = data.length; i < len; i++) {
				var children = data[i].children || [];
				if (children.length > 0) {
					this.navStr.push('<li data-code="' + data[i].leafIcon + '" class="dropdown">');
					this.navStr.push('<a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-tachometer"></i>' +
						data[i].name + '<b class="fa fa-plus dropdown-plus"></b>' +
						'</a>');
					this.initNav(children, 'dropdown-menu', true);
					this.navStr.push('</li>');
				} else {
					var parentIcon = '';
					if ('menu' === className) {
						parentIcon = '<i class="fa ' + classNames[i] + '"></i>';
					}
					var fa = flag ? '<i class="fa fa-caret-right"></i>' : '';
					this.navStr.push('<li data-id="' + data[i].id + '" data-code="' + data[i].leafIcon + '"><a href="javascript:void(0)">' + parentIcon + fa + data[i].name + '</a></li>');
				}
			}
			this.navStr.push('</ul>');
			return this.navStr.join('');
		},
		/**
		 * 第一次初始化菜单
		 */
		initFisrt: function() {
			var firstLi = $("#navigation .menu > li").first();
			firstLi.addClass('active');
			if (firstLi.hasClass('dropdown')) {
				firstLi.addClass('open');
				firstLi.find('li').first().addClass('resource active');
				this.sendredirectByCode(firstLi.find('li').first());
				return;
			} else {
				firstLi.addClass('resource');
			}
			this.sendredirectByCode(firstLi);
		},
		/**
		 * 通过菜单code做相应的逻辑
		 * @param {Object} ele
		 */
		sendredirectByCode: function(ele) {
			var code = ele.attr('data-code');
			if (typeof this.menuCode[code] === 'function') {
				this.menuCode[code]();
			}
		},
		/**
		 * 给菜单注册事件
		 */
		addEvent: function() {
			var that = this;
			$("#navigation .menu > li").on('click', function(e) {
				$(this).siblings().removeClass('open');
				$("#navigation .menu li").removeClass('resource');
				$('#navigation .dropdown-menu > li').removeClass('resource active');
				if ($(this).hasClass('dropdown')) {
					if (!$(this).hasClass("open")) {
						$(this).addClass('open');
						var fisrtLi = $(this).find('li').first();
						fisrtLi.addClass('resource active');
						that.sendredirectByCode(fisrtLi);
					} else {
						$(this).removeClass('open');
						$(this).find('li').removeClass('active');
						$(this).find('li').removeClass('resource');
					}
				} else {
					$(this).addClass('resource');
					that.sendredirectByCode($(this));
				}

				if (!$(this).hasClass('active')) {
					$(this).addClass('active').siblings().removeClass('resource active');
				}
				e.stopPropagation();
			});
			$('#navigation .dropdown-menu > li').on('click', function(e) {
				if (!$(this).hasClass('resource active')) {
					$(this).addClass('resource active').siblings().removeClass('resource active');
					that.sendredirectByCode($(this));
				};
				e.stopPropagation();
			});

			$("#navigation .menu > li").hover(function() {
				$(this).addClass("hovered");
				$("#sidebar").addClass("open")
			}, function() {
				$(this).removeClass("hovered");
				$("#sidebar").removeClass("open")
			});
		},
		resize: function() {
			widthLess1024();
			widthLess768();
		}
	};

	module.exports = {
		resize: function() {
			Nav.resize();
		},
		init: function(contentId, rootClassName) {
			Nav.getNavData(contentId, rootClassName);
		}
	};
});