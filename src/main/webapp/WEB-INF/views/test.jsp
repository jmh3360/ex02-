<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<title>Document</title>
	<script src="/resources/plugins/jQuery/jQuery-2.1.4.min.js"></script>
	<style>
		#modDiv{
			width: 300px;
			height: 100px;
			background-color: gray;
			position: absolute;
			top: 50%;
			left: 50%;
			margin-top: -50px;
			margin-left: -150px;
			padding: 10px;
			z-index: 1000;
			display: none;
		}
	</style>
</head>
<body>
	<h2>Ajax Text Page</h2>
	<div>
		REPLYER <input type="text" name="replyer" id="newReplyWriter" />
	</div>
	<div>
		REPLY TEST <input type="text" name="replytext" id="newReplyText" />
	</div>
	<button id="replyAddBtn">ADD REPLY</button>
	<ul id="replies">
	</ul>
	<ul class="pagination">
		
	</ul>
	<div id="modDiv">
		<div class="modal-title"></div>
		<div>
			<input type="text" id="replytext" />
		</div>
		<div>
		<button type="button" id="replyModBtn">Modify</button>
		<button type="button" id="replyDelBtn">DELETE</button>
		<button type="button" id="closeBtn">Close</button>
		</div>
	</div>
</body>
<script>

 	var bno = 459739;
 	var reply = 1;
 	$('.pagination').on("click","li a",function(e){
 		e.preventDefault();
 		replyPage = $(this).attr("href");
 		getPageList(replyPage);
 	});
	$(function(){
		getPageList(1);		
	});
 	$('#replyAddBtn').click(function(){
 		var replyer = $('#newReplyWriter').val();
 		var replytext = $('#newReplyText').val();
 		
 		$.ajax({
 			type : 'post',
 			url : '/replies',
 			headers : {
 				"Content-Type"  : "application/json",
 				"X-HTTP-Method-Override" : "POST"
 			},
 			dataType : 'text',
 			data : JSON.stringify({bno:bno,replyer:replyer,replytext:replytext}),
 			success :  function(result){
 				if (result == "SUCCESS") {
					alert("등록 되었습니다.");
					getAllList();
				}
 				
 			}
 		});
 		
 	});
 	  
 	function getAllList(){
 		
 		$.getJSON("/replies/all/"+bno,function(data){
 	 		
 	 		var str  = "";
 	 		
 	 		$(data).each(function(){
 	 			str +=  "<li data-rno='"+this.rno+"' class='replyLi'>"
 	 				+	this.rno + ":" + this.replytext
 	 				+  	"<button>MOD</button></li>"
 	 		});
 	 		$('#replies').html(str);
 	 	});
 	}
 	
 	
 	/* 객체 접근 방식 $('#replies li button').on('click' 이런 형태의 객체 접근방식을 사용 했을 때
 		현재 html 소스 내에는 ul 태그만 그려져 있고 li 태그는 그려져 있지 않아 이벤트를  할당하는 데 문제가 있음
 		그래서 다음과 같은 방식으로 수정 필요
 		$('#replies').on('click','.replyLi button') 신기방기
 			*/
 	$('#replies').on('click','.replyLi button',function(){
 		var replyLi = $(this).parent();
 		var rno = replyLi.attr("data-rno");
 		var replytext = replyLi.text();
 		$('.modal-title').html(rno);
 		$('#replytext').val(replytext);
 		$('#modDiv').show('slow');
 	});
 	$('#replyDelBtn').on('click',function(){
 		var rno = $('.modal-title').html();
 		var replytext = $('replytext').val();
 		
 		$.ajax({
 			type : 'delete',
 			url : '/replies/' + rno,
 			headers : {
 				"Content-Type" : "application/json",
 				"X-HTTP-Method-Override" : "DELETE"
 			},
 			dataType:"text",
 			success : function(result){
 				console.log("result : "+result);
 				if (result == 'SUCCESS') {
					alert('삭제되었습니다.');
					$('#modDiv').hide('slow');
					getAllList();
				}
 			}
 			
 		});
 	});
 	$('#replyModBtn').on('click',function(){
 		var rno = $('.modal-title').html();
 		var replytext = $('#replytext').val();
 		
 		$.ajax({
 			type : 'put',
 			url : '/replies/' + rno,
 			headers : {
 				"Content-Type" : "application/json",
 				"X-HTTP-Method-Override" : "PUT"
 			},
 			data:JSON.stringify({replytext:replytext}),
 			dataType:"text",
 			success : function(result){
 				console.log("result : "+result);
 				if (result == 'SUCCESS') {
					alert('수정되었습니다.');
					$('#modDiv').hide('slow');
					/* getAllList(); */
					getPageList(1);
				}
 			}
 			
 		});
 	});
 	function getPageList(page){
 		$.getJSON("/replies/"+bno+"/"+page, function(data){
 			console.log(data.list.length);
 			
 			var str = "";
 			
 			$(data.list).each(function(){
 				str +=  "<li data-rno='"+this.rno+"' class='replyLi'>"
 	 				+	this.rno + ":" + this.replytext
 	 				+  	"<button>MOD</button></li>"
 	 		});
 	 		$('#replies').html(str);	
 	 		
 	 		printPaging(data.pageMaker);
 		});
 	function printPaging(pageMaker){
 		var str ="";
 		
 		if (pageMaker.prev) {
			str += "<li><a href='"+(pageMaker.startPage+1 )+"'> << </a></li>"
		}
 		for (var i = pageMaker.startPage,len=pageMaker.endPage;i <= len; i++) {
			var startClass = pageMaker.cri.page == i ? 'class=active':'';
			str += "<li "+startClass+"><a href='"+i+"'>"+i+"</a></li>";
		}
 		if (pageMaker.next) {
			str += str += "<li><a href='"+(pageMaker.endPage-1 )+"'> >> </a></li>"
		}
 		$('.pagination').html(str);
 	}
 	}
 	
</script>
</html>
 
