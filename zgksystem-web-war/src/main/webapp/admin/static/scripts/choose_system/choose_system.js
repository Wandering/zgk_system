define(function(require, exports, module) {
	require('bootstrap');
	var message = require('../index/message.js');

	require('cookie');
	var token = $.cookie('bizData');
	//选择系统界面初始化
	var SYS = {
		init: function(data, contentId) {
			if (data.length <= 0) {
				return;
			};
			$('#' + contentId).html(this.tmpl(data));
			this.addEvent(contentId);
		},
		tmpl: function(data) {

			console.log(data)
				if(data.length==2){
					data[0].systemLogo = '../static/images/icon_manage.png';
					data[1].systemLogo = '../static/images/icon_organization.png';
				}else{
					data[0].systemLogo = '../static/images/icon_organization.png';
				}


			console.log(data);
			var htmlStr = [];
			if (data.length <= 0) {
				return '<h1 style="margin-top: 150px;color:#fff">请联系系统管理员分配平台管理权限！</h1>';
			}
			for (var i = 0, len = data.length; i < len; i++) {
				htmlStr.push('<span data-systemCode="' + data[i].systemCode + '" data-url="' + data[i].systemUrl + '">' +
					'<img src="' + data[i].systemLogo + '"  />' +
					'<h6>' + data[i].systemName + '</h6>' +
					'</span>');
			}
			return htmlStr.join('');
		},
		addEvent: function(contentId) {
			$('#' + contentId + ' img').on('mouseover', function(e) {
				$(this).addClass('focus');
			});
			$('#' + contentId + ' img').on('mouseout', function(e) {
				$(this).removeClass('focus');
			});
			$('#' + contentId + ' span').on('click', function(e) {
				var url = $(this).attr('data-url');
				var systemCode = $(this).attr('data-systemCode');
				if (url) {
					if ('index.html' === url) {
						window.location.href = url + '?systemCode=' + systemCode;
					} else {
						$.ajax({
							url: url + '?token=' + token,
							dataType:"jsonp",
							jsonp:"callback",
							success:function(data){
								//console.log(data);
								var indexURL = url.split('/');
								window.location.href = indexURL[0] + '//' + indexURL[2] + '/dist/app/index.html?token=' + token + '&systemCode=' + systemCode;
							}
						});
					}
				}
			})
		}
	};

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


	$.get('/system/system/getSystemList?token=' + token, function(data) {
		if ('0000000' === data.rtnCode) {
			console.log(data)
			SYS.init(data.bizData, 'content');
		} else {
			ajaxEerror(data);
		}
	});
});