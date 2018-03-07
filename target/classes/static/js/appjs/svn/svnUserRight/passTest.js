$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		passTest();
	}
});

function passTest() {
	$.ajax({
		cache : false, //cache的作用是第一次请求完毕之后，如果再次去请求，可以直接从缓存里面读取而不是再到服务器端读取。数据长时间不变时，可以设置为true。
		type : "POST",
		url : "/svn/admin/svnRightPassTest", //写一个测试SVN用户权限连通性的接口
		data : $('#svnUserRightPassTestForm').serialize(),// 你的formid
		beforeSend : beforeSend, //用于在向服务器发送请求之前执行显示进度条
		async : true, //异步
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg(data.msg);
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);
			} else {
				parent.layer.alert(data.msg);
			}
		},
		complete : complete
	});
}

function beforeSend(XMLHttpRequest) {
	// 禁用按钮防止重复提交
	$("#submit").attr({disabled : "disabled"});
	// 显示进度条
	$("#showLoading").append("<div><img src='/img/loading-bar.gif' /><div>");
}

function complete(XMLHttpRequest, textStatus) {
	// 取消禁用按钮
	$("#submit").removeAttr("disabled");
	// 隐藏进度条
	$("#showLoading").remove();
}

function validateRule() {
	//var icon = "<i class='fa fa-times-circle'></i> ";
	$("#svnUserRightPassTestForm").validate({
		rules : {
			/*svnRepoName : {
				required : true
			},
			svnRepoUrl : {
				required : true
			},
			svnUserName : {
				required : true
			},
			svnPath : {
				required : true
			}*/
		},
		messages : {
			/*svnRepoName : {
				required : icon + "请输入需要测试联通性的SVN仓库名"
			},
			svnRepoUrl : {
				required : icon + "请输入需要测试联通性的SVN仓库URL"
			},
			svnUserName : {
				required : icon + "请输入SVN用户名"
			},
			svnPath : {
				required : icon + "请输入需要测试联通性的SVN权限路径"
			}*/
		}
	});
}

