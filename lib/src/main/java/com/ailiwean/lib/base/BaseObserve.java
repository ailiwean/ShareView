package com.ailiwean.lib.base;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.ailiwean.lib.utils.PatternUtils;
import com.ailiwean.lib.utils.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class BaseObserve<T, V extends BaseViewHolder> {

    ArrayList<Class> classes = new ArrayList<>();

    public BaseObserve() {
        autoAnaylsType();
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

        TypeToken typeToken = new TypeToken<HashMap<HashMap<ArrayList, TextView>, Integer>>() {
        }.getType();

        Log.e("TAG", typeToken.equals(classes) + "");

    }

    public abstract void response(V vh, T t);

}
