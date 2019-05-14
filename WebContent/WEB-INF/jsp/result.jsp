<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix = "form"%>

<!DOCTYPE html>
<html>

<head>
    <link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet" type="text/css">
</head>

<h1>Output</h1> 

<div class="alert alert-primary">${Text}</div>


<div class="container">

<input type="hidden" id="filePath" value="${audioFile}">

<ul id="musicPlayer"></ul>

</div>
<script src="<c:url value="/resources/js/player.js" />"></script>
</html>