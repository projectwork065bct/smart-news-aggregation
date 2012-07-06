/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DbInformation;

/**
 *
 * @author robik
 */
import ArticleCollection.Article;
import ArticleCollection.RSSLink;
import ArticleCollection.RelatedArticle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QueriesForProcessing {

    /*
     * To change this template, choose Tools | Templates
     * and open the template in the editor.
     */
    /**
     *
     * @author robik
     */
    /*Whenever the database is to be accessed, we can use this class.*/
    //private String host = "127.0.0.1";
    Database db;

    public QueriesForProcessing() {
        db = new Database();
        db.setConnection(true);
    }

    ///////////////////Connect to the database////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ////These are the functions to get articles from the database//////////////
    public Vector<Article> getArticleVectorOfCategory(String category) {
        Vector<Article> articleVector = new Vector();
        ResultSet rs = db.executeAndReturnResult("SELECT * FROM sna_article WHERE category = " + getCategoryId(category));
        articleVector = getArticleVectorFromResultSet(rs);

        return articleVector;
    }

    public Vector<Article> getArticleVectorOfNoSubCategory(String category) {
        Vector<Article> articleVector = new Vector();
        ResultSet rs = db.executeAndReturnResult("SELECT * FROM sna_article WHERE sub_category is null and category = " + getCategoryId(category));
        articleVector = getArticleVectorFromResultSet(rs);

        return articleVector;
    }

    public Vector<Article> getArticleVectorOfSubCategory(String subCategory) {
        Vector<Article> articleVector = new Vector();
        ResultSet rs = db.executeAndReturnResult("SELECT * FROM sna_article WHERE sub_category = " + getSubCategoryId(subCategory));
        articleVector = getArticleVectorFromResultSet(rs);

        return articleVector;
    }

    public Vector<Article> getArticleVectorOfDate(String date) {
        Vector<Article> articleVector = new Vector();

        ResultSet rs = db.executeAndReturnResult("SELECT * FROM sna_article WHERE date = '" + date + "'");
        articleVector = getArticleVectorFromResultSet(rs);

        return articleVector;
    }

    public Vector<Article> getArticleVectorOfDateAndSubCategory(String date, String subCategory) {
        Vector<Article> articleVector = new Vector();
        ResultSet rs = db.executeAndReturnResult("SELECT * FROM sna_article WHERE sub_category = " + getSubCategoryId(subCategory) + " AND date = '" + date + "'");
        articleVector = getArticleVectorFromResultSet(rs);
        return articleVector;
    }

    public Vector<Article> getArticleVectorFromResultSet(ResultSet rs) {
        Vector<Article> articleVector = new Vector();
        try {
            while (rs.next()) {
                Article article = new Article();
                article.setArticleId(rs.getInt("article_id"));
                article.setTitle(rs.getString("title"));
                article.setContent(rs.getString("content"));
                article.setCategory(getCategoryFromId(rs.getInt("category")));
                article.setSubCategory(getSubCategoryFromId(rs.getInt("sub_category")));
                article.setPlace(rs.getString("place"));
                articleVector.add(article);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueriesForProcessing.class.getName()).log(Level.SEVERE, null, ex);
        }
        return articleVector;
    }
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////
    ///////These are the functions to get certain data from the databvase/////
    public Vector<RSSLink> getRSSLinks() {
        Vector<RSSLink> rssLinks = new Vector();
        String tableName = "sna_rss";
        try {
            ResultSet rs = db.executeAndReturnResult("SELECT * FROM " + tableName);

            while (rs.next()) {
                if (isChannelActive(rs.getString("channel"))) {
                    RSSLink rssLink = new RSSLink();
                    rssLink.setId(rs.getInt("id"));
                    rssLink.setLink(rs.getString("link"));
                    rssLink.setCategory(rs.getString("category"));
                    rssLink.setSubCategory(rs.getString("sub_category"));
                    rssLink.setChannel(rs.getString("channel"));
                    rssLink.setIsDapper(rs.getBoolean("is_dapper"));
                    rssLinks.add(rssLink);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(QueriesForProcessing.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rssLinks;
    }

    public boolean isChannelActive(String channel) {
        boolean isActive = false;
        String tableName = "sna_channel";
        Statement st;
        try {
            ResultSet rs = db.executeAndReturnResult("SELECT is_active FROM " + tableName + " where name='" + channel + "'");
            if (rs.next()) {
                isActive = rs.getBoolean("is_active");
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueriesForProcessing.class.getName()).log(Level.SEVERE, null, ex);
            return isActive;
        }
        return isActive;
    }

    public String getCategoryFromId(int id) {
        String category = null;
        try {
            ResultSet rs = db.executeAndReturnResult("SELECT category FROM sna_category WHERE id = " + id);
            if (rs.next()) {
                category = rs.getString("category");
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueriesForProcessing.class.getName()).log(Level.SEVERE, null, ex);
        }
        return category;

    }

    public String getSubCategoryFromId(int id) {
        String subCategory = null;
        try {
            ResultSet rs = db.executeAndReturnResult("SELECT sub_category FROM sna_sub_category WHERE id = " + id);
            if (rs.next()) {
                subCategory = rs.getString("sub_category");
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueriesForProcessing.class.getName()).log(Level.SEVERE, null, ex);
        }
        return subCategory;
    }

    public int getCategoryId(String category) {
        int id = -1;
        String tableName = "sna_category";

        ResultSet rs = null;
        try {
            rs = db.executeAndReturnResult("SELECT id from " + tableName + " where category='" + category + "'");
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueriesForProcessing.class.getName()).log(Level.SEVERE, null, ex);
        }

        return id;
    }

    public int getSubCategoryId(String subCategory) {
        int id = -1;
        String tableName = "sna_sub_category";
        try {
            ResultSet rs = null;
            rs = db.executeAndReturnResult("SELECT id from " + tableName + " where sub_category='" + subCategory + "'");
            if (rs.next()) {
                id = rs.getInt("id");
            }

        } catch (SQLException ex) {
            Logger.getLogger(QueriesForProcessing.class.getName()).log(Level.SEVERE, null, ex);
        }

        return id;
    }

    public String getCategoryOfSubCategory(String subCategory) {
        String category = null;
        try {
            ResultSet rs = db.executeAndReturnResult("SELECT category FROM sna_sub_category where sub_category = '" + subCategory + "'");
            if (rs.next()) {
                category = rs.getString("category");
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueriesForProcessing.class.getName()).log(Level.SEVERE, null, ex);
        }

        return category;
    }

    //Returns a vector of all the categories.
    public Vector<String> getCategories() {
        Vector<String> categories = new Vector();
        Statement st;
        try {
            ResultSet rs = db.executeAndReturnResult("SELECT category FROM sna_category");
            while (rs.next()) {
                categories.add(rs.getString("category"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueriesForProcessing.class.getName()).log(Level.SEVERE, null, ex);

        }
        return categories;
    }

    //Returns a vector of all the sub-categories
    public Vector<String> getSubCategories() {
        Vector<String> subCategories = new Vector();
        try {
            ResultSet rs = db.executeAndReturnResult("SELECT sub_category FROM sna_sub_category");
            while (rs.next()) {
                subCategories.add(rs.getString("sub_category"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueriesForProcessing.class.getName()).log(Level.SEVERE, null, ex);

        }
        return subCategories;
    }

    public Vector<String> getSubCategoriesOfCategory(String category) {
        Vector<String> subCategories = new Vector();
        try {
            ResultSet rs = db.executeAndReturnResult("SELECT sub_category FROM sna_sub_category where category = '" + category + "'");
            while (rs.next()) {
                subCategories.add(rs.getString("sub_category"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueriesForProcessing.class.getName()).log(Level.SEVERE, null, ex);

        }


        return subCategories;
    }

    public boolean hasSpaceOnly(String a) {
        boolean result = true;
        if (a == null) {
            return result;
        }
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != ' ') {
                return false;
            }
        }
        return result;
    }

    public Vector<String> getPlaces() {
        Vector<String> places = new Vector();
        String tableName = "sna_longitude_latitude";
        String sql = "SELECT District, Headquarter from " + tableName;
        try {
            ResultSet rs = db.executeAndReturnResult(sql);
            while (rs.next()) {
                String district = rs.getString("District");
                if (!hasSpaceOnly(district)) {
                    places.add(district);
                }

                String headquarter = rs.getString("Headquarter");
                if (!hasSpaceOnly(headquarter)) {
                    places.add(headquarter);

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueriesForProcessing.class.getName()).log(Level.SEVERE, null, ex);
        }
        return places;
    }

    //////////////////////////////////////////////////////////////////////////
    ///////Different ways of getting keywords from the database///////////////
    public Vector<String> getKeywords(String subCategory, int weight) {
        Vector<String> keywordVector = new Vector();
        try {
            ResultSet rs = db.executeAndReturnResult("SELECT keywords from sna_keywords where sub_category='" + subCategory + "' and weight = " + weight);
            if (rs.next()) {
                String keywords = rs.getString("keywords");
                String keywordsArray[] = keywords.split(" ");
                for (int i = 0; i < keywordsArray.length; i++) {
                    if (keywordsArray[i].length() != 0 && keywordsArray[i] != null) {
                        keywordVector.add(keywordsArray[i]);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueriesForProcessing.class.getName()).log(Level.SEVERE, null, ex);
        }
        return keywordVector;
    }

    public Vector<String> getKeywordsVectorOfSubCategory(String subCategory) {
        Vector<String> keywordsVector = new Vector();
        try {
            ResultSet rs = db.executeAndReturnResult("SELECT keywords from sna_keywords where sub_category='" + subCategory + "'");
            while (rs.next()) {
                String keywords = rs.getString("keywords");
                String keywordsArray[] = keywords.split(" ");
                for (int i = 0; i < keywordsArray.length; i++) {
                    if (keywordsArray[i].length() != 0 && keywordsArray[i] != null) {
                        keywordsVector.add(keywordsArray[i]);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueriesForProcessing.class.getName()).log(Level.SEVERE, null, ex);
        }

        return keywordsVector;
    }

    //It returns the keywords as if they formed a real article. i.e. the keywords are separated by spaces or commasand are not split into individual words
    public String getKeywordsStringOfSubCategory(String subCategory) {
        String keywordString = subCategory + " ";
        try {
            ResultSet rs = db.executeAndReturnResult("SELECT keywords from sna_keywords where sub_category='" + subCategory + "'");
            while (rs.next()) {
                String keywords = rs.getString("keywords");
                if (keywords.length() > 0 && keywords != null) {
                    keywordString += keywords + " ";
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(QueriesForProcessing.class.getName()).log(Level.SEVERE, null, ex);
        }

        return keywordString;
    }

    public String getTitleOfArticleId(int articleId) {
        String title = "";
        try {
            ResultSet rs = db.executeAndReturnResult("SELECT title FROM sna_article WHERE article_id = " + articleId);
            if (rs.next()) {
                title = rs.getString("title");
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueriesForProcessing.class.getName()).log(Level.SEVERE, null, ex);
        }
        return title;
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    public boolean isTitleDuplicate(String title) {
        boolean ans = true;
        String tableName = "sna_article";
        String sql = "SELECT article_id, title FROM " + tableName + " WHERE title LIKE ?";
        try {
            Connection con = db.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, "%" + title + "%");
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                ans = true;
            } else {
                System.out.println("This article has not been stored before: " + title);
                ans = false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueriesForProcessing.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ans;
    }

    //Gets the directory name from the table
    public String getDirectoryName(String subCategory, Vector<String> previousDates, String currentDate) {
        String directoryName = "DATE_" + currentDate + " SUBCATEGORY_" + subCategory;
        String table = "sna_directory";
        String sql = "SELECT directory_name FROM " + table + " WHERE directory_name like ?";

        //now we generate a directory name whose version is based on previous directories created on currentDate (if any)
        try {
            Connection con = db.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + directoryName + "%");
            ResultSet rs = ps.executeQuery();
            int size = 1;
            while (rs.next()) {
                ++size;
            }
            directoryName += " VERSION_" + size;
        } catch (SQLException ex) {
            Logger.getLogger(QueriesForProcessing.class.getName()).log(Level.SEVERE, null, ex);
        }
        //after generating the directory name, we need to insert the dates into the database
        try {
            Connection con = db.getConnection();
            Statement st = con.createStatement();
            String sql1 = "INSERT INTO " + table + "(directory_name,sub_category,date) VALUES (?,?,?)";
            PreparedStatement ps1 = con.prepareStatement(sql1);
            for (int i = 0; i < previousDates.size(); i++) {

                ps1.setString(1, directoryName);
                ps1.setString(2, subCategory);
                ps1.setString(3, previousDates.get(i));
                ps1.executeUpdate();
            }
            ps1.setString(1, directoryName);
            ps1.setString(2, subCategory);
            ps1.setString(3, currentDate);
            ps1.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(QueriesForProcessing.class.getName()).log(Level.SEVERE, null, ex);
        }
        return directoryName;
    }

    public Vector<String> getDirectoryFromDateAndSubCategory(String subCategory, String date) {
        Vector<String> directoryVector = new Vector();
        String table = "sna_directory";
        String sql = "SELECT distinct directory_name FROM " + table + " WHERE sub_category = '" + subCategory + "' AND date = '" + date + "'";
        try {
            ResultSet rs = db.executeAndReturnResult(sql);
            while (rs.next()) {
                directoryVector.add(rs.getString("directory_name"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueriesForProcessing.class.getName()).log(Level.SEVERE, null, ex);
        }
        return directoryVector;
    }

///////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////
///These are the functions to insert articles to the database//////////////
    public boolean insertArticles(Vector<Article> articleVector) {

        Statement st = null;
        String currentDate = getCurrentDate();
        try {
            Connection con = db.getConnection();
            st = con.createStatement();
            String tableName = "sna_article";

            for (int i = 0; i < articleVector.size(); i++) {
                Article article = articleVector.get(i);

                if (getSubCategoryId(article.getSubCategory()) != -1) {
                    String sql = "INSERT INTO " + tableName + " (title,content,channel,category,sub_category,date,place) VALUES (?,?,?,?,?,?,?)";

                    PreparedStatement ps = con.prepareStatement(sql);
                    ps.setString(1, article.getTitle());
                    ps.setString(2, article.getContent());
                    ps.setString(3, article.getChannel());
                    ps.setInt(4, getCategoryId(article.getCategory()));
                    ps.setInt(5, getSubCategoryId(article.getSubCategory()));
                    ps.setString(6, currentDate);
                    ps.setString(7, (article.getPlace()));
                    ps.executeUpdate();
                } else {
                    String sql = "INSERT INTO " + tableName + " (title,content,channel,category,date,place) VALUES (?,?,?,?,?,?)";

                    PreparedStatement ps = con.prepareStatement(sql);
                    ps.setString(1, article.getTitle());
                    ps.setString(2, article.getContent());
                    ps.setString(3, article.getChannel());
                    ps.setInt(4, getCategoryId(article.getCategory()));
                    ps.setString(5, currentDate);
                    ps.setString(6, (article.getPlace()));
                    ps.executeUpdate();
                }

                System.out.println("The article entitled: " + article.getTitle() + " has been inserted.");
            }//for
        } catch (SQLException ex) {
            Logger.getLogger(QueriesForProcessing.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ////////////////These are the functions used to update the database///////
    //It updates the sub category of given articles inside article vector
    public void updateSubCategory(Vector<Article> articleVector) {
        String table = "sna_article";
        for (int i = 0; i < articleVector.size(); i++) {
            Article article = articleVector.get(i);
            String subCategory = article.getSubCategory();
            int articleId = article.getArticleId();
            String sql = "UPDATE " + table + " set sub_category = " + getSubCategoryId(subCategory) + " WHERE article_id = " + articleId;
            if (subCategory != null) {
                db.executeQuery(sql);
            }
        }

    }

    public void updateScore(Vector<Article> articleVectorWithScore) {
        String table = "sna_related_articles";
        for (int i = 0; i < articleVectorWithScore.size(); i++) {
            Article article = articleVectorWithScore.get(i);
            int mainId = article.getArticleId();

            Vector<RelatedArticle> relatedVector = article.getRelatedArticleVector();

            if (relatedVector == null) {
                continue;
            }
            for (int j = 0; j < relatedVector.size(); j++) {
                RelatedArticle related = relatedVector.get(j);
                int relatedId = related.getId();
                float score = 1;
                score = related.getScore();
                try {
                    ResultSet rs = db.executeAndReturnResult("SELECT * FROM " + table + " where main_id = " + mainId + " AND related_id = " + relatedId);
                    if (rs.next())//if the score was already present, no need to rewrite it
                    {
                        System.out.println("main id = " + mainId + " related id = " + relatedId + " already exist");
                        db.executeQuery("UPDATE " + table + " set bm25_score = " + score + " WHERE main_id = " + mainId + " and related_id = " + relatedId);
                    } else {
                        String sql = "INSERT INTO " + table + "(main_id,related_id,bm25_score) VALUES(?,?,?)";
                        System.out.println("inserting main id " + mainId + " relatedId " + relatedId + " score " + score);
                        Connection con = db.getConnection();
                        PreparedStatement ps = con.prepareStatement(sql);
                        ps.setInt(1, mainId);
                        ps.setInt(2, relatedId);
                        ps.setFloat(3, score);
                        ps.executeUpdate();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(QueriesForProcessing.class.getName()).log(Level.SEVERE, null, ex);
                }




            }
        }

    }

    public void updateErrorLink(String url, boolean hasError) {
        if (hasError) {
            db.executeQuery("UPDATE sna_rss SET has_error = 1 WHERE link = '" + url + "'");
        } else {
            db.executeQuery("UPDATE sna_rss SET has_error = 0 WHERE link = '" + url + "'");
        }


    }
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////

    public String getCurrentDate() {
        Calendar now = Calendar.getInstance();
        String date = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + (now.get(Calendar.DATE));
        return date;
    }

    /////////////Queries to get rid of unnecessary articles//////////////
    public void deleteArticle(int articleId) {
        db.executeQuery("DELETE FROM sna_article where article_id = " + articleId);

    }

    public Vector<String> getSubCategoriesForCosine() {
        Vector<String> subCategories = new Vector();
        // Statement st;
        Database db1 = new Database();
        db1.setConnection(true);
        try {
            //st = con.createStatement();
            ResultSet rs = db1.executeAndReturnResult("SELECT sub_category FROM sna_sub_category");
            if (rs != null) {
                while (rs.next()) {
                    subCategories.add(rs.getString("sub_category"));
                }
            } else {
                System.out.println("Database class line 207, rs==null");
            }
        } catch (Exception ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);

        }
        return subCategories;
    }
}
