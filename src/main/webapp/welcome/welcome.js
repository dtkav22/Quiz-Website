document.getElementById("register_button").onclick = function() {
    var button = this;
    button.classList.add('animated');

    setTimeout(function() {
        window.location.href = "/Register";
    }, 300);
};

document.getElementById("login_button").onclick = function(event) {
    event.preventDefault();
    sendRequest();
};

function sendRequest() {
    var xhr = new XMLHttpRequest();
    var url = "/Login";
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;
    var params = "username=" + encodeURIComponent(username) +
        "&password=" + encodeURIComponent(password);

    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var errorDiv = document.getElementById("error");
            var jsonResponse = JSON.parse(xhr.responseText);
            if (jsonResponse.error) {
                errorDiv.innerHTML = jsonResponse.error;
                errorDiv.style.color = "red";
            }else if(jsonResponse.success){
                window.location.href = "/UserHomePage";
            }
        }
    };
    xhr.send(params);
}