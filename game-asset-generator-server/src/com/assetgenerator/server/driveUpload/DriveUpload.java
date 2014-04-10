package com.assetgenerator.server.driveUpload;

import java.net.URL;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.model.File;

public interface DriveUpload {

	public URL upload(File file, FileContent mediaContent);
}
