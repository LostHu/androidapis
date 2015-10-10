package com.lity.android.apis.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

public class ObjectsFromXmlWindow extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.v("DEBUG", "hello,  \n方法");
		XmlParser1 parser = new XmlParser1(this);
		parser.startParse();
	}

}

class XmlParser extends Object {
	
	/*
	 * TODO: 1 android 平台上开发,对于本地的节点流(文件)不能像控制台程序那样(res/xml/test.xml)使用,
	 * 		   也不能像打包成jar那样(/res/xml/test.xml)使用。只能以android 平台本身提供的访问方式(getResource().getAssets().open(text.xml)来使用,
	 * 		  并且text.xml文件到放在assets下面.
	 * 
	 */
	
	
	private static final String TAG = "DEBUG";
	
	private String name;
	private String age;
	private String label;
	private Context mContext;
	
	private String tempLabel;
	private String tempContent;
	private List<Map<String, String>> result = new ArrayList<Map<String, String>>();
	private Map<String, String> currentObject;
	
	
//	{
//		Map<String, String> item = new HashMap<String, String>();
//		result.add(item);
//	}
	
	public XmlParser(Context context) {
		mContext = context;
	}
	
	public void startParse() {
		SAXParserFactory sax = SAXParserFactory.newInstance();
		XMLReader reader = null;
		InputStream is = null;
		try {
			is = mContext.getAssets().open("object_arrays.xml");
			byte[] buffer = new byte[300];
//			int length = 0;
//			while(-1 != (length = is.read(buffer))) {
//				Log.v(TAG, new String(buffer, 0, length, "UTF-8"));
//			}
			reader = sax.newSAXParser().getXMLReader();
			reader.setContentHandler(mHandler);
			reader.parse(new InputSource(is));
		} catch (Exception e) {
			Log.v(TAG, "Exception e:" + e);
		}
		finally
		{
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private DefaultHandler mHandler = new DefaultHandler(){
		
		/*
		 * 不解析属性
		 */

		@Override
		public void startDocument() throws SAXException {
			Log.v(TAG, "start parse");
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			label = localName;
			if (label.equals("object")) {
				currentObject = new HashMap<String, String>();
			}
			else if(label.equals("name")) {
				name = "";
			}
			else if (label.equals("age")) {
				age = "";
			}
			Log.v(TAG, "startElement, label:" + label);
			
			
			tempLabel = localName;
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			Log.v(TAG, "characters, label:" + label + ", ch:" + String.copyValueOf(ch, start, length));
			if (label.equals("name")) {
				name += String.copyValueOf(ch, start, length);
			}
			else if (label.equals("age")) {
				age += String.copyValueOf(ch, start, length);
			}
			
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			Log.v(TAG, "endElement, localName: " + localName);

			if (localName.equals("object")) {
				Log.v(TAG, "parsed object, name:" + name + ", age:" + age);
			}
			label = "";
		}

		@Override
		public void endDocument() throws SAXException {
			super.endDocument();
			Log.v(TAG, "end parse");
		}

		
	};
	
}

class XmlParser1 extends Object {
	
	/*
	 * TODO: 1 android 平台上开发,对于本地的节点流(文件)不能像控制台程序那样(res/xml/test.xml)使用,
	 * 		   也不能像打包成jar那样(/res/xml/test.xml)使用。只能以android 平台本身提供的访问方式(getResource().getAssets().open(text.xml)来使用,
	 * 		  并且text.xml文件到放在assets下面.
	 * 
	 */
	
	
	private static final String TAG = "DEBUG";
	
	private Context mContext;
	
	private String tempLabel;
	private String tempContent;
	private List<Map<String, String>> result = new ArrayList<Map<String, String>>();
	private Map<String, String> currentObject;
	
	
//	{
//		Map<String, String> item = new HashMap<String, String>();
//		result.add(item);
//	}
	
	public XmlParser1(Context context) {
		mContext = context;
	}
	
	public void startParse() {
		SAXParserFactory sax = SAXParserFactory.newInstance();
		XMLReader reader = null;
		InputStream is = null;
		try {
			is = mContext.getAssets().open("object_arrays.xml");
			byte[] buffer = new byte[300];
//			int length = 0;
//			while(-1 != (length = is.read(buffer))) {
//				Log.v(TAG, new String(buffer, 0, length, "UTF-8"));
//			}
			reader = sax.newSAXParser().getXMLReader();
			reader.setContentHandler(mHandler);
			reader.parse(new InputSource(is));
		} catch (Exception e) {
			Log.v(TAG, "Exception e:" + e);
		}
		finally
		{
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private DefaultHandler mHandler = new DefaultHandler(){
		
		/*
		 * 不解析属性
		 */

		@Override
		public void startDocument() throws SAXException {
			Log.v(TAG, "start parse");
			tempLabel = "";
			tempContent = "";
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			tempLabel = localName;
			if (tempLabel.equals("object")) {
				currentObject = new HashMap<String, String>();
			}
			tempContent = "";
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			if (!tempLabel.equals("")) {
				tempContent += String.copyValueOf(ch, start, length);
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			if (tempLabel.equals(localName)) {
				currentObject.put(tempLabel, tempContent);
			}
			if (localName.equals("object")) {
				result.add(currentObject);
			}
			tempLabel = "";
		}

		@Override
		public void endDocument() throws SAXException {
			Log.v(TAG, "end parse");
			Log.v(TAG, "result:" + result);
		}

		
	};
	
}
