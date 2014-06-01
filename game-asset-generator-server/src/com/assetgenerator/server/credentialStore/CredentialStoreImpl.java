package com.assetgenerator.server.credentialStore;

import java.io.IOException;

import com.assetgenerator.server.googleAuthorization.Client;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.extensions.appengine.datastore.AppEngineDataStoreFactory;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.client.util.store.DataStore;

public class CredentialStoreImpl implements CredentialStore {

	@Override
	public void setCredential(String key, Credential c) {
		AppEngineDataStoreFactory factory = new AppEngineDataStoreFactory();
		DataStore<StoredCredential> store = null;
		try {
			store = factory
					.getDataStore(StoredCredential.DEFAULT_DATA_STORE_ID);
		} catch (IOException e) {
			e.printStackTrace();
		}
		StoredCredential storedCredential = new StoredCredential(c);
		try {
			store.set(key, storedCredential);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Credential getCredential(String key) {
		AppEngineDataStoreFactory factory = new AppEngineDataStoreFactory();
		DataStore<StoredCredential> store = null;
		try {
			store = factory
					.getDataStore(StoredCredential.DEFAULT_DATA_STORE_ID);
		} catch (IOException e) {
			e.printStackTrace();
		}
		StoredCredential storedCredential = null;
		try {
			storedCredential = store.get(key);
		} catch (IOException e) {
			e.printStackTrace();
		}

		HttpTransport httpTransport = new NetHttpTransport();
		JsonFactory jsonFactory = new JacksonFactory();
		GoogleCredential googleCredential = new GoogleCredential.Builder()
				.setTransport(httpTransport).setJsonFactory(jsonFactory)
				.setClientSecrets(Client.getSecrets()).build();
		googleCredential.setAccessToken(storedCredential.getAccessToken());
		googleCredential.setRefreshToken(storedCredential.getRefreshToken());

		return googleCredential;
	}

}
