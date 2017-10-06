package JavaCore.Lesson2;

/**
 * JavaCore.Lesson2
 * Created by Михаил Силантьев on 06.10.2017.
 */
/*1. Напишите метод, на вход которого подаётся двумерный строковый массив размером 4х4,
при подаче массива другого размера необходимо бросить исключение MyArraySizeException.

2. Далее метод должен пройтись по всем элементам массива, преобразовать в int, и просуммировать.
Если в каком-то элементе массива преобразование не удалось (например, в ячейке лежит символ или текст вместо числа),
должно быть брошено исключение MyArrayDataException, с детализацией в какой именно ячейке лежат неверные данные.

3. В методе main() вызвать полученный метод, обработать возможные исключения MySizeArrayException и MyArrayDataException, и вывести результат расчета.
*/
public class Main {
    public static void main(String[] args) throws MyArrayDataException,MyArraySizeException{
        String[][] arr={{"1","2","3","a"},{"1","2","3","s"},{"1","2","3","4"},{"1","2","3","4"}};
        System.out.println("start");
        try {
            System.out.println(metod1(arr));
        }
        catch (MyArraySizeException e){
            e.printStackTrace();
        }
        catch (MyArrayDataException e){
            e.printStackTrace();
        }
        System.out.println("end");
    }
    public static int metod1(String[][] arr) throws MyArrayDataException,MyArraySizeException {
        int sum=0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if(arr.length!=4 || arr[i].length!=4)throw new MyArraySizeException(i,j,arr[i].length);
                try {
                    sum+=Integer.parseInt(arr[i][j]);
                }
                catch (NumberFormatException e){
                    throw new MyArrayDataException(i,j,arr[i][j]);
                }
            }
        }
        return sum;
    }
}
