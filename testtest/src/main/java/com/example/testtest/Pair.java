package com.example.testtest;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Pair<K, V> extends ArrayList {
    private K key;
    private V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public static void main(String[] args) {
        Field[] fields = Person.class.getDeclaredFields();
        for (Field field : fields) {
            if (field.getGenericType() instanceof ParameterizedType) {
                ParameterizedType genericType = (ParameterizedType) field.getGenericType();
                Type[] arguments = genericType.getActualTypeArguments();
                for (Type type : arguments) {
                    System.out.println(type);
                }
            }
        }
    }



    static class Person {
        List<Integer> integerList;
        List<Double> doubleList;
    }
}
