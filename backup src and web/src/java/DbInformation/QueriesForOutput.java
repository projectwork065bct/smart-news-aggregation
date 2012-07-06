/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DbInformation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author power
 */
public class QueriesForOutput {

    Database db1;

    public QueriesForOutput() {
        db1 = new Database();
        db1.setConnection(true);
    }

    public Vector<String> getActiveChannelsVector() {
        Vector<String> vect = new Vector();
        ResultSet rs;
        String q = "select * from sna_channel where is_active=1";
        rs = db1.executeAndReturnResult(q);
        try {
            if (rs != null) {
                while (rs.next()) {
                    vect.add(rs.getString("name"));
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return vect;
    }

    public Vector<String> getActiveRssVector() {
        Vector<String> vect = new Vector();
        ResultSet rs;
        String q = "select link from sna_rss where is_active=1 and channel in (select name from sna_channel where is_active=1) ";
        rs = db1.executeAndReturnResult(q);//all rss links
        try {
            if (rs != null) {
                while (rs.next()) {
                    vect.add(rs.getString("link"));
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return vect;
    }

    public Vector<String> getCategoryVector() {
        Vector<String> vect = new Vector();
        ResultSet rs;
        String q = "select category from sna_category ";
        rs = db1.executeAndReturnResult(q);//all rss links
        try {
            if (rs != null) {
                while (rs.next()) {
                    vect.add(rs.getString("category"));
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return vect;
    }

    public Vector<String> getSubCategoryVector(String category) {
        Vector<String> vect = new Vector();
        ResultSet rs;
        String q = "select  sub_category from sna_sub_category where category like  '" + category + "'";
        rs = db1.executeAndReturnResult(q);//all rss links
        try {
            if (rs != null) {
                while (rs.next()) {
                    vect.add(rs.getString("sub_category"));
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return vect;
    }

    public String getSubCategorizedArticles() {
        String str = "<table>";
        ResultSet rs = null;
        rs = db1.executeAndReturnResult("SELECT article_id, title, sub_category FROM sna_article WHERE date = '" + getCurrentDate() + "' ORDER BY category, sub_category");
        String subCategory = "", subCategoryTemp = "";
        try {
            while (rs.next()) {
                
                ResultSet rs2 = null;
                rs2 = db1.executeAndReturnResult("SELECT sub_category FROM sna_sub_category where id = " + rs.getInt("sub_category"));
                if (rs2.next()) {
                    subCategoryTemp = rs2.getString("sub_category");
                }


                if (subCategoryTemp.compareTo(subCategory) != 0) {
                    subCategory = subCategoryTemp;
                    str += "<tr><th>SUB CATEGORY: <th>" + subCategory + "</tr>";
                    str += "<tr><th>ID<hr/><th>Title<hr/></tr>";
                }


                str += "<tr><td>" + rs.getInt("article_id") + "<td>" + rs.getString("title") + "</tr>";
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(QueriesForOutput.class.getName()).log(Level.SEVERE, null, ex);
        }
        return str + "</table>";
    }

    public String getRelatedArticles() {
        String relatedArticles = "<table>";
        QueriesForProcessing qfp = new QueriesForProcessing();
        ResultSet rs = null;
        String sql = "SELECT all main_id, related_id, bm25_score, sub_category"
                + " FROM sna_related_articles"
                + " INNER JOIN sna_article ON main_id = article_id"
                + " order by category, sub_category, main_id";
        rs = db1.executeAndReturnResult(sql);
        String subCategory = "", subCategoryTemp = "";

        int previousMainId = 0, mainId = 0;
        try {
            while (rs.next()) {
                ResultSet rs2 = null;
                rs2 = db1.executeAndReturnResult("SELECT sub_category FROM sna_sub_category WHERE id = " + rs.getInt("sub_category"));
                if (rs2.next()) {
                    subCategoryTemp = rs2.getString("sub_category");

                }
                if (subCategoryTemp.compareTo(subCategory) != 0) {
                    subCategory = subCategoryTemp;
                    relatedArticles += "<br><br><tr><td colspan='2'><h1><center>SUB CATEGORY:</h1> <td><h1>" + subCategory + "</h1></tr>";
                    relatedArticles += "<tr><th>MAIN ARTICLE<th>RELATED ARTICLE<th>SCORE</tr>";
                }

                relatedArticles += "<tr><td>" + qfp.getTitleOfArticleId(rs.getInt("main_id")) + "<td>" + qfp.getTitleOfArticleId(rs.getInt("related_id")) + "<td>" + rs.getFloat("bm25_score");
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueriesForOutput.class.getName()).log(Level.SEVERE, null, ex);
        }
        relatedArticles += "</table>";
        return relatedArticles;
    }

    public String getCurrentDate() {
        Calendar now = Calendar.getInstance();
        String date = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + (now.get(Calendar.DATE));
        return date;
    }
}
