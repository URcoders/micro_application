package com.linxu.microapp.utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author linxu
 * @version 1.0
 * @date 2018/11/16
 * using to check the something(obj) is null or not
 */
public class EmptyUtil {
    /**
     * is empty or not
     *
     * @param obj obj
     * @return boolean
     */
    public static boolean isEmpty(final String obj) {
        return (obj == null) || (obj.trim().length() <= 0);
    }

    /**
     * is empty or not
     *
     * @param obj obj
     * @return boolean
     */
    public static boolean isEmpty(final Character obj) {
        return (obj == null) || obj.equals(' ');
    }

    /**
     * is empty or not
     *
     * @param obj obj
     * @return boolean
     */
    public static boolean isEmpty(final Object obj) {
        return (obj == null);
    }

    /**
     * is empty or not
     *
     * @param obj obj
     * @return boolean
     */
    public static boolean isEmpty(final Object[] obj) {
        return (obj == null) || (obj.length <= 0);
    }

    /**
     * is empty or not
     *
     * @param obj obj
     * @return boolean
     */
    public static boolean isEmpty(final Collection<?> obj) {
        return (obj == null) || (obj.size() <= 0);
    }

    /**
     * is empty or not
     *
     * @param obj obj
     * @return boolean
     */
    public static boolean isEmpty(final Set<?> obj) {
        return (obj == null) || (obj.size() <= 0);
    }

    /**
     * is empty or not
     *
     * @param obj obj
     * @return boolean
     */
    public static boolean isEmpty(final Map<?, ?> obj) {
        return (obj == null) || (obj.size() <= 0);
    }

    /**
     * is empty or not
     *
     * @param obj obj
     * @return boolean
     */
    public static boolean isEmpty(final List<?> obj) {
        return (obj == null) || (obj.size() <= 0);
    }
}
