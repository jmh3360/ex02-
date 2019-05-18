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
 			<div class="box">
 				<div class="box-header with-border">
 						<h3 class="box-title">READ BOARD</h3>
 				</div>
 			<form role="form" action="modifyPage" method="post">
 				<input type="hidden" name='page' value="${cri.page }" />
 				<input type="hidden" name='perPageNum' value="${cri.perPageNum}" />
 				<input type="hidden" name="searchType" value='${cri.searchType}' />
				<input type="hidden" name="keyword" value='${cri.keyword}' />
 				<div class="box-body">
 					<div class="form-group">
						<label for="exampleInputEmail1">BNO</label>
						<input type="text" name="bno" class="form-control" 
						value='${boardVO.bno}' readonly="readonly" />
					</div>
 					<div class="form-group">
						<label for="exampleInputEmail1">Title</label>
						<input type="text" name="title" class="form-control" 
						value='${boardVO.title}' />
					</div>
					<div class="form-group">
						<label for="exampleInputPassword1">Content</label>
						<textarea class="form-control" name="content" rows="3">${boardVO.content}</textarea>
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">Writer</label>
						<input type="text" name="writer" class="form-control" 
						value='${boardVO.writer}' readonly />
					</div>
 				</div>
 			</form>
 				<div class="box-footer">
 					<button type="submit" class="btn btn-primary">SAVE</button>
 					<button type="submit" class="btn btn-warning">CANSCEL</button>
 				</div>
 			</div>
 		</div>
 	</div>
 </section>
 <script>
 $(function(){
	 var formObj = $('form[role="form"]');
	 console.log("페이지  번호//${cri.page}");
	 console.log("퍼페이지 넘//${cri.perPageNum}");
	 console.log("검색 조건//${cri.searchType}");
	 console.log("검색내용//${cri.keyword}");
	 console.log(formObj);
	 
	 $('.btn-warning').on('click',function(){
		 self.location = "/sboard/list?page=${cri.page}&perPageNum=${cri.perPageNum}"
						+"&searchType=${cri.searchType}&keyword=${cri.keyword}";
	 });
	 $('.btn-primary').on('click',function(){
		formObj.submit();
	 });
	 
	 
	 
 });
 </script>
 
 
<%@include file="../include/footer.jsp" %>
