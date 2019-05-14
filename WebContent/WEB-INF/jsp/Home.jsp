<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix = "form"%>

<html>
<head> <script src="<c:url value="/resources/js/recorder.js" />"></script></head>
    	<body>
    	<p>
    		<button id=record></button>
    		<button id=stopRecord disabled>Stop</button>
    		</p>
    		<audio controls autoplay></audio>
    		
                    <fieldset><legend>RECORD AUDIO</legend>
    		<input onclick="startRecording()" type="button" value="start recording" />
    		<input onclick="stopRecording()" type="button" value="stop recording and play" />
                    </fieldset>
    		<script>
    			var onFail = function(e) {
    				console.log('Rejected!', e);
    			};

    			var onSuccess = function(s) {
    				var context = new webkitAudioContext();
    				var mediaStreamSource = context.createMediaStreamSource(s);
    				recorder = new Recorder(mediaStreamSource);
    				recorder.record();

    				// audio loopback
    				// mediaStreamSource.connect(context.destination);
    			}

    			window.URL = window.URL || window.webkitURL;
    			navigator.getUserMedia  = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia || navigator.msGetUserMedia;

    			var recorder;
    			var audio = document.querySelector('audio');

    			function startRecording() {
    				if (navigator.getUserMedia) {
    					navigator.getUserMedia({audio: true}, onSuccess, onFail);
    				} else {
    					console.log('navigator.getUserMedia not present');
    				}
    			}

    			function stopRecording() {
    				recorder.stop();
    				recorder.exportWAV(function(s) {
                                
                                 	audio.src = window.URL.createObjectURL(s);
    				});
    			}
    		</script>
    	</body>
    </html>