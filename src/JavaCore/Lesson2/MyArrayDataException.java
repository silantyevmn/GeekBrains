package JavaCore.Lesson2;

/**
 * JavaCore.Lesson2
 * Created by Михаил Силантьев on 06.10.2017.
 */
public class MyArrayDataException extends Exception{
    int x;
    int y;
    String value;

    public MyArrayDataException(int x, int y, String value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Ошибка! Символ '"+value+"' в строке "+this.y+", столбец "+this.x+" не может быть преобразован к числу";
    }
}
