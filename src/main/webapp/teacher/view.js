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
	var tags = $('.new-form');
	tags.css('display','none');
	
	$.getJSON('detail.json?memberNo=' + memberNo, function(ajaxResult) {
		var status = ajaxResult.status;
		
		if (status != "success") {
			alert(ajaxResult.data);
			return;
		}
		
		var teahcer = ajaxResult.data;

		$('#email').val(teahcer.email);
		$('#name').val(teahcer.name);
		$('#tel').val(teahcer.tel);
		$('#homepage').val(teahcer.homepage);
		$('#facebook').val(teahcer.facebook);
		$('#twitter').val(teahcer.twitter);
		try {$('#photo-img1').attr('src', "../upload/" + teahcer.photoList[0].filePath);} catch (error) {};
		try {$('#photo-img2').attr('src', "../upload/" + teahcer.photoList[1].filePath);} catch (error) {};
		try {$('#photo-img3').attr('src', "../upload/" + teahcer.photoList[2].filePath);} catch (error) {};
	});
	
	// 삭제 버튼을 클릭 했을 떄 호출될 함수(클릭 이벤트 핸들러) 등록
	$('#delete-btn').click(function() {
		$.getJSON('delete.json?memberNo=' + memberNo, function(ajaxResult) {
			if (ajaxResult.status != "success") {
				alert(ajaxResult.data);
				return;
			}
			location.href = 'main.html';
		}); // getJSON()
	}); // click()
	
	// 변경 버튼을 클릭 했을 떄 호출될 함수(클릭 이벤트 핸들러) 등록
	$('#update-btn').click(function() {
	  var param = {
		"memberNo": memberNo,
		"email": $('#email').val(),
		"name": $('#name').val(),
		"tel": $('#tel').val(),
		"homepage": $('#homepage').val(),
		"facebook": $('#facebook').val(),
		"twitter": $('#twitter').val()
	  };
	  
	  $.post('update.json', param, function(ajaxResult) {
		if (ajaxResult.status != "success") {
			alert(ajaxResult.data);
			return;
		}
		location.href = 'main.html';
	  }, 'json');
	}); // click()
} // prepareViewForm()


function prepareNewForm() {
	$('.view-form').css('display', 'none');

	$('#add-btn').click(function() {
		var param = {
			"email": $('#email').val(),
			"name": $('#name').val(),
			"tel": $('#tel').val(),
			"homepage": $('#homepage').val(),
			"facebook": $('#facebook').val(),
			"twitter": $('#twitter').val()
		};
		
		$.post('add.json', param, function(ajaxResult) {
			if (ajaxResult.status != "success") {
			  alert(ajaxResult.data);
			  return;
			}
			location.href = 'main.html';
		}, 'json'); // post()
	}); // click()
}

//목록 버튼을 클릭했을 때 호출될 함수(이벤트 핸들러) 등록!
$('#list-btn').click(function() {
	location.href = 'main.html';
}); // click()