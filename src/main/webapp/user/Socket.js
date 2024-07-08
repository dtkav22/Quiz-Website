var socket;

function initSocket() {

    if (socket && socket.readyState === WebSocket.OPEN) {
        console.log("WebSocket is already open.");
        return;
    }

    socket = new WebSocket("ws://" + location.host + "/your-websocket-endpoint");

    socket.onmessage = function(event) {
        const message = JSON.parse(event.data);
        if(message.type === "challengeRequest") {
            getChallengeRequests();
        } else {
            getFriendRequests(message.from);
        }
    };

    socket.onopen = function(event) {
        console.log("WebSocket connection opened");
    };

    socket.onclose = function(event) {
        console.log("WebSocket connection closed");
    };

    socket.onerror = function(error) {
        console.error("WebSocket error:", error);
    };
}

window.onload = function() {
    initSocket();
}

window.onbeforeunload = function() {
    if (socket) {
        socket.close();
    }
}


function getFriendRequests(sender_id) {
    $('#friendsRequest').load(document.URL + ' #friendsRequest');
    $('#profileInfo-' + sender_id).load(document.URL + ' #profileInfo-' + sender_id);
}

function getChallengeRequests() {
    $('#challengeRequest').load(document.URL + ' #challengeRequest');
}