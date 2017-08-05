package cesc.shang.utilslib.utils.debug;

import android.text.TextUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by shanghaolongteng on 2017/8/4.
 */

public class ReflectUtils {
    public String analyzeClass(String className) {
        if (!TextUtils.isEmpty(className)) {
            try {
                return analyzeClass(getClass(className));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Class<?> getClass(String className) throws ClassNotFoundException {
        if (TextUtils.isEmpty(className)) {
            return null;
        } else {
            return Class.forName(className);
        }
    }

    public String analyzeClass(Class clazz) {
        if (clazz == null) {
            return null;
        } else {
            StringBuilder builder = new StringBuilder();

            formatClassInfo(clazz, builder);
            appendLinePartition(builder);

            formatFieldsInfo(clazz, builder);
            appendLineFeed(builder);

            formatConstructorsInfo(clazz, builder);
            appendLineFeed(builder);

            formatMethodsInfo(clazz, builder);
            appendLineFeed(builder);

            return builder.toString();
        }
    }

    protected void formatMethodsInfo(Class clazz, StringBuilder builder) {
        Method[] methods = clazz.getMethods();
        if (methods != null && methods.length > 0) {
            for (Method method : methods) {
                String classModifier = Modifier.toString(method.getModifiers());
                if (!TextUtils.isEmpty(classModifier))
                    builder.append(classModifier).append(" ");
                builder.append(method.getReturnType().getSimpleName()).append(" ");
                builder.append(method.getName());
                builder.append("( ");
                Class<?>[] params = method.getParameterTypes();
                if (params != null && params.length > 0) {
                    for (Class<?> param : params) {
                        builder.append(param.getSimpleName()).append(" ");
                    }
                }
                builder.append(")");
                appendLineFeed(builder);
            }
        }
    }

    public void formatConstructorsInfo(Class clazz, StringBuilder builder) {
        Constructor<?>[] constructors = clazz.getConstructors();
        if (constructors != null && constructors.length > 0) {
            String className = clazz.getSimpleName();
            for (Constructor<?> constructor : constructors) {
                String classModifier = Modifier.toString(constructor.getModifiers());
                if (!TextUtils.isEmpty(classModifier))
                    builder.append(classModifier).append(" ");
                builder.append(className).append(" ");
                Class<?>[] params = constructor.getParameterTypes();
                builder.append("( ");
                if (params != null && params.length > 0) {
                    for (Class<?> param : params) {
                        builder.append(param.getSimpleName()).append(" ");
                    }
                }
                builder.append(")");
                appendLineFeed(builder);
            }
        }
    }

    public void formatFieldsInfo(Class clazz, StringBuilder builder) {
        Field[] fields = clazz.getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                String classModifier = Modifier.toString(field.getModifiers());
                if (!TextUtils.isEmpty(classModifier))
                    builder.append(classModifier).append(" ");
                builder.append(field.getType().getSimpleName()).append(" ");
                builder.append(field.getName());
                appendLineFeed(builder);
            }
        }
    }

    public void formatClassInfo(Class clazz, StringBuilder builder) {
        String classModifier = Modifier.toString(clazz.getModifiers());
        if (!TextUtils.isEmpty(classModifier))
            builder.append(classModifier).append(" ");
        builder.append(clazz.getSimpleName());
        Class superClass = clazz.getSuperclass();
        if (superClass != null && superClass != Object.class) {
            builder.append(" extends ").append(superClass.getSimpleName());
        }
    }

    private void appendLinePartition(StringBuilder builder) {
        appendLineFeed(builder);
        appendLineFeed(builder);
    }

    private void appendLineFeed(StringBuilder builder) {
        builder.append("\n");
    }
}
