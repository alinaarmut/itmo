<?php 
ini_set('session.gc_maxlifetime', 1200);
session_start();
date_default_timezone_set('Europe/Moscow');


$x = $_GET['x'];
$x = str_replace(',', '.', $x);
$y = $_GET['y'];
$y = str_replace(',', '.', $y);
$r = $_GET['numberField'];
$r = str_replace(',', '.', $r);
$current_time = date("Y-m-d H:i:s");
$startTime = microtime(true);

if(!is_numeric($x[-1])){
    $x = substr($x, 0, -1);
}

if(!is_numeric($y[-1])){
    $y = substr($y, 0, -1);
}

if(!is_numeric($r[-1])){
    $r = substr($r, 0, -1);
}

function checkPoint($x,$y,$r)
{
if (($x >=0) && ($y<=0) && (pow($x,2) + pow($y,2) <=pow($r,2))) {
    return "Прокнуло";
} elseif (($x >=-$r) && ($x<=0) && ($y>= -$r) && ($y <= 0) && ($x+$r)>= $y) {
    return "Прокнуло";

} elseif (($x >= -$r) && ($x<=0) && ($y>=0) && ($y<=$r)) {
    return "Прокнуло";
} else {
    return "Мимо";
} 

}

 


    function validate_X ($x) {

    $Xval = array (-4, -3, -2, -1, 0, 1, 2, 3, 4);

        if (!isset($x) ||  !is_numeric($x)){
            return false;
          }
        $numX = str_replace(',', '.', $x);
        $numX = str_replace(' ', '', $numX);
        return in_array($numX, $Xval);
    }

    function validate_Y ($y) {
        $Y_MIN = -5;
        $Y_MAX = 5;

        if (!isset($y)){
            return false;
        }

      $numY = str_replace(',', '.', $y);
      $numY = str_replace(' ', '', $numY);
      return is_numeric(($numY)) && $numY >= $Y_MIN && $numY <= $Y_MAX;
    }


    function validate_R ($r) {
        $Rval = array (1, 1.5, 2, 2.5, 3);

     if (!isset($r) || !is_numeric($r)){
        return false;
      }
      $numR = str_replace(',', '.', $r);
      $numR = str_replace(' ', '', $numR);
      return in_array($numR, $Rval);
            
        }


        function validate($x,$y,$r){
            return validate_X($x) && validate_Y($y) && validate_R($r);
        }
        ?>

        <!DOCTYPE html>
        <html lang="ru">
        <head>
        <meta charset="UTF-8">
        <title>Результаты</title>
        <link rel="stylesheet" href ="next.css" />
        
        
        </head>
        <body class="next_page">
        
        <table border="2" id="response-table" class="response-table" align="center"  cellpadding ="20" >
                    <tr>
                        <td>X</td>
                        <td>Y</td>
                        <td>R</td>
                        <td>Результат</td>
                        <td>Время</td>
                        <td>Время выполнения</td>
                        </tr>
        
        
        <?php
        
                 if ( validate($x, $y, $r)) {
                    $result = checkPoint($x, $y, $r);
                    $executeTime = round(microtime(true) - $startTime, 12);
                    $_SESSION['data'][] = [$x, $y, $r, $result, $current_time, $executeTime];
        
  //          var_dump($_SESSION['data']);
                
            foreach ($_SESSION["data"] as $row) {
                echo "<tr>";
                foreach ($row as $value) {
                    echo "<td>$value</td>";
                }
                echo "</tr>";
            }
        }  else {
            //echo 'console.log("произошла ошибка")';
            echo '<p class ="error"> Ломай ломай мы же миллионеры.... </p>';
         }
        
            ?>

        </table>
        <a href="lab.html" class="previous">Камбекнуть</a>
        </body>
        </html>