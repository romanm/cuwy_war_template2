package edu.cuwy.postjson;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;


public class JSONServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// This will store all received articles
	List<Article> articles = new LinkedList<Article>();

	/***************************************************
	 * URL: /jsonservlet
	 * doPost(): receives JSON data, parse it, map it and send back as JSON
	 ****************************************************/
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{

		// 1. get received JSON data from request
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String json = "";
		if(br != null){
			json = br.readLine();
		}

		// 2. initiate jackson mapper
		ObjectMapper mapper = new ObjectMapper();

		// 3. Convert received JSON to Article
		Article article = mapper.readValue(json, Article.class);

		// 4. Set response type to JSON
		response.setContentType("application/json");

		// 5. Add article to List<Article>
		articles.add(article);

		// 6. Send List<Article> as JSON to client
		mapper.writeValue(response.getOutputStream(), articles);

		System.out.println("------------------- 2 ");
		writeToFile(mapper, articles);
		System.out.println("------------------- 3 ");
	}

	String filePfadBase = "/home/roman/Documents/01_curepathway/work1/cuwy_war_template2/";
	String filePfad = filePfadBase + "target/cuwy_war_template2/db/";
	String fileName = "articles.json";
	String fileSrcPfad = filePfadBase + "src/main/webapp/db/";
	private void writeToFile(ObjectMapper mapper, List<Article> articles) {
		File file = new File(filePfad+fileName);
		writerPrettyPrinter(mapper, articles, file);
		copyDbJsonFile(file);
	}

	private void copyDbJsonFile(File file) {
		File destFile = new File(fileSrcPfad+fileName);
		try {
			FileUtils.copyFile(file, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writerPrettyPrinter(ObjectMapper mapper,
			List<Article> articles2, File file) {
		ObjectWriter writerWithDefaultPrettyPrinter = mapper.writerWithDefaultPrettyPrinter();
		try {
			String writeValueAsString = writerWithDefaultPrettyPrinter.writeValueAsString(articles);
			writerWithDefaultPrettyPrinter.writeValue(file, articles);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
