

var au = document.createElement('audio');
var li = document.createElement('li');
au.controls = true;
au.src = window.location.origin+"/"+window.location.pathname.split("/")[1]+document.getElementById("filePath").value;
li.appendChild(au);
document.getElementById("musicPlayer").appendChild(li);