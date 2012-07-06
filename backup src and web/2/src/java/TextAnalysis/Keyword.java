/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TextAnalysis;

/**
 *
 * @author robik
 */
public class Keyword implements java.io.Serializable{
    
    public String keyword;
    public int weight;

    @Override
    public String toString() {
        StringBuffer buffer=new StringBuffer();
        buffer.append(keyword);
        buffer.append("\n");
        buffer.append(weight);
        buffer.append("\n");
      return buffer.toString();
    }

    public Keyword() {
    }

    public Keyword(String keyword, int weight) {
        this.keyword = keyword;
        this.weight = weight;
    }

    
}
