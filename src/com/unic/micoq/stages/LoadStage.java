/**
 * 
 */
package com.unic.micoq.stages;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.unic.cicoco.plugins.BaseStage;
import com.unic.micoq.R;

/**
 * @author javafx
 * 
 */
public class LoadStage extends BaseStage {

	@Override
	public void onCreate(Bundle savedInstanceState, Intent intent) {
		setContentView(R.layout.activity_load);
		Handler handler = new Handler();
		handler.postAtTime(new Runnable() {

			@Override
			public void run() {
				navigatorProxy.navTo(LoginStage.class);
			}
		}, 3000l);
	}

}
