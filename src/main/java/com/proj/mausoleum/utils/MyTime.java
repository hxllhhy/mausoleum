package com.proj.mausoleum.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyTime {

    public static String getCurrentTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}
