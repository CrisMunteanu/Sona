package de.syntax_institut.androidabschlussprojekt.domain.util



import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class ReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        Log.d("ReminderReceiver", "Empfange Alarm – Notification wird angezeigt")

        NotificationHelper.showReminderNotification(
            context = context,
            title = "🧘 Zeit für dich",
            message = "Gönn dir eine kurze Meditation oder ein Zitat für den Tag ✨"
        )
    }
}