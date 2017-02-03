try {
	var memberNo = location.href.split('?')[1].split('=')[1];
} catch (error) {
	var memberNo = -1;
}

if (memberNo > 0) {
	prepareViewForm();
} else {
	prepareNewForm();
}

function prepareViewForm() {
	// 등록 버튼은 감춘다.
	var tags = $('.new-form')
	tags.css('display', 'none');

	$.getJSON('detail.json?memberNo=' + memberNo, function(ajaxResult) {
		var status = ajaxResult.status;
		
		if (status != "success") {
			alert(ajaxResult.data);
			return;
		}
		
		var manager = ajaxResult.data;
		
		$('#email').val(manager.email);
		$('#name').val(manager.name);
		$('#position').val(manager.position);
		$('#tel').val(manager.tel);
		$('#fax').val(manager.fax);
		$('#photo-img').attr('src', "../upload/" + manager.photoPath);
	});
	
	// 삭제 버튼 핸들러 등록
	$('#delete-btn').click(function() {
		$.getJSON('delete.json?memberNo=' + memberNo, function(ajaxResult) {
			if (ajaxResult.status != "success") {
				alert(ajaxResult.data);
				return;
			}
			location.href = 'main.html';
		});
	});
	
	$('#update-btn').click(function() {
		var param = {
			"memberNo": memberNo,
			"name": $('#name').val(),
			"tel": $('#tel').val(),
			"email": $('#email').val(),
			"password": $('#password').val(),
			"position": $('#position').val(),
			"fax": $('#fax').val()
		}
	
		$.post('update.json', param, function(ajaxResult) {
			if (ajaxResult.status != "success") {
				alert(ajaxResult.data);
				return;
			}
			location.href = 'main.html';
		}, 'json');
	});
}

function prepareNewForm() {
	// 변경, 삭제 버튼은 감춘다.
	var tags = $('.view-form');
	tags.css('display', 'none');
	
	// 추가 버튼을 클릭 했을 떄 호출될 함수(클릭 이벤트 핸들러) 등록
	$('#add-btn').click(function() {
		var param = {
			"name": $('#name').val(),
			"tel": $('#tel').val(),
			"email": $('#email').val(),
			"password": $('#password').val(),
			"position": $('#position').val(),
			"fax": $('#fax').val()
		};
		
		$.post('add.json', param, function(ajaxResult) {
			if (ajaxResult.status != "success") {
			  alert(ajaxResult.data);
			  return;
			}
			location.href = 'main.html';
		}, 'json');
	});
}

//목록 버튼을 클릭했을 때 호출될 함수(이벤트 핸들러) 등록!
$('#list-btn').click(function() {
	location.href = 'main.html';
});