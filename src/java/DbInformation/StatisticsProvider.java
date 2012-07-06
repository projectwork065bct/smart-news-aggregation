/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DbInformation;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ramsharan
 */
public class StatisticsProvider {
    
    Database db;
    
    public StatisticsProvider()
    {
        db=new Database();
        db.setConnection(true);
    }
    //provides number of articles collected today
    public String getDate(){
        
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        return(dateFormat.format(date));
    }
    //provides number of the articles of current date
    public int numberOfArticles(){
        String currentDate=this.getDate();
        String query="select count(*) as number from sna_article where date = "+"\'"+currentDate+"\'";
        ResultSet rset;
        rset=db.executeAndReturnResult(query);
        try {
            rset.next();
        } catch (SQLException ex) {
            Logger.getLogger(StatisticsProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        int i=0;
        try {
            i=rset.getInt("number");
        } catch (SQLException ex) {
            Logger.getLogger(StatisticsProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }
    //
    public String category(){
        String currentDate=this.getDate();
        
        String query="select sna_category.category,count(sna_category.category) as number"+
                   " from sna_category,sna_article"+
                   " where sna_category.Id=sna_article.category and date = "+"\'"+currentDate+"\'"+
                    " group by sna_category.category";
        ResultSet rset;
        rset=db.executeAndReturnResult(query);
        
        String table;
        table="<table><tr><th>Category</th><th>Number of articles</th></tr>";
        try {
            while(rset.next()){
                table=table+"<tr><td>"+rset.getString("category")+"</td><td>"+rset.getInt("number")+"</td></tr>";
            }
        } catch (SQLException ex) {
            Logger.getLogger(StatisticsProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        table=table+"</table>";
        
        return table;
    }
    //
    public String subCategory(){
        String currentDate=this.getDate();
        
        String query="select sna_sub_category.sub_category,count(sna_sub_category.sub_category) as number"+
                   " from sna_sub_category,sna_article"+
                   " where sna_sub_category.Id=sna_article.sub_category and date = "+"\'"+currentDate+"\'"+
                    " group by sna_sub_category.sub_category order by sna_sub_category.category, sna_sub_category.sub_category";
        ResultSet rset;
        rset=db.executeAndReturnResult(query);
        
        String table;
        table="<table><tr><th colspan='2'>STATISTICAL REPORT:<hr></th></tr><tr><th>Sub-category</th><th>Number of articles</th></tr>";
        try {
            while(rset.next()){
                table=table+"<tr><td>"+rset.getString("sub_category")+"</td><td>"+rset.getInt("number")+"</td></tr>";
            }
        } catch (SQLException ex) {
            Logger.getLogger(StatisticsProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        table=table+"</table>";
        
        return table;
    }
    //
    public String statusOfChannel(){
        String query="select name , is_active from sna_channel";
        ResultSet rset=db.executeAndReturnResult(query);
        
        String table;
        table="<table><tr><th>Channel</th><th>Status</th></tr>";
        try {
            while(rset.next()){
                table=table+"<tr><td>"+rset.getString("name")+"</td><td>";
                if(rset.getInt("is_active")==1){
                    table=table+"active"+"</td></tr>";
                }
                else{
                    table=table+"<font color='red'>inactive</font>"+"</td></tr>";
                }   
            }
        } catch (SQLException ex) {
            Logger.getLogger(StatisticsProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        table=table+"</table>";
        
        return table;
    }
    
    //
    public String channelAndArticle(){
        String currentDate=this.getDate();
        
        String query="select channel,count(channel) as number "+
                "from sna_article "+
                "where date ="+"\'"+currentDate+"\' "+
                "group by channel ";
        
        ResultSet rset=db.executeAndReturnResult(query);
        
        String table;
        table="<table><tr><th>Channel</th><th>Number of articles</th></tr>";
        try {
            while(rset.next()){
                table=table+"<tr><td>"+rset.getString("channel")+"</td><td>"+rset.getInt("number")+"</td></tr>";
            }
        } catch (SQLException ex) {
            Logger.getLogger(StatisticsProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        table=table+"</table>";
        
        return table;
    }
    
    //
    public String linkAndError(){
        String currentDate=this.getDate();
        
        
        
        String query="select link,has_error"+
                   " from sna_rss"+
                   " where is_active=1";
        ResultSet rset=db.executeAndReturnResult(query);
        
        String table;
        table="<table><tr><th>RSS Link</th><th>status</th></tr>";
        try {
            while(rset.next()){
                table=table+"<tr><td><a href='"+rset.getString("link")+"'>"+rset.getString("link")+"</a>"+"</td><td>";
                if(rset.getBoolean("has_error")){
                    table=table+"<font color=\"red\">Not Working</font></td</tr>";
                }
                else {
                table=table+"Working</td</tr>";
                    
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(StatisticsProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        table=table+"</table>";
        
        return table;
        
    }
    
    
}
