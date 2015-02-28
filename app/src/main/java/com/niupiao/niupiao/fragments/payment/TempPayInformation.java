package com.niupiao.niupiao.fragments.payment;

/**
 * Created by Inanity on 2/27/2015.
 */
public class TempPayInformation {

    public final static class PayInfo {
        private static String name1 = "";
        private static String name2 = "";
        private static String name3 = "";
        private static String name4 = "";
        private static String name5 = "";

        private static String cell1 = "";
        private static String cell2 = "";
        private static String cell3 = "";
        private static String cell4 = "";
        private static String cell5 = "";

        private static Boolean me1 = false;
        private static Boolean me2 = false;
        private static Boolean me3 = false;
        private static Boolean me4 = false;
        private static Boolean me5 = false;

        public static String[] getNames(){
            String[] output = new String[5];
            output[0] = name1;
            output[1] = name2;
            output[2] = name3;
            output[3] = name4;
            output[4] = name5;

            return output;
        }

        public static String[] getCells(){
            String[] output = new String[5];
            output[0] = cell1;
            output[1] = cell2;
            output[2] = cell3;
            output[3] = cell4;
            output[4] = cell5;

            return output;
        }

        public static Boolean[] getMe(){
            Boolean[] output = new Boolean[5];

            output[0] = me1;
            output[1] = me2;
            output[2] = me3;
            output[3] = me4;
            output[4] = me5;

            return output;
        }

        public static void setNames(String n1, String n2, String n3, String n4, String n5){
            name1 = n1;
            name2 = n2;
            name3 = n3;
            name4 = n4;
            name5 = n5;
        }

        public static void setCells(String c1, String c2, String c3, String c4, String c5){
            cell1 = c1;
            cell2 = c2;
            cell3 = c3;
            cell4 = c4;
            cell5 = c5;
        }

        public static void setMe(Boolean m1, Boolean m2, Boolean m3, Boolean m4, Boolean m5){
            me1 = m1;
            me2 = m2;
            me3 = m3;
            me4 = m4;
            me5 = m5;
        }
    }
}
