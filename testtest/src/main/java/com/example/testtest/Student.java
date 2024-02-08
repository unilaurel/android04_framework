package com.example.testtest;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Student extends ArrayList<Person> {

    public static void main(String[] args) {
        Per person = new Per();

        System.out.println("--------------------------");
//        Class<Student> clazz = Student.class;
//        System.out.println(clazz);
//        Type superclass = clazz.getGenericSuperclass();
//        System.out.println(superclass);
//        if (superclass instanceof Class) {
//            System.out.println("是类");
//        } else {
//            ParameterizedType type = (ParameterizedType) superclass;
//            Type[] arguments = type.getActualTypeArguments();
//            System.out.println("no");
//            System.out.println("输出每个泛型的真实类型");
//            for (Type argument : arguments) {
//                System.out.println(argument);
//            }
//        }
    }
}
