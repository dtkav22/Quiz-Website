function showPerformances() {
    const userPerformances = document.getElementById("userPerformances");
    userPerformances.innerHTML = "Your Performances:";
    const params = document.getElementById("performancesOrder");
    const xhr = new XMLHttpRequest();
    const url = "/PerformancesHandler?" + params.value;

    xhr.open("GET", url, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const jsonResponse = JSON.parse(xhr.responseText);
            jsonResponse.forEach(addPerformanceInList);
        }
    };
    xhr.send();
}

function addPerformanceInList(performance) {
    const userPerformances = document.getElementById("userPerformances");
    const score = performance.score.toFixed(2);
    const date = performance.date;
    const used_time = performance.used_time;
    const li = document.createElement("li");
    li.innerHTML = "Score : " + score +  ", Date: " + date + ", Time Used: " + used_time + " hrs.";
    userPerformances.appendChild(li);
}

function challengeUser() {
    let fusername = document.getElementById("fusername").value;
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
                sendChallengeRequest(response.profile_id);
            }
        },
        error: function(xhr, status, error) {
            console.error("AJAX error: " + status + " - " + error);
        }
    });
}

function sendChallengeRequest(friendId) {
    let quizCurrent = document.getElementById("quizCurId").value;
    $.ajax({
        type: "POST",
        url: "SendRequest",
        data: {
            friend_id: friendId,
            quiz_id: quizCurrent,
            type: "challenge"
        },
        success: function(response) {
            if(response.message) {
                alert("You cannot send a challenge. You are not friends with this user.");
            }
        },
        error: function(error) {
        }
    });
}