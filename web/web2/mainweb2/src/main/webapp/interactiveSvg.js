// Константы для пересчета
const svgWidth = 400;
const svgHeight = 300;
const TO_RECALC_COORD = 160;
const TO_RECALC_R = 80;

const MIN_COORD = 135;
const MAX_COORD = 240;


document.addEventListener('DOMContentLoaded', function () {
    const svg = document.getElementById('svg');
    const rInput = document.getElementById('r');

    restoreSavedPoints();
    rInput.addEventListener('change', function () {
        const selectedR = getSelectedR(); // Получаем выбранный радиус

    });

    // Добавляем слушатель события клика по SVG
    svg.addEventListener('click', function (event) {
        // Получаем радиус из текстового поля
        const selectedR = getSelectedR();

        // Если радиус установлен, обрабатываем клик
        if (selectedR !== null) {
            handleSvgClick(event, selectedR);
        } else {
            // Выводим сообщение о невозможности определения координат точки
            //   window.location.href = ctx + '/error.jsp';
            showError(document.body, 'Вы не ввели радиус или ввели некорректный радиус. Введите верный радиус чтобы выбрать точки на графике. Радиус от 1..4');
        }
    });

    // function updatePointCoordinates(newR, selectedR) {
    //     const clickedPoints = document.querySelectorAll('.clicked-point');
    //
    //     clickedPoints.forEach(point => {
    //         const x = parseFloat(point.getAttribute('cx'));
    //         const y = parseFloat(point.getAttribute('cy'));
    //
    //         // Пересчитываем координаты в соответствии с новым радиусом
    //         const updatedX = TO_RECALC_COORD + (x / selectedR * newR);
    //         const updatedY = TO_RECALC_COORD - (y / selectedR * newR);
    //         console.log(updatedX)
    //         console.log(updatedY)
    //
    //         // Устанавливаем новые координаты точки
    //         point.setAttribute('cx', updatedX);
    //         point.setAttribute('cy', updatedY);
    //        // updatePointColor(point, updatedX, updatedY);
    //     });
    // }

    // function updatePoints() {
    //     const existingPoints = document.querySelectorAll('.clicked-point');
    //     existingPoints.forEach(point => point.remove());
    //
    //     // Получаем значение нового радиуса из формы
    //     const newRadius = parseFloat(rInput.value);
    //
    //     // Добавить новые точки с учетом нового радиуса
    //     updatePointCoordinates(newRadius);
    //
    //
    // }
    function restoreSavedPoints() {
        const savedPoints = JSON.parse(localStorage.getItem('savedPoints')) || [];

        savedPoints.forEach(point => {
            drawPoint(point.x, point.y, point.r);

        });
    }
    // Функция для обработки клика по SVG
    function handleSvgClick(event, selectedR) {
        // Получаем координаты клика внутри SVG
        const svgRect = svg.getBoundingClientRect();
        const x = event.clientX - svgRect.left;
        const y = event.clientY - svgRect.top;
        const R =  selectedR;
        console.log('Clicked coordinates:', x, y);

        // let toSendX = parseFloat(((x - TO_RECALC_COORD) / TO_RECALC_R * selectedR).toFixed(5));
        // let toSendY = parseFloat(((TO_RECALC_COORD - y) / TO_RECALC_R * selectedR).toFixed(5));
        let toSendX = parseFloat((((x - 150) / 50 * selectedR)/2).toFixed(3));
        let toSendY = parseFloat((((150- y) / 50 * selectedR)/2).toFixed(3));


        // Обновляем изображение (добавляем новую точку)
        drawPoint(x, y, selectedR);

        savePoint({ x, y, selectedR });


        // Отправляем координаты на сервер для проверки
        sendCoordinatesToServer(toSendX, toSendY, selectedR);
        console.log(toSendX, toSendY, selectedR);
    }

    // Функция для сохранения точки в Local Storage
    function savePoint(point) {
        const savedPoints = JSON.parse(localStorage.getItem('savedPoints')) || [];
        savedPoints.push(point);

        localStorage.setItem('savedPoints', JSON.stringify(savedPoints));
    }

    // Функция для отправки координат на сервер
    function sendCoordinatesToServer(x, y, r) {
        // Используйте AJAX для отправки координат на сервере
        $.ajax({
    type: 'GET',
    url: ctx + '/ControllerServlet',
    data: { 'x': x, 'y': y, 'r': r },
    success: function (data) {
        // Обработка успешного ответа от сервера
        window.location.replace('./result.jsp')
        console.log(x,y,r);
        console.log('Координаты успешно отправлены на сервер.');
    },
    error: function (xhr, textStatus, err) {
        // Обработка ошибки
        console.error('Ошибка при отправке координат на сервер:', err);
        showError(document.body, 'Вы не ввели радиус или ввели некорректный радиус. Введите верный радиус чтобы выбрать точки на графике. Радиус от 1..4');
        //window.location.href = 'error.jsp?message=Пожалуйста, используйте только цифры и точки для ввода.';
    }
});
}

// Функция для получения значения R из текстового поля
function getSelectedR() {
    const selectedR = parseFloat(rInput.value);

    if (!isNaN(selectedR) && selectedR >= 1 && selectedR <= 4) {
        return selectedR;
    }

    return null;
}

// Функция для отображения точки на SVG


// function updatePointColor(point, x, y) {
//     // Проверяем, попала ли точка в область, и устанавливаем цвет
//
//     const isPointInArea = (x >= MIN_COORD && x <= MAX_COORD ) && (y >= MIN_COORD  && y <= MAX_COORD );
//     point.setAttribute('fill', isPointInArea ? 'green' : 'red');
// }



function drawPoint(x, y) {
    const circle = document.createElementNS('http://www.w3.org/2000/svg', 'circle');
    circle.setAttribute('cx', x);
    circle.setAttribute('cy', y);
    circle.setAttribute('r', '3');
    circle.setAttribute('fill',  'black' );
    circle.classList.add('clicked-point'); // Добавление класса для последующего удаления
    svg.appendChild(circle);
    //updatePointColor(circle, x, y);


}
function showError(element, message) {
    const errorElement = document.createElement('div');
    errorElement.classList.add('error-message');
    errorElement.textContent = message;
    errorElement.style.color = 'white';
    errorElement.style.fontSize = '20px';
    errorElement.style.textAlign = 'left';
    element.parentNode.insertBefore(errorElement, element.nextSibling);
    setTimeout(function () {
        errorElement.remove();
    }, 99999999);
}

});