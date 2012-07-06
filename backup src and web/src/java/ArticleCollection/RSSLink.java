/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ArticleCollection;

/**
 *
 * @author robik
 */
public class RSSLink implements java.io.Serializable {

    private int id;
    private String link=null, category=null, subCategory=null, channel=null;
    private boolean isDapper = true;

    public void setId(int id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setIsDapper(boolean isDapper) {
        this.isDapper = isDapper;
    }

    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public String getChannel() {
        return channel;
    }

    public String getLink() {
        return link;
    }

    public boolean getIsDapper() {
        return isDapper;
    }
    
    
    @Override
    public String toString() {

        StringBuffer buffer = new StringBuffer();
        try {
            buffer.append("The link is: " + link);

            buffer.append("\n");
            buffer.append("The channel is: " + channel);
            buffer.append("\n");
            buffer.append("The category is: " + category);

            buffer.append("\n");
            buffer.append("The sub category id is: " + subCategory);
            buffer.append("\n");
            buffer.append("The id is: " + id);

            buffer.append("\n");
        } catch (Exception e) {
        }
        return buffer.toString();
    }
}
