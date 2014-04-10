package com.assetgenerator.server.driveUpload.hostAuthorization;

import com.assetgenerator.server.credentialStore.CredentialStore;
import com.assetgenerator.server.credentialStore.CredentialStoreImpl;
import com.google.api.client.auth.oauth2.Credential;

public class HostCredentials {
	private static final String DRIVE_HOST_CREDENTIAL_ID = "driveHostCredentialId";

	private CredentialStore store = new CredentialStoreImpl();

	public void set(Credential c) {
		store.setCredential(DRIVE_HOST_CREDENTIAL_ID, c);
	}

	public Credential get() {
		return store.getCredential(DRIVE_HOST_CREDENTIAL_ID);
	}
}
