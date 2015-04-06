package com.niupiao.niupiao.utils;

/**
 * Created by Inanity on 4/6/2015.
 */

/*
 * StringUtils is a utility class that provides some basic String manipulation methods.
 */
public class StringUtils {

    /*
     * A very small utility method. Based in part on producing the same output as
     * a similar method as org.apache.commons.lang3.StringUtils
     *
     * @param String str is the string to be repeated.
     * @param int num is the number of times to repeat the string. If 0, an empty string is returned.
     *        Values lss than 0 produce output identical to 0.
     */
    public static String repeatHelper(String str, int num){
        String output = "";
        for(int i = 0; i < num; i ++){
            output = output + str;
        }
        return output;
    }
}
