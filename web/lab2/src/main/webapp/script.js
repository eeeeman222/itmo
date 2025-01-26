let isClick = false;


function getRValue() {
    const checkboxes = document.querySelectorAll('input[name="rValue"]:checked');
    if (checkboxes.length > 0) {
        return parseFloat(checkboxes[0].value);
    }
    return null;
}

function clearErrors() {
    document.getElementById('yError').textContent = '';
    document.getElementById('rError').textContent = '';
}

function showError(elementId, message) {
    document.getElementById(elementId).textContent = message;
}

function validateNumberWithLimit(value, min, max, decimals) {
    const regex = new RegExp(`^-?\\d+(\\.\\d{0,${decimals}})?$`);
    const numValue = parseFloat(value);
    return !isNaN(numValue) && regex.test(value) && numValue >= min && numValue <= max;
}

function savePoints(x, y, r) {
    const points = JSON.parse(localStorage.getItem('points')) || [];
    points.push({ x, y, r });
    localStorage.setItem('points', JSON.stringify(points));
}

function loadPoints(ctx, scale, centerX, centerY) {
    const points = JSON.parse(localStorage.getItem('points')) || [];
    points.forEach(point => {
        const pointX = centerX + point.x * scale;
        const pointY = centerY - point.y * scale;

        ctx.beginPath();
        ctx.arc(pointX, pointY, 5, 0, Math.PI * 2);
        ctx.fillStyle = 'red';
        ctx.fill();
    });
}
function draw(){
    const canvas = document.getElementById('graphCanvas');
    const ctx = canvas.getContext('2d');

    const centerX = canvas.width / 2;
    const centerY = canvas.height / 2;
    const scale = 30;
    R = getRValue();

    ctx.clearRect(0, 0, canvas.width, canvas.height);

    ctx.beginPath();
    ctx.moveTo(0, centerY);
    ctx.lineTo(canvas.width, centerY);
    ctx.moveTo(centerX, 0);
    ctx.lineTo(centerX, canvas.height);
    ctx.strokeStyle = 'black';
    ctx.stroke();

    ctx.fillStyle = 'blue';
    ctx.beginPath();
    ctx.arc(centerX, centerY, R * scale, Math.PI / 2, Math.PI);
    ctx.lineTo(centerX, centerY);
    ctx.closePath();
    ctx.fill();

    ctx.fillRect(centerX - R * scale, centerY - R * scale, R * scale, R * scale);

    ctx.beginPath();
    ctx.moveTo(centerX, centerY);
    ctx.lineTo(centerX + R * scale, centerY);
    ctx.lineTo(centerX, centerY - R * scale);
    ctx.closePath();
    ctx.fill();
}

function drawGraph() {
    clearErrors();

    const canvas = document.getElementById('graphCanvas');
    const ctx = canvas.getContext('2d');
    const R = getRValue();
    const YInput = document.getElementById('yInput').value;
    const Y = parseFloat(YInput);
    const X = parseFloat(document.getElementById("x").value);


    let hasError = false;

    if (isNaN(R)) {
        showError('rError', "Выберете значение R");
        hasError = true;
    }
    if (!validateNumberWithLimit(X, -3, 5, 2)) {
        showError('xError', 'Введите корректное значение X');
        hasError = true;
    }
    if (!validateNumberWithLimit(Y, -3, 5, 2)) {
        showError('yError', 'Введите корректное значение Y');
        hasError = true;
    }
    if (hasError) return;

    ctx.clearRect(0, 0, canvas.width, canvas.height);

    const centerX = canvas.width / 2;
    const centerY = canvas.height / 2;
    const scale = 30;

    ctx.beginPath();
    ctx.moveTo(0, centerY);
    ctx.lineTo(canvas.width, centerY);
    ctx.moveTo(centerX, 0);
    ctx.lineTo(centerX, canvas.height);
    ctx.strokeStyle = 'black';
    ctx.stroke();

    ctx.fillStyle = 'blue';
    ctx.beginPath();
    ctx.arc(centerX, centerY, R * scale, Math.PI / 2, Math.PI);
    ctx.lineTo(centerX, centerY);
    ctx.closePath();
    ctx.fill();

    ctx.fillRect(centerX - R * scale, centerY - R * scale, R * scale, R * scale);

    ctx.beginPath();
    ctx.moveTo(centerX, centerY);
    ctx.lineTo(centerX + R * scale, centerY);
    ctx.lineTo(centerX, centerY - R * scale);
    ctx.closePath();
    ctx.fill();

    const pointX = centerX + X * scale;
    const pointY = centerY - Y * scale;
    ctx.beginPath();
    ctx.arc(pointX, pointY, 5, 0, 2 * Math.PI);
    ctx.fillStyle = 'red';
    ctx.fill();

    savePoints(X, Y, R);

    loadPoints(ctx, scale, centerX, centerY);

    if (!isClick) {
        sendHtml(X, Y, R);
    }
}

document.getElementById('graphCanvas').addEventListener('click', event => {
    const canvas = event.target;
    const rect = canvas.getBoundingClientRect();

    const R = getRValue();
    if (isNaN(R)) {
        alert('Установите значение радиуса R');
        return;
    }

    console.log("Полученное значение R:", R);

    const centerX = canvas.width / 2;
    const centerY = canvas.height / 2;
    const scale = 30;

    const x = ((event.clientX - rect.left) - centerX) / scale;
    const y = (centerY - (event.clientY - rect.top)) / scale;

    console.log("Координаты клика:", event.clientX, event.clientY);
    console.log("Вычисленные X и Y:", x, y);

    isClick = true;
    sendHtml(x.toFixed(2), y.toFixed(2), R);

    const ctx = canvas.getContext('2d');
    ctx.beginPath();
    ctx.arc(centerX + x * scale, centerY - y * scale, 5, 0, 2 * Math.PI);
    ctx.fillStyle = 'red';
    ctx.fill();

    savePoints(x, y, R);

    isClick = false;
});

function clearSavedPoints() {
    localStorage.removeItem('points');
}

function sendHtml(x, y, r) {
    console.log(`Отправка данных: X=${x}, Y=${y}, R=${r}`);
    const query = `/web2-1.0-SNAPSHOT/controller?x=${x}&y=${y}&r=${r}`;

    fetch(query, {
        method: 'GET'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(response => {
            console.log("Данные успешно отправлены:", response);
            const newRow = `
            <tr>
                <td>${response.x}</td>
                <td>${response.y}</td>
                <td>${response.r}</td>
                <td>${response.isInArea == true ? "изи" : "не изи"}</td>
            </tr>`;

            document.getElementById('outputTable').insertAdjacentHTML('beforeend', newRow);
            document.querySelector('.notification').textContent = 'Точка добавлена';
        })
        .catch(error => {
            console.error("Ошибка при отправке данных:", error.message);
            alert(`Ошибка! ${error.message}`);
        });
}

function setupCheckbox() {
    const checkboxesR = document.querySelectorAll('input[name="rValue"]');

    checkboxesR.forEach(checkbox => {
        checkbox.addEventListener('change', () => {
            checkboxesR.forEach(otherCheckbox => {
                if (otherCheckbox !== checkbox) {
                    otherCheckbox.checked = false;
                }
            });
            clearSavedPoints();
        });
    });
}

window.onload = function() {
    setupCheckbox();
    draw();
}