package ru.silantyevmn.java_core_professinal.lesson6;

import org.junit.Test;

/**
 * ru.silantyevmn.java_core_professinal.lesson6
 * Created by Михаил Силантьев on 04.12.2017.
 */

public class TesterMetod1RuntimeException {

    @Test(expected = RuntimeException.class)
    public void metod1RuntimeException() {
        Lesson6.metod1(new int[]{1, 2, 3}, 4);
    }


}
