package com.assetgenerator.server.driveUpload.hostAuthorization;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.assetgenerator.server.googleAuthorization.Client;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.drive.DriveScopes;

/**
 * The first Step of the authorization process of the drive host is done here:
 * Build an authorization URL which contains a redirect URL and the required
 * permissions (called scopes).
 * 
 * In a second step an Authorization Code will be received at the redirect URL.
 * 
 * This class is
 * 
 * @author oli
 * 
 */
public class OneTimeAuthorizationUrlServlet extends HttpServlet {

	// required to build an authorization url:
	private static final String REDIRECT_URI = "http://localhost:8888/game_asset_generator_server/oneTimeCredentials";
	private static final List<String> REQUIRED_SCOPES = Arrays.asList(
			DriveScopes.DRIVE,
			"https://www.googleapis.com/auth/userinfo.email",
			"https://www.googleapis.com/auth/userinfo.profile");

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @throws IOException
	 * @throws ServletException
	 * 
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		// Set response content type
		res.setContentType("text/html");

		res.setHeader("Access-Control-Allow-Origin", "*");
		res.setHeader("Access-Control-Allow-Methods",
				"GET,POST,PUT,DELETE,OPTIONS");
		res.setHeader("Access-Control-Allow-Headers", "Content-Type");

		// Actual logic goes here.
		PrintWriter out = res.getWriter();
		out.println("<h1>Click here to grant an Authorization Code to the App Engine of Game Asset Generator:</h1>");

		String authorizationUrl = getAuthorizationUrl();
		out.println("<a href=\"" + authorizationUrl + "\">link</a>");
	}

	private static String getAuthorizationUrl() {

		// get some flow object
		HttpTransport httpTransport = new NetHttpTransport();
		JacksonFactory jsonFactory = new JacksonFactory();
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
				httpTransport, jsonFactory, Client.getId(), Client.getSecret(),
				REQUIRED_SCOPES).setAccessType("offline")
				.setApprovalPrompt("force").build();

		// build and return an url
		GoogleAuthorizationCodeRequestUrl urlBuilder = flow
				.newAuthorizationUrl().setRedirectUri(REDIRECT_URI);
		return urlBuilder.build();
	}
}
