/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testopen;

import TextAnalysis.ScoreAndSave;

/**
 *
 * @author power
 */
public class TestOpen {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ScoreAndSave ss=new ScoreAndSave();
        //ss.AutoCalculateCosineSimilarity(10);
        //ss.AutoUpdateCosinesAlreadyPresentInTable();
        //ss.AutoUpdateMCSAlreadyPresentInTable();
        //ss.AutoUpdateIDFAlreadyPresentInTable();
        
        ss.AutoCalculateAndUpdateAllCosines();
       //ss.AutoCalculateAndUpdateAllidf(30);
       //ss.AutoCalculateAndUpdateAllmcs(30);
        
        //ss.AutoCalculateRelevenceScore(20);
    }
}
