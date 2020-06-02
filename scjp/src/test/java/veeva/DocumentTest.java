package veeva;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class DocumentTest {	

	@Test
	public void testget25CharwithoutCutWord() {		
		
		String result = Document.get25CharwithoutCutWord("Inventory list of sadkha .");
		assertEquals("Inventory list of sadkha .", result);
		
		result = Document.get25CharwithoutCutWord("How the Xray shows your hralth");
		assertEquals("How the Xray shows your hralth", result);
		
	}


	@Test
	public void testPrintDocumentsReport() {
		
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
		
		doc.printDocumentsReport(list);

	}
	
	

}
