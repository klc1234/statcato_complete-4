/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.utils;

/**
 * A class representing a data value, containing both its absolute and signed
 * value.
 *
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
    public class DataValue {
        private double absoluteValue;
        private double signedValue;

        public DataValue(double signedValue) {
            this.absoluteValue = Math.abs(signedValue);
            this.signedValue = signedValue;
        }

        public double getAbsoluteValue() {
            return absoluteValue;
        }

        public double getSignedValue() {
            return signedValue;
        }

        public int getSign() {
            return (signedValue >= 0?1:-1);
        }
    }