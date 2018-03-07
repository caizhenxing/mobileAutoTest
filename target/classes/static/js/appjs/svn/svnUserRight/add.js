$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});

function save() {
	$.ajax({
		cache : true, //cache的作用是第一次请求完毕之后，如果再次去请求，可以直接从缓存里面读取而不是再到服务器端读取。数据长时间不变时，可以设置为true。
		type : "POST",
		url : "/svn/svnUserRight/addSvnUserRight",
		data : $('#svnUserRightAddForm').serialize(),// 你的formid
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
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#svnUserRightAddForm").validate({
		rules : {
			svnRepoName : {
				required : true
			},
			svnUserName : {
				required : true
			},
			svnPath : {
				required : true
			},
			svnUserAuthz : {
				required : true
			}
		},
		messages : {
			svnRepoName : {
				required : icon + "请输入SVN仓库名"
			},
			svnUserName : {
				required : icon + "请输入SVN用户名"
			},
			svnPath : {
				required : icon + "请输入需要开通权限的SVN路径"
			},
			svnUserAuthz : {
				required : icon + "请输入需要添加的权限(r-仅可读, rw-可读可写)"
			}
		}
	});
}

function openSvnPath() {
	var svnRepoName = $('#svnRepoName').val();
	layer.open({
		type:2,
		title: "请选择需要开通权限的SVN路径(SVN树形结构解析时间较长，请耐心等待...)",
		area : [ '700px', '650px' ],
		content: "/svn/svnUserRight/treeView/" + svnRepoName
	});
};

function loadSvnPath(svnPath) {
	$("#svnPath").val(svnPath);
}
