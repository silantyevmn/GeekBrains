package Java1.lesson3;

import java.util.Scanner;

/**
 * Java Level 1. Lesson 3.
 *
 * @author Mikhail Silantev
 * @version dated 03.08.2017.
 */

/*Делать только одну задачу
        1 Написать программу, которая загадывает случайное число от 0 до 9, и пользователю дается 3 попытки угадать это число.
         При каждой попытке компьютер должен сообщить больше ли указанное пользователем число чем загаданное, или меньше.
         После победы или проигрыша выводится запрос – «Повторить игру еще раз? 1 – да / 0 – нет»(1 – повторить, 0 – нет).
        2 * Создать массив из слов String[] words =
        {"apple", "orange", "lemon", "banana", "apricot", "avocado", "broccoli", "carrot", "cherry", "garlic", "grape", "melon", "leak", "kiwi", "mango", "mushroom", "nut", "olive", "pea", "peanut", "pear", "pepper", "pineapple", "pumpkin", "potato"};
        При запуске программы компьютер загадывает слово, запрашивает ответ у пользователя, сравнивает его с загаданным словом и сообщает правильно ли ответил пользователь.
        Если слово не угадано, компьютер показывает буквы которые стоят на своих местах. apple – загаданное apricot - ответ игрока ap############# (15 символов, чтобы пользователь не мог узнать длину слова)
        Для сравнения двух слов посимвольно, можно пользоваться: String str = "apple"; str.charAt(0); - метод, вернет char, который стоит в слове str на первой позиции
        Играем до тех пор, пока игрок не отгадает слово Используем только маленькие буквы*/

