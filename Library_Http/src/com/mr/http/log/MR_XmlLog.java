package com.mr.http.log;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import android.util.Log;

import com.mr.http.util.LogManager;

/**
 * Xml Log
 */
public class MR_XmlLog {

    public static void printXml(String tag, String xml, String headString) {

    	tag = LogManager.PREFIX + tag;
    	
        if (xml != null) {
            xml = MR_XmlLog.formatXML(xml);
            xml = headString + "\n" + xml;
        } else {
            xml = headString + LogManager.NULL_TIPS;
        }

        MR_PrintUtil.printLine(tag, true);
        String[] lines = xml.split(LogManager.LINE_SEPARATOR);
        for (String line : lines) {
            if (!MR_PrintUtil.isEmpty(line)) {
            	   Log.e(tag,"║ " + line);
            }
        }
        MR_PrintUtil.printLine(tag, false);
    }

    public static String formatXML(String inputXML) {
        try {
            Source xmlInput = new StreamSource(new StringReader(inputXML));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(xmlInput, xmlOutput);
            return xmlOutput.getWriter().toString().replaceFirst(">", ">\n");
        } catch (Exception e) {
            e.printStackTrace();
            return inputXML;
        }
    }

}
