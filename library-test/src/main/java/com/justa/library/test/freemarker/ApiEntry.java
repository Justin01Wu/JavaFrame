package com.justa.library.test.freemarker;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestResult;

public class ApiEntry implements Comparable<ApiEntry> {
	private String httpMethod;
	private String url;
	private String className;
	private String methodName;
	private String comment;
	
	private List<ParameterEntry> parameters = new ArrayList<>();
	

	public ApiEntry(String httpMethod, String url, String className, String methodName) {
		super();
		this.httpMethod = httpMethod;
		this.url = url;
		this.className = className;
		this.methodName = methodName;
	}
	
	public String getHttpMethod() {
		return httpMethod;
	}
	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public List<ParameterEntry> getParameters() {
		return parameters;
	}

	public void setParameters(List<ParameterEntry> parameters) {
		this.parameters = parameters;
	}
	
	public void addParameter(ParameterEntry parameter) {
		parameters.add(parameter);
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	@Override
    public int compareTo(ApiEntry another) {
        String url2 = another.getUrl();
        /* For Ascending order*/
        return this.url.compareTo(url2);

    }

}