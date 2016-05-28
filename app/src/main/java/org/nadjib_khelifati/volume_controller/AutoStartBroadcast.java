package org.nadjib_khelifati.volume_controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AutoStartBroadcast extends BroadcastReceiver {

	public void onReceive(Context context, Intent intent) {

		// Start a service after boot system
		context.startService(new Intent(context, ControlVolumeService.class));
	}
}
