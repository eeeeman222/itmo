const svgElement = document.getElementById("graphSvg"); 

document.addEventListener("DOMContentLoaded", function () {
    svgElement.addEventListener("click", function (event) {
        console.log('graph clicked')

        const r = document.querySelector('select[name="pointForm:r"]').value;

        const rect = svgElement.getBoundingClientRect();

        const svgX = event.clientX - rect.left; // Click position in SVG width
        const svgY = event.clientY - rect.top;  // Click position in SVG height
        console.log('coorinates x,y,r: ', svgX, svgY, r);

        const x = ((svgX - 200) / 80) * r; // Transform SVG X to graph X
        const y = ((200 - svgY) / 80) * r; // Transform SVG Y to graph Y and invert Y-axis

        const svgFormId = 'svg-form'

        document.getElementById(svgFormId + ":xValue").value = x.toFixed(3);
        document.getElementById(svgFormId + ":yValue").value = y.toFixed(3);
        document.getElementById(svgFormId + ":rValue").value = r;

        document.getElementById(svgFormId + ":svgClickButton").click();

    });
});

function updateGraph() {


    console.log('updating graph...');
    const table = document.getElementById("resultsTable");

    const selectedRInput = document.querySelector('select[name="pointForm:r"]').value;
    
    removePoints();


    for (let i = 1; i < table.rows.length; i++) {
        const row = table.rows[i];
        const x = parseFloat(row.cells[0].innerText.trim());
        const y = parseFloat(row.cells[1].innerText.trim());
        
        const result = checkArea(x, y, selectedRInput);

        const coordinates = graphToSvgCoordinates(x, y, selectedRInput);
        drawPoint(coordinates.x, coordinates.y, result ? 'green' : 'red');
    }
    
}

function checkArea(x, y, R) {
    // Четверть круга (верхняя правая область)
    if (x >= 0 && y >= 0 && y <= R && x <= R/2) {
        return true;
    }

    // Треугольник (нижняя левая область)
    if (x <= 0 && y >= 0 && y <= R/2 + x) {
        return true;
    }

    // Полукруг (нижняя правая область)
    if (x >= 0 && y <= 0 && x * x + y * y <= R * R) {
        return true;
    }

    return false;  // Если точка не попадает в одну из областей
}

function removePoints(){
    console.log('removing points');    
    const svg = document.getElementById('graphSvg');
    const oldPoints = svg.querySelectorAll('.graph-point');
    console.log(oldPoints);
    if (oldPoints.length > 0) {
        oldPoints.forEach(point => point.remove());
    }
}


function drawPoint(x, y, color) {
    if (isNaN(x) || isNaN(y)) {
        console.log('Invalid data in drawPoint:', x, y);
        return;
    }
    console.log('in drawPoint: ', x,y,color);
    const point = document.createElementNS("http://www.w3.org/2000/svg", "circle");
    point.setAttribute("cx", x);
    point.setAttribute("cy", y);
    point.setAttribute("r", "3");
    point.setAttribute("fill", color);
    point.setAttribute("class", "graph-point");
    svgElement.appendChild(point); 
}

function graphToSvgCoordinates(x, y, r) {
    // Transform graph x to SVG x
    const svgX = 200 + (x / r) * 80; // Based on graph setup, 170px corresponds to R
    
    // Transform graph y to SVG y (and invert y-axis)
    const svgY = 200 - (y / r) * 80;
    
    return {x: svgX, y: svgY};
}

document.addEventListener("DOMContentLoaded", function () {
    const pointForm = document.getElementById('pointForm');

    pointForm.addEventListener('change', function(event) {
        if (event.target.name === 'pointForm:r') {
            const newR = parseFloat(event.target.value);
            console.log('R change detected. New R:', newR); 
            updateGraph();
            console.log('graph updated!');
        }
    });
});