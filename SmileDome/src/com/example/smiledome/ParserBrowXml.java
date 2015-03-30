package com.example.smiledome;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

/***
 * 解析表情列表文件
 *
 */

public class ParserBrowXml {
	public static List<Smile> getInfo(InputStream inputStream) {

		XmlPullParser parser = Xml.newPullParser();
		int eventType = 0;
		List<Smile> smiles = null;
		Smile smile = null;
		try {
			parser.setInput(inputStream, "UTF-8");
			eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {

				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:

					smiles = new ArrayList<Smile>();
					break;
				case XmlPullParser.START_TAG:
					if ("brow".equals(parser.getName())) {
						smile = new Smile();

					} else if ("code".equals(parser.getName())) {
						smile.setCode(parser.nextText());
					} else if ("name".equals(parser.getName())) {
						smile.setName(parser.nextText());
					}
					break;
				case XmlPullParser.END_TAG:
					if ("brow".equals(parser.getName())) {
						smiles.add(smile);
						smile = null;
					}
					break;

				default:
					break;
				}

				eventType = parser.next();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return smiles;
	}
}
