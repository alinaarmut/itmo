let numX, numY, numR;
const form = document.getElementById('form');
form.addEventListener('submit', validateForm);


function isNumeric(n) {
  return !isNaN(parseFloat(n)) && isFinite(n);
}


function validateX () {
  const MIN = -4;
  const MAX = 4;
  let var_x = document.getElementById("x");
  numX = var_x.value.replace(',', '.');
 // console.log("Value of numX:", numX);
  if ( isNumeric(numX && ( numX > MAX || numX < MIN))) {
    return false;
  } else {
return true;
  }
} 


// могу сделать только на поле Y
function validateY () {
  const MIN = -5;
  const MAX = 5;

  let var_y = document.getElementById("y");
  numY = var_y.value.replace(',', '.');
 // console.log("Value of numY:", numY);
  if (isNumeric(numY) && (numY > MAX || numY < MIN)) {
   // var_y.style.borderColor = "red"; не работает корректно (
    return false;
  } else {
    //var_y.style.borderColor = "green";
    return true;
  }


  }

function validateR () {
  const MIN = 1;
  const MAX = 3;

  let var_r = document.getElementById("numberField");
  numR = var_r.value.replace(',', '.');
  //console.log("Value of numR:", numR);
  if ( isNumeric(numR) && ( numR > MAX || numR < MIN)) {
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
//console.log("Validation successful");
return true;
}

function send (){
  if (validateForm()) {

  let xhr = new XMLHttpRequest();
  FD = new FormData();
  FD.append("X", numX);
  FD.append("Y", numY);
  FD.append("R", numR);
  console.log("Data to be sent:", FD);
  FD.append('timezone', new Date().getTimezoneOffset());
  fetch(`index.php?x=${numX}&y=${numY}&r=${numR}`, {
      method: "GET"
  })
  .then(response => {
    if (response.ok) {
      // Обработка успешного ответа
      console.log("Запрос успешно выполнен.");
      return response.text(); // Возвращает текст ответа, если он нужен
    } else {
      // Обработка ошибки
      console.error("Произошла ошибка при выполнении запроса.");
      throw new Error("Ошибка при выполнении запроса.");
    }
  })
  .then(data => {
  
    console.log("Данные с сервера:", data);
  })
  .catch(error => {
    // Обработка ошибки, если что-то пошло не так
    console.error("Произошла ошибка:", error);
  });
}
}