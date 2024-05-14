package com.example.ItSolutionCore.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GenericUtil {


    public static Long convertToTimeStamp(String jsTime) throws ParseException {
        SimpleDateFormat dateFormat  = new SimpleDateFormat("yyyy-MM-dd");

        Date formattedDate = dateFormat.parse(jsTime);

        return formattedDate.getTime();
    }
}
