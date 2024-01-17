var months = new Array(13);
months[1] = "января"; months[2] = "февраля"; months[3] = "марта"; months[4] = "апреля"; months[5] = "мая"; months[6] = "июня"; months[7] = "июля"; months[8] = "августа"; months[9] = "сентября"; months[10] = "октября"; months[11] = "ноября"; months[12] = "декабря";

function updateDateTime() {
    var time = new Date();
    var thismonth = months[time.getMonth() + 1];
    var date = time.getDate();
    var thisyear = time.getFullYear(); // исправлено: getYear() заменено на getFullYear()
    var day = time.getDay() + 1;

    if (day == 1) DayofWeek = "Воскресенье";
    if (day == 2) DayofWeek = "Понедельник";
    if (day == 3) DayofWeek = "Вторник";
    if (day == 4) DayofWeek = "Среда";
    if (day == 5) DayofWeek = "Четверг";
    if (day == 6) DayofWeek = "Пятница";
    if (day == 7) DayofWeek = "Суббота";

    var datetime = time.getHours() + ":"+ time.getMinutes()+ ":"+ time.getSeconds()+" "+ date + " " + thismonth + " " + thisyear + " года — " + DayofWeek;

    var clockdat = document.getElementById("clockdat");
    clockdat.innerHTML = datetime;
}

// Обновление времени и даты каждые 9 секунд
setInterval(updateDateTime, 9000);

// Обновление времени в момент загрузки страницы
updateDateTime();
