package org.nadjib_khelifati.volume_controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// start (or restart) service after start application
		startService(new Intent(this, ControlVolumeService.class));
	}
}
