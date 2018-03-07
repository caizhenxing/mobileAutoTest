var menuIds;
$(function() {
	validateRule();
});
$.validator.setDefaults({
	submitHandler : function() {
		update();
	}
});
function update() {
	var batch = $('#signupForm').serialize();
	$.ajax({
		cache : true,
		type : "POST",
		url : "/sys/batch/update",
		data : batch, // 你的formid
		async : false,
		error : function(request) {
			alert("Connection error");
		},
		success : function(r) {
			if (r.code == 0) {
				parent.layer.msg(r.msg);
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				parent.layer.msg(r.msg);
			}

		}
	});
}
function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			batchName : {
				required : true
			},
			startDate : {
				required : true,
				dateISO : true
			},
			endDate : {
				required : true,
				dateISO : true
			},
			batchSvnPath : {
				required : true,
			}
		},
		messages : {
			batchName : {
				required : icon + "请输入产品名"
			}
		}
	});
}