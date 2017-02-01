//학생 목록 가져와서 tr 태그를 만들어 붙인다.
get('list.json', function(jsonText) {
  //result JSON 문자열을 자바스크립트 객체로 만든다.
  var ajaxResult = JSON.parse(jsonText);
  var status = ajaxResult.status;
  
  if (status != "success")
	  return;
  
  var list = ajaxResult.data;
  var tbody = document.querySelector('#list-table > tbody');
  
  for (var student of list) {
	  var tr = document.createElement("tr");
	  tr.innerHTML = "<tr><td>" + 
	    student.memberNo + "</td><td><a class='name-link' href='#' data-no='" + 
	    student.memberNo + "'>" + 
	    student.name + "</a></td><td>" + 
	    student.tel + "</td><td>" + 
	    student.working + "</td><td>" +
	    student.grade + "</td><td>" +
	    student.schoolName + "</td></tr>";
	  tbody.appendChild(tr);  
  }
  
	// 학생 목록에서 이름 링크에 click 이벤트를 처리한다.
	var al = document.querySelectorAll('.name-link');
	for (var a of al) {
		a.onclick = function(event) {
			event.preventDefault();
			location.href = 'view.html?memberNo=' + this.getAttribute("data-no");
		}
	}
});

// 추가 버튼에 클릭 이벤트 핸들러 등록하기
document.querySelector('#new-btn').onclick = function(event) {
	// a 태그를 클릭하면 기본으로 href에 설정된 url을 요청한다.
	// 이 기본 행동을 막아야 한다.
	event.preventDefault()
	
	// 다음과 같이 자바스크립트 명령으로 화면을 이동하면,
	// 캐시된 파일이 로딩되지 않고 정상적으로 자바스크립트를 실행한다.
	location.href='view.html';
};