package com.niupiao.niupiao.fragments.payment;

/**
 * Created by Inanity on 2/27/2015.
 */
public class TempPayInformation {

    public final static class PayInfo {
        public static String name1 = "";
        public static String name2 = "";
        public static String name3 = "";
        public static String name4 = "";
        public static String name5 = "";

        public static String cell1 = "";
        public static String cell2 = "";
        public static String cell3 = "";
        public static String cell4 = "";
        public static String cell5 = "";

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
    }
}
