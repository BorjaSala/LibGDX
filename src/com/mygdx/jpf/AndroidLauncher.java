package com.mygdx.jpf;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.jpf.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new Main(), config);
		startSendHttpRequestThread();


	}


	private void startSendHttpRequestThread()
	{
		Thread sendHttpRequestThread = new Thread()
		{

			@Override
			public void run()
			{
				try {
					URL url = new URL("https://www.york.ac.uk/teaching/cws/wws/webpage1.html");

					HttpURLConnection httpConn = (HttpURLConnection)url.openConnection();

					httpConn.setRequestMethod("GET");

					InputStream inputStream = httpConn.getInputStream();
					InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
					BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
					String line = bufferedReader.readLine();

					Juego.setTexto(line);

					httpConn.disconnect();

				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		};
		sendHttpRequestThread.start();
	}

}
