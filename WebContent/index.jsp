<%-- <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix = "form"%>

<!DOCTYPE html>
<html>
    <head>
    
    <link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet" type="text/css">
    </head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>Speech To Text</title>
    </head>
<body>
<form method="POST" action="callAPI">
<div class="container">

<div class="alert alert-primary text-center" role="alert">
    <p>Make sure you are using a recent version of Google Chrome.</p>
    <p>Also before you enable microphone input either plug in headphones or turn the volume down if you want to avoid ear splitting
        feedback!
    </p>
</div>

<div class="text-center mb-5">
    <button id="start-btn" class="btn btn-primary">Start recording</button>
    <button id="stop-btn" class="btn btn-secondary" disabled>Stop recording</button>
</div>

<div class="mb-2 text-center alert alert-dark">
    <h2>Stored Recordings</h2>
    </div>
    <div class=text-center>
    <ul id="recordingslist"></ul>
    </div>
    <div class="mb-2 text-center">
	<input type="submit" name="submit" class="btn btn-success" value="Click here to convert the audio to text" id="speechToText" disabled>
	</div>
	<input type="hidden" name="fileName" id="fileName" >
    <script>
        
    </script>
</div>
    </form>
</body>
<script src="<c:url value="/resources/js/recorder.js" />"></script>
</html> --%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix = "form"%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>Speech Recognition</title>
  <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>">
  <link href="https://fonts.googleapis.com/css?family=Shadows+Into+Light" rel="stylesheet">
  <!-- load font awesome here for icon used on the page -->
</head>
<body>
  <div class="container"> <!--page container -->
    <div class="text-box" contenteditable="true"></div> <!--text box which will contain spoken text -->
    <i class="fa fa-microphone"></i> <!-- microphone icon to be clicked before speaking -->
  </div>
  <audio class="sound" src="chime.mp3"></audio> <!-- sound to be played when we click icon => http://soundbible.com/1598-Electronic-Chime.html -->
  <script src="<c:url value="/resources/js/index.js" />"></script> <!-- link to index.js script -->
</body>
</html>

