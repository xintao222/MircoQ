/**
 * 
 */
package com.unic.micoq.stages;

import com.unic.cicoco.navigator.PluginLuancher;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author javafx
 * 
 */
public class LauncherActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new PluginLuancher(this).luanch("com.unic.micoq.stages", "LoadStage");
		finish();
	}
}
