package ru.silantyevmn.java_core_professinal.lesson7;

import java.lang.annotation.*;

/**
 * ru.silantyevmn.java_core_professinal.lesson7
 * Created by Михаил Силантьев on 07.12.2017.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AfterSuite {
}
