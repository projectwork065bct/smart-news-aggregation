/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TextAnalysis;

import TextAnalysis.Document;
import ArticleCollection.Article;
import java.util.Vector;

/**
 *
 * @author rajan
 */
/*
 * A set of document objects needs to be passeed to this class, and then the 
 * function needs to be called to calculate the similarity and other things.
 */
public class DocumentCollection {
    Vector <Document> allDocuments=new Vector();//individual documents
    Document overAll=new Document();
    //overall words status document combined to form one document
    Double[] weight;
    int[][] matrix;/*   terms*documentsNumber   */
    public void addDocumentsVector(Vector<Article> vec){
        for(int i=0;i<vec.size();i++){
            addOneDocument(new Document(vec.elementAt(i).getContent()));
        }
    }
    public void addOneDocument(Document doc){
    /*
     * add documents one by one using this method
     */
        allDocuments.add(doc);
        for(int i=0;i<doc.words.size();i++){
            overAll.insertWord(doc.words.elementAt(i));
        }
    }
    public void initializeWeightsArray(){
        int docsCount=allDocuments.size();//num of documents
        int distWordsCount=overAll.getDistinctWordsCount();//num of words
        weight=new Double[distWordsCount];//make array of weights
        for(int i=0;i<distWordsCount;i++){
            weight[i]=Math.log(docsCount)-Math.log(numOfDocContaining(overAll.words.elementAt(i)));
        }
    }
    public void initializeMatrix(){
        /*
         * i=index corresponding to the term
         * j=index corresponding to the document
         */
        int docsCount=allDocuments.size();
        int distWordsCount=overAll.getDistinctWordsCount();
        matrix=new int[distWordsCount][docsCount];
        for(int j=0;j<docsCount;j++){
            for(int i=0;i<distWordsCount;i++){
                matrix[i][j]=allDocuments.elementAt(j).getCountOfTerm(overAll.words.elementAt(i));
            }
        }
    }
    public double getScore(int seedDoc,int candidateDoc){
        /*
         * This will return the term frequency-inverse document frequency
         * score from the colleciton of documents among seed Document and 
         * candidate document
         */
        double score=0;
        Document seed=allDocuments.elementAt(seedDoc);
        Document candidate=allDocuments.elementAt(candidateDoc);
        for(int i=0;i<allDocuments.elementAt(seedDoc).words.size();i++){
            score+=candidate.getCountOfTerm(seed.words.elementAt(i))*getIDFWeight(seed.words.elementAt(i));
        }
        return score;
    }
    public int numOfDocContaining(String w){
        //usually private calls only
        int count=0;
        for(int i=0;i<allDocuments.size();i++){
            if(allDocuments.elementAt(i).words.contains(w)){
                count++;
            }
        }
        return count;
    }
    public double getIDFWeight(String term){
        /*
         * returns the Inverse Document Frequency weight of the term
         */
        int i=overAll.words.indexOf(term);
        return weight[i];
    }
    public double getCosineSimilarity(int doc1, int doc2){
        return (getDotProduct(doc1,doc2)/(getModulus(doc1)*getModulus(doc2)));
    }
    public double getModifiedCosineSimilarity(int doc1,int doc2){
        double score=0;
        /*
         * To calculate the modified cosinesimilarity score, the weight array and the matrix
         * should be pre initialized;
         */
        if(weight==null){
            this.initializeWeightsArray();
            this.initializeMatrix();
        }
        score=(getModifiedDotProduct(doc1,doc2)/(getModifiedModulus(doc1)*getModifiedModulus(doc2)));
        return score;
    }
    public int getDotProduct(int doc1, int doc2){
        int score=0;
        int s=overAll.getDistinctWordsCount();
        String term;
        for(int i=0;i<s;i++){
            term=overAll.words.elementAt(i);
            score+=allDocuments.elementAt(doc1).getCountOfTerm(term)*
                    allDocuments.elementAt(doc2).getCountOfTerm(term);
        }
        return score;
    }
    public int getModifiedDotProduct(int doc1, int doc2){
        int score=0;
        int s=overAll.getDistinctWordsCount();
        String term;
        for(int i=0;i<s;i++){
            term=overAll.words.elementAt(i);
            score+=allDocuments.elementAt(doc1).getCountOfTerm(term)*
                    allDocuments.elementAt(doc2).getCountOfTerm(term)*weight[i];
        }
        return score;
    }
    public double getModulus(int doc){
        double w=0;
        for(int i=0;i<allDocuments.elementAt(doc).words.size();i++){
            w+=(allDocuments.elementAt(doc).count.elementAt(i).count
                    *allDocuments.elementAt(doc).count.elementAt(i).count);
        }
        
        return Math.sqrt(w);
    }
    public double getModifiedModulus(int doc){
        double w=0;
        String term;
        int s=overAll.getDistinctWordsCount();
        for(int i=0;i<s;i++){
            term=overAll.words.elementAt(i);
            w+=allDocuments.elementAt(doc).getCountOfTerm(term)*
                    allDocuments.elementAt(doc).getCountOfTerm(term)*weight[i];
                    /*(allDocuments.elementAt(doc).count.elementAt(i).count
                    *allDocuments.elementAt(doc).count.elementAt(i).count*
                    this.getIDFWeight(allDocuments.elementAt(doc).words.elementAt(i)));*/
        }
        
        return Math.sqrt(w);
    }
}
