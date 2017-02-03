$.getJSON('list.json', function(ajaxResult) {
	var status = ajaxResult.status;
	
  if (status != "success")
	  return;
	
	var list = ajaxResult.data;
	var tbody = $('#list-table > tbody');
	
	for (var manager of list) {
		$("<tr>")
			.html("<td>" + 
				manager.memberNo + "</td><td><a class='name-link' href='#' data-no='" + 
				manager.memberNo + "'>" + 
				manager.name + "</a></td><td>" + 
				manager.position + "</td><td>" + 
				manager.tel + "</td>")
			.appendTo(tbody);
	}
	
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