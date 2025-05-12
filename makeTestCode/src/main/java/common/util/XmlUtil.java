package common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.xml.sax.SAXException;

//import common.form.FormInputItem;


import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathConstants;

//import org.apache.xml.serialize.OutputFormat;

/*
 * 作成日： 2005/01/09
 *
 *
 * ウィンドウ ＞ 設定 ＞ Java ＞ コード・スタイル ＞ コード・テンプレート
 */

/**
 * @author ken
 *
 *  この生成された型コメントのテンプレートを変更するには次を参照。 ウィンドウ ＞ 設定 ＞ Java ＞ コード・スタイル ＞
 * コード・テンプレート
 */
public class XmlUtil {
    private String xmlFilePath = null;

    private DocumentBuilderFactory factory = null;

    private DocumentBuilder builder = null;

    private Document document = null;

    private String encoding = "UTF-8";

    private XPath xpath ;

    private Node rootNode = null ;

    /**
     * XML Dcumentを取得
     *
     * @return
     */
    public Document getDocument() {
        return document;
    }

    public XmlUtil() throws ParserConfigurationException,
    							SAXException, IOException {
//        this.xmlFilePath = xmlFilePath;
        factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
        document = builder.newDocument();

//        xpath = XPathFactory.newInstance().newXPath();
    }
    /**
     * コンストラクタ
     *
     * @param xmlFilePath
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public XmlUtil(String xmlFilePath) throws ParserConfigurationException,
            SAXException, IOException {
        this.xmlFilePath = xmlFilePath;
        factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
        document = builder.parse(new File(xmlFilePath));

//        xpath = XPathFactory.newInstance().newXPath();

    }
    public String getContent(String expression){

		Node node = null ;
		String content = "";
    	try {
			node = (Node) xpath.evaluate(expression, document, XPathConstants.NODE);
			content = node.getTextContent();

		} catch (XPathExpressionException e) {
			//  自動生成された catch ブロック
			e.printStackTrace();
		}
		return content ;
    }
    public ArrayList getContentList(String expression){

		Node itemNode = null ;
		Node childNode = null ;
    	NodeList nodeList = null ;
		String content = "";
		ArrayList vallist = new ArrayList();
//    	try {
//    		nodeList = (NodeList) xpath.evaluate(expression, document, XPathConstants.NODESET);
//
//    		for(int i=0; i < nodeList.getLength(); i++){
//    			itemNode = nodeList.item(i);
//    			NodeList childList = itemNode.getChildNodes();
//
//        		for(int j=0; j < childList.getLength(); j++){
//        			FormInputItem formInputItem = null ;
//
//        			childNode = childList.item(j);
//        			if (childNode.getNodeType()==Node.ELEMENT_NODE){
//	        			content = childNode.getTextContent();
//	        			if (childNode.getNodeName().equals("label")){// input(text)タグのタイトル
//	        				formInputItem = new FormInputItem();
//	        				formInputItem.setLabel(content);
//	        			}else if (childNode.getNodeName().equals("id")){//input(text)タグのID
//	        				formInputItem.setId(content);
//
//	        				vallist.add(formInputItem);
//	        			}
//        			}
//        		}
//    		}
//
//
//		} catch (XPathExpressionException e) {
//			//  自動生成された catch ブロック
//			e.printStackTrace();
//		}
		return vallist ;
    }

    /**
     * root要素を作成する
     * @param elname
     * @return
     */
    public Element createRootElement(String elname){
        Element elem = document.createElement(elname);
        document.appendChild(elem);

        return elem ;
    }
    /**
     * parent の子要素(elname) を作成
     * @param parent
     * @param elname
     * @return
     */
    public Element appendChild(Element parent, String elname){
        Element elem = document.createElement(elname);
        parent.appendChild(elem);

        return elem ;
    }
    /**
     * アトリビュート追加
     *
     * @param parentTagName
     *            親タグ名称
     * @param tagInfo
     *            更新するタグ情報
     */
    public boolean appendPropertyTagAttribute(String parentTagName, UpdateTagInfo tagInfo) {
        boolean rslt = false ;

        //同一アトリビュート存在判定
        if (isPropertyTagAttributeExist(parentTagName, tagInfo)) {
            return rslt ;
        }

        Element configTag = document.getDocumentElement();
        NodeList parentTags = configTag.getElementsByTagName(parentTagName);

        Element parentTag = (Element) parentTags.item(0);

        Element elem = document.createElement("property");

        AttributeParam keyAttr = tagInfo.getKeyAttr();
        AttributeParam updateAttr = tagInfo.getUpdateAttr();
        elem.setAttribute(keyAttr.getName(), keyAttr.getValue());
        elem.setAttribute(updateAttr.getName(), updateAttr.getValue());

        parentTag.appendChild(elem);

        return true ;
    }

