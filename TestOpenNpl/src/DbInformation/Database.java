/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DbInformation;

import com.mysql.jdbc.Statement;
import java.sql.ResultSet;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author power
 */
public class Database {
   // DbClass1 db1;
    public Database(){
       
    }
    /*
     * These methods are to be used by HTMLConvertor class
     */
    public Vector<String> getActiveChannelsVector(){
        DbClass1 db1=new DbClass1();
        db1.setConnection(true);
        Vector <String> vect=new Vector();
        ResultSet rs;
        String q="select * from sna_channel where is_active=1";
        rs=db1.executeAndReturnResult(q);
        try{
            if(rs!=null){
                while(rs.next()){
                    vect.add(rs.getString("name"));
                }
            }
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
       // System.out.println(vect);
        db1.setConnection(false);
        return vect;
    }
    public Vector<String> getActiveRssVector(){
        DbClass1 db1=new DbClass1();
        db1.setConnection(true);
        Vector <String> vect=new Vector();
        ResultSet rs;
        String q="select link from sna_rss where is_active=1 and channel in (select name from sna_channel where is_active=1) ";
        rs=db1.executeAndReturnResult(q);//all rss links
        try{
            if(rs!=null){
                while(rs.next()){
                    vect.add(rs.getString("link"));
                }
            }
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
       // System.out.println(vect);
        db1.setConnection(false);
        return vect;
    }
    public Vector<String> getCategoryVector(){
        DbClass1 db1=new DbClass1();
        db1.setConnection(true);
        Vector <String> vect=new Vector();
        ResultSet rs;
        String q="select category from sna_category ";
        rs=db1.executeAndReturnResult(q);//all rss links
        try{
            if(rs!=null){
                while(rs.next()){
                    vect.add(rs.getString("category"));
                }
            }
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
        db1.setConnection(false);
        return vect;
    }
    public Vector<String> getSubCategoryVector(String category){
        DbClass1 db1=new DbClass1();
        db1.setConnection(true);
        Vector <String> vect=new Vector();
        ResultSet rs;
        String q="select distinct sub_category from sna_sub_category where category like  "+category;
        rs=db1.executeAndReturnResult(q);//all rss links
        try{
            if(rs!=null){
                while(rs.next()){
                    vect.add(rs.getString("sub_category"));
                }
            }
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
       // System.out.println(vect);
        db1.setConnection(false);
        return vect;
    }
    
    /*
     * These methods are to be used by ScoreAndSave class of idf_package and contains absolute methods
     * 
     */
    public Vector<Article> getArticleVectorOfDateAndSubCategory(String subCategory) {
        DbClass1 db1=new DbClass1();
        db1.setConnection(true);
        Vector<Article> articleVector = new Vector();
        try {
           // Statement st = con.createStatement();
            ResultSet rs = db1.executeAndReturnResult("SELECT * FROM sna_article WHERE sub_category = " + getSubCategoryId(subCategory) + " AND where date<=CURDATE() and date>=DATE_SUB(CURDATE(),INTERVAL 10 DAY)ORDER BY date DESC");
            articleVector = getArticleVectorFromResultSet(rs);
        } catch (Exception ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        db1.setConnection(false);
        return articleVector;
    }
    public int getSubCategoryId(String subCategory) {
        DbClass1 db1=new DbClass1();
        db1.setConnection(true);
        int id = -1;
        String tableName = "sna_sub_category";
        //Statement st = null;
        try {
            //st = con.createStatement();
            ResultSet rs = null;
            rs = db1.executeAndReturnResult("SELECT id from " + tableName + " where sub_category='" + subCategory + "'");
            if (rs.next()) {
                id = rs.getInt("id");
            }

        } catch (Exception ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        db1.setConnection(false);
        return id;
    }
    public Vector<Article> getArticleVectorFromResultSet(ResultSet rs) {
        Vector<Article> articleVector = new Vector();
        String subCategory="";
        String category="";
        int i=0;
        try {
            //subCategory=getSubCategoryFromId(rs.getInt("sub_category"));
            while (rs.next()) {
                if(i++<1){
                    subCategory=getSubCategoryFromId(rs.getInt("sub_category"));
                    category=getCategoryFromId(rs.getInt("category"));
                }
                Article article = new Article();
                article.setArticleId(rs.getInt("article_id"));
                article.setTitle(rs.getString("title"));
                article.setContent(rs.getString("content"));
                article.setCategory(category);
                article.setSubCategory(subCategory);
                article.setPlace(rs.getString("place"));
                articleVector.add(article);
            }
        } catch (Exception ex) {
            System.out.println("rajan "+ex.getMessage());
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return articleVector;
    }
    public Vector<Article> getArticleVectorOfSubCategory(String subCategory) {
        Vector<Article> articleVector = new Vector();
        DbClass1 db1=new DbClass1();
        db1.setConnection(true);
        int sub_id=getSubCategoryId(subCategory);
        if(sub_id<0){
            System.err.println("subcategory =="+subCategory+" returns=="+sub_id);
        }
        try {
            //Statement st = con.createStatement();
            String query="SELECT * FROM sna_article WHERE sub_category = " + sub_id
                    +" and  date>=DATE_SUB(CURDATE(),INTERVAL 30 DAY) and (article_id in (select  "
                    + "main_id from sna_related_articles) or article_id in (select  related_id from sna_related_articles))";
            System.out.println(query);
            ResultSet rs = db1.executeAndReturnResult(query);
            if(rs==null){
                System.out.println("The resultset is null from database class");
            }
            articleVector = getArticleVectorFromResultSet(rs);
        } catch (Exception ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        db1.setConnection(false);
        return articleVector;
    }
    
      public String getCategoryFromId(int id) {
          DbClass1 db1=new DbClass1();
        db1.setConnection(true);
        String category = null;
        try {
            //Statement st = con.createStatement();
            ResultSet rs = db1.executeAndReturnResult("SELECT category FROM sna_category WHERE id = " + id);
            if (rs.next()) {
                category = rs.getString("category");
            }
        } catch (Exception ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        db1.setConnection(false);
        return category;

    }
      public String getSubCategoryFromId(int id) {
          DbClass1 db1=new DbClass1();
        db1.setConnection(true);
        String subCategory = null;
        try {
            //Statement st = con.createStatement();
            ResultSet rs = db1.executeAndReturnResult("SELECT sub_category FROM sna_sub_category WHERE id = " + id);
            if (rs.next()) {
                subCategory = rs.getString("sub_category");
            }
        } catch (Exception ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        db1.setConnection(false);
        return subCategory;
    }
      public Vector<String> getSubCategories() {
        Vector<String> subCategories = new Vector();
       // Statement st;
        DbClass1 db1=new DbClass1();
        db1.setConnection(true);
        try {
            //st = con.createStatement();
            ResultSet rs = db1.executeAndReturnResult("SELECT distinct sub_category FROM sna_sub_category");
            if(rs!=null){
                while (rs.next()) {
                    subCategories.add(rs.getString("sub_category"));
                }
            }else{
                System.out.println("Database class line 207, rs==null");
            }
        } catch (Exception ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);

        }
        db1.setConnection(false);
        return subCategories;
    }
}
