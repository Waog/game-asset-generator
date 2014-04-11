package com.assetgenerator.server.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.assetgenerator.server.driveUpload.DriveUpload;
import com.assetgenerator.server.driveUpload.DriveUploadImpl;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.ParentReference;

public class UploadResourceServlet extends HttpServlet {

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
		out.println("<h1>Hello from UploadResourceServlet</h1>");
		out.println("req.getLocalAddr(): " + req.getLocalAddr());
		out.println("req.getLocalName(): " + req.getLocalName());
		out.println("req.getPathInfo(): " + req.getPathInfo());
		out.println("req.getRemoteAddr(): " + req.getRemoteAddr());
		out.println("req.getRemoteHost(): " + req.getRemoteHost());
		out.println("req.getRemoteUser(): " + req.getRemoteUser());
	}

	/**
	 * @throws IOException
	 * @throws ServletException
	 * 
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		super.doPost(req, res);
		res.setHeader("Access-Control-Allow-Origin", "*");
		res.setHeader("Access-Control-Allow-Methods",
				"GET,POST,PUT,DELETE,OPTIONS");
		res.setHeader("Access-Control-Allow-Headers", "Content-Type");
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		res.setHeader("Access-Control-Allow-Origin", "*");
		res.setHeader("Access-Control-Allow-Methods",
				"GET,POST,PUT,DELETE,OPTIONS");
		res.setHeader("Access-Control-Allow-Headers", "Content-Type");

		try {
			StringBuilder sb = new StringBuilder("{\"result\": [");

			if (req.getHeader("Content-Type") != null
					&& req.getHeader("Content-Type").startsWith(
							"multipart/form-data")) {
				ServletFileUpload upload = new ServletFileUpload();

				FileItemIterator iterator = upload.getItemIterator(req);

				while (iterator.hasNext()) {
					sb.append("{");
					FileItemStream item = iterator.next();

					// own code to convert into a file object
					System.out.println("uploading: " + item);
					dynamicUploadToDrive(item);

//					sb.append("\"fieldName\":\"").append(item.getFieldName())
//							.append("\",");
//					if (item.getName() != null) {
//						sb.append("\"name\":\"").append(item.getName())
//								.append("\",");
//					}
//					if (item.getName() != null) {
//						sb.append("\"size\":\"")
//								.append(size(item.openStream())).append("\"");
//					} else {
//						sb.append("\"value\":\"")
//								.append(read(item.openStream())).append("\"");
//					}
					sb.append("}");
					if (iterator.hasNext()) {
						sb.append(",");
					}
				}
			} else {
				sb.append("{\"size\":\"" + size(req.getInputStream()) + "\"}");
			}

			sb.append("]");
			sb.append(", \"requestHeaders\": {");
			@SuppressWarnings("unchecked")
			Enumeration<String> headerNames = req.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				String header = headerNames.nextElement();
				sb.append("\"").append(header).append("\":\"")
						.append(req.getHeader(header)).append("\"");
				if (headerNames.hasMoreElements()) {
					sb.append(",");
				}
			}
			sb.append("}}");

			res.getWriter().write(sb.toString());
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}

	protected int size(InputStream stream) {
		int length = 0;
		try {
			byte[] buffer = new byte[2048];
			int size;
			while ((size = stream.read(buffer)) != -1) {
				length += size;
				// for (int i = 0; i < size; i++) {
				// System.out.print((char) buffer[i]);
				// }
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return length;

	}

	protected String read(InputStream stream) {
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(stream));
		try {
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
			}
		}
		return sb.toString();

	}

	private static void dynamicUploadToDrive(FileItemStream item) {
		
		DriveUpload driveUpload = new DriveUploadImpl();
		driveUpload.upload(item);
	}
}
