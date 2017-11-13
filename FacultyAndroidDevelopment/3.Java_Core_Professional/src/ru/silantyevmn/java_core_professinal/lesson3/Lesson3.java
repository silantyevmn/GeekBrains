package ru.silantyevmn.java_core_professinal.lesson3;


import java.io.*;
import java.util.*;

/**
 * ru.silantyevmn.java_core_professinal.lesson3
 * Created by Михаил Силантьев on 12.11.2017.
 * <p>
 * 1. Прочитать файл (около 50 байт) в байтовый массив и вывести этот массив в консоль;
 * 2. Последовательно сшить 5 файлов в один (файлы также ~100 байт).
 * <p>
 * Может пригодиться следующая конструкция:
 * ArrayList<InputStream> al = new ArrayList<>();
 * ...
 * Enumeration<InputStream> e = Collections.enumeration(al);
 * 3. Написать консольное приложение, которое умеет постранично читать текстовые файлы (размером > 10 mb), вводим страницу, программа выводит ее в консоль (за страницу можно принять 1800 символов). Время чтения файла должно находится в разумных пределах (программа не должна загружаться дольше 10 секунд), ну и чтение тоже не должно занимать >5 секунд.
 * <p>
 * Чтобы не было проблем с кодировкой используйте латинские буквы.
 */
public class Lesson3 {
    public static final String FILE_PATH = "files/";
    public static final int CHAR_PAGE = 1800;
    public static ArrayList<InputStream> files = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        //1
        String text = "Hello, world!";
        String file1 = FILE_PATH + "0.txt";
        newFile(text, file1);
        readFile(file1);
        System.out.println();
        //2
        addFileArrayList(file1, 5);
        String file2 = FILE_PATH + "full.txt";
        sequenceFiles(file2);// склеиваем 5 файлов
        readFile(file2);
        System.out.println();
        //3
        String file3 = FILE_PATH+"10mb.txt";
        addFileArrayList(file1, 1000);//увеличиваем коллекцию на 1000файлов
        sequenceFiles(file3); //склеиваем 1000 и записываем в file3
        pageReadFile(file3); //вывод постранично
    }

    private static void pageReadFile(String nameFile) {
        try (InputStream in = new FileInputStream(nameFile);
             ByteArrayOutputStream book = new ByteArrayOutputStream()) {
            byte[] buff = new byte[CHAR_PAGE];
            int len;
            while ((len = in.read(buff)) > 0) {
                book.write(buff, 0, len);
            }
            int page = 0;
            int numberOfPages = book.size() / CHAR_PAGE;
            int pages;
            int enterPage;
            int offset;
            int turnThePages;
            Scanner input = new Scanner(System.in);
            while (true) {
                pages = numberOfPages;
                do {
                    System.out.printf("%n%nвведите номер страницы: 1 - %d%n", pages + 1);
                    page = input.nextInt();
                } while (page < 1 && page < pages);

                enterPage = page;
                offset = (enterPage - 1) * CHAR_PAGE;
                turnThePages = offset + CHAR_PAGE;

                byte[] bytes = book.toByteArray();
                len = bytes.length;

                while (offset != turnThePages && offset < len) {
                    System.out.print((char) bytes[offset]);
                    offset++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addFileArrayList(String inNameFile, int count) throws IOException {
        files.clear();
        for (int i = 1; i <= count; i++) {
            files.add(new FileInputStream(inNameFile));
        }
    }

    private static void sequenceFiles(String outNameFile) throws IOException {
        Enumeration<InputStream> enumeration = Collections.enumeration(files);
        SequenceInputStream in = new SequenceInputStream(enumeration);
        FileOutputStream out = new FileOutputStream(outNameFile);
        int x;
        while ((x = in.read()) != -1) {
            out.write((char) x);
        }
        in.close();
        out.close();
    }

    private static void deleteFile(String nameFile) {
        try {
            File file = new File(nameFile);
            if (file.exists()) file.delete(); //удалить файл, если есть
        } catch (Exception e) {
            System.out.println("Проблема с файлом." + e.getMessage());
        }
    }

    private static void newFile(String text, String nameFile) {
        OutputStream out = null;
        deleteFile(nameFile);
        try {
            out = new BufferedOutputStream(new FileOutputStream(nameFile));
            for (int i = 0; i < text.length(); i++) {
                out.write(text.charAt(i));
            }
            out.close();
        } catch (IOException e) {
            System.out.println("Ошибка при открытии/записи файла." + e.getMessage());
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                System.out.println("Ошибка при закрытия файла." + e.getMessage());
            }
        }
    }

    private static void readFile(String nameFile) {
        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(nameFile));
            int x;
            while ((x = in.read()) != -1) {
                System.out.print((char) x);
            }
        } catch (IOException e) {
            System.out.println("Ошибка при открытии/чтения файла." + e.getMessage());
            e.printStackTrace();
        }
        try {
            in.close();
        } catch (IOException e) {
            System.out.println("Ошибка при закрытия файла." + e.getMessage());
        }
    }

}
