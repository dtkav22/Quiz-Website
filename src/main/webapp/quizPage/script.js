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
    const score = performance.score;
    const date = performance.date;
    const used_time = performance.used_time;
    const li = document.createElement("li");
    li.innerHTML = "Score : " + score +  ", Date: " + date + ", Time Used: " + used_time + " hrs.";
    userPerformances.appendChild(li);
}