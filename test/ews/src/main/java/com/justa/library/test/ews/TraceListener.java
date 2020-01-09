package com.justa.library.test.ews;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import microsoft.exchange.webservices.data.misc.ITraceListener;

public class TraceListener implements ITraceListener {
	
	public static final ThreadLocal<Boolean> hasWorkingElsewhereEvent = new ThreadLocal<Boolean>()	{
		@Override
		protected Boolean initialValue() {
			return false;
		}
	};

	
	public void trace(String traceType, String traceMessage) {
		CreateXMLTextFile(traceType, traceMessage.toString());
	}

	private void CreateXMLTextFile(String fileName, String traceContent) {
		
		System.out.println(traceContent);
		String[] results = traceContent.split("\n");
		
		try {
			
			
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(results[1]));

			Document doc = db.parse(is);

			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();

			XPathExpression expr = xpath.compile("//CalendarEventArray/CalendarEvent[BusyType='WorkingElsewhere']");
			NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			if (nodes.getLength() > 0) {
				System.out.println("target user has WorkingElsewhere event in target period");
				hasWorkingElsewhereEvent.set(true);
			}

			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);

				if (node instanceof Element) {
					Element ele = (Element) node;
					
					Node type = ele.getElementsByTagName("BusyType").item(0);					
					System.out.println(type.getTextContent());
					
					Node startTime = ele.getElementsByTagName("StartTime").item(0);
					System.out.println(startTime.getTextContent());
					
					Node endTime = ele.getElementsByTagName("EndTime").item(0);
					System.out.println(endTime.getTextContent());

				}

			}

		} catch (Exception e) {
			// If the trace data is not valid XML, save it as a text document.
			e.printStackTrace();
		}
	}
}