    /**
     * アトリビュート更新
     *
     * @param parentTagName
     *            親タグ名称
     * @param tagInfo
     *            更新するタグ情報
     */
    public boolean updateAttribute(String parentTagName, UpdateTagInfo tagInfo) {
        boolean rslt = false ;

        Element configTag = document.getDocumentElement();
        NodeList parentTags = configTag.getElementsByTagName(parentTagName);

        Element parentTag = (Element) parentTags.item(0);

        //<mail>タグ配下の<property>タグのリストを取得
        NodeList propertyTags = parentTag.getElementsByTagName(tagInfo
                .getTagName());

        Element smtpServerPropertyTag = null;
        for (int i = 0; i < propertyTags.getLength(); i++) {
            Element propertyTag = (Element) propertyTags.item(i);

            String tagInfoKeyAttrValue = tagInfo.getKeyAttr().getValue();
            String propertyAttribute = propertyTag.getAttribute(tagInfo
                    .getKeyAttr().getName());

            if (tagInfoKeyAttrValue.equals(propertyAttribute)) {
                smtpServerPropertyTag = propertyTag;
                break;
            }
        }
        if (smtpServerPropertyTag != null) {
            AttributeParam updateAttr = tagInfo.getUpdateAttr();
            smtpServerPropertyTag.setAttribute(updateAttr.getName(), updateAttr.getValue());
            rslt = true ;
        }
        return rslt ;
    }
    /**
     * 同一アトリビュート存在判定
     *
     * @param parentTagName
     *            親タグ名称
     * @param tagInfo
     *            更新するタグ情報
     */
    public boolean isPropertyTagAttributeExist(String parentTagName, UpdateTagInfo tagInfo){
        boolean rslt = false;

        Element configTag = document.getDocumentElement();
        NodeList parentTags = configTag.getElementsByTagName(parentTagName);

        Element parentTag = (Element) parentTags.item(0);

        //<mail>タグ配下の<property>タグのリストを取得
        NodeList propertyTags = parentTag.getElementsByTagName(tagInfo.getTagName());

//        Element smtpServerPropertyTag = null;
        for (int i = 0; i < propertyTags.getLength(); i++) {
            Element propertyTag = (Element) propertyTags.item(i);

            String tagInfoKeyAttrValue = tagInfo.getKeyAttr().getValue();
            String propertyAttribute = propertyTag.getAttribute(tagInfo.getKeyAttr().getName());

            if (tagInfoKeyAttrValue.equals(propertyAttribute)) {
//                smtpServerPropertyTag = propertyTag;
                rslt = true ;
                break;
            }
        }

        return rslt;
    }
    /**
     *
     * @param xmlpath
     * @param indent
     */
    public void saveXml(String xmlpath, boolean indent) {
        if (document == null) {
            throw new IllegalArgumentException("documentが、存在しない");
        }
        if (xmlpath == null) {
            throw new IllegalArgumentException("");
        }

        TransformerFactory transFactory = TransformerFactory.newInstance();
        Transformer transformer;
		try {
			transformer = transFactory.newTransformer();
	        DOMSource source = new DOMSource(document);
	        File newXML = new File(xmlpath);
	        FileOutputStream os = new FileOutputStream(newXML);
	        StreamResult result = new StreamResult(os);
	        transformer.transform(source, result);

		} catch (TransformerConfigurationException e) {
			//  自動生成された catch ブロック
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			//  自動生成された catch ブロック
			e.printStackTrace();
		} catch (TransformerException e) {
			//  自動生成された catch ブロック
			e.printStackTrace();
		}


        //  XML 文書の出力
/*		FileOutputStream outputStream = null ;
*/
/*        DOMSource source = new DOMSource(document);
//        StreamResult result = new StreamResult(xmlpath);
        StreamResult result = new StreamResult();

        FileOutputStream outputStream = null ;
		try {
			outputStream = new FileOutputStream(xmlpath);
			result.setOutputStream(outputStream);
		} catch (FileNotFoundException e2) {
			//  自動生成された catch ブロック
			e2.printStackTrace();
		}
        OutputStreamWriter osw = null ;
    	try {
			osw = new OutputStreamWriter(outputStream, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			//  自動生成された catch ブロック
			e1.printStackTrace();
		}

        TransformerFactory transFactory = TransformerFactory.newInstance();

        Transformer transformer = null;
        try {
            transformer = transFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            System.err.println("Document cannnot be configured:");
            System.err.println(e.toString());
            return;
        }

        transformer.setOutputProperty(OutputKeys.ENCODING, encoding);
        if (indent){
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        }else{
            transformer.setOutputProperty(OutputKeys.INDENT, "no");
        }

        try {
            transformer.transform(source, result);

        } catch (TransformerException e) {
            System.err.println("Document cannnot be tranformed:");
            System.err.println(e.toString());
            return;
        }
        try {

			osw.close();
		} catch (IOException e) {
			//  自動生成された catch ブロック
			e.printStackTrace();
		}
*/
    }
    /**
     * 作成された DOM を XML 文書として出力する。
     *
     */
    public void saveXml(boolean indent) {
        saveXml(xmlFilePath, indent);
    }
    /**
     *
     * @param nodeList
     * @param attrName
     * @param value
     * @return
     */
    public String getElementValue(NodeList nodeList, String attrName,  String value) {
        Element element = null;

        for (int i = 0; i < nodeList.getLength(); i++) {
            // 要素を取得
            element = (Element) nodeList.item(i);

            // name属性が"CS_ITEM"のelementを収集
            if (element.getAttribute(attrName).equals(value)) {
                break;
            }
        }
        return element.getAttribute("value");
    }

