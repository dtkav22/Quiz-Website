document.getElementById("register_button2").onclick = function(event) {
    event.preventDefault();
    sendRequest();
};

function sendRequest() {
    var xhr = new XMLHttpRequest();
    var url = "/Register";
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;
    var email = document.getElementById("email").value;
    var params = "username=" + encodeURIComponent(username) +
        "&password=" + encodeURIComponent(password) +
        "&email=" + encodeURIComponent(email);

    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var errorDiv = document.getElementById("error");
            var jsonResponse = JSON.parse(xhr.responseText);
            if (jsonResponse.error) {
                errorDiv.innerHTML = jsonResponse.error;
                errorDiv.style.color = "red";
                errorDiv.style.font = "0.005";
            }else if(jsonResponse.success){
                window.location.href = "/Login";
            }
        }
    };
    xhr.send(params);
}