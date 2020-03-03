package tokyo.punchdrunker.hocho

import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import tokyo.punchdrunker.hocho.databinding.ActivityNotificationBinding


class NotificationActivity : AppCompatActivity() {
    private val notificationAppId = 2
    private val intentExtraKey = "notify"
    private val notificationManager: NotificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private val binding: ActivityNotificationBinding by lazy {
        DataBindingUtil.setContentView<ActivityNotificationBinding>(this, R.layout.activity_notification)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupToolbar()
        binding.activity = this

        // 通知を開いた時だけ、メッセージを表示する
        if (intent.getBooleanExtra(intentExtraKey, false)) {
            Snackbar.make(binding.root, "Did you tap notification?", Snackbar.LENGTH_SHORT).show()
        }
    }

    fun navigateToSetting(view: View) {
        val uriString = "package:$packageName"
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse(uriString))
        startActivity(intent)
    }

    // 普通の通知
    fun showNotification(view: View) {
        val builder = NotificationCompat.Builder(this, Channel.C1.id)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("My first notification")
                .setContentText("Hello World!")
        val resultIntent = Intent(this, NotificationActivity::class.java)
        // Activityにパラメータを渡す
        resultIntent.putExtra(intentExtraKey, true)

        val stackBuilder = TaskStackBuilder.create(this)
        stackBuilder.addParentStack(NotificationActivity::class.java)
        stackBuilder.addNextIntent(resultIntent)
        val resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        )
        builder.setContentIntent(resultPendingIntent)
        notificationManager.notify(notificationAppId, builder.build())

        Snackbar.make(view, "check notification instatus bar", Snackbar.LENGTH_SHORT).show()
    }

    // 大きな画像付きの通知
    fun showBigPictureNotification(view: View) {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.img_drawer_droidkaigi)
        val builder = NotificationCompat.Builder(this, Channel.C2.id)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Using Big Picture")
                .setContentText("this notification is using big picture style")
                .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
        notificationManager.notify(notificationAppId, builder.build())

        Snackbar.make(view, "check notification instatus bar", Snackbar.LENGTH_SHORT).show()
    }

    // 重要な通知
    fun showImportantNotification(view: View) {
        val builder = NotificationCompat.Builder(this, Channel.C4.id)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Super Important!!!!")
                .setContentText("Hello Woooooooooorldddddzdzdz!!!!!")
        notificationManager.notify(notificationAppId, builder.build())

        Snackbar.make(view, "this is head up notification", Snackbar.LENGTH_SHORT).show()
    }

    // api level 26 以上の端末用
    @RequiresApi(Build.VERSION_CODES.O)
    fun showNotificationChannels(view: View) {
        val names = notificationManager.notificationChannels.map { it.name }
        Snackbar.make(view, "Channels: " + names.joinToString(","), Snackbar.LENGTH_SHORT).show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun deleteNotificationChannels(view: View) {
        notificationManager.notificationChannels.forEach {
            notificationManager.deleteNotificationChannel(it.id)
        }
        notificationManager.notificationChannelGroups.forEach {
            notificationManager.deleteNotificationChannelGroup(it.id)
        }
        Snackbar.make(view, "deleted channels and groups", Snackbar.LENGTH_SHORT).show()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createGroupAndChannels(view: View) {
        val group1 = createGroup(Group.First)
        createChannel(Channel.C1, group1.id)
        createChannel(Channel.C2, group1.id)

        val group2 = createGroup(Group.Second)
        createChannel(Channel.C3, group2.id)
        createChannel(Channel.C4, group2.id)

        val group3 = createGroup(Group.Third)
        createChannel(Channel.C5, group3.id)
        createChannel(Channel.C6, group3.id)

        Snackbar.make(view, "created channels and groups", Snackbar.LENGTH_SHORT).show()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createGroup(group: Group): NotificationChannelGroup {
        val notificationGroup = NotificationChannelGroup(group.id, group.groupName)
        notificationManager.createNotificationChannelGroup(notificationGroup)
        return notificationGroup
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel(
            channel: Channel,
            groupId: String? = null
    ) {
        val notificationChannel = NotificationChannel(channel.id, channel.channelName, channel.impotance)

        notificationChannel.description = channel.description
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.RED
        notificationChannel.enableVibration(true)
        notificationChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
        if (!groupId.isNullOrEmpty()) {
            notificationChannel.group = groupId
        }
        notificationManager.createNotificationChannel(notificationChannel)
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

    enum class Group(val id: String, val groupName: String) {
        First("group_1", "DroidKaigi"),
        Second("group_2", "Hocho"),
        Third("group_3", "Tokyo")
    }

    enum class Channel(
            val id: String,
            val channelName: String,
            val description: String,
            val impotance: Int) {
        C1("channel_1", "news", "This channel is a news from hocho app", NotificationManager.IMPORTANCE_LOW),
        C2("channel_2", "promotion", "This channel is a promotion from hocho app", NotificationManager.IMPORTANCE_LOW),
        C3("channel_3", "comment", "Comments from friends", NotificationManager.IMPORTANCE_DEFAULT),
        C4("channel_4", "like", "like from friends", NotificationManager.IMPORTANCE_MAX),
        C5("channel_5", "fizz", "fizz notification", NotificationManager.IMPORTANCE_MIN),
        C6("channel_6", "buzz", "buzz notification", NotificationManager.IMPORTANCE_NONE)
    }
}

