// document.addEventListener('DOMContentLoaded', function () {
//     const svg = document.getElementById('svg');
//     const rInput = document.getElementById('r');
//
//     // Добавляем слушатель события клика по SVG
//     svg.addEventListener('click', function (event) {
//         // Получаем радиус из текстового поля
//         const selectedR = getSelectedR();
//
//         // Если радиус установлен, обрабатываем клик
//         if (selectedR !== null) {
//             handleSvgClick(event, selectedR);
//         } else {
//             // Выводим сообщение о невозможности определения координат точки
//             window.location.href = ctx + '/error.jsp';
//         }
//     });
//
//     // Функция для обработки клика по SVG
//     function handleSvgClick(event, selectedR) {
//         const x = event.clientX;
//         const y = event.clientY;
//         const point = svg.createSVGPoint();
//         point.x = x;
//         point.y = y;
//         const transformedPoint = point.matrixTransform(svg.getScreenCTM().inverse());
//
//         // Убираем старую точку, если она существует
//         removeExistingPoint();
//
//         // Обновляем изображение (добавляем новую точку)
//         drawPoint(transformedPoint.x, transformedPoint.y, selectedR);
//
//         // Отправляем координаты на сервер для проверки
//         sendCoordinatesToServer(transformedPoint.x, transformedPoint.y, selectedR);
//     }
//
//     // Функция для отправки координат на сервер
//     function sendCoordinatesToServer(x, y, r) {
//         // Используйте AJAX для отправки координат на сервер
//
//         $.ajax({
//             type: 'GET',
//             url: ctx + '/ControllerServlet',
//             data: { 'x': x, 'y': y, 'r': r },
//             success: function (data) {
//                 // Обработка успешного ответа от сервера
//                 window.location.replace('./result.jsp')
//                 console.log('Координаты успешно отправлены на сервер.');
//             },
//             error: function (xhr, textStatus, err) {
//                 // Обработка ошибки
//                 console.error('Ошибка при отправке координат на сервер:', err);
//             }
//         });
//     }
//
//     // Функция для получения значения R из текстового поля
//     function getSelectedR() {
//         const selectedR = parseFloat(rInput.value);
//
//         if (!isNaN(selectedR) && selectedR >= 1 && selectedR <= 4) {
//             return selectedR;
//         }
//
//         return null;
//     }
//
//     // Функция для отображения точки на SVG
//     function drawPoint(x, y, r) {
//         const circle = document.createElementNS('http://www.w3.org/2000/svg', 'circle');
//         circle.setAttribute('cx', x);
//         circle.setAttribute('cy', y);
//         circle.setAttribute('r', '3');
//         circle.setAttribute('fill', 'red');
//         circle.classList.add('clicked-point'); // Добавление класса для последующего удаления
//         svg.appendChild(circle);
//     }
//
//     // Функция для удаления существующей точки
//     function removeExistingPoint() {
//         const existingPoint = svg.querySelector('.clicked-point');
//         if (existingPoint) {
//             svg.removeChild(existingPoint);
//         }
//     }
// });
// Константы для пересчета
const svgWidth = 400;
const svgHeight = 300;
const TO_RECALC_COORD = 160;
const TO_RECALC_R = 80;
document.addEventListener('DOMContentLoaded', function () {
    const svg = document.getElementById('svg');
    const rInput = document.getElementById('r');



    // Добавляем слушатель события клика по SVG
    svg.addEventListener('click', function (event) {
        // Получаем радиус из текстового поля
        const selectedR = getSelectedR();

        // Если радиус установлен, обрабатываем клик
        if (selectedR !== null) {
            handleSvgClick(event, selectedR);
        } else {
            // Выводим сообщение о невозможности определения координат точки
            window.location.href = ctx + '/error.jsp';
        }
    });

    // Функция для обработки клика по SVG
    function handleSvgClick(event, selectedR) {
        // Получаем координаты клика внутри SVG
        const svgRect = svg.getBoundingClientRect();
        const x = event.clientX - svgRect.left;
        const y = event.clientY - svgRect.top;

        let toSendX = parseFloat(((x - TO_RECALC_COORD) / TO_RECALC_R * selectedR).toFixed(5));
        let toSendY = parseFloat(((TO_RECALC_COORD - y) / TO_RECALC_R * selectedR).toFixed(5));

        // Убираем старую точку, если она существует
        removeExistingPoint();

        // Обновляем изображение (добавляем новую точку)
        drawPoint(x, y, selectedR);

        // Отправляем координаты на сервер для проверки
        sendCoordinatesToServer(toSendX, toSendY, selectedR);
    }

    // Функция для отправки координат на сервер
    function sendCoordinatesToServer(x, y, r) {
        // Используйте AJAX для отправки координат на сервер

        $.ajax({
            type: 'GET',
            url: ctx + '/ControllerServlet',
            data: { 'x': x, 'y': y, 'r': r },
            success: function (data) {
                // Обработка успешного ответа от сервера
                window.location.replace('./result.jsp')
                console.log('Координаты успешно отправлены на сервер.');
            },
            error: function (xhr, textStatus, err) {
                // Обработка ошибки
                console.error('Ошибка при отправке координат на сервер:', err);
                window.location.href = 'error.jsp?message=Пожалуйста, используйте только цифры и точки для ввода.';
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
    function drawPoint(x, y, r) {
        const circle = document.createElementNS('http://www.w3.org/2000/svg', 'circle');
        circle.setAttribute('cx', x);
        circle.setAttribute('cy', y);
        circle.setAttribute('r', '3');
        circle.setAttribute('fill', 'red');
        circle.classList.add('clicked-point'); // Добавление класса для последующего удаления
        svg.appendChild(circle);
    }

    // Функция для удаления существующей точки
    function removeExistingPoint() {
        const existingPoint = svg.querySelector('.clicked-point');
        if (existingPoint) {
            svg.removeChild(existingPoint);
        }
    }
});
