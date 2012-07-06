/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testopennpl;

import DbInformation.Database;
import DbInformation.DbClass1;
import DbInformation.HTMLConvertor;
import Idf_Package.ScoreAndSave;

/**
 *
 * @author power
 */
public class TestOpenNpl {

    /**
     * @param args the command line arguments
     */
    public void generateInformation(){
        HTMLConvertor h=new HTMLConvertor();
        System.out.println(h.getActiveChannelTable()+"\n");
        System.out.println(h.getActiveRssTable());
    }
    public void cosine(){
        ScoreAndSave ss=new ScoreAndSave();
        ss.AutoCalculateCosineSimilarity();
        //System.out.println(ss.isAlreadyPresent(405, 406));
    }
    public void idf(){
        ScoreAndSave ss=new ScoreAndSave();
        ss.AutoCalculateIDF();
    }
    public void mcs(){
        
        ScoreAndSave ss=new ScoreAndSave();
        ss.AutoCalculateMCS();
    }
    public static void main(String[] args) {
        // TODO code application logic here
//        SentenceDetectorME sentenceDetector = new SentenceDetectorME(model);
//        String sentences[] = sentenceDetector.sentDetect("  First sentence. Second sentence. ");
        System.out.println("hellow rajan");
        TestOpenNpl t=new TestOpenNpl();
        //t.idf();
        t.mcs();
       // t.cosine();
    }
}
