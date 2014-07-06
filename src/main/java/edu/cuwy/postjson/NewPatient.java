package edu.cuwy.postjson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

public class NewPatient extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		System.out.println("----protected void doPost-----1-");
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String json = "";
		if(br != null){
			json = br.readLine();
		}
		System.out.println(json);
		ObjectMapper mapper = new ObjectMapper();
		PatientesDiagnoses patientesDiagnoses = mapper.readValue(json, PatientesDiagnoses.class);
		System.out.println(patientesDiagnoses);

		System.out.println("----protected void doPost-----2-");
	}

}
