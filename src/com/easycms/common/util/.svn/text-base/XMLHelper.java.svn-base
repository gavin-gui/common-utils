package com.easycms.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author dengjiepeng:
 * @areate Date:2012-3-12
 * 
 */
public class XMLHelper {
	private Document doc;
	private Element root;
	public static final String AVAILABLE = "1000";
	public static final String VERIFIED = "VERIFIED";
	public static final String INITIAL = "INITIAL";
	public static final String ACTIVATED = "ACTIVATED";
	public static final String NONE = "NONE";
	public static final String ADD = "ADD";
	public static final String CHANGE = "CHANGE";
	
	public XMLHelper(String xmlStr) {
		try {
			if (xmlStr != null) {
				doc = DocumentHelper.parseText(xmlStr);
				root = doc.getRootElement();
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public XMLHelper(InputStream in) {
		try {
			if (in != null) {
				SAXReader reader = new SAXReader();
				doc = reader.read(in);
				root = doc.getRootElement();
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Document getDoc() {
		return this.doc;
	}

	public Element getRootElement() {
		return this.doc.getRootElement();
	}

	public String asXML() {
		return this.doc.asXML();
	}

	/*	*//**
	 * find the top node
	 * 
	 * @param node
	 * @return
	 */
	/*
	 * public String findNode(String node) { if (root != null &&
	 * root.hasContent()) { return root.elementTextTrim(node); } else { return
	 * null; } }
	 *//**
	 * find the son level Node.
	 * 
	 * @param topNodeStr
	 * @param fristNodeStr
	 * @param secondNodeStr
	 * @param target
	 * @return
	 */
	/*
	 * public String findNode(String topNodeStr, String fristNodeStr, String
	 * secondNodeStr, String target) { String result = null; if (root != null) {
	 * Element topNode = root.element(topNodeStr); if (topNode != null &&
	 * topNode.hasContent()) { Element fristNode =
	 * topNode.element(fristNodeStr); if (fristNode != null &&
	 * fristNode.hasContent()) { Element secondNode =
	 * fristNode.element(secondNodeStr); if (secondNode != null &&
	 * secondNode.hasContent()) { Element targetNode =
	 * secondNode.element(target); if (targetNode != null &&
	 * targetNode.hasContent()) { result = targetNode.getText().trim(); } } } }
	 * } return result; }
	 *//**
	 * find the son level node
	 * 
	 * @param topNodeStr
	 * @param fristNodeStr
	 * @param target
	 * @return
	 */
	/*
	 * public String findNode(String topNodeStr, String fristNodeStr, String
	 * target) { String result = null; if (root != null) { Element topNode =
	 * root.element(topNodeStr); if (topNode != null && topNode.hasContent()) {
	 * Element fristNode = topNode.element(fristNodeStr); if (fristNode != null
	 * && fristNode.hasContent()) { Element targetNode =
	 * fristNode.element(target); if (targetNode != null &&
	 * targetNode.hasContent()) { result = targetNode.getText().trim(); } } } }
	 * return result; }
	 */

	/**
	 * Return text of the target node if this element dose not contain children
	 * element ,else return textual XML of this node.
	 * 
	 * @param target
	 *            The target node name.
	 * @return Text of this node or textual XML of this node.
	 */
	public String findNode(String target) {
		String result = null;
		if (root != null && target != null && !"".equals(target)) {
			List<Element> bottle = new ArrayList<Element>();
			recursionNodes(target, root, bottle);
			if (!bottle.isEmpty()) {
				// System.out.println("[bottle.size]:"+bottle.size());
				Element element = bottle.get(0);
				// System.out.println("element:" + element);
				if (element != null) {
					if (!element.elements().isEmpty()) {
						result = element.asXML();
					} else {
						result = element.getText().trim();
					}
				}
			}
			/*
			 * if(topNode!=null&&topNode.hasContent()) { Element fristNode =
			 * topNode.element(fristNodeStr);
			 * if(fristNode!=null&&fristNode.hasContent()){ Element targetNode =
			 * fristNode.element(target);
			 * if(targetNode!=null&&targetNode.hasContent()) { result =
			 * targetNode.getText().trim(); } } }
			 */
		}
		return result;
	}

	/**
	 * find nodes of the XML tree
	 * 
	 * @param target
	 * @return
	 */
	public List<String> findNodes(String target) {
		String result = null;
		List<String> nodesList = new ArrayList<String>();
		if (root != null && target != null && !"".equals(target)) {
			List<Element> bottle = new ArrayList<Element>();
			recursionNodes(target, root, bottle);
			if (!bottle.isEmpty()) {
				// System.out.println("[bottle.size]:"+bottle.size());
				for (int i = 0; i < bottle.size(); i++) {
					Element element = bottle.get(i);
					if (element != null) {
						if (!element.elements().isEmpty()) {
							result = element.asXML();
						} else {
							result = element.getText().trim();
						}
						nodesList.add(result);
					}

				}
				// System.out.println("element:" + element);
			}
			/*
			 * if(topNode!=null&&topNode.hasContent()) { Element fristNode =
			 * topNode.element(fristNodeStr);
			 * if(fristNode!=null&&fristNode.hasContent()){ Element targetNode =
			 * fristNode.element(target);
			 * if(targetNode!=null&&targetNode.hasContent()) { result =
			 * targetNode.getText().trim(); } } }
			 */
		}
		return nodesList;
	}
	
	public List<String> findChildNodes(String childname,String target) {
		String result = null;
		List<String> nodesList = new ArrayList<String>();
		if (root != null && target != null && !"".equals(target)) {
			Element child = root.element(childname);
			if(child != null) {
				List<Element> bottle = new ArrayList<Element>();
				recursionNodes(target, child, bottle);
				if (!bottle.isEmpty()) {
					// System.out.println("[bottle.size]:"+bottle.size());
					for (int i = 0; i < bottle.size(); i++) {
						Element element = bottle.get(i);
						if (element != null) {
							if (!element.elements().isEmpty()) {
								result = element.asXML();
							} else {
								result = element.getText().trim();
							}
							nodesList.add(result);
						}
						
					}
					// System.out.println("element:" + element);
				}
			}
			/*
			 * if(topNode!=null&&topNode.hasContent()) { Element fristNode =
			 * topNode.element(fristNodeStr);
			 * if(fristNode!=null&&fristNode.hasContent()){ Element targetNode =
			 * fristNode.element(target);
			 * if(targetNode!=null&&targetNode.hasContent()) { result =
			 * targetNode.getText().trim(); } } }
			 */
		}
		return nodesList;
	}
	
	

	@SuppressWarnings("unchecked")
	private void recursionNodes(String target, Element parentNode,
			List<Element> bottle) {
		if (target != null && !"".equals(target) && parentNode != null) {
			// 1.Return null if the target node is not exist
			List<Element> elementList = parentNode.elements();
			for (Element e : elementList) {
				// System.out.println("e:"+e.getName()+",text:"+e.getTextTrim());
				if (target.equals(e.getName().trim())) {
					// System.out.println("find it e:"+e.getName()+",text:"+e.getTextTrim());
					bottle.add(e);
					// break;
				} else if (!e.elements().isEmpty()) {
					recursionNodes(target, e, bottle);
				}
			}
		}
	}

	public static void main(String[] args) {
		String xmlStr = null;
		try {
			InputStream in = XMLHelper.class.getClassLoader()
					.getResourceAsStream("test.xml");
			xmlStr = IOUtils.toString(in);
			XMLHelper helper = new XMLHelper(xmlStr);
			// String result = helper.findNode("record");
			// System.out.println("result:"+result);
			String num = helper.findNode("oversea-number");
			System.out.println("".equals(num));
			/*
			 * List<String> list = helper.findNodes("record"); for(String node :
			 * list) { System.out.println(node); }
			 */
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
