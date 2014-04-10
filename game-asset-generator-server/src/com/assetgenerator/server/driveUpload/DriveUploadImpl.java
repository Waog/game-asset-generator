package com.assetgenerator.server.driveUpload;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.assetgenerator.server.driveUpload.hostAuthorization.HostCredentials;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

public class DriveUploadImpl implements DriveUpload {

	@Override
	public URL upload(File file, FileContent mediaContent) {

		HostCredentials hostCredentials = new HostCredentials();
		Credential credential = hostCredentials.get();

		// Create a new authorized API client
		HttpTransport transport = credential.getTransport();
		JsonFactory jsonFactory = credential.getJsonFactory();
		Drive service = new Drive.Builder(transport, jsonFactory, credential)
				.build();

		File file2 = null;
		try {
			if (mediaContent == null) {
				file2 = service.files().insert(file).execute();
			} else {
				file2 = service.files().insert(file, mediaContent).execute();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("File ID: " + file2.getId());

		String webViewLink = file2.getWebViewLink();
		URL url = null;
		try {
			url = new URL(webViewLink);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return url;
	}
}
