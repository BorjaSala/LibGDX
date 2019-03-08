package com.mygdx.jpf.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.jpf.Juego;
import com.mygdx.jpf.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new Main(), config);
		startSendHttpRequestThread();
	}


	private static void startSendHttpRequestThread()
	{
		Thread sendHttpRequestThread = new Thread()
		{

			@Override
			public void run()
			{
				try {
					URL url = new URL("http://localhost:8080/examples/jsp/nombre.jsp");

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
