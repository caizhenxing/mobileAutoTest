$(document).ready(function() {
	var taskId = $("#taskId").val();
	getTreeData(taskId);
});
function getTreeData(taskId) {
	$.ajax({
		type : "GET",
		url : "/script/getTaskScriptTreeData/" + taskId,
		success : function(tree) {
			loadTree(tree);
		}
	});
}
function loadTree(tree) {
	if(tree == null){
		layer.msg("该测试任务尚未关联脚本！");
	} else {
		$('#scriptTree').jstree({
			'core' : {
				'data' : tree
			},
		});
		$('#scriptTree').jstree().open_all();
	}
}