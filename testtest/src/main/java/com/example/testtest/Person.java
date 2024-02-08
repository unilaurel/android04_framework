package com.example.testtest;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class Person<T, E> {

    public Person() {
        Class<? extends Person> aClass = getClass();
        System.out.println("aClass: " + aClass);
        Type type = aClass.getGenericSuperclass();
        if (type instanceof Class) {
            System.out.println("shige lei");
        } else {
            ParameterizedType type1 = (ParameterizedType) type;
            Type[] arguments = type1.getActualTypeArguments();
            for (Type argument : arguments) {
                System.out.println(argument);

            }
        }
    }
}
