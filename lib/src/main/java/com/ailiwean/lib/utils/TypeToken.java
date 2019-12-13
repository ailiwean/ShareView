package com.ailiwean.lib.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

/***
 * 通过匿名内部类实现范型获取
 * @param <T>
 */
public class TypeToken<T> {

    ArrayList<Class> classes = new ArrayList<>();

    public TypeToken(Class c) {
        classes.add(c);
    }

    public TypeToken() {

    }

    public ArrayList<Class> getClasses() {
        return classes;
    }

    public TypeToken getType() {
        autoAnaylsType();
        return this;
    }

    private void autoAnaylsType() {

        try {
            Type superClass = getClass().getGenericSuperclass();
            Type[] oneOrder = ((ParameterizedType) superClass).getActualTypeArguments();

            //一阶
            for (Type type : oneOrder) {
                classes.add(PatternUtils.getClassFromType(type));
            }

            //二阶
            for (Type type : oneOrder) {
                if (type instanceof ParameterizedType) {
                    Type[] twoOrder = ((ParameterizedType) type).getActualTypeArguments();
                    for (Type typeInner : twoOrder)
                        classes.add(PatternUtils.getClassFromType(typeInner));
                }
            }

            //三阶级
            for (Type type : oneOrder) {
                if (type instanceof ParameterizedType) {
                    Type[] twoOrder = ((ParameterizedType) type).getActualTypeArguments();
                    for (Type typeInner : twoOrder) {
                        if (typeInner instanceof ParameterizedType) {
                            Type[] typeInnerInner = ((ParameterizedType) typeInner).getActualTypeArguments();
                            for (Type item : typeInnerInner) {
                                classes.add(PatternUtils.getClassFromType(item));
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

