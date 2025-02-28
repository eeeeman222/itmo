import React, {useEffect, useRef, useState} from 'react';
import '../../../styles/plot.css'

const MyPlot = (
    {width, height, minX, maxX, minY, maxY, radius, points, onClickChart}
)=> {
    const canvasRef = useRef(null);


    useEffect(() => {
        const canvas = canvasRef.current
        const context = canvas.getContext('2d')

        drawPlot(canvas, context)
        drawPoints(canvas, context)
    }, [width, height, minX, maxX, minY, maxY, radius, points])

    const handleClickOnPlot = (event) => {
        const canvas = canvasRef.current;
        const rect = canvas.getBoundingClientRect();
        const clickX = event.clientX - rect.left;
        const clickY = event.clientY - rect.top;
        const centerX = canvas.width / 2;
        const centerY = canvas.height / 2;
        const R = (canvas.height - 50) / 2 / (5 / radius);

        const x = (clickX - centerX) / R * radius;
        const y = (centerY - clickY) / R * radius;

        onClickChart({x, y, radius})
    }

    const drawPoints = (canvas, ctx) => {
        const centerX = canvas.width / 2;
        const centerY = canvas.height / 2;
        radius = Math.abs(radius)
        const R = (canvas.height - 50) / 2 / (5 / radius);
        points.forEach((point) => {
            const poR = (canvas.height - 50) / 2 / (5 / Math.abs(point.r));
            console.log(poR, point)
            ctx.fillStyle = (point.isHit) ? "green" : "red";
            const ran = R/poR
            ctx.beginPath();
            console.log("prev x:", point.x, "new x:", point.x * ran)
            ctx.arc(point.x * ran / radius * R + centerX, -(point.y * ran / radius * R - centerY), 5, 0, 2 * Math.PI);
            ctx.fill();
        })
    }

    const drawPlot = (canvas, ctx) => {
        const R = (canvas.height - 50) / 2 / (5 / Math.abs(radius));
        const centerX = canvas.width / 2;
        const centerY = canvas.height / 2;

        ctx.clearRect(0, 0, canvas.width, canvas.height);
        ctx.lineWidth = 2;
        ctx.strokeStyle = "#000000";
        ctx.fillStyle = "rgba(236,54,210,0.67)";

        // Оси
        ctx.beginPath();
        ctx.moveTo(0, centerY);
        ctx.lineTo(canvas.width, centerY);
        ctx.moveTo(centerX, 0);
        ctx.lineTo(centerX, canvas.height);
        ctx.stroke();

        // Засечки и подписи
        const marks = [-R, -R / 2, R / 2, R];
        const labels = ["-R", "-R/2", "R/2", "R"];

        marks.forEach((offset, i) => {
            ctx.beginPath();
            ctx.moveTo(centerX + offset, centerY - 5);
            ctx.lineTo(centerX + offset, centerY + 5);
            ctx.stroke();
            ctx.fillText(labels[i], centerX + offset - 10, centerY - 10);
        });

        marks.forEach((offset, i) => {
            ctx.beginPath();
            ctx.moveTo(centerX - 5, centerY - offset);
            ctx.lineTo(centerX + 5, centerY - offset);
            ctx.stroke();
            ctx.fillText(labels[i], centerX + 10, centerY - offset + 5);
        });

        ctx.strokeStyle = "navy";

        // Треугольник (верхний)
        ctx.beginPath();
        ctx.moveTo(centerX, centerY);
        ctx.lineTo(centerX, centerY - R);
        ctx.lineTo(centerX + R / 2, centerY);
        ctx.closePath();
        ctx.fill();
        ctx.stroke();

        // Четверть круга (верхний левый)
        ctx.beginPath();
        ctx.moveTo(centerX, centerY);
        ctx.arc(centerX, centerY, R / 2, Math.PI, -Math.PI / 2, false);
        ctx.closePath();
        ctx.fill();
        ctx.stroke();

        // Прямоугольник (нижний правый)
        ctx.fillRect(centerX, centerY, R, R / 2);
        ctx.strokeRect(centerX, centerY, R, R / 2);
    };

    return (
        <div className="plot">
            <h3>мишень</h3>
            <div className="chart-container">
                <canvas
                    ref={canvasRef}
                    id="chart"
                    width={width}
                    height={height}
                    onClick={handleClickOnPlot}/>
            </div>
        </div>
    );
}


export default MyPlot;