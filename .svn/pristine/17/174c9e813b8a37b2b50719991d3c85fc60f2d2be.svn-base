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
		cache : true,
		type : "POST",
		url : "/svn/svnRepo/addSvnRepo",
		data : $('#svnRepoAddForm').serialize(),// 你的formid
		beforeSend : beforeSend, //用于在向服务器发送请求之前执行显示进度条
		async : true, //异步
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("操作成功");
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
	$("#svnRepoAddForm").validate({
		rules : {
			svnRepoName : {
				required : true,
				checkSvnRepoName : true
			},
			svnRepoPath : {
				required : true
			},
			svnRepoUrl : {
				required : true,
				isSvnUrl : true
			}
		},
		messages : {
			svnRepoName : {
				required : icon + "请输入SVN仓库名称"
			},
			svnRepoPath : {
				required : icon + "请输入SVN仓库路径"
			},
			svnRepoUrl : {
				required : icon + "请输入SVN仓库URL (eg. svn://22.11.31.40/BMTC)"
			}
		}
	});
}

//校验svn仓库名
$.validator.addMethod("checkSvnRepoName",function(value,element){
	var checkSvnRepoName = /^[0-9a-zA-Z_]+$/;
	return this.optional(element) || (checkSvnRepoName.test(value));
},"SVN仓库名不能包含特殊字符");


//校验svn的url
$.validator.addMethod("isSvnUrl",function(value,element){
	var svnUrl = /(^svn):\/\/+\d+\.\d+\.\d+\.\d+\/[A-Za-z0-9_]/;//以svn://开头
	return this.optional(element) || (svnUrl.test(value));
},"请填写正确的SVN的URL(eg. svn://22.11.31.40/BMTC)");
