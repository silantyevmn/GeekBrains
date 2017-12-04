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
public class TesterMetod2 {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new int[]{1, 2, 3, 4}, true},
                {new int[]{1, 4, 3, 2}, true},
                {new int[]{1, 4, 1, 2}, true},
                {new int[]{0, 2, 3, 3}, false},
                {new int[]{1, 2, 3, 3}, false},
                {new int[]{4, 2, 3, 3}, false},
        });
    }

    private int[] inArr;
    private boolean out;

    public TesterMetod2(int[] inArr, boolean out) {
        this.inArr = inArr;
        this.out = out;
    }

    @Test
    public void metod2Test() {
        Assert.assertEquals(out, Lesson6.metod2(inArr, 4, 1));
    }


}
