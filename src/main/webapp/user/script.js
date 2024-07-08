function goToDisplayQuizzes(type, id = null) {
    let url = "/displayQuizzes?type=" + type;
    if (id !== null) {
        url += "&profile_id=" + id;
    }
    window.location.href = url;
}

function goToDisplayRequests(type) {
    window.location.href = "/displayRequests?type=" + type;
}


function goToFriends(id) {
    window.location.href = "/friendsList?profile_id=" + id;
}


function handleFriendRequest(refresh, action, friendId) {
    $.ajax({
        type: "POST",
        url: "FriendRequest",
        data: {
            friend_id: friendId,
            action: action
        },
        success: function(response) {
            if(refresh) {
                $('#friendsPerformance').load(document.URL +  ' #friendsPerformance');
                $('#friendsRequest').load(document.URL +  ' #friendsRequest');
            } else {
                $('#quiz-box-' + friendId).remove();
            }
        },
        error: function(error) {
            console.error("Error:", error);
        }
    });
}

function handleChallengeRequest(box_id, action, friendId, quizId) {
    $.ajax({
        type: "POST",
        url: "ChallengeRequest",
        data: {
            friend_id: friendId,
            action: action,
            quiz_id: quizId,
        },
        success: function(response) {
            if(action === "accept") {
                console.log("here");
                window.location.href = "/quizPage?quiz_id=" + encodeURIComponent(quizId);
            } else {
                if(box_id === "-1") {
                    $('#challengeRequest').load(document.URL +  ' #challengeRequest');
                } else {
                    $('#quiz-box-' + box_id).remove();
                }
            }

        },
        error: function(error) {
            console.error("Error:", error);
        }
    });
}

function sendFriendRequest(friendId) {
    $.ajax({
        type: "POST",
        url: "SendRequest",
        data: {
            friend_id: friendId,
            type: "friend"
        },
        success: function(response) {
            $("#sendFriendRequest").remove();
        },
        error: function(error) {
            console.error("Error:", error);
        }
    });
}

function sendToLogin() {
    let fusername = document.getElementById("fusername").value;
    console.log(fusername);
    $.ajax({
        type: "POST",
        url: "findProfile",
        data: {
            fusername: fusername,
            type: "friend"
        },
        success: function(response) {
            if (response.error) {
                alert("Error: " + response.error);
            } else {
                window.location.href = "/profilePage?profile_id=" + response.profile_id;
            }
        },
        error: function(xhr, status, error) {
            console.error("AJAX error: " + status + " - " + error);
        }
    });
}