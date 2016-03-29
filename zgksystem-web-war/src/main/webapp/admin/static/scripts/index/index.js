define(function(require, exports, module) {
	require('bootstrap');
	require('cookie');
	require('md5');

	function GetQueryString(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null) return unescape(r[2]);
		return null;
	}

	var Tool = require('./tools.js');
	var token = $.cookie('bizData');

	if (!token) {
		window.location.href = 'login.html';
	}
	$(window).load(function() {
		var nav = require('./nav.js');
		var userInfo = JSON.parse($.cookie('userInfo'));
		$('#login_real_name').html(userInfo.userName);
		//init menu
		nav.init('navigation', 'menu');
		nav.resize();
		$(window).resize(function() {
			nav.resize();
		});
		//用户下拉菜单
		$(".quick-actions .dropdown").on("show.bs.dropdown", function(d) {
			$(this).find(".dropdown-menu").addClass("animated fadeInDown");
			$(this).find("#user-inbox").addClass("animated bounceIn")
		});

		$('.mask').on('click', function(e) {
			e.stopPropagation();
			return;
		});
		
		//change background
		$("#color-schemes li a").click(function() {
			var d = $(this).attr("class");
			var e = $("body").attr("class").split(" ").pop();
			$("body").removeClass(e).addClass(d);
		});

		//hidden left menu
		$(".sidebar-collapse a").on("click", function() {
			$("#sidebar, #navbar").toggleClass("collapsed");
			$("#navigation").find(".dropdown.open").removeClass("open");
			$("#navigation").find(".dropdown-menu.animated").removeClass("animated");
			$("#sidebar > li.collapsed").removeClass("collapsed");
			if ($("#sidebar").hasClass("collapsed")) {
				if ($(window).width() < 1024) {
					$("#content").animate({
						left: "0px"
					}, 150)
				} else {
					$("#content").animate({
						paddingLeft: "35px"
					}, 150)
				}
			} else {
				if ($(window).width() < 1024) {
					$("#content").animate({
						left: "210px"
					}, 150)
				} else {
					$("#content").animate({
						paddingLeft: "265px"
					}, 150)
				}
			}
		});

		$('#logout').on('click', function() {
			$.cookie('bizData', null, {
				expires: 0,
				path: '/'
			});
			$.cookie('userInfo', null, {
				expires: 0,
				path: '/'
			});
			window.location.href = 'login.html';
		});
		var setTingInfoAjax = function(userPojoJson, action, callback) {
			$.ajax({
				type: 'post',
				url: '/system/userInfo/' + action + '?token=' + token,
				contentType: 'application/x-www-form-urlencoded;charset=UTF-8',
				data: {
					userPojoJson: JSON.stringify(userPojoJson)
				},
				dataType: 'json',
				success: function(data) {
					callback(data);
				}
			});
		};

		var userPojoJson = {
			accountId:userInfo.accountId,
			userInfoId:userInfo.userInfoId
		};
		
		//modal of change password
		$('#change_pwd').on('click', function() {
			require.async('dialog', function(dialog) {
				$("#change_pwd_form").dialog({
					title: "账户设置",
					tmpl:$("#tmpl_2").html(),
					render: function() {
						$('#change_basic_info').show();
						$('.login-user-name').html(userInfo.loginNumber);
						$('.user-name').html(userInfo.userName);
						$('.user-phone').html(userInfo.phone);
						$('.user-eamil').html(userInfo.email);
						$('.controls a').on('click', function(e) {
							var that = $(this);
							var span = $(this).parent().find('span');
							var input = $(this).parent().find('input');
							var dataClass = that.attr('data-class');
							if (that.hasClass('change')) {
								that.html('保存');
								that.removeClass('change');
								that.addClass('save');
								if ('change_pwd' === dataClass) {
									var cbi = $('#change_basic_info');
									var cbp = $('#change_basic_pwd');
									var fal = $('.fa-arrow-left');
									cbi.hide();
									cbp.show();
									fal.show();
									fal.off('click');
									fal.on('click', function() {
										that.html('修改密码');
										that.removeClass('save');
										that.addClass('change');
										cbi.show();
										cbp.hide();
										$(this).hide();
									});
									$('#old_password').blur(function() {
										var OP = $(this).val();
										OP = $.md5(OP);
										userPojoJson.password = OP;
										var that = this;
										setTingInfoAjax(userPojoJson, 'checkOldPassword', function(ret) {
											var tip = $(that).parent().parent();
											if ('0100009' === ret.rtnCode) {
												Tool.tip(tip, ret.msg);
												$(that).focus();
											} else {
												$(that).attr('data-flag', '1');
											}
										});
									});
								} else {
									var value = span.html();
									span.hide();
									input.show();
									input.val(value);
								}
							} else if ($(this).hasClass('save')) {
								if ('change_pwd' === dataClass) {
									var op = $('#old_password');
									if ('1' !== op.attr('data-flag')) {
										var errorTip = op.parent().parent();
										Tool.tip(errorTip, '旧密码错误');
										return;
									}
									var np = $('#new_password').val();

									if (!np) {
										var errorTip = $('#new_password').parent().parent();
										Tool.tip(errorTip, '新密码不能为空');
										return;
									}

									var cnp = $('#confirm_new_password').val();
									if (cnp !== np) {
										var et = $('#confirm_new_password').parent().parent();
										Tool.tip(et, '确认密码和新密码不一致');
										return;
									}

									np = $.md5(np);
									userPojoJson.password = np;
									setTingInfoAjax(userPojoJson, 'updateUserInfo', function(ret) {
										if ('0000000' === ret.rtnCode) {
											window.location.href = 'login.html';
										}
									});
								} else {
									var key = input.attr('id');
									var value = input.val();
									var method = that.attr('data-method');
									if (!Tool[method](value)) {
										Tool.tip(input.parent().parent(), '格式错误');
										return;
									}
									userPojoJson[key] = value;
									userPojoJson.token= JSON.parse($.cookie('userInfo')).token;
									setTingInfoAjax(userPojoJson, 'updateUserInfo', function(ret) {
										if ('0000000' === ret.rtnCode) {
											input.hide();
											span.html(value);
											span.show();
											that.removeClass('save');
											that.addClass('change');
											that.html(that.attr('data-title'));
											var newUserInfo = JSON.parse($.cookie('userInfo'));
											newUserInfo[key] = value;
											$.cookie('userInfo', JSON.stringify(ret.bizData), {expires: 7, path: '/'});
											userInfo = JSON.parse(ret.bizData);
										}
									});
								}
							}

						});
					},
					onClose: function() {
						$("#change_pwd_form").dialog("destroy");
					},
					buttons: [{
						text: "关闭",
						'class': "btn btn-primary",
						click: function() {
							$("#change_pwd_form").dialog("destroy");
						}
					}]
				});
			});
		});
	});
});