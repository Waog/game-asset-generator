package com.assetgenerator.server.googleAuthorization;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.json.jackson.JacksonFactory;

public class Client {

	// Path to client_secrets.json which should contain a JSON document such as:
	// {
	// "web": {
	// "client_id": "[[YOUR_CLIENT_ID]]",
	// "client_secret": "[[YOUR_CLIENT_SECRET]]",
	// "auth_uri": "https://accounts.google.com/o/oauth2/auth",
	// "token_uri": "https://accounts.google.com/o/oauth2/token"
	// }
	// }
	private static final String CLIENTSECRETS_LOCATION = "client_secrets.json";

	public static String getId() {
		GoogleClientSecrets secrets = getSecrets();
		return secrets.getWeb().getClientId();
	}

	public static String getSecret() {
		GoogleClientSecrets secrets = getSecrets();
		return secrets.getWeb().getClientSecret();
	}
	
	public static GoogleClientSecrets getSecrets() {
		
		File file = new File(CLIENTSECRETS_LOCATION);
		boolean exists = file.exists();
		InputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JacksonFactory jsonFactory = new JacksonFactory();
		Reader reader = new InputStreamReader(fileInputStream);
		GoogleClientSecrets clientSecrets = null;
		try {
			clientSecrets = GoogleClientSecrets.load(jsonFactory, reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return clientSecrets;
	}
}
