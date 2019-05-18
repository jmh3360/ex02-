<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<title>Document</title>
	<script src="/resources/plugins/jQuery/jQuery-2.1.4.min.js"></script>
</head>
<body>
	  <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js" ></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js" ></script>
    <div class="displayDiv">

    </div>
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
    <script type="text/javascript">
      var source = $('#template').html();
      var template = Handlebars.compile(source);
		
      var data = {name:"홍길동",userid:"user00",addr:"조선 한양"}
		console.log(template(data));
      $('.displayDiv').html(template(data));
    </script>
</body>
<script>

 	
</script>
</html>
 
