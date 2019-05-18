<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@include file="../include/header.jsp" %>

<!-- Main content -->
 <section class="content">
 	<!-- lef column -->
 	<div class="row">
 		<div class="col-md-12">
 			<!-- general form element -->
 			<div class="box box-success">
 				<div class="box-header">
 					<h3 class="box-title">
 						ADD NEW REPLY
 					</h3>
 				</div>
 				<div class="box-body">
 					<label for="newReplyWriter"></label>
 					<input type="text" class="form-control" id="newReplyWriter" placeholder="USER ID" />
 					<label for="newReplyText"></label>
 					<input type="text" class="form-control" id="newReplyText" placeholder="REPLY TEXT" />
 				</div>
				<div class="box-footer">
					<input type="button" class="btn btn-primary" id="replyAddBtn" value="ADD REPLY"/>
					
				</div>
 			</div>
 			<div class="box">
 				<div class="box-header with-border">
 						<h3 class="box-title">READ BOARD</h3>
 				</div>
 				<div class="box-body">
 					<div class="form-group">
						<label for="exampleInputEmail1">Title</label>
						<input type="text" name="title" class="form-control" 
						value='${boardVO.title}' readonly="readonly" />
					</div>
					<div class="form-group">
						<label for="exampleInputPassword1">Content</label>
						<textarea class="form-control" name="content" rows="3" 
						readonly="readonly">${boardVO.content}</textarea>
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">Writer</label>
						<input type="text" name="writer" class="form-control" 
						value='${boardVO.writer}' readonly="readonly" />
					</div>
 				</div>
 				<div class="box-footer">
 					<button type="submit" class="btn btn-warning">Modify</button>
 					<button type="submit" class="btn btn-danger">REMOVE</button>
 					<button type="submit" class="btn btn-primary list">GO LIST</button>
 				</div>
 				<!-- The Time line -->
 				<ul class="timeline">
 					<!-- timeline time label -->
 					<li class="time-label" id="repliesDiv">
	 					<span class="bg-green">Replies  List</span>
	 				</li>
 				</ul>
 				
 				<div class="text-center">
 					<ul id="pagination" class="pagination pagination-sm no-margin">
 					</ul>
 				</div>
				<form role="form" action="modifyPage" method="post">
					<input type="hidden" name="bno" value='${boardVO.bno}' />
					<input type="hidden" name="page" value='${cri.page}' />
					<input type="hidden" name="perPageNum" value='${cri.perPageNum}' />
					<input type="hidden" name="searchType" value='${cri.searchType}' />
					<input type="hidden" name="keyword" value='${cri.keyword}' />
				</form>
 			</div>
 		</div>
 	</div>
 </section>
 <script id="template" type="text/x-handlerbars-template">
		  {{#each .}}
      <li class="replyLi" data-rno={{rno}}>
        <i className="fa fa-comments bg-blue"></i>
        <div className="timeline-item">
          <span className="time">
            <i className="fa fa-clock-o"></i>{{prettifyDate regdate}}
          </span>
          <h3 className="timeline-header">
            <strong>{{rno}}</strong>
            -{{replyer}}
          </h3>
          <div className="timeline-body">{{replytext}}</div>
          <div className="timeline-footer">
            <a className="btn btn-primary btn-xs" data-toggle="modal" data-target="#modifyModal">Modify</a>
          </div>
        </div>
      </li>	
      {{/each}}
</script>
 <script>
 	
	 var bno =${boardVO.bno};
	 var replyPage = 1;
	 $("#repliesDiv").on('click',function(){
		 if ($('.timeline li').size() > 1) return;
		 getPage("/replies/"+bno+"/1");
	 });
	 $('.pagination').on('click',"li a",function(e){
		 e.preventDefault();
		 replyPage = $(this).attr("href");
		 getPage("/replies/"+bno+"/"+replyPage);
	 });
	 $('#replyAddBtn').click(function(e){
		 e.preventDefault();
		 alert("왜 형 빡치게하니  또?");
		 var replyerObj = $('#newReplyWriter');
		 var replytextObj = $('#newReplyText');
		 console.log(replytextObj.value);
		 var replyer = replyerObj.val();
		 var replytext = replytextObj.val();
		 console.log("replyText 잘 들어 왔니?"+replytextObj.val());
		 $.ajax({
			 type:'post',
			 url:'/replies',
			 headers:{
				 "Content-Type":"application/json",
				 "X-HTTP-Method-Override" : "POST"
			 },
		 dataType:'text',
		 data: JSON.stringify({bno:bno,replyer:replyer,replytext:replytext}),
		 success:function(result){
			 console.log("result : "+result);
			 if (result == "SUCCESS") {
				alert("등록되었습니다.");
				replyPage = 1;
				getPage("/replies/"+bno+"/"+replyPage);
				replyerObj.val("");
				replytextObj.val("");
			}
		 }
		 });
		 
	 });
	 function getPage(pageInfo){
		 $.getJSON(pageInfo,function(data){
			 printData(data.list,$('#repliesDiv'),$('#template'));
			 printPaging(data.pageMaker, $(".pagination"));
			 
			 $('#modifyModal').modal('hide');
		 });
	 }
	 
	 
	 var printPaging = function (pageMaker, target){
		 var str = "";
		 if (pageMaker.prev) {
				str += "<li><a href='"+(pageMaker.startPage-1 )+"'> << </a></li>"
			}
	 		for (var i = pageMaker.startPage,len=pageMaker.endPage;i <= len; i++) {
				var startClass = pageMaker.cri.page == i ? 'class=active':'';
				str += "<li "+startClass+"><a href='"+i+"'>"+i+"</a></li>";
			}
	 		if (pageMaker.next) {
				str += str += "<li><a href='"+(pageMaker.endPage+1 )+"'> >> </a></li>"
			}
	 		target.html(str);
	 }
 
	 var formObj = $('form[role="form"]');
	 
	 console.log(formObj);
	 
	 $('.btn-warning').on('click',function(){
		 formObj.attr({'method':'get'});
		 formObj.submit();
	 });
	 $('.btn-danger').on('click',function(){
		 formObj.attr({'action':'/sboard/removePage'});
		 formObj.submit();
	 });
	 $('.btn.btn-primary.list').on('click',function(){
		 formObj.attr({'action':'/sboard/list','method':'get'});
		 formObj.submit();
	 });
	 
	 Handlebars.registerHelper("prettifyDate", function(timeValue){
		 var dateObj  =  new Date(timeValue);
		 var year = dateObj.getFullYear();
		 var month = dateObj.getMonth() +1;
		 var date = dateObj.getDate();
		 return year+"/"+month+"/"+date;
	 });
	 var printData = function(replyArr,target,templateObject){
		 console.log("게시글 배열"+replyArr);
		 console.log("갔다 박을 곳"+target);
		 console.log("박을 형태"+templateObject);
		 var template =  Handlebars.compile(templateObject.html());
		 
		 var html = template(replyArr);
		 $(".replyLi").remove();
		 target.after(html);
	 }
	 
	 
 </script>
 
 
<%@include file="../include/footer.jsp" %>
