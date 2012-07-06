/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DbInformation;

import java.util.Vector;

/**
 *
 * @author power
 */
public class HTMLConverter {
    QueriesForOutput d=new QueriesForOutput();
    
    public String getActiveChannelTable(){
        Vector <String> v=d.getActiveChannelsVector();
        //System.out.println(v);
        String html="<tr><th>List of Active Channels";
        int s=v.size();
        for(int i=0;i<s;i++){
            //System.out.println(v.remove(0));
            html+="</td></tr>\n<tr><td>"+v.remove(0);
        }
        html+="</td></tr>";
        return html;
    }
    public String getActiveRssTable(){
        Vector <String> v=d.getActiveRssVector();
        //System.out.println(v);
        String html="<tr><th>List of Active Rss links";
        int s=v.size();
        for(int i=0;i<s;i++){
            //System.out.println(v.remove(0));
            String link=v.remove(0);
            html+="</td></tr>\n<tr><td><a href=\""+link+"\" target=\"_blank\">"+link;
        }
        html+="</td></tr>";
        return html;
    }
    public String getCategorySubCategoryTable(){
        Vector <String> cat=d.getCategoryVector();
        System.out.println(cat);
        String html="<tr><th>Category</th>"
                + "<th>Sub-Category</th></tr>\n";
        Vector<String> sub;
        int s=cat.size();
        for(int i=0;i<s;i++){
            //System.out.println(v.remove(0));
            sub=d.getSubCategoryVector(cat.elementAt(0));
            //System.out.println(sub);
            html+="<tr><td>"+cat.remove(0)+"</td><td></td></tr>\n";
            int ss=sub.size();
            for(int j=0;j<ss;j++){
                html+="<tr><td></td><td>"+sub.remove(0)+"</td></tr>";
            }
        }
        //System.out.println(html);
        return html;
    }
}
