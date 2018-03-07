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
		url : "/svn/svnCreateBranch/update",
		data : $('#svnCreateBranchEditForm').serialize(),// 你的formid
		beforeSend : beforeSend, //用于在向服务器发送请求之前执行显示进度条
		async : true, //异步
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
	$("#svnCreateBranchEditForm").validate({
		rules : {
			svnRepoName : {
				required : true
			},
			svnTrunk : {
				required : true
			},
			newBranch : {
				required : true
			}
		},
		messages : {
			svnRepoName : {
				required : icon + "请输入SVN仓库名称"
			},
			svnTrunk : {
				required : icon + "请输入需要新建SVN分支的基线URL(eg. svn://22.11.31.51/test/trunk)"
			},
			newBranch : {
				required : icon + "请输入SVN需要新建的分支URL(eg. svn://22.11.31.51/test/branch)"
			}
		}
	});
}

function openDept() {
	layer.open({
		type : 2,
		title : "请选择部门",
		closeBtn : 0,
		area : [ '300px', '450px' ],
		content : "/svn/svnCreateBranch/showDepts"
	});
}

function loadDept(deptId, deptName) {
	$("#deptId").val(deptId);
	$("#deptName").val(deptName);
}

var openBatch = function() {
	layer.open({
		type : 2,
		title : "请选择批次",
		closeBtn : 0,
		area : [ '300px', '450px' ],
		content:"/svn/svnCreateBranch/showBatch"
	});
};

function loadBatch(batchId, batchName) {
	$("#batchId").val(batchId);
	$("#batchName").val(batchName);
}