$.getJSON('list.json', function(ajaxResult) {
var status = ajaxResult.status;

if (status != "success") {
	return;
}

var list = ajaxResult.data;
var tbody = $('#list-table > tbody');

for (var teacher of list) {
	$("<tr>")
		.html("<td>" + 
			teacher.memberNo + "</td><td><a class='name-link' href='#' data-no='" + 
			teacher.memberNo + "'>" + 
			teacher.name + "</a></td><td>" + 
			teacher.tel + "</td><td>" + 
			teacher.email + "</td><td>" + 
			teacher.homepage + "</td>")
		.appendTo(tbody);
}

// 학생 목록에서 이름 링크에 click 이벤트를 처리한다.
$('.name-link').click(function(event) {
		event.preventDefault();
		location.href = 'view.html?memberNo=' + $(this).attr("data-no");
	});
});

//추가 버튼에 클릭 이벤트 핸들러 등록하기
$('#new-btn').click(function(event) {
	event.preventDefault()
	location.href='view.html';
});