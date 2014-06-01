package com.assetgenerator.server.driveUpload;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Random;

import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.io.FilenameUtils;

import com.assetgenerator.server.driveUpload.hostAuthorization.HostCredentials;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.IOUtils;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.ParentReference;

public class DriveUploadImpl implements DriveUpload {

	@Override
	public URL upload(File file, AbstractInputStreamContent mediaContent) {

		HostCredentials hostCredentials = new HostCredentials();
		Credential credential = hostCredentials.get();

		// Create a new authorized API client
		HttpTransport transport = credential.getTransport();
		JsonFactory jsonFactory = credential.getJsonFactory();
		Drive.Builder builder = new Drive.Builder(transport, jsonFactory,
				credential);
		builder.setApplicationName("anyName");
		Drive service = builder
				.build();

		// attach a parent folder to the given file
		String publicFolderId = getPublicFolderId(service);
		file.setParents(Arrays.asList(new ParentReference()
				.setId(publicFolderId)));

		// potentially use resumable uploads instead of "inserts" here.
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

		String webContentLink = file2.getWebContentLink();
		
		URL url = null;
		try {
			url = new URL(webContentLink);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;
	}

	@Override
	public URL upload(FileItemStream item) {
		File file = new File();
		
		String fileExtension = FilenameUtils.getExtension(item.getName());
		
		file.setTitle("[" + item.getContentType() + "] " + item.getName()
				+ " (" + item.getFieldName() + "_"
				+ Math.abs(new Random().nextLong()) + ")." + fileExtension);
		file.setDescription("Description of " + item.getName());
		file.setMimeType(item.getContentType());

		AbstractInputStreamContent mediaContent = toAbstractInputStreamContent(item);
		return upload(file, mediaContent);
	}

	private static AbstractInputStreamContent toAbstractInputStreamContent(
			FileItemStream item) {
		AbstractInputStreamContent result = null;
		try {
			InputStream inputStream = item.openStream();
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			IOUtils.copy(inputStream, byteArrayOutputStream);
			byte[] byteArray = byteArrayOutputStream.toByteArray();
			result = new ByteArrayContent(item.getContentType(), byteArray);
			result.setType(item.getContentType());
			inputStream.close();
			byteArrayOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	private static String getPublicFolderId(Drive service) {
		try {
			com.google.api.services.drive.Drive.Files.List filesList = service.files().list();
			FileList executeFilesList = filesList.execute();
			java.util.List<File> filesReference = executeFilesList.getItems();
			for (File curFile : filesReference) {
				if ("public".equals(curFile.getTitle())) {
					return curFile.getId();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
