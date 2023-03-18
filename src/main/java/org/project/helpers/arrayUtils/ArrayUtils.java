package org.project.helpers.arrayUtils;

import java.util.List;

/**
 * Provides various methods work around arrays and collections.
 */
public class ArrayUtils {

    /**
     * Checks if a list is empty or not.
     * @param list list to check.
     * @return True: if List is empty or null. False: if not.
     * @param <T> Object type of the items within the list.
     */
    public static <T> boolean isEmpty(List<T> list){
        if(list == null)
            return true;

        for (Object object : list) {
            if (object != null)
                return false;
        }
        return true;
    }

    /**
     * Checks if an array is empty or not.
     * @param array array to check.
     * @return True: if array is empty or null. False: if not.
     * @param <T> Object type of the items within the array.
     */
    public static <T> boolean isEmpty(T[] array){
        if(array == null)
            return true;

        for (Object object : array) {
            if (object != null)
                return false;
        }
        return true;
    }

    /**
     * Checks if an index exist within a given list.
     * @param list list to check.
     * @param index index to look for.
     * @return True: if index exist. False: if not.
     */
    public static boolean isIndexInList(List<?> list, int index){
        if(isEmpty(list))
            return false;
        if(index >= list.size() || index < 0)
            return false;
        return true;
    }

    /**
     * Checks if an index exist within a given array.
     * @param array array to check.
     * @param index index to look for.
     * @return True: if index exist. False: if not.
     */
    public static <T> boolean isIndexInArray(T[] array, int index){
        if(isEmpty(array))
            return false;
        if(index >= array.length || index < 0)
            return false;
        return true;
    }
}
