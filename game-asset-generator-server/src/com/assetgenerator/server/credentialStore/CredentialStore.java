package com.assetgenerator.server.credentialStore;

import com.google.api.client.auth.oauth2.Credential;

public interface CredentialStore {

	public void setCredential(String key, Credential c);

	public Credential getCredential(String key);

}
