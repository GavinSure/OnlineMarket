package util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    private DateUtil(){}

    public static String format(Date date){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(date);
        return format;
    }
}
