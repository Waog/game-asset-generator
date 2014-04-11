package com.assetgenerator.server.driveUpload;

import java.net.URL;

import org.apache.commons.fileupload.FileItemStream;

import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.services.drive.model.File;

public interface DriveUpload {

	public URL upload(File file, AbstractInputStreamContent mediaContent);
	
	public URL upload(FileItemStream item);
}
