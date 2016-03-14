define(function(require, exports, module) {
	$('.input-group input').on('focus', function(e) {
		$('#error_tip').hide(500);
	});
	
	$('#login').on('click', function(e) {

		var username = $('#username').val();
		var password = $('#password').val();
		if (!username || !password) {
			$('#error_tip').html('账号或密码不能为空！').show(500);
			return;
		};

		require('md5');
		password = $.md5(password);

		$('#login').attr('disabled',"true");
		$.ajax({
			type: 'post',
			url: '/system/login/checkLogin',
			data: {
				loginNumber: username,
				pwd: password
			},
			dataType: 'json',
			success: function(data) {
				$('#login').removeAttr("disabled");
				//console.log(data);
				if ('0000000' === data.rtnCode) {
					require('cookie');
					$.cookie('bizData', data.bizData.token, {expires: 7,path:'/'});
					$.cookie('userInfo', JSON.stringify(data.bizData), {expires: 7,path:'/'});
					window.location.href = 'choose_system.html';
				} else {
					$('#error_tip').html(data.msg).show(500);
				}
			},
			error: function(data) {
				$('#error_tip').html('服务器异常！！！').show(500);
				$('#login').removeAttr("disabled");
			}
		});
	});

	$(document).keydown(function(e){
		var Event = jQuery.Event("click");
		if(e.keyCode == "13") {
			$("#login").trigger(Event);
		}
	});
});