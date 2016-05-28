package org.nadjib_khelifati.volume_controller;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.widget.RemoteViews;
import android.widget.Toast;

public class ControlVolumeService extends Service {

	private static String CONTROL_VOLUME_RING = "RING";
	private static String CONTROL_VOLUME_MUSIC = "MUSIC";

	private AudioManager audioManager;

	public void onCreate() {

		audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

		this.showNotification();
	}

	// auto-execute after "onCreate()" if a service dont exist (firstTime),
    // else it execute first
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		if (intent != null) {
			String action = intent.getAction();

			if (action != null)
				this.mofidyVolume(action.equals(CONTROL_VOLUME_RING));
		}

		return super.onStartCommand(intent, flags, startId);
	}

	// Modify volume
	private void mofidyVolume(boolean isRingVolume) {

		if (audioManager == null)
			Toast.makeText(
					this.getApplicationContext(),
					R.string.cantControlVolume,
					Toast.LENGTH_LONG).show();
		else {
			int streamType = (isRingVolume) ? AudioManager.STREAM_RING
					: AudioManager.STREAM_MUSIC;

			int currentVolume = audioManager.getStreamVolume(streamType);

			audioManager.setStreamVolume(streamType, currentVolume,
					AudioManager.FLAG_SHOW_UI);
		}
	}

	// show a notification
	private void showNotification() {

		RemoteViews myRemoteView = new RemoteViews(getPackageName(),
				R.layout.notification_layout);

		Intent intentRing = new Intent(this, ControlVolumeService.class);
		intentRing.setAction(CONTROL_VOLUME_RING);
		PendingIntent pIntentRing = PendingIntent.getService(this, 0,
				intentRing, 0);

		myRemoteView.setOnClickPendingIntent(R.id.buttonRing, pIntentRing);

		Intent intentMusic = new Intent(this, ControlVolumeService.class);
		intentMusic.setAction(CONTROL_VOLUME_MUSIC);
		PendingIntent pIntentMusic = PendingIntent.getService(this, 0,
				intentMusic, 0);

		myRemoteView.setOnClickPendingIntent(R.id.buttonMusic, pIntentMusic);

		Notification.Builder builder = new Notification.Builder(this)
				.setSmallIcon(R.drawable.ic_volume_small)
				.setContent(myRemoteView).setAutoCancel(false)
				.setPriority(Notification.PRIORITY_MAX).setOngoing(true);

		Notification notification = builder.build();

		NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(0, notification);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
