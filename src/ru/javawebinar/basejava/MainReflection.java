package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {

    public static void main(String[] args) throws IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException {
        Resume r = new Resume("Blabla");
        Field field = r.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(r));
        field.set(r, "new_uuid");
        System.out.println(r);
        //Invoke toString via Reflection Api
        Class clazz = Class.forName(r.getClass().getTypeName());
        Method method = clazz.getDeclaredMethod("toString");
        System.out.println(method.invoke(r));
    }
}