    /**
     * Nodeリストから特定属性値のelementを抽出する
     */
    public LinkedList<Element> getElementList(NodeList nodeList, String attrName,String value) {
        LinkedList<Element> elementList = new LinkedList<Element>();

        for (int i = 0; i < nodeList.getLength(); i++) {
            // formGroup要素を取得
            Element element = (Element) nodeList.item(i);

            // name属性が"CS_ITEM"のelementを収集
            if (element.getAttribute(attrName).equals(value)) {
                elementList.add(element);
            }
        }
        return elementList;
    }
    /**
     * 指定されたタグのノードリスト取得
     * @param tagname　タグ名称
     * @return　指定されたタグのノードリスト
     */
    public NodeList getNodeListByRoot(String tagname){
		Document xmldoc = this.getDocument();
		Element root = xmldoc.getDocumentElement();

    	return root.getElementsByTagName(tagname);
    }
    /**
     * 属性を作成
     * @param attrName
     * @param attrValue
     */
    public void appendAttribute(Element elem, String attrName, String attrValue){
    	elem.setAttribute(attrName, attrValue);
    }
    public void appendAttributeList(Element elem, List<AttributeParam> attrInfoList){
    	Iterator<AttributeParam> itr = attrInfoList.iterator();
    	while(itr.hasNext()){
    		AttributeParam info = itr.next();
    		elem.setAttribute(info.getName(), info.getValue());
    	}
    }
	/**
	 * 属性値を取得
	 * @param parent	親element
	 * @param tagname	タグ名
	 * @param attrname	属性名
	 * @return
	 */
	public String getAttributeByName(Element parent, String tagname, String attrname){
		NodeList targetNodes = parent.getElementsByTagName( tagname );
        Element targetElem = (Element) targetNodes.item(0);
		return targetElem.getAttribute( attrname );
	}

}