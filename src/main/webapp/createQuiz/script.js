function updateImageSrc() {
    var image = document.getElementById("Image");
    image.src = document.getElementById("ImageUrl").value;
}

function addWrongAnswer() {
    var wrongAnswers = document.getElementById("wrongAnswers");
    var field = document.createElement("input");
    field.setAttribute("type", "text");
    field.setAttribute("class", "wrongAnswers");
    wrongAnswers.appendChild(field);
}