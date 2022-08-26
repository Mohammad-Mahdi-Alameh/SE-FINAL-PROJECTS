//package com.example.watchout_frontend_kotlin.activities
//
//import android.app.AlarmManager
//import android.app.PendingIntent
//import android.content.Context
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.app.NotificationCompat
//import com.example.watchout_frontend_kotlin.R
//import com.example.watchout_frontend_kotlin.receivers.NotificationReceiver
//import java.util.concurrent.TimeUnit
//
//
//class LockScreenActitvity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_lock_screen_actitvity)
//        val chanelId = "id"
//        val description = "Ya rab"
//        val builder = NotificationCompat.Builder(this,chanelId )
//            .setSmallIcon(android.R.drawable.arrow_up_float)
//            .setContentTitle(title)
//            .setContentText(description)
//            .setPriority(NotificationCompat.PRIORITY_MAX)
//
//// request code and flags not added for demo purposes
//        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
//
//        builder.setFullScreenIntent(pendingIntent,true) // THIS HERE is the full-screen intent
//        fun Context.scheduleNotification(isLockScreen: Boolean) {
//            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
//            val SCHEDULE_TIME = 10000000000
//            val timeInMillis = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(SCHEDULE_TIME)
//
//            with(alarmManager) {
//                setExact(AlarmManager.RTC_WAKEUP, timeInMillis, getReceiver(isLockScreen))
//            }
//        }
//    }
//    private fun showNotifications(){
//        val chanelId = "id"
//        val description = "Ya rab"
//        val builder = NotificationCompat.Builder(this,chanelId )
//            .setSmallIcon(android.R.drawable.arrow_up_float)
//            .setContentTitle(title)
//            .setContentText(description)
//            .setPriority(NotificationCompat.PRIORITY_MAX)
//
//// request code and flags not added for demo purposes
//        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
//
//        builder.setFullScreenIntent(pendingIntent,true) // THIS HERE is the full-screen intent
//    }
//        private fun Context.getReceiver(isLockScreen: Boolean): PendingIntent {
//            // for demo purposes no request code and no flags
//            return PendingIntent.getBroadcast(
//                this,
//                0,
//                NotificationReceiver.build(this, isLockScreen),
//                0
//            )
//        }
//
//}