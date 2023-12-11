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
    if (!(validateX() && validateY() && validateR())) {
        event.preventDefault();
        return false;
    }
    return true;
}
rButtons.forEach((button) => {
    button.addEventListener('click', function () {
        rButtons.forEach((btn) => {
            btn.classList.remove('checked');
        });

        // Select the clicked button
        this.classList.add('checked');

        // You can perform any actions you need with the selected value here
        const selectedValue = this.value;
        console.log(`Выбранное R: ${selectedValue}`);


        // You can perform any actions you need with the selected value here
        drawShapesByR(+selectedValue);
        for (var i = 0; i < resultList.length; i++) {
            console.log("start");
            if (parseFloat(resultList[i].r) === parseFloat(selectedValue)) {
                console.log("draw");
                drawPoint(resultList[i].x, resultList[i].y, resultList[i].res);
            }
        }

    });
});