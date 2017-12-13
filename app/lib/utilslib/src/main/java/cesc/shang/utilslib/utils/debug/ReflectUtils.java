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
    /**
     * 分析指定类
     *
     * @param className 要分析完全类名
     * @return 分析结果
     */
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

    /**
     * 转换指定类
     *
     * @param className 完全类名
     * @return 指定类Class
     * @throws ClassNotFoundException 指定类不存在
     */
    public Class<?> getClass(String className) throws ClassNotFoundException {
        if (TextUtils.isEmpty(className)) {
            return null;
        } else {
            return Class.forName(className);
        }
    }

    /**
     * 分析指定类
     *
     * @param clazz 要分析类的Class
     * @return 分析结果
     */
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

    /**
     * 分析指定类的函数部分
     *
     * @param clazz   要分析的类
     * @param builder 分析结果缓存
     */
    public void formatMethodsInfo(Class clazz, StringBuilder builder) {
        Method[] methods = clazz.getMethods();
        if (methods != null && methods.length > 0) {
            for (Method method : methods) {
                String classModifier = Modifier.toString(method.getModifiers());
                if (!TextUtils.isEmpty(classModifier)) {
                    builder.append(classModifier).append(" ");
                }
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

    /**
     * 分析指定类的构造器部分
     *
     * @param clazz   要分析的类
     * @param builder 分析结果缓存
     */
    public void formatConstructorsInfo(Class clazz, StringBuilder builder) {
        Constructor<?>[] constructors = clazz.getConstructors();
        if (constructors != null && constructors.length > 0) {
            String className = clazz.getSimpleName();
            for (Constructor<?> constructor : constructors) {
                String classModifier = Modifier.toString(constructor.getModifiers());
                if (!TextUtils.isEmpty(classModifier)) {
                    builder.append(classModifier).append(" ");
                }
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

    /**
     * 分析指定类的变量部分
     *
     * @param clazz   要分析的类
     * @param builder 分析结果缓存
     */
    public void formatFieldsInfo(Class clazz, StringBuilder builder) {
        Field[] fields = clazz.getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                String classModifier = Modifier.toString(field.getModifiers());
                if (!TextUtils.isEmpty(classModifier)) {
                    builder.append(classModifier).append(" ");
                }
                builder.append(field.getType().getSimpleName()).append(" ");
                builder.append(field.getName());
                appendLineFeed(builder);
            }
        }
    }

    /**
     * 分析指定类的继承关系
     *
     * @param clazz   要分析的类
     * @param builder 分析结果缓存
     */
    public void formatClassInfo(Class clazz, StringBuilder builder) {
        String classModifier = Modifier.toString(clazz.getModifiers());
        if (!TextUtils.isEmpty(classModifier)) {
            builder.append(classModifier).append(" ");
        }
        builder.append(clazz.getSimpleName());
        Class superClass = clazz.getSuperclass();
        if (superClass != null && superClass != Object.class) {
            builder.append(" extends ").append(superClass.getSimpleName());
        }
    }

    /**
     * 在缓存中添加空行
     *
     * @param builder 结果缓存
     */
    private void appendLinePartition(StringBuilder builder) {
        appendLineFeed(builder);
        appendLineFeed(builder);
    }

    /**
     * 在缓存中换行
     *
     * @param builder 结果缓存
     */
    private void appendLineFeed(StringBuilder builder) {
        builder.append("\n");
    }
}
