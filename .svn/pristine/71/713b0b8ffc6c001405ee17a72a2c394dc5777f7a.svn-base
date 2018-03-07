var prefix = "/executePlan";
$(document).ready(function() {
	$('#executePlanList').hide();
	load();
});

function load() {
	$('#executePlanTable')
		.bootstrapTable(
			{
				method : 'get', // 服务器数据的请求方式 get or post
				url : prefix + "/list", // 测试任务数据的加载地址
				showRefresh : true,
				// showToggle : true,
				showColumns : true,
				iconSize : 'outline',
				toolbar : '#exampleToolbar',
				striped : true, // 设置为true会有隔行变色效果
				dataType : "json", // 服务器返回的数据类型
				pagination : true, // 设置为true会在底部显示分页条
				// queryParamsType : "limit",
				// //设置为limit则会发送符合RESTFull格式的参数
				singleSelect : false, // 设置为true将禁止多选
				// contentType : "application/x-www-form-urlencoded",
				// //发送到服务器的数据编码类型
				pageSize : 10, // 如果设置了分页，每页数据条数
				pageNumber : 1, // 如果设置了分布，首页页码
				// search : true, // 是否显示搜索框
				showColumns : false, // 是否显示内容下拉框（选择显示的列）
				sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者
				// "server"
				queryParams : function(params) {
					return {
						// 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
						limit : params.limit,
						offset : params.offset,
						taskName : $('#searchName').val(),
					};
				},
				// 请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
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
						field : 'Number', // 列字段名
						title : '序号', // 列标题
						formatter : function(value,row,index){
							return index + 1;
						},
						align : 'center'
					},
					{
						field : 'taskName',
						title : '测试任务名称',
						align : 'center'
					},
					{
						field : 'deptName',
						title : '所属产品名称',
						align : 'center'
					},
					{
						field : 'batchName',
						title : '所属批次名称',
						align : 'center'
					},
					{
						field : 'scripts',
						title : '关联脚本',
						align : 'center',
						formatter : function(value, row, index) {
							var e = '<button class=".btn" onclick="showScript(' + row.id + ')">查看</button>';
							return e;
						}
					},
					{
						field : 'triggerMode',
						title : '触发方式',
						align : 'center',
						formatter : function(value, row, index) {
							if (value == '0') {
								return '<span class="label label-danger">立即触发</span>';
							} else if (value == '1') {
								return '<span class="label label-primary">定时触发</span>';
							}
						}
					},
					{
						field : 'condition',
						title : '触发条件',
						align : 'center'
					},
					{
						field : 'status',
						title : '状态',
						align : 'center',
						formatter : function(value, row, index) {
							if (value == '0') {
								return '<span class="label label-danger">空闲</span>';
							} else if (value == '1') {
								return '<span class="label label-primary">执行中</span>';
							}
						}
					},
					{
						field : 'deviceType',
						title : '执行设备',
						align : 'center',
						formatter : function(value, row, index) {
							if (value == '0') {
								return '<span>Android</span>';
							} else if (value == '1') {
								return '<span>IOS</span>';
							}
						}
					},
					{
						title : '操作',
						field : 'id',
						align : 'center',
						formatter : function(value, row, index) {
							var e = '<a  class="btn btn-primary btn-sm ' + s_edit_h + '" href="#" mce_href="#" title="编辑" onclick="edit(\''
								+ row.id
								+ '\')"><i class="fa fa-edit "></i></a> ';
							var d = '<a class="btn btn-warning btn-sm ' + s_remove_h + '" href="#" title="删除"  mce_href="#" onclick="remove(\''
								+ row.id
								+ '\')"><i class="fa fa-remove"></i></a> ';
							var f = '<a id = "active" class="btn btn-warning btn-sm ' + s_active_h + '" href="#" title="执行"  mce_href="#" onclick="active(\''
							+ row.id
							+ '\')"><i class="fa fa-paper-plane-o"></i></a> ';
							return e + d + f;
						}
					} ]
			});
}
function reLoad() {
	$('#executePlanTable').bootstrapTable('refresh');
}
function showScript(id) {
	layer.open({
		type : 2,
		title : '关联场景',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '600px', '620px' ],
		content : '/script/showScript/' + id
	});
}
function add() {
	// iframe层
	var addHtml = layer.open({
		type : 2,
		title : '添加任务',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/add'
	});
	layer.full(addHtml);
}
function remove(id) {
	layer.confirm('确定要删除选中的记录？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : "/executePlan/remove",
			type : "post",
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
	})
}
function edit(id) {
	var addHtml = layer.open({
		type : 2,
		title : '任务修改',
		maxmin : true,
		shadeClose : false,
		area : [ '800px', '520px' ],
		content : prefix + '/edit/' + id // iframe的url
	});
	layer.full(addHtml);
}
function active(id) {
	
	$.ajax({
		type : 'POST',
		data : {
			"id" : id
		},
		url : prefix + '/active',
		success : function(r) {
			layer.msg(r);
			reLoad();
			// TODO 置灰这个A标签
		}
	});
}
function batchRemove() {
	var rows = $('#executePlanTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
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