package com.assetgenerator.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

}
