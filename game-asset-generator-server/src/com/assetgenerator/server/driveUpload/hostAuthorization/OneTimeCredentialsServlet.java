package com.assetgenerator.server.driveUpload.hostAuthorization;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.assetgenerator.server.googleAuthorization.GoogleAuthCodeForTokenExchanger;
import com.assetgenerator.server.googleAuthorization.GoogleAuthCodeForTokenExchanger.CodeExchangeException;
import com.assetgenerator.server.googleAuthorization.GoogleAuthCodeForTokenExchanger.NoRefreshTokenException;
import com.google.api.client.auth.oauth2.Credential;

/**
 * <p>
 * The second, third and forth step of the authorization process of the drive
 * host are done here.
 * </p>
 * <p>
 * <b>The second step</b> of the authorization process: Receiving an Authorization
 * Code. And trade it away for the according credentials.
 * {@link GoogleAuthCodeForTokenExchanger} is used for this trade. The
 * HTTP-communication of this trading process is hidden from this class.
 * </p>
 * <p>
 * <b>The third step:</b> Receiving credentials for the credentials (containing
 * authToken and refreshToken). A method of
 * {@link GoogleAuthCodeForTokenExchanger} returns the credentials.
 * </p>
 * <p>
 * <b>The forth step:</b> Store the credentials in the {@link HostCredentials}. After
 * this step the {@link HostCredentials} may be used to upload files to the
 * drive host.
 * </p>
 * 
 * @author oli
 * 
 */
public class OneTimeCredentialsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

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
		String authCode = req.getParameter("code");
		if (authCode != null && authCode.length() > 0) {

			out.println("Received an auth code:<br />");
			out.println(authCode + " <br />");

			Credential credentials = null;
			try {
				credentials = GoogleAuthCodeForTokenExchanger.getCredentials(
						authCode, "");
			} catch (CodeExchangeException e) {
				e.printStackTrace();
			} catch (NoRefreshTokenException e) {
				e.printStackTrace();
			}

			out.println("Traded the auth code for credentials:<br />");
			out.println("" + credentials + " <br />");

			HostCredentials hostCredentials = new HostCredentials();
			hostCredentials.set(credentials);
		} else {
			out.println("Error: No Authorization Code received.");
		}

	}
}
