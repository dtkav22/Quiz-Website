function updateImageSrc() {
    var image = document.getElementById("Image");
    image.src = document.getElementById("ImageUrl").value;
}

function addWrongAnswer() {
    var wrongAnswers = document.getElementById("wrongAnswers");
    var field = document.createElement("input");
    field.setAttribute("type", "text");
    field.setAttribute("class", "AnswerFields");
    field.setAttribute("name", "wrongAnswers");
    wrongAnswers.appendChild(field);
}

function addCorrectAnswer(TaskType) {
    var correctAnswers = document.getElementById("correctAnswers" + TaskType);
    var field = document.createElement("input");
    field.setAttribute("type", "text");
    field.setAttribute("class", "AnswerFields");
    field.setAttribute("name", "correctAnswers");
    correctAnswers.appendChild(field);
}

