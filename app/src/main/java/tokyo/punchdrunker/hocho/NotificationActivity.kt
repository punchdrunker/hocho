package tokyo.punchdrunker.hocho

import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.annotation.RequiresApi
import android.support.design.widget.Snackbar
import android.support.v4.app.NotificationCompat
import android.support.v4.app.TaskStackBuilder
import android.support.v7.app.AppCompatActivity
import android.view.View
import tokyo.punchdrunker.hocho.databinding.ActivityNotificationBinding


class NotificationActivity : AppCompatActivity() {
    val notificationManager: NotificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private val binding: ActivityNotificationBinding by lazy {
        DataBindingUtil.setContentView<ActivityNotificationBinding>(this, R.layout.activity_notification)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupToolbar()
        binding.activity = this
    }

    fun navigateToSetting(view: View) {
        val uriString = "package:" + packageName
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse(uriString))
        startActivity(intent)
    }

    // api level 26 より古い端末用
    fun showNotification(view: View) {
        val builder = NotificationCompat.Builder(this, CHANNEL_1)
                .setSmallIcon(R.drawable.ic_notifications_active_black_24dp)
                .setContentTitle("My first notification")
                .setContentText("Hello World!")
        val resultIntent = Intent(this, NotificationActivity::class.java)


        val stackBuilder = TaskStackBuilder.create(this)
        stackBuilder.addParentStack(NotificationActivity::class.java)
        stackBuilder.addNextIntent(resultIntent)
        val resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        )
        builder.setContentIntent(resultPendingIntent)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(2, builder.build())
    }

    // api level 26 以上の端末用
    @RequiresApi(Build.VERSION_CODES.O)
    fun showNotificationChannels(view: View) {
        val names = notificationManager.notificationChannels.map { it.name }
        Snackbar.make(view, "Channels: " + names.joinToString(","), Snackbar.LENGTH_SHORT).show()
    }

    // api level 26 以上の端末用
    @RequiresApi(Build.VERSION_CODES.O)
    fun showNotificationChannelGroups(view: View) {
        val names = notificationManager.notificationChannelGroups.map { it.name }
        Snackbar.make(view, "Groups: " + names.joinToString(","), Snackbar.LENGTH_SHORT).show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel(view: View) {
        createChannel(CHANNEL_1, "news", "This channel is news from hocho app")
        createChannel(CHANNEL_2, "promotion", "This channel is a promotion from hocho app")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun deleteNotificationChannels(view: View) {
        notificationManager.notificationChannels.forEach {
            notificationManager.deleteNotificationChannel(it.id)
        }
        notificationManager.notificationChannelGroups.forEach {
            notificationManager.deleteNotificationChannelGroup(it.id)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createGroupAndChannels(view: View) {
        var group = createGroup(GROUP_1, "Hocho")
        createChannel(
                CHANNEL_1,
                "news",
                "This channel is a news from hocho app",
                NotificationManager.IMPORTANCE_HIGH,
                group.id)
        createChannel(
                CHANNEL_2,
                "promotion",
                "This channel is a promotion from hocho app",
                NotificationManager.IMPORTANCE_HIGH,
                group.id)

        group = createGroup(GROUP_2, "Friends")
        createChannel(
                CHANNEL_3,
                "comment",
                "Comments from friends",
                NotificationManager.IMPORTANCE_HIGH,
                group.id)
        createChannel(
                CHANNEL_4,
                "like",
                "Likes from friends",
                NotificationManager.IMPORTANCE_HIGH,
                group.id)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createGroup(id: String, name: String) : NotificationChannelGroup {
        val group = NotificationChannelGroup(id, name)
        notificationManager.createNotificationChannelGroup(group)
        return group
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel(
            id: String,
            name: String,
            description: String,
            importance: Int = NotificationManager.IMPORTANCE_HIGH,
            groupId: String? = null
    ) {
        val channel = NotificationChannel(id, name, importance)

        channel.description = description
        channel.enableLights(true)
        channel.lightColor = Color.RED
        channel.enableVibration(true)
        channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
        if (!groupId.isNullOrEmpty()) {
            channel.group = groupId
        }
        notificationManager.createNotificationChannel(channel)
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = ""
            setDisplayHomeAsUpEnabled(true)
        }

        binding.toolbarTitle.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getString(R.string.notification_title_v26)
        } else {
            getString(R.string.notification_title)
        }
    }

    companion object {
        const val GROUP_1 = "group_1"
        const val GROUP_2 = "group_2"

        const val CHANNEL_1 = "channel_1"
        const val CHANNEL_2 = "channel_2"
        const val CHANNEL_3 = "channel_3"
        const val CHANNEL_4 = "channel_4"

    }
}