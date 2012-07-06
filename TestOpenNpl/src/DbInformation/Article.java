/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DbInformation;

/**
 *
 * @author robik
 */
//import TextAnalysis.Keyword;
import java.util.LinkedList;
import java.util.Vector;

public class Article implements Cloneable, java.io.Serializable {

    /**
     *
     * @author robik
     */
    /**Article consists of many fields. Each field is associated with an article. The fields of the articles are assigned at different stages of the execution.*/
    private int articleId;
    private String title;
    private String content;
    private String link;
    private String date;
    private String channel;
    private String category;
    private String subCategory;
    private String place;
   // private Vector<Keyword> keywordVector;
    private Vector<RelatedArticle> relatedArticleVector=new Vector();

    @Override
    public Article clone() {
        Article o = null;
        try {
            o = (Article) super.clone();
        } catch (CloneNotSupportedException e) {
            return o;
        }
        return o;
    }

    @Override
    public String toString() {

        StringBuffer buffer = new StringBuffer();
        try {
            buffer.append("The title is: " + title);
            buffer.append("\n");
            buffer.append("The link is: " + link);

            buffer.append("\n");
            buffer.append("The place is: " + place);

            buffer.append("\n");
            buffer.append("The date is: " + date);
            buffer.append("\n");
            buffer.append("The channel is: " + channel);
            buffer.append("\n");
            buffer.append("The category is: " + category);

            buffer.append("\n");
            buffer.append("The sub category id is: " + subCategory);
            //buffer.append("\n");
            //buffer.append("The related linked list is: " + relatedArticleVector);

            buffer.append("\n");
            buffer.append("The article id is: " + articleId);

            buffer.append("\n");
            buffer.append("The article content is: " + content);

            buffer.append("\n");
          //  buffer.append("Keyword list is: " + keywordVector);
            buffer.append("The place is:" + place + " \n");
            buffer.append("\n");
        } catch (Exception e) {
        }
        return buffer.toString();
    }

    public void setCategory(String category) {
        this.category = category;
    }
    

    public void setArticleId(int id) {
        this.articleId = id;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getChannel() {
        return channel;
    }

    

    public String getCategory() {
        return category;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPlace() {
        return place;
    }

//    public void setKeywordVector(Vector<Keyword> keywordVector) {
//        this.keywordVector = keywordVector;
//    }

//    public Vector<Keyword> getKeywordVector() {
//        return keywordVector;
//    }

    
    
    public void addRelatedArticle(RelatedArticle article2)
    {
        this.relatedArticleVector.add(article2);
    }
    
    public Vector<RelatedArticle> getRelatedArticleVector()
    {
        return relatedArticleVector;
    }
    
}
