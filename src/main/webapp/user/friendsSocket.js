var socket;

function initSocket() {
    socket = new WebSocket("ws://" + location.host + "/friendRequestsSocket");
    socket.onmessage = function(event) {
        getFriendRequests();
    };
    socket.onopen = function (event) {
        $.ajax({
            type: "POST",
            url: "SendId",
            data: {
                type: "friends"
            },
            success: function(response) {
                console.log("success");
            },
            error: function(error) {
                console.error("Error:", error);
            }
        });
    }
}

window.onload = function() {
    initSocket();
    //getFriendRequests();
}

function getFriendRequests() {
    $('#friendsRequest').load(document.URL +  ' #friendsRequest');
    for(let i = 0; i < 10000; i++) {
        console.log(i);
    }
}