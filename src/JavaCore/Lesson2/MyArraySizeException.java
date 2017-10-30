package JavaCore.Lesson2;

/**
 * JavaCore.Lesson2
 * Created by Михаил Силантьев on 06.10.2017.
 */
public class MyArraySizeException extends Exception {
    int arrX;
    int arrY;
    int arrSize;

    public MyArraySizeException(int arrX, int arrY, int arrSize) {
        this.arrX = arrX;
        this.arrY = arrY;
        this.arrSize = arrSize;
    }

    @Override
    public String toString() {
        return "Ошибка! Массив должен быть 4*4, а текущий элемент x:"+this.arrX+",y:"+this.arrY+" длина:"+this.arrSize;
    }
}
