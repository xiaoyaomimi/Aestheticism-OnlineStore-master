package com.caidongdong.aestheticism.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.caidongdong.aestheticism.R;
import com.caidongdong.aestheticism.activity.SettingActivity;

/**
 * Aestheticism
 * 作者：caidongdong on 2015/12/15 10:26
 * 邮箱：mircaidong@163.com
 */
public class NotificationUtils {

    /**
     * 消息通知
     * @param msg
     * @param title
     * @param context
     */
    public static void notification(String msg,String title,Context context) {
        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        //定义下拉通知栏时要展现的内容信息
        CharSequence contentTitle = title;
        CharSequence contentText = msg;
        Notification.Builder builder = new Notification.Builder(context);
        Intent intent = new Intent(context, SettingActivity.class);//点击该通知后要跳转的Activity
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("isFirst", true);   //是否直接是跳转到此界面
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen( System.currentTimeMillis())
                .setContentTitle(title)
                .setContentText(msg);
        CharSequence tickerText = msg;
       Notification notification = new Notification.Builder(context)
                                                    .setTicker(msg)
                                                    .setContentIntent(pendingIntent)
                                                    .setContentTitle(title)
                                                    .setContentText(msg)
//                                                    .setLargeIcon(abitmap)
                                                    .setSmallIcon(R.mipmap.ic_launcher)
                                                    .setWhen( System.currentTimeMillis()).build();

        notification.flags = Notification.FLAG_AUTO_CANCEL;//点击后自动消失
        manager.notify(0, notification);//发动通知,id由自己指定，每一个Notification对应的唯一标志
    }
}
