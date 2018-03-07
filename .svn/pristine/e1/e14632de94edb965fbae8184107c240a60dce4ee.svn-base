var scriptNames,scriptIds;
$(document).ready(function() {
	getTreeData();
});
function getTreeData() {
	$.ajax({
		type : "GET",
		url : "/script/getScriptTree",
		success : function(tree) {
			loadTree(tree);
		}
	});
}
function loadTree(tree) {
	$('#scriptTree').jstree({
		'core' : {
			'data' : tree
		},
		"checkbox" : {
			"three_state" : true,
		},
		"plugins" : [ "wholerow", "checkbox" ]
	});
	$('#scriptTree').jstree().open_all();
}
function setScriptIds() {
	getAllSelectNodes();
	parent.loadScripts(scriptIds,scriptNames);
	var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
	parent.layer.close(index);
}
function getAllSelectNodes() {
	var ref = $('#scriptTree').jstree(true); // 获得整个树
	scriptIds = ref.get_selected(); // 获得所有选中节点的，返回值为数组
	$("#scriptTree").find(".jstree-undetermined").each(function(i, element) {
		scriptIds.push($(element).closest('.jstree-node').attr("scriptId"));
	});
}