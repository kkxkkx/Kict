package bit.edu.cn.dictionary.notification;

import java.util.Calendar;

public class SetTime {
    public  static void  setZeroTime(Calendar calendar)
    {
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);

    }
}
