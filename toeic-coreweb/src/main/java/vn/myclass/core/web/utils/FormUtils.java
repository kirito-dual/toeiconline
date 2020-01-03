package vn.myclass.core.web.utils;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;


public class FormUtils {
    // truyen cac ten cua file jsp map cac thuoc tinh voi class dto
    public static <T> T populate(Class<T> clazz, HttpServletRequest request) {
        T object = null;
        try {
            object = (T) clazz.newInstance();
            BeanUtils.populate(object, request.getParameterMap());
        } catch (InstantiationException e) {

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return object;
    }
}
