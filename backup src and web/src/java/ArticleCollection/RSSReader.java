/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ArticleCollection;

import DbInformation.QueriesForProcessing;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.util.Vector;

/**
 *
 * @author robik
 */
/**RSSReader is used to read the RSS and obtain the articles. The RSS link is set and that link may belong to a particular category and subcategory.*/
public class RSSReader {

    private String rssLink;
    private DocumentBuilderFactory dbf;
    private DocumentBuilder db;
    private Document doc;
    private URL u;
    private Vector<Article> articleVector = new Vector();
    private Article sampleArticle = new Article();

    public void setRSSLink(String rssLink) {
        this.rssLink = rssLink;
        try {
            u = new URL(rssLink);
        } catch (MalformedURLException ex) {
            Logger.getLogger(RSSReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getRSSLink() {
        return this.rssLink;
    }

    public void setChannel(String channel) {
        this.sampleArticle.setChannel(channel);
    }

    public String getChannel() {
        return this.sampleArticle.getCategory();
    }

    public void setCategory(String category) {

        this.sampleArticle.setCategory(category);
    }

    public String getCategory() {
        return this.sampleArticle.getCategory();
    }

    public void setSubCategory(String subCategory) {
        this.sampleArticle.setSubCategory(subCategory);
    }

    public String getSubCategory() {
        return this.sampleArticle.getSubCategory();
    }

    public void documentBuilder() {
        QueriesForProcessing database=new QueriesForProcessing();
            
        dbf = DocumentBuilderFactory.newInstance();
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(RSSReader.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        
        try {
            doc = db.parse(u.openStream());
        } catch (Exception ex) {
            database.updateErrorLink(u.toString(),true);
            Logger.getLogger(RSSReader.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        database.updateErrorLink(u.toString(),false);
    }//documentBuilder()

    public void retrieveArticles() {
        if (doc != null) {
            NodeList itemList = doc.getElementsByTagName("item");
            Article article = sampleArticle.clone();

            for (int l1 = 0; l1 < itemList.getLength(); l1++) {
                Node item = itemList.item(l1);
                NodeList tOrDList = item.getChildNodes();//title OR description list



                for (int l2 = 0; l2 < tOrDList.getLength(); l2++) {
                    Node tOrD = tOrDList.item(l2);
                    if ((tOrD.getNodeName().compareToIgnoreCase("title")) == 0) {
                        article = sampleArticle.clone();
                        article.setTitle(tOrD.getTextContent());
                        articleVector.add(article);
                    }

                    if ((tOrD.getNodeName().compareToIgnoreCase("description")) == 0) {
                        //Keep on updating the contents of the article
                        String txt = tOrD.getTextContent();
                        String previousTxt = "";
                        int last = articleVector.size() - 1;
                        if (articleVector.get(last).getContent() != null) {
                            if (articleVector.get(last).getContent().compareToIgnoreCase("null") != 0) {
                                previousTxt = articleVector.get(last).getContent();
                            }
                        }
                        articleVector.get(last).setContent(previousTxt + " <br> " + txt);
                    }
                }
            }
        }
    }

    public void automaticStart() {
        documentBuilder();
        retrieveArticles();
    }

    public Vector<Article> getArticleVector() {
        return articleVector;
    }
}
