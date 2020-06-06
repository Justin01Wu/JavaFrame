package veeva;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Prints a report of the list of documents in the following format:
 *
 * Group by document.createdBy
 * Sort the groups using document.createdBy ascending, case insensitive
 *      Sort each sub list of documents by document.createdTime ascending
 * Format the output of document.size to be a more friendly format. Ex.  50 mb, 900 k, 342 bytes, etc...
 * Format the dates using the format: yyyy-MM-dd
 * Format the output of document.description such that
 *  - no more than the first 25 characters of the description are displayed
 *  - don't truncate any words unless the first word is longer than 25 characters
 *  - display "..." at the end of the description to indicate that it has been truncated 
 *  (these three characters do not count as part of the 25 character limit)
 *
 * Example:
 * Andy Andrews
 *      "Bobby Timmons Biography","An exhaustive look at the ...",233 mb,2013-05-09,2013-05-14
 *      "Apple Sauce","Study of apple sauces.”,87 gb,2013-05-10,2013-05-10
 *      "Zed","All matters, A to Zed”,924 k,2013-05-12,2013-05-12
 * Janet Smith
 *      "Xray","How the Xray shows your ...",48 mb,2010-10-22,2010-12-02
 *      "Computers","Inventory list of ...",423 bytes,2013-03-01,2013-02-17
 * 
 */
public class Document {
	
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");	
	
    String name;
    String description;
    String createdBy;
    String lastModifiedBy;
    Long sizeInBytes;
    Long createdTime;
    Long modifiedTime;

	public String toString() {
		String size = null;
		if(this.sizeInBytes >1000000000) {
			size = (this.sizeInBytes / 1000000000) + " gb";
		}else if(this.sizeInBytes >1000000) {
			size = (this.sizeInBytes / 1000000) + " mb";
		}else if(this.sizeInBytes >1000) {
			size = (this.sizeInBytes / 1000) + " kb";
		}else  {
			size = (this.sizeInBytes ) + " bytes";
		}
		Date createdAt =  new Date(this.createdTime);
		Date modifiedAt  =  new Date(this.modifiedTime);
		String createdStr = simpleDateFormat.format(createdAt);
		String modifiedStr = simpleDateFormat.format(modifiedAt);
		
		String desc = get25CharwithoutCutWord(this.description);

		return String.format("  %s, %s , %s, %s, %s", this.name, desc, size, createdStr, modifiedStr);
	}
	
	static String get25CharwithoutCutWord(String s) {
		if(s.length()<=25) {
			return s;
		}
		String[] words = s.split("\\W+");
		if(words.length ==1) {
			// cut first word if it is more than 25 
			return s.substring(0,  25);
		}
		int end=0;
		for(String word :words) {
			// reuse 'end' to stop duplicate word
			end = s.indexOf(word, end) + word.length();
			if( end > 25) {
				if(end == s.length()) {
					return s.substring(0,  end) ;
				}else {
					return s.substring(0,  end) + "...";
				}				
			}
		}
		return s;
	}
	


    public void printDocumentsReport(List<Document> documents) {
    	Map<String, List<Document>> sortedKeyDocument = new TreeMap<>();
    	for(Document doc: documents) {
    		String key = doc.createdBy.toUpperCase();
    		List<Document> list = sortedKeyDocument.get(key);
    		if(list == null) {
    			list =  new ArrayList<>();    			
    			sortedKeyDocument.put(key, list);
    		}
    		list.add(doc);
    	}
    	
    	for(String key :  sortedKeyDocument.keySet()) {
    		List<Document> list = sortedKeyDocument.get(key);
    		System.out.println(key);
    		Collections.sort(list, new SortbyCreatedTime()); 

    		for(Document doc: list) {
    			System.out.println(doc);
    		}
    	}
    }
    
	class SortbyCreatedTime implements Comparator<Document> {

		public int compare(Document a, Document b) {
			if (a.createdTime > b.createdTime) {
				return 1;
			} else if (a.createdTime < b.createdTime) {
				return -1;
			} else {
				return 0;
			}
		}
	}

}
