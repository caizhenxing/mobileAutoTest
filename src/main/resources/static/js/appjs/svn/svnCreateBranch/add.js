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
		url : "/svn/svnCreateBranch/save",
		data : $('#svnCreateBranchAddForm').serialize(),// 你的formid
		beforeSend : beforeSend, //用于在向服务器发送请求之前执行显示进度条
		async : true, //异步
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 1) {
				var con = confirm(data.msg);
				// parent.layer.msg(data.msg);
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				if(con == true) parent.layer.close(index);
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
	$("#svnCreateBranchAddForm").validate({
		rules : {
			svnRepoName : {
				required : true
			},
			svnTrunk : {
				required : true,
				isSvnTrunk : true
			},
			newBranch : {
				required : true,
				isNewBranchSvnUrl : true,
				equalToSvnTrunk : true
			},
			createBranchComment : {
				checkCreateBranchComment : true
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
				required : icon + "请输入SVN需要新建的分支URL(eg. svn://22.11.31.51/test/branch)",
				equalToSvnTrunk : icon + "请输入与SVN基线同仓库的分支URL"
			}
		}
	});
}

//校验svn新建分支备注信息
$.validator.addMethod("checkCreateBranchComment",function(value,element){
	var checkCreateBranchComment = /^[0-9a-zA-Z_ ]+$/;
	return this.optional(element) || (checkCreateBranchComment.test(value));
},"SVN新建分支备注信息只能由大小写字母、数字、下划线、空格组成");


//校验svn基线的url
$.validator.addMethod("isSvnTrunk",function(value,element){
	var svnTrunk = /(^svn):\/\/+\d+\.\d+\.\d+\.\d+\/[A-Za-z0-9_]/;//以svn://开头
	return this.optional(element) || (svnTrunk.test(value));
},"请填写正确的SVN的URL(eg. svn://22.11.31.40/BMTC/trunk)");


//校验新建分支的svnUrl
$.validator.addMethod("isNewBranchSvnUrl",function(value,element){
	var isNewBranchSvnUrl = /(^svn):\/\/+\d+\.\d+\.\d+\.\d+\/[A-Za-z0-9_]/;//以svn://开头
	return this.optional(element) || (isNewBranchSvnUrl.test(value));
},"请填写与SVN基线同仓库的SVNURL(eg. svn://22.11.31.40/BMTC/branch)");


//校验新建分支的svnUrl与基线svnUrl是否属于同一个svn仓库
$.validator.addMethod("equalToSvnTrunk",function(){
	var svnTrunk = $('#svnTrunk').val();
	var newBranch = $('#newBranch').val();
	var svnTrunkTemp = [];
	var newBranchTemp = [];
	svnTrunk = svnTrunk.split("/");
	newBranch = newBranch.split("/");
	for(var i = 0; i < svnTrunk.length; i++) {
		svnTrunkTemp.push(svnTrunk[i]);
	}
	for(var i = 0; i < newBranch.length; i++) {
		newBranchTemp.push(newBranch[i]);
	}
	var len;
	if(svnTrunk.length > newBranch.length) {
		len = newBranch.length;
	} else {
		len = svnTrunk.length;
	}
	if(len >= 4) {
		len = 4;
	} else {
		return false;
	}
	for(var i = 0; i < len; i++) {
		if(newBranchTemp[i] != svnTrunkTemp[i]) return false;
	}
	return true;
},"请填写与SVN基线同仓库的SVNURL(eg. svn://22.11.31.40/BMTC/branch)");



var openSvnRepo = function() {
	layer.open({
		type:2,
		title:"选择SVN仓库",
		area : [ '300px', '450px' ],
		content:"/svn/svnCreateBranch/svnRepoTreeView"
	});
};

function loadSvnRepo(svnRepoId, svnRepoName) {
	$("#svnRepoId").val(svnRepoId);
	$("#svnRepoName").val(svnRepoName);
}

function openSvnTrunk() {
	var svnRepoName = $('#svnRepoName').val();
	if(svnRepoName == null || svnRepoName == "") {layer.alert('请先选择SVN仓库！');}
	layer.open({
		type:2,
		title: "请选择需要新建分支的基线SVN URL(SVN树形结构解析时间较长，请耐心等待...)",
		area : [ '700px', '650px' ],
		content: "/svn/svnCreateBranch/svnTrunkTreeView/" + svnRepoName
	});
};

function loadSvnTrunk(svnTrunk) {
	$("#svnTrunk").val(svnTrunk);
}

function openDept() {
	layer.open({
		type:2,
		title:"请选择部门",
		area : [ '300px', '450px' ],
		content:"/svn/svnCreateBranch/showDepts"
	});
}
function loadDept(deptId, deptName) {
	$("#deptId").val(deptId);
	$("#deptName").val(deptName);
}

var openBatch = function() {
	layer.open({
		type:2,
		title:"请选择批次",
		closeBtn : 0,
		area : [ '300px', '450px' ],
		content:"/svn/svnCreateBranch/showBatch"
	});
};

function loadBatch(batchId, batchName) {
	$("#batchId").val(batchId);
	$("#batchName").val(batchName);
}
