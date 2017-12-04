package ru.silantyevmn.java_core_professinal.lesson6;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.*;


/**
 * ru.silantyevmn.java_core_professinal.lesson6
 * Created by Михаил Силантьев on 04.12.2017.
 */

@RunWith(Parameterized.class)
public class TesterMetod1 {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new int[]{1, 2, 3, 4}, new int[0]},
                {new int[]{1, 4, 3, 2}, new int[]{3, 2}},
                {new int[]{4, 2, 3, 3}, new int[]{2, 3, 3}},
                {new int[]{1, 4, 1, 2}, new int[]{1, 2}},
        });
    }

    private int[] inArr;
    private int[] outArr;

    public TesterMetod1(int[] inArr, int[] outArr) {
        this.inArr = inArr;
        this.outArr = outArr;
    }

    @Test
    public void metod1Test() {
        Assert.assertArrayEquals(outArr, Lesson6.metod1(inArr, 4));
    }


}
