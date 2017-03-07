<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head><title></title></head>
<body>
<script type="text/javascript">
var socket;
if (!window.WebSocket) {
  window.WebSocket = window.MozWebSocket;
}
if (window.WebSocket) {
  socket = new WebSocket("ws://localhost:9090/websocket/?request=e2lkOjE7cmlkOjI2O3Rva2VuOiI0MzYwNjgxMWM3MzA1Y2NjNmFiYjJiZTExNjU3OWJmZCJ9");
  socket.onmessage = function(event) {
	  alert(event.data);
  };
  socket.onopen = function(event) {
	  alert("websocket 打开了");
  };
  socket.onclose = function(event) {
	  alert("websocket 关闭了");
  };
}

function send(message) {
  if (!window.WebSocket) { return; }
  if (socket.readyState == WebSocket.OPEN) {
    socket.send(message);
  } else {
    alert("The socket is not open.");
  }
}
</script>
<form onsubmit="return false;">
  <input type="text" name="message" value="Hello, World!"/>
  <input type="button" value="Send Web Socket Data" onclick="send(this.form.message.value)" />
</form>
</body>
</html>