/*
 * Created on 2006-7-30
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.izhbg.typz.sso.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Administrator TODO To change the template for this generated type
 *         comment go to Window - Preferences - Java - Code Style - Code
 *         Templates
 */
public class ParseUtil {
	private static Log log = LogFactory.getLog(ParseUtil.class);
	public static int isPic(String ext) {
		String pic = "jpg,gif,png,jpeg";
		return pic.indexOf(ext) < 0 ? 2 : 1;
	}
	/*public static String getKeyword(int len, String content, String defaultStr) {
		String str = plainText(content);
		str = StringHelper.getByteString(str, len);
		if (StringHelper.isEmpty(str))
			str = defaultStr;
		return str;
	}*/
	public static String getNumber(String sSource) {
		Pattern p = null; // 正则表达式
		Matcher m = null; // 操作的字符串
		p = Pattern.compile("([0-9]+)", Pattern.CASE_INSENSITIVE);
		m = p.matcher(sSource);
		if (m.find())
			return m.group();
		return "";
	}
	/*public static String filterHtml(String sSource) {
		sSource = tagReplace(sSource, "<b|</b|<p|<img|<strong|</strong");
		sSource = plainText(sSource);
		sSource = tagReverse(sSource);
		return sSource;
	}*/
	public static String tagReplace(String sSource, String sTag) {
		Pattern p = null; // 正则表达式
		Matcher m = null; // 操作的字符串
		String s = null;
		StringBuffer sb = null;
		p = Pattern.compile("(" + sTag + ")", Pattern.CASE_INSENSITIVE);
		m = p.matcher(sSource);
		sb = new StringBuffer();
		while (m.find()) {
			s = m.group().replaceAll("<", "donnedonnedonne");
			m.appendReplacement(sb, s);
		}
		m.appendTail(sb);
		return sb.toString();
	}
	public static String htmlReplace(String sSource) {
		if (StringHelper.isEmpty(sSource))
			return "";
		Pattern p = null; // 正则表达式
		Matcher m = null; // 操作的字符串
		String s = null;
		StringBuffer sb = new StringBuffer();
		p = Pattern.compile("(\\n|\\r|,|'|@)", Pattern.CASE_INSENSITIVE);
		m = p.matcher(sSource);
		while (m.find()) {
			s = m.group().toLowerCase();
			if (s.equals(","))
				s = "，";
			else if (s.equals("'"))
				s = "“";
			else if (s.equals("\n"))
				s = "";
			else if (s.equals("\r"))
				s = "";
			else if (s.equals("@"))
				s = "＠";
			m.appendReplacement(sb, s);
		}
		m.appendTail(sb);
		return sb.toString();
	}
	public static String pakReplace(String sSource) {
		if (StringHelper.isEmpty(sSource))
			return "";
		Pattern p = null; // 正则表达式
		Matcher m = null; // 操作的字符串
		String s = null;
		StringBuffer sb = new StringBuffer();
		p = Pattern.compile("(\\n|\\r|,|'|@)", Pattern.CASE_INSENSITIVE);
		m = p.matcher(sSource);
		while (m.find()) {
			s = m.group().toLowerCase();
			if (s.equals(","))
				s = "，";
			else if (s.equals("'"))
				s = "“";
			else if (s.equals("\n"))
				s = "<br/>";
			else if (s.equals("\r"))
				s = "";
			else if (s.equals("@"))
				s = "＠";
			m.appendReplacement(sb, s);
		}
		m.appendTail(sb);
		return sb.toString();
	}
	public static String tagReplace(String sSource) {
		Pattern p = null; // 正则表达式
		Matcher m = null; // 操作的字符串
		String s = null;
		StringBuffer sb = null;
		p = Pattern.compile("(<b|<p|<img|<strong|</strong)", Pattern.CASE_INSENSITIVE);
		m = p.matcher(sSource);
		sb = new StringBuffer();
		while (m.find()) {
			s = m.group().replaceAll("<", "donnedonnedonne");
			m.appendReplacement(sb, s);
		}
		m.appendTail(sb);
		return sb.toString();
	}
	public static String tagReplace2(String sSource) {
		Pattern p = null; // 正则表达式
		Matcher m = null; // 操作的字符串
		String s = null;
		StringBuffer sb = null;
		p = Pattern.compile("(<br|)", Pattern.CASE_INSENSITIVE);
		m = p.matcher(sSource);
		sb = new StringBuffer();
		while (m.find()) {
			s = m.group().replaceAll("<", "donnedonnedonne");
			m.appendReplacement(sb, s);
		}
		m.appendTail(sb);
		return sb.toString();
	}
	public static String tagReverse(String sSource) {
		Pattern p = null; // 正则表达式
		Matcher m = null; // 操作的字符串
		String s = null;
		StringBuffer sb = null;
		p = Pattern.compile("(donnedonnedonne)", Pattern.CASE_INSENSITIVE);
		m = p.matcher(sSource);
		sb = new StringBuffer();
		while (m.find()) {
			s = "<";
			m.appendReplacement(sb, s);
		}
		m.appendTail(sb);
		return sb.toString();
	}
	public static String getFileInfo(String FileName) {
		InputStreamReader read = null;
		BufferedReader reader = null;
		try {
			log.info(FileName);
			read = new InputStreamReader(new FileInputStream(FileName));
			reader = new BufferedReader(read);
			String line = "";
			StringBuffer sb = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				line = line.trim();
				if (StringHelper.isNotEmpty(line))
					sb.append(line + "\n");
			}
			return sb.toString();
		} catch (Exception ex) {
			log.info(ex.getMessage());
			return "";
		} finally {
			try {
				reader.close();
				read.close();
			} catch (Exception ex) {
				log.info(ex.getMessage());
			}
		}
	}
	public static void writeFileBk(String fileInfo, String fileName) {
		try {
			OutputStreamWriter write;
			BufferedWriter writer;
			write = new OutputStreamWriter(new FileOutputStream(fileName), "utf-8");
			writer = new BufferedWriter(write);
			writer.write(fileInfo);
			writer.close();
			write.close();
		} catch (Exception e) {
			log.error("writeFile Error: " + fileName);
		}
	}
	public static boolean copyFile(String from, String to) {
		File fromFile, toFile;
		fromFile = new File(from);
		toFile = new File(to);
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			toFile.createNewFile();
			fis = new FileInputStream(fromFile);
			fos = new FileOutputStream(toFile);
			int bytesRead;
			byte[] buf = new byte[4 * 1024];
			while ((bytesRead = fis.read(buf)) != -1) {
				fos.write(buf, 0, bytesRead);
			}
			fos.flush();
			fos.close();
			fis.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	public static void Mkdir(String path) {
		try {
			File dir = new File(path);
			if (!dir.exists())
				dir.mkdirs();
		} catch (Exception ex) {
		}
	}
	/*public static String plainUrl(String txtTitle) {
		try {
			Parser myParser = Parser.createParser(txtTitle, null);
			StringBuffer html = new StringBuffer();
			ObjectFindingVisitor visitorLink = new ObjectFindingVisitor(LinkTag.class);
			myParser.visitAllNodesWith(visitorLink);
			Node[] links = visitorLink.getTags();
			for (int i = 0; i < links.length; i++) {
				LinkTag linkTag = (LinkTag) links[i];
				html.append(linkTag.getLink());
			}
			return html.toString();
		} catch (Exception ex) {
			return "";
		}
	}
	public static String plainHref(String txtTitle) {
		try {
			Parser myParser = Parser.createParser(txtTitle, null);
			StringBuffer html = new StringBuffer();
			ObjectFindingVisitor visitorLink = new ObjectFindingVisitor(LinkTag.class);
			myParser.visitAllNodesWith(visitorLink);
			Node[] links = visitorLink.getTags();
			for (int i = 0; i < links.length; i++) {
				LinkTag linkTag = (LinkTag) links[i];
				html.append(linkTag.getLinkText() + ((i < links.length - 1) ? "," : ""));
			}
			return html.toString();
		} catch (Exception ex) {
			return "";
		}
	}
	public static String plainHref2(String txtTitle) {
		try {
			Parser myParser = Parser.createParser(txtTitle, null);
			StringBuffer html = new StringBuffer();
			ObjectFindingVisitor visitorLink = new ObjectFindingVisitor(LinkTag.class);
			myParser.visitAllNodesWith(visitorLink);
			Node[] links = visitorLink.getTags();
			for (int i = 0; i < links.length; i++) {
				LinkTag linkTag = (LinkTag) links[i];
				html.append("," + linkTag.getLinkText());
			}
			return html.toString();
		} catch (Exception ex) {
			return "";
		}
	}
	public static String plainImg(String txtTitle) {
		try {
			Parser myParser = Parser.createParser(txtTitle, null);
			StringBuffer html = new StringBuffer();
			ObjectFindingVisitor visitorLink = new ObjectFindingVisitor(ImageTag.class);
			myParser.visitAllNodesWith(visitorLink);
			Node[] links = visitorLink.getTags();
			for (int i = 0; i < links.length; i++) {
				ImageTag linkTag = (ImageTag) links[i];
				html.append(linkTag.getImageURL());
			}
			return html.toString();
		} catch (Exception ex) {
			return "";
		}
	}
	public static String plainText(String txtTitle) {
		try {
			Parser myParser = Parser.createParser(txtTitle, null);
			StringBuffer html = new StringBuffer();
			for (NodeIterator ih = myParser.elements(); ih.hasMoreNodes();)
				html.append(ih.nextNode().toPlainTextString());
			txtTitle = html.toString();
			return txtTitle;
		} catch (Exception ex) {
			return "";
		}
	}
	public static boolean matchKeywords(String sSource, String sKey) {
		if (StringHelper.isEmpty(sSource))
			return false;
		Pattern p = null; // 正则表达式
		Matcher m = null; // 操作的字符串
		p = Pattern.compile(sKey, Pattern.CASE_INSENSITIVE);
		m = p.matcher(sSource);
		return m.find();
	}
	public static String fitHtml(String tmpContent) {
		try {
			Parser myParser = Parser.createParser(tmpContent, null);
			StringBuffer html = new StringBuffer();
			for (NodeIterator i = myParser.elements(); i.hasMoreNodes();)
				html.append(i.nextNode().toHtml());
			tmpContent = html.toString();
			return tmpContent;
		} catch (Exception ex) {
			return "";
		}
	}*/
	public static void main(String[] args) {
		try {
		} catch (Exception e) {
		}
	}
}
