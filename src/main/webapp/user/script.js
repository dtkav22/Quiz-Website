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