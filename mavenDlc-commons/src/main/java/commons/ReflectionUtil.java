package commons;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;

public class ReflectionUtil {

/* Generic Types */
    
    public static <T> Class<T> getGenericTypeClass(Class<?> targetClass) {
        return (Class<T>) ((ParameterizedType) targetClass.getGenericSuperclass()).getActualTypeArguments()[0];
    }
    
    /* Annotations */
    
    public static ArrayList<Field> getFieldsByAnnotation(Object sourceObject, Class<? extends Annotation> annotationClass) {
        ArrayList<Field> fields = new ArrayList<Field>();
        
        for (Field field : sourceObject.getClass().getDeclaredFields()) {
            if ((field.isAnnotationPresent(annotationClass))) 
            {       
                fields.add(field);                
            }
        }
        
        return fields;
    }
    
    /* Fields */
    
    public static void setField(Object object, String fieldName, Object value) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException
    {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
        field.setAccessible(false);
    }
 
    public static Object getFieldValue(Object object, String fieldName) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException
    {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        Object returnValue = (Object) field.get(object);
        field.setAccessible(false);
        
        return returnValue;
    }
 
    public static Object copyValues(Object sourceObject, Class<?> targetClass) throws Exception
    {
        Object targetValue = (Object) targetClass.newInstance();
        
        for (Field field : sourceObject.getClass().getDeclaredFields())
        {
            setField(targetValue, field.getName(), getFieldValue(sourceObject, field.getName()));
        }

        return targetValue;
    }
	
}
