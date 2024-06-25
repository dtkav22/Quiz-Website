document.getElementById("register_button").onclick = function() {
    var button = this;

    button.classList.add('animated');

    setTimeout(function() {
        window.location.href = "register.jsp";
    }, 300);
};

document.getElementById("login_button").onclick = function() {
    var button = this;
    button.classList.add('animated');
};