$(document).ready(function() {
	getTreeData();
});
function getTreeData() {
	$.ajax({
		type : "GET",
		url : "/task/getTaskListTree",
		success : function(tree) {
			if(tree == null){
				
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);
			} else {
				loadTree(tree);
			}
		}
	});
}
function loadTree(tree) {
	$('#taskListTree').jstree({
		'core' : {
			'data' : tree
		},
	});
	$('#taskListTree').jstree().open_all();
	
}
$('#taskListTree').on("changed.jstree", function(e, data) {
	if(data.node.id!=-1){
		// parent.loadTask(data.node.id,data.node.text);
		var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
		parent.layer.close(index);
	}
});