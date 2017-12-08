/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.utils;

import java.util.Comparator;

/**
 * A comparator class for DataValue.  Compares objects by their absolute
 * values.
 *
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
    public class DataValueComparator implements Comparator {
        @Override
        public int compare(Object Value1, Object Value2) {
            DataValue v1 = (DataValue) Value1;
            DataValue v2 = (DataValue) Value2;
            if (v1.getAbsoluteValue() < v2.getAbsoluteValue())
                return -1;
            else if (v1.getAbsoluteValue() == v2.getAbsoluteValue())
                return 0;
            else
                return 1;
        }
    }
