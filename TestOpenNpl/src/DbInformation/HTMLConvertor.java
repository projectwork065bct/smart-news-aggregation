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
public class HTMLConvertor {
    Database d=new Database();
    
    public String getActiveChannelTable(){
        Vector <String> v=d.getActiveChannelsVector();
        //System.out.println(v);
        String html="<table><tr><td>List of Active Channels";
        int s=v.size();
        for(int i=0;i<s;i++){
            //System.out.println(v.remove(0));
            html+="</td></tr>\n<tr><td>"+v.remove(0);
        }
        html+="</td></tr></table>";
        return html;
    }
    public String getActiveRssTable(){
        Vector <String> v=d.getActiveRssVector();
        //System.out.println(v);
        String html="<table><tr>List of Active Rss links is:";
        int s=v.size();
        for(int i=0;i<s;i++){
            //System.out.println(v.remove(0));
            html+="</tr>\n<tr>"+v.remove(0);
        }
        html+="</tr></table>";
        return html;
    }
}
