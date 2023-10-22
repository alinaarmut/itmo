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
return true;
}

function send (){
  if (validateForm) {

  let xhr = new XMLHttpRequest();
  FD = new FormData();
  FD.append("X", numX);
  FD.append("Y", numY);
  FD.append("R", numR);
  FD.append('timezone', new Date().getTimezoneOffset());
  fetch(`index.php?x=${xValue}&y=${yValue}&r=${rValue}`, {
      method: "GET"
  })
}
}