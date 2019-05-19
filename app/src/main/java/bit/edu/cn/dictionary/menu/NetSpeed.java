package bit.edu.cn.dictionary.menu;

import android.net.TrafficStats;
import android.util.Log;

/*
@function   获取网速
 */

public class NetSpeed {

    private static final String TAG = NetSpeed.class.getSimpleName();
    private long lastTotalRxBytes = 0;
    private long lastTimeStamp = 0;

    public String getNetSpeed(int uid) {
        long nowTotalRxBytes = getTotalRxBytes(uid);
        long nowTimeStamp = System.currentTimeMillis();
        //Log.v(TAG, "net"+String.valueOf(nowTotalRxBytes-lastTimeStamp));
        Log.v(TAG, "net"+String.valueOf((nowTotalRxBytes-lastTotalRxBytes)*1000));
        Log.v(TAG, "time"+String.valueOf(nowTimeStamp-lastTimeStamp));
        double speed = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 / (nowTimeStamp - lastTimeStamp));//毫秒转换
        Log.v(TAG,"speed"+speed);
        lastTimeStamp = nowTimeStamp;
        lastTotalRxBytes = nowTotalRxBytes;
        if(speed>1024)
        {
            speed/=2014;
            return String.valueOf(speed)+"M/s";
        }
        return String.valueOf(speed) + " K/s";
    }


    public long getTotalRxBytes(int uid) {
        return TrafficStats.getUidRxBytes(uid) == TrafficStats.UNSUPPORTED ? 0 : (TrafficStats.getTotalRxBytes() / 1024);//转为KB
    }
}
