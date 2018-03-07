// 以下为官方示例
$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		update();
	}
});

function update() {
	$.ajax({
		cache : true,
		type : "POST",
		url : "/sys/config/update",
		data : $('#configEditForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg(data.msg);
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				parent.layer.msg(data.msg);
			}

		}
	});

}

function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#configEditForm").validate({
		rules : {
			svnServer : {
				required : true,
				isSvnServer: true
			},
			svnServerUserName : {
				required : true
			},
			svnServerPassword : {
				required : true
			},
			confirm_password : {
				required : true,
				equalTo : "#svnServerPassword"
			},
			svnConfFilesLocation : {
				required : true
			},
			svnRootUserName : {
				required : true
			}
		},
		messages : {
			svnServer : {
				required : icon + "请输入SVN服务器IP地址"
			},
			svnServerUserName : {
				required : icon + "请输入SVN服务器用户名"
			},
			svnServerPassword : {
				required : icon + "请输入SVN服务器登录口令"
			},
			confirm_password : {
				required : icon + "请再次输入SVN服务器登录口令",
				equalTo : icon + "两次输入的密码不一致"
			},
			svnConfFilesLocation : {
				required : icon + "请输入SVN配置文件临时存放位置"
			},
			svnRootUserName : {
				required : icon + "请输入超级用户的SVN用户名"
			}
		}
	});
}

//校验svn的服务器的IP
$.validator.addMethod("isSvnServer",function(value,element){
	var isSvnServer = /\d+\.\d+\.\d+\.\d/;
	return this.optional(element) || (isSvnServer.test(value));
},"请填写正确的SVN服务器IP地址");
