package veeva;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class DocumentTest {	

	@Test
	public void testGet25CharwithoutCutWord_Cut() {
		String result = Document.get25CharwithoutCutWord("How the Xray shows your health status");
		assertEquals("How the Xray shows your health...", result);		
	}
	
	@Test
	public void testGet25CharwithoutCutWord_NoCut() {		
		String result = Document.get25CharwithoutCutWord("Inventory list of sadkha .");
		assertEquals("Inventory list of sadkha .", result);
	}
	
	@Test
	public void testGet25CharwithoutCutWordOnDuplicateWord() {		
		String result = Document.get25CharwithoutCutWord("Justin called himself Justin because his legal name is hard to pronounce.");
		assertEquals("Justin called himself Justin...", result);		
	}
	
	@Test
	public void testGet25CharwithoutCutWord_singleLongWord() {		
		String result = Document.get25CharwithoutCutWord("JustinCalledHimselfJustinJustinJustinerhdhrtydhfghd");
		assertEquals("JustinCalledHimselfJustin", result);		
	}



	@Test
	public void testSort() {
		
		List<Document> list =  new ArrayList<>();
		
		Document doc = new Document();
		doc.name = "Xray" ;
		doc.description = "How the Xray shows your hralth";
		doc.createdBy = "Janet Smith";
		doc.createdTime = new Date().getTime() - 30000000000l;
		doc.modifiedTime = doc.createdTime;
		doc.sizeInBytes = 12000l;
		list.add(doc);
		
		doc = new Document();
		doc.name = "Computers" ;
		doc.description = "Inventory list of sadkha .";
		doc.createdBy = "Janet Smith";
		doc.createdTime = new Date().getTime();
		doc.modifiedTime = doc.createdTime;
		doc.sizeInBytes = 423l;
		list.add(doc);
		
		doc = new Document();
		doc.name = "Bobby Timmons Biography" ;
		doc.description = "An exhaustive look at the hjdf khad";
		doc.createdBy = "Andy Andrews";
		doc.createdTime = new Date().getTime() - 1200000000;
		doc.modifiedTime = doc.createdTime;
		doc.sizeInBytes = 12l;		
		list.add(doc);
		
		doc = new Document();
		doc.name = "Apple Sauce" ;
		doc.description = "Study of apple sauces.";
		doc.createdBy = "Andy Andrews";
		doc.createdTime = new Date().getTime() - 1100000000;
		doc.modifiedTime = doc.createdTime;
		doc.sizeInBytes = 87000000000l;		
		list.add(doc);		
		
		Map<String, List<Document>> result = doc.sort(list);
		
		int i=1;
    	for(String key :  result.keySet()) {
    		List<Document> list2 = result.get(key);
    		if(i==1 || i==2) {
    			assertEquals(key, "ANDY ANDREWS");
    		}

    		for(Document doc2: list2) {
    			String docStr = doc2.toString();
    			if(i==1) assertEquals(docStr, "  Bobby Timmons Biography, An exhaustive look at the hjdf... , 12 bytes, 2020-05-24, 2020-05-24");
    			if(i==2) assertEquals(docStr, "  Apple Sauce, Study of apple sauces. , 81 gb, 2020-05-25, 2020-05-25");
    			if(i==3) assertEquals(docStr, "  Xray, How the Xray shows your hralth , 11 kb, 2019-06-26, 2019-06-26");
    			if(i==4) assertEquals(docStr, "  Computers, Inventory list of sadkha . , 423 bytes, 2020-06-07, 2020-06-07");
    			
    			i++;
    		}
    	}

	}
	
	

}
