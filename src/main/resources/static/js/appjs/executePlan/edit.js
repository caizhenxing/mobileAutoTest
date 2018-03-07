var testSuiteCaseNames;
$(document).ready(function() {
	$("#trigger").hide();
	$("#part1").show();
	$("#part2").hide();
	var testSuiteCaseNames = '';
	validateRule();
});
$.validator.setDefaults({
	submitHandler : function() {
		update();
	}
});
function update() {
	getAllSelectNodes();
	$.ajax({
		cache : true,
		type : "POST",
		url : "/executePlan/update",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("修改失败，请联系管理员！");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("操作成功");
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);
			} else {
				parent.layer.alert(data.msg)
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);
			}

		}
	});

}
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
		}
	})
}
function showTrigger(){
	$("#trigger").show();
}
function hidTrigger(){
	$("#trigger").hide();
}
function toStep12() {
	if(validateRule().form()){
		var taskId = $("#taskId").val();
		getTreeData(taskId);
		$("#part1").hide();
		$("#part2").show();
	}
}
function toStep21() {
	$("#jstree").jstree("destroy");
	$("#part1").show();
	$("#part2").hide();
}
function showTaskList() {
	layer.open({
		type : 2,
		title : '关联场景',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '500px', '520px' ],
		content : '/task/showTaskList'
	});
}
function loadTask(taskId,taskName){
	$("#taskId").val(taskId);
	$("#taskName").val(taskName);
}
function getTreeData(taskId) {
	$.ajax({
		type : "post",
		url : "/task/getTreeData",// 获得svn库的树形结构路径
		data : {
			'taskId' : taskId
		},
		success : function(tree) {
			loadTree(tree);
		}
	});
}
function loadTree(tree) {
	$('#jstree').jstree({
		"plugins" : [ "wholerow", "checkbox" ],
		'core' : {
			'data' : tree
		},
		"checkbox" : {
		}
	});
	$('#jstree').jstree().close_all();
}
function getAllSelectNodes() {
	var ref = $('#jstree').jstree(true); // 获得整个树
	testSuiteCaseNames = ref.get_selected(); // 获得所有选中节点的，返回值为数组
	$("#testSuiteCaseNames").val(testSuiteCaseNames);
}