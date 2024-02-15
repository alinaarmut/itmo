package aca98b.web4l.util;

public class AreaChecker {
    public static boolean getResult(float x, float y, float r){
        boolean resultF = false;

//        круг
        if (x <= 0 && y >= 0) {
            if ( (Math.pow(x, 2) + Math.pow(y, 2) <= Math.pow(r, 2)) ){
          resultF = true;

      }
        }


//        прямоугольник
        if (x <= 0 && y <= 0) {
            if ( (x >= (-r/2)) && (y >= (-r)) ) {
                resultF = true;
            }
        }


        if (x>= 0 && y <=0) {
            if (y >= -(r/2) && x<=r/2 && y >= x - r/2) {
                resultF = true;
            }
        }

        return resultF;
    }
}