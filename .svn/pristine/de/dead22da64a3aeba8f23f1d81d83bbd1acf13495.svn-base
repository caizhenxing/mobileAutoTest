$(document).ready(function() {
	var scriptId = $("#scriptId").val();
	getTreeData(scriptId);
});
function getTreeData(scriptId) {
	$.ajax({
		type : "GET",
		url : "/script/getCaseNameTree/" + scriptId,
		success : function(tree) {
			loadTree(tree);
		}
	});
}
function loadTree(tree) {
	$('#caseNameTree').jstree({
		'core' : {
			'data' : tree
		},
	});
	$('#caseNameTree').jstree().open_all();
}