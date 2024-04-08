package com.justa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.jsonpath.JsonPath;

import net.minidev.json.JSONArray;

public class ApiTestUtil {
	
	private static final Logger LOG = LoggerFactory.getLogger(ApiTestUtil.class);
	/** go through every fields in recursive way to verify JSON structure
	 * expectedJson can has less fields than actual Json for backward compatibility */
	// For non 3rd library implementation, please see https://stackoverflow.com/questions/50967015/how-to-compare-json-documents-and-return-the-differences-with-jackson-or-gson
	public static void verifyJson(Map<String, Object> actualJson, Map<String, Object> expectedJson) throws JsonProcessingException, IOException {
		
		for(String key:  expectedJson.keySet()) {
			Object expectValue = expectedJson.get(key);
		    Object actualValue = JsonPath.read(actualJson, "$."+key);
		    if(expectValue instanceof Map) {		    		
		    	@SuppressWarnings("unchecked")
				Map<String,Object> a = (Map<String, Object>)actualValue;
		    	@SuppressWarnings("unchecked")
		    	Map<String,Object> e = (Map<String,Object>)expectValue;		    	
		    	verifyJson(a,e);
		    }else if(expectValue instanceof JSONArray) {
		    	if(!(actualValue instanceof JSONArray)) {
		    		fail("different type on " + key);
		    	}
		    	List<?>  e = (ArrayList<?>)expectValue;
		    	List<?> a = (ArrayList<?>)actualValue;

		    	if(a.size() != e.size()) {
		    		fail("different size on " + key);
		    	}
		    	//no need to sort before compare because JSON specs said: 
		    	//  An array is an ordered sequence of zero or more values
		    	
		    	for(int i=0;i<a.size();i++) {
		    		Object aa = a.get(i);
		    		Object ee = e.get(i);
		    		if(aa == null) {
		    			if(ee != null) {
		    				fail("expected value should be null on " + i + " of " + key);
		    			}
		    		}else {
		    			if(ee == null) {
		    				fail("expected value should not be null on " + i + " of " + key);
		    			}else {
		    				// both are not null
		    				if(aa instanceof Map) {
		    					if(ee instanceof Map) {
						    		verifyJson((Map<String, Object>)aa, (Map<String, Object>)ee);	    						
		    					}else {
		    						fail("expected value should be a Json Object on " + i + " of " + key);
		    					}
		    				}else if(aa instanceof Double){
		    					if(ee instanceof Double) {
		    						if(!aa.equals(ee)) {
		    							fail("expected value not equals on " + i + " of " + key);
		    						}
		    					}else {
		    						fail("expected value should be Double on " + i + " of " + key);
		    					}
		    					
		    				}else {
		    					LOG.warn("    == notImplemented on " + aa.getClass().getName() + "on " + i + " of " + key);
		    				}
		    				
		    			}
		    		}
		    	}		    	
		    }else {
		    	assertEquals("verify property " + key, expectValue, actualValue);
		    }		    
		}
	}
}
