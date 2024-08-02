var ws;
function openSocket() {
    ws = new WebSocket("ws://localhost:8080/ws");
    ws.onopen = function() {
        console.log("WebSocket connection opened.");
    };
    ws.onmessage = function(event) {
        document.getElementById("messages").innerHTML += "<br/>" + event.data;
    };
    ws.onclose = function() {
        console.log("WebSocket connection closed.");
    };
}

function sendMessage() {
    var message = document.getElementById("messageInput").value;
    ws.send(message);
}