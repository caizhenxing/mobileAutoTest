$(document).ready(function() {
	validateRule();
});
$.validator.setDefaults({
	submitHandler : function() {
		updateScript();
	}
});
function updateScript() {
	$.ajax({
		cache : true,
		type : "POST",
		url : "/script/updateScripts",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("保存失败，请联系管理员！");
		},
		success : function(data) {
			if (data.code == 0) {
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);
				parent.layer.msg("操作成功");
				parent.reLoad();
			} else {
				parent.layer.alert(data.msg);
			}
		}
	});
}
$(validateRule());
function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	return $("#signupForm").validate({
		rules : {
			taskName : {
				required : true
			},
			scriptNames : {
				required : true
			},
			svnPath : {
				required : true
			},
			batchName : {
				required : true
			},
			batchSvnPath : {
				required : true
			},
			agree : "required"
		},
		messages : {
			taskName : {
				required : icon + "请输入测试任务名称"
			},
			scriptNames : {
				required : icon + "请选择关联脚本"
			},
			svnPath : {
				required : icon + "请输入测试任务的svn路径"
			},
			batchName : {
				required : icon + "请选择所属批次"
			},
			batchSvnPath : {
				required : icon + "请输入SVN批次分支名称"
			},
		}
	})
}
function openDept(){
	layer.open({
		type:2,
		title:"选择部门",
		area : [ '300px', '450px' ],
		content:"/task/showDepts"
	})
}
function loadDept( deptId,deptName){
	$("#deptId").val(deptId);
	$("#deptName").val(deptName);
	$.ajax({
		type : "post",
		url : "/task/getSvnPath",// 获得svn库的树形结构路径
		data : {
			'deptId' : deptId
		},
		success : function(data) {
			if(data.code != 0){
				parent.layer.alert(data.msg);
			} else {
				$("#svnPath").val(data.msg);
			}
		}
	});
}