public class Lesson3 {
    public static Scanner scanner=new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("Привет! Начнем играть:)");
        while (true) {
            int num = getNumberFromScanner("Введите номер игры, 1 - угадай число , 2 - угадай букву, 0 - выход. \nВаш выбор : ", 0, 2);
            if(num==1){
                job1();
            } else if(num==2){
                job2();
            } else
                break;
        }
        System.out.println("\nДо свидания! До новых встреч!");
    }

    private static void job1() {
        /*1 Написать программу, которая загадывает случайное число от 0 до 9, и пользователю дается 3 попытки угадать это число.
        При каждой попытке компьютер должен сообщить больше ли указанное пользователем число чем загаданное, или меньше.
        После победы или проигрыша выводится запрос – «Повторить игру еще раз? 1 – да / 0 – нет»(1 – повторить, 0 – нет).*/
        int min=0,max=9;
        int compRandom=nextRandom(max);// число загаданное компьютером
        int attemp=3; //попыток
        int playerNumber;
        System.out.println("Я уже загадал число, это число находится в пределах от "+min+" до "+max+".");
        do {
            System.out.println("Осталось "+((attemp==1)? " последняя попытка. ": ""+attemp+" попытки. ") );
            playerNumber = getNumberFromScanner("Введите число: ", min, max);
            if(playerNumber==compRandom) {
                System.out.println("Поздравляем с победой!");
                break;
            }
            if(playerNumber>compRandom){
                System.out.println("Загаданное число меньше "+playerNumber);
            } else {
                System.out.println("Загаданное число больше "+playerNumber);
            }
            attemp--;
            if(attemp<=0) System.out.println("Увы, вам не повезло:((((\nКомпьютер загадал число "+compRandom);
        } while(attemp>0);

        while(getNumberFromScanner("Повторить игру еще раз? 1 – да / 0 – нет»(1 – повторить, 0 – нет).\nВаш выбор :",0,1)!=0){
            job1();
        }
    }

    private static void job2() {
        /*2 * Создать массив из слов String[] words =
                {"apple", "orange", "lemon", "banana", "apricot", "avocado", "broccoli", "carrot", "cherry", "garlic", "grape", "melon", "leak", "kiwi", "mango", "mushroom", "nut", "olive", "pea", "peanut", "pear", "pepper", "pineapple", "pumpkin", "potato"};
        При запуске программы компьютер загадывает слово, запрашивает ответ у пользователя, сравнивает его с загаданным словом и сообщает правильно ли ответил пользователь.
        Если слово не угадано, компьютер показывает буквы которые стоят на своих местах. apple – загаданное apricot - ответ игрока ap############# (15 символов, чтобы пользователь не мог узнать длину слова)
        Для сравнения двух слов посимвольно, можно пользоваться: String str = "apple"; str.charAt(0); - метод, вернет char, который стоит в слове str на первой позиции
        Играем до тех пор, пока игрок не отгадает слово Используем только маленькие буквы*/
        String[] words={"apple", "orange", "lemon", "banana", "apricot", "avocado", "broccoli", "carrot", "cherry", "garlic", "grape", "melon", "leak", "kiwi", "mango", "mushroom", "nut", "olive", "pea", "peanut", "pear", "pepper", "pineapple", "pumpkin", "potato"};
        String compString=words[nextRandom(words.length)];// загаданное слово
        //char[] tempWords=new char[compString.length()]; // вспомогательный массив
        char[] tempWords=new char[15]; // вспомогательный массив
        int counterWords=compString.length(); //длина загаданного слова
        int counter=0; //ходы пользователя

        initArray(tempWords);// заполнить вспомогательный массив *
        System.out.println(compString);
        while(counterWords>0) {
            showArrString(tempWords);// показать массив
            //запросить у игрока букву и вернуть char
            System.out.print("\nВаша буква? : ");
            String playerChar = getStringFromScanner(1);
            //если буква есть, вывести поле
            int isCharNum=0;
            for (int i = 0; i < compString.length(); i++) {
                if (playerChar.charAt(0)==compString.charAt(i)) {
                    if(playerChar.charAt(0)==tempWords[i]){
                        isCharNum =-1;
                    } else {
                        tempWords[i] = playerChar.charAt(0);
                        counterWords--;
                        isCharNum = 1;
                    }
                }
            }
            counter++;
            // угадал, не угадал букву
            if(isCharNum==1) {
                System.out.println("Буква "+playerChar+" есть в слове! Откройте ее!");
            } else if(isCharNum==0){
                System.out.println("Увы,буквы "+playerChar+" нет в слове!");
            } else
                System.out.println("Очень невнимательно! Вы уже вводили данную букву, попробуйте еще!");
        }
        if (counter < counterWords +5 ){
            System.out.println("Примите мои поздравления! Вы отгадали "+compString+" за "+counter+" ходов.");
        } else {
            System.out.println("Вы отгадали " + compString + " за " + counter + " ходов.");
        }
    }

    private static void initArray(char[] tempWords) {
        for (int i = 0; i < tempWords.length; i++) {
            tempWords[i]='*';
        }
    }

    private static void showArrString(char[] arrs) {
        System.out.print("\n");
        for (int i = 0; i < arrs.length; i++) {
            System.out.print(arrs[i]);
        }
    }

    static int nextRandom(int x){
        return (int)(Math.random()*x);
    }

    static int getNumberFromScanner(String message,int min,int max){
        int num=0;
        do {
            System.out.print(message);
            try {
                num = Integer.parseInt(getStringFromScanner());
            } catch (Exception e) {
                System.out.println("\nОшибка! Попробуйте еще раз!");
                num=-1;
            }
            if(num < min || num > max) System.out.println("Введенное значение не корректно, повторите ввод!\n");
        } while (num < min || num > max );
        return num;
    }

    static String getStringFromScanner(int max){
        String text;
        do{
            text=getStringFromScanner();
            if(text.length()!=max) System.out.println("Необходимо ввести только 1 букву! Попробуйте еще раз!");
        } while (text.length()!=max);
        return text;
    }

    static String getStringFromScanner(){
        return scanner.nextLine();
    }
}
