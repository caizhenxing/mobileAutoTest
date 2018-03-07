var prefix = "/svn/svnCreateBranch";
	
$(function() {
	load();
});

function load() {
	$('#svnCreateBranchTable')
		.bootstrapTable(
			{
				method : 'get', // 服务器数据的请求方式 get or post
				url : prefix + "/list", // 服务器数据的加载地址
				showRefresh : true,
				showToggle : true,
				// showColumns : true,
				iconSize : 'outline',
				toolbar : '#exampleToolbar',
				striped : true, // 设置为true会有隔行变色效果
				dataType : "json", // 服务器返回的数据类型
				pagination : true, // 设置为true会在底部显示分页条
				queryParamsType : "limit",
				// //设置为limit则会发送符合RESTFull格式的参数
				singleSelect : false, // 设置为true将禁止多选
				// contentType : "application/x-www-form-urlencoded",
				// //发送到服务器的数据编码类型
				pageSize : 10, // 如果设置了分页，每页数据条数
				pageNumber : 1, // 如果设置了分布，首页页码
				// search : true, // 是否显示搜索框
				showColumns : true, // 是否显示内容下拉框（选择显示的列）
				sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者
				// "server"
				queryParams : function(params) {
					return {
						// 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
						limit : params.limit,
						offset : params.offset,
						svnRepoName : $('#searchSvnRepoName').val()
					};
				},
				// //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar中的参数 如果
				// queryParamsType = 'limit' ,返回参数必须包含
				// limit, offset, search, sort, order 否则, 需要包含:
				// pageSize, pageNumber, searchText, sortName,
				// sortOrder.
				// 返回false将会终止请求
				columns : [
					{
						checkbox : true,
						align : 'center'
					},
					{
						field : 'id', // 列字段名
						title : '序号', // 列标题
						align : 'center'
					},
					{
						field : 'deptName',
						title : '所属产品',
						align : 'center'
					},
					{
						field : 'batchName',
						title : '所属批次',
						align : 'center'
					},
/*					{
						field : 'svnRepoId',
						title : 'SVN仓库ID',
						align : 'center'
					},*/
					{
						field : 'svnRepoName',
						title : 'SVN仓库名',
						align : 'center'
					},
					{
						field : 'svnRepoPath',
						title : 'SVN仓库路径',
						align : 'center'
					},
					{
						field : 'svnRepoUrl',
						title : 'SVN仓库URL',
						align : 'center'
					},
/*					{
						field : 'svnRepoDes',
						title : 'SVN仓库描述',
						align : 'center'
					},*/
					{
						field : 'svnTrunk',
						title : 'SVN分支基线',
						align : 'center'
					},
					{
						field : 'newBranch',
						title : 'SVN新分支',
						align : 'center'
					},
					{
						field : 'createBranchComment',
						title : 'SVN新建分支备注信息',
						align : 'center'
					},
					{
						field : 'createDate',
						title : '创建时间',
						align : 'center'
					},
					{
						field : 'modifyDate',
						title : '修改时间',
						align : 'center'
					},
					{
						title : '操作',
						align : 'center',
						formatter : function(value, row, index) {
							var e = '<a  class="btn btn-primary btn-sm ' + s_edit_h + '" href="#" mce_href="#" title="编辑" onclick="edit(\''
								+ row.id
								+ '\')"><i class="fa fa-edit"></i></a> ';
							var d = '<a class="btn btn-warning btn-sm ' + s_remove_h + '" href="#" title="删除"  mce_href="#" onclick="remove(\''
								+ row.id
								+ '\')"><i class="fa fa-remove"></i></a> ';
							return e + d;
						}
					} ]
			});
}

function reLoad() {
	$('#svnCreateBranchTable').bootstrapTable('refresh');
}

function add() {
	// iframe层
	layer.open({
		type : 2,
		title : '新建SVN分支',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '900px', '720px' ],
		content : prefix + '/add'
	});
}

function edit(id) {
	layer.open({
		type : 2,
		title : '修改SVN新建分支信息',
		maxmin : true,
		shadeClose : false,
		area : [ '900px', '720px' ],
		content : prefix + '/edit/' + id // iframe的url
	});
}

function remove(id) {
	layer.confirm('确定要删除选中的记录？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : prefix + "/remove",
			type : "POST",
			data : {
				'id' : id
			},
			success : function(r) {
				if (r.code == 0) {
					layer.msg(r.msg);
					reLoad();
				} else {
					layer.msg(r.msg);
				}
			}
		});
	});
}


function batchRemove() {
	var rows = $('#svnCreateBranchTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
	if (rows.length == 0) {
		layer.msg("请选择要删除的数据");
		return;
	}
	layer.confirm("确认要删除选中的'" + rows.length + "'条数据吗?", {
		btn : [ '确定', '取消' ]
	// 按钮
	}, function() {
		var ids = new Array();
		// 遍历所有选择的行数据，取每条数据对应的ID
		$.each(rows, function(i, row) {
			ids[i] = row['id'];
		});
		$.ajax({
			type : 'POST',
			data : {
				"ids" : ids
			},
			url : prefix + '/batchRemove',
			success : function(r) {
				if (r.code == 0) {
					layer.msg(r.msg);
					reLoad();
				} else {
					layer.msg(r.msg);
				}
			}
		});
	}, function() {});
}