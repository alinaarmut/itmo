document.getElementById('form').addEventListener('submit', function (e) {
    e.preventDefault();
    let x = document.getElementById('x');
    let y = document.getElementById('y');
    let r = document.getElementById('r');

    if (validate(x, y, r)) {
        console.log("x:", x.value);
        console.log("y:", y.value);
        console.log("r:", r.value);
        send(x.value, y.value, r.value);
    } else {
        // Добавляем return false, чтобы форма не отправлялась
        return false;
    }
});

function send(x, y, r) {
    $.ajax({
        type: "GET",
        url: "ControllerServlet",
        async: false,
        data: {"x": x, "y": y, "r": r},
        success: function (result) {

            window.location.replace("./result.jsp");
        },
        error: function (xhr, textStatus, err) {
            showError(document.getElementById('button-check'), "readyState: " + xhr.readyState + "\n" +
                "responseText: " + xhr.responseText + "\n" +
                "status: " + xhr.status + "\n" +
                "text status: " + textStatus + "\n" +
                "error: " + err);
        }
    });
}

function showError(element, message) {
    const errorElement = document.createElement('div');
    errorElement.classList.add('error-message');
    errorElement.textContent = message;
    errorElement.style.color = 'black';
    errorElement.style.fontSize = '20px';
    errorElement.style.textAlign = 'left';
    element.parentNode.insertBefore(errorElement, element.nextSibling);
    setTimeout(function () {
        errorElement.remove();
    }, 99999999);
}


function validate(x, y, r) {
    if (x == null || y == null || r == null) {
        showError(null, "Необходимо выбрать значения X, Y и R");
        return false;
    }
    let replaceDot = val => val.replace(',', '.');
    let y1 = replaceDot(y.value);
    let xVal = parseFloat(x.value);
    let yVal = parseFloat(y1);
    let rVal = parseFloat(r.value);



    if (isNaN(xVal) || isNaN(yVal) || isNaN(rVal)) {
        // Возвращаем false, чтобы форма не отправлялась

        return false;
    }

    const validChars =  "-?[0-9]*[.,]?[0-9]+";
    if (!x.value.match(validChars) || !y.value.match(validChars) || !r.value.match(validChars)) {
         showError(null, 'Пожалуйста, используйте только цифры и точки для ввода.');

        return false;
    }


    if (xVal < -4 || xVal > 4) {
        showError(x, 'Значение X должно быть числом в пределах от -4 до 4.');
        return false;
    }

    if (yVal < -3 || yVal > 3) {
        showError(y, 'Значение Y должно быть числом в пределах от -3 до 3.');
        return false;
    }

    if (rVal < 1 || rVal > 4) {
        showError(r, 'Значение R должно быть числом в пределах от 1 до 4.');
        return false;
    }

    return true;
}