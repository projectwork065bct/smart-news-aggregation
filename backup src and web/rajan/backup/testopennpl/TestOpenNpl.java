/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testopennpl;

import DbInformation.Database;
import DbInformation.Database;
import DbInformation.HTMLConverter;
import TextAnalysis.ScoreAndSave;

/**
 *
 * @author power
 */
public class TestOpenNpl {

    /**
     * @param args the command line arguments
     */
    public void generateInformation(){
        HTMLConverter h=new HTMLConverter();
        System.out.println(h.getActiveChannelTable()+"\n");
        System.out.println(h.getActiveRssTable());
    }
    public void cosine(){
        ScoreAndSave ss=new ScoreAndSave();
        ss.AutoCalculateCosineSimilarity(30);
        //System.out.println(ss.isAlreadyPresent(405, 406));
    }
    public static void main(String[] args) {
        // TODO code application logic here
//        SentenceDetectorME sentenceDetector = new SentenceDetectorME(model);
//        String sentences[] = sentenceDetector.sentDetect("  First sentence. Second sentence. ");
       // System.out.println("hellow rajan");
        TestOpenNpl t=new TestOpenNpl();
        t.cosine();
    }
}
