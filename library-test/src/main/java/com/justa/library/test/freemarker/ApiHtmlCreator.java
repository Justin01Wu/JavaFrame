package com.justa.library.test.freemarker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateNotFoundException;
import freemarker.template.Version;

public class ApiHtmlCreator {
	

	public static void create(List<ApiEntry> allApis, OutputStream out) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException{
		// 1. Configure FreeMarker
        //
        // You should do this ONLY ONCE, when your application starts,
        // then reuse the same Configuration object elsewhere.

        Configuration cfg = new Configuration(new Version(2, 3, 20));

        // Where do we load the templates from:
        cfg.setClassForTemplateLoading(ApiHtmlCreator.class, "templates");

        // Some other recommended settings:
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.US);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        // 2. Proccess template(s)
        //
        // You will do this for several times in typical applications.

        // 2.1. Prepare the template input:

        Map<String, Object> input = new HashMap<String, Object>();

        input.put("title", "RESTful Api list");

        input.put("allApis", allApis);

        // 2.2. Get the template

        Template template = cfg.getTemplate("api_template.html");

        // 2.3. Generate the output

        // Write output 
        Writer consoleWriter = new OutputStreamWriter(out);
        template.process(input, consoleWriter);
	}
	
	public static List<ApiEntry> prepareData() throws Exception {
		 List<ApiEntry> allApis = new ArrayList<>();
		 ApiEntry one = new ApiEntry("GET", "/api/users", "Abc", "getAllUser");		 
		 allApis.add(one);
		 one = new ApiEntry("POST", "/api/users", "Abc", "createUser");
		 allApis.add(one);
		 one = new ApiEntry("DELETE", "/api/users/{userId}", "Abc", "deleteUser");
		 allApis.add(one);
		 one = new ApiEntry("PUT", "/api/users/{userId}", "Abc", "updateUser");
		 allApis.add(one);		 
		 
		 one = new ApiEntry("GET", "/api/programs", "ProgramApi", "getAllPrograms");		 
		 allApis.add(one);
		 one = new ApiEntry("POST", "/api/programs", "ProgramApi", "createProgram");
		 allApis.add(one);
		 one = new ApiEntry("DELETE", "/api/programs/{programId}", "ProgramApi", "deleteProgram");
		 allApis.add(one);
		 one = new ApiEntry("PUT", "/api/programs/{programId}", "ProgramApi", "updateProgram");
		 allApis.add(one);
		 
		 one = new ApiEntry("GET", "/api/contracts", "ContractApi", "getAllContract");		 
		 allApis.add(one);
		 one = new ApiEntry("POST", "/api/contracts", "ContractApi", "createContract");
		 allApis.add(one);
		 one = new ApiEntry("DELETE", "/api/contracts/{contractId}", "ContractApi", "deleteContract");
		 allApis.add(one);
		 one = new ApiEntry("PUT", "/api/contracts/{contractId}", "ContractApi", "updateContract");
		 allApis.add(one);
		 
		 return allApis;
		
	}
	
	 public static void main(String[] args) throws Exception {
		 
		 String outputPath = "c:\\temp\\apiDoc.html";
		 
		 List<ApiEntry> allApis =  prepareData();
		 
		 File output = new File(outputPath);
		 FileOutputStream out = new FileOutputStream(output);
		 
		 create(allApis, out);
		 
	 }
}