var taskId;
$().ready(function() {
	$("#part1").show();
	$("#part2").hide();
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
		url : "/task/update",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			alert("修改失败，请联系管理员！");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg(data.msg);
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);
			} else {
				parent.layer.msg(data.msg);
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);
			}

		}
	});

}
$(validateRule());
function openDept(){
	layer.open({
		type : 2,
		title : "选择部门",
		closeBtn : 0,
		area : [ '300px', '450px' ],
		content : "/task/showDepts"
	})
}
function loadDept(deptId,deptName){
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
function toStep12() {
	var batchId = $("#batchId").val();
	var deptName = $("#deptName").val();
	$.ajax({
		type : "POST",
		url : "/task/checkBatchName/",// 获得svn库的树形结构路径
		data : { // 要传递的数据
			'batchId' : batchId,
			'deptName' : deptName,
		},
		success : function(result) {
			if (result.code != 0) {
				parent.layer.msg(result.msg);
			} else {
				if(validateRule().form()){
					$("#part1").hide();
					$("#part2").show();
				}
			}
		}
	});
}
function toStep21() {
	$("#part1").show();
	$("#part2").hide();
}
function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	return $("#signupForm").validate({
		rules : {
			taskName : {
				required : true
			},
			deptName : {
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
			deptName : {
				required : icon + "请选择所属产品机构"
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
var openBatch = function(){
	layer.open({
		type : 2,
		title : "选择批次",
		closeBtn : 0,
		area : [ '300px', '450px' ],
		content:"/sys/batch/showBatch"
	});
};
function loadBatch(batchId,batchName){
	$("#batchId").val(batchId);
	$("#batchName").val(batchName);
	$.ajax({
		type : "post",
		url : "/task/getBatchSvnPath",// 获得svn库的树形结构路径
		data : {
			'batchId' : batchId
		},
		success : function(data) {
			if(data.code != 0){
				parent.layer.alert(data.msg);
			} else {
				$("#batchSvnPath").val(data.msg);
			}
		}
	});
}