//обработка данных из формы

let numX, numY, numR;

const form = document.getElementById('form');
form.addEventListener('submit', validateForm);





function isNumeric(n) {
    return !isNaN(parseFloat(n)) && isFinite(n);
}

function validateX () {
    const MIN = -4;
    const MAX = 4;
    let xField = document.getElementById('x');
    numX = xField.value.replace(',', '.');
    if (isNumeric(numX) && (numX > MAX || numX < MIN)) {
        return false;
    } else {
        return true;
    }
}

function validateY () {
    const MIN = -3;
    const MAX = 3;
    let yField = document.getElementById('y');
    numY = yField.value.replace(',', '.');
    if (isNumeric(numY) && (numY > MAX || numY < MIN)) {
        return false;
    } else {
        return true;
    }
}

function validateR () {
    const MIN = 1;
    const MAX = 4;
    let rField = document.getElementById('r');
    numR = rField.value.replace(',', '.');
    if (isNumeric(numR) && (numR > MAX || numR < MIN)) {
        return false;
    } else {
        return true;
    }
}

function validateForm(event) {
    if (!(validateX() && validateY() && validateR()) ) {
        event.preventDefault(); //блокируем кнопку на отправку форму
        return false;

    }

    return true;
}

function send(x, y, r){
    var params = new URLSearchParams('x=' + x + '&y=' + y + '&r=' + r);

    fetch('/ControllerServlet', {
        method: 'GET',
        body: params
    }).then(
        response => {
            return response.text();
        }).then(data => {
        document.write(data);
    });

}

