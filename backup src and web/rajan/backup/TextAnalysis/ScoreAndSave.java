/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TextAnalysis;

import TextAnalysis.DocumentCollection;
import ArticleCollection.Article;
import DbInformation.Database;
import DbInformation.QueriesForOutput;
import DbInformation.QueriesForProcessing;
import java.sql.ResultSet;
import java.util.Vector;

/**
 *
 * @author power
 */
public class ScoreAndSave {

    Database db = new Database();
    Vector<Article> articles;

    public ScoreAndSave() {
    }

    private void displayArticlesId(Vector<Article> a) {
        for (int i = 0; i < a.size(); i++) {
            System.out.println(a.elementAt(i).getArticleId());
        }
    }

    public void AutoCalculateCosineSimilarity(int days) {
        QueriesForProcessing qfp = new QueriesForProcessing();
        Vector<String> sub = qfp.getSubCategories();
        //Article seed;
        DocumentCollection dc;//=new DocumentCollection();//
        //System.out.println(sub);
        String str;
        int size = sub.size();//size of subcategory vector
        for (int i = 0; i < size; i++) {//taking one sub-category at a time
            /*fetching the articles of certain sub category only*/
            str = sub.elementAt(i);
            articles = qfp.getArticleVectorOfSubCategory2(str, days);
            dc = new DocumentCollection();
            dc.addDocumentsVector(articles);//make the matrices and other structures
            /*
             * Take one article at a time and calculate its score and update the score
             */
            for (int j = 0; j < articles.size(); j++) {
                System.err.println("updating relation " + j);
                this.updateCosineSimilarity(dc, j);//cosine
            }
        }
    }

    public void AutoCalculateIDF(int days) {
        QueriesForProcessing qfp = new QueriesForProcessing();
        Vector<String> sub = qfp.getSubCategories();
        //Article seed;
        DocumentCollection dc;//=new DocumentCollection();//
        //System.out.println(sub);
        String str;
        int size = sub.size();//size of subcategory vector
        for (int i = 0; i < size; i++) {//taking one sub-category at a time
            /*fetching the articles of certain sub category only*/
            str = sub.elementAt(i);
            //System.err.println("going to fetch articles of subCategory "+str);
            articles = qfp.getArticleVectorOfSubCategory2(str, days);
            this.displayArticlesId(articles);
            //System.err.println("articles of "+str+" fetched");
            //System.out.println(articles);
            System.out.println("this much is ok:::");
            dc = new DocumentCollection();
            dc.addDocumentsVector(articles);//make the matrices and other structures
            /*
             * Take one article at a time and calculate its score and update teh score
             */
            for (int j = 0; j < articles.size(); j++) {
                //System.out.println(j);
                System.err.println("updating relation " + j);
                // this.updateCosineSimilarity(dc, j);//cosine
                this.updateTermFrequency(dc, j);
                //  this.updateModifiedCosineScore(dc, j);
            }
        }
    }

    public void AutoCalculateMCS(int days) {
        QueriesForProcessing qfp = new QueriesForProcessing();
        Vector<String> sub = qfp.getSubCategories();
        //Article seed;
        DocumentCollection dc;//=new DocumentCollection();//
        System.out.println(sub);
        String str;
        int size = sub.size();//size of subcategory vector
        for (int i = 0; i < size; i++) {//taking one sub-category at a time
            /*fetching the articles of certain sub category only*/
            str = sub.elementAt(i);
            //System.err.println("going to fetch articles of subCategory "+str);
            articles = qfp.getArticleVectorOfSubCategory2(str, days);
            this.displayArticlesId(articles);
            //System.err.println("articles of "+str+" fetched");
            //System.out.println(articles);
            dc = new DocumentCollection();
            dc.addDocumentsVector(articles);//make the matrices and other structures
            /*
             * Take one article at a time and calculate its score and update teh score
             */
            for (int j = 0; j < articles.size(); j++) {
                //System.out.println(j);
                // this.updateCosineSimilarity(dc, j);//cosine
                // this.updateTermFrequency(dc,j);
                this.updateModifiedCosineScore(dc, j);
            }
        }
    }

    public void updateCosineSimilarity(DocumentCollection dc, int docNumber) {
        /*
         * calculate all the cosine similarity of documents with document number docNumber
         * and updates the database; sna_scores
         */
        double score = 0;
        boolean opt;
        for (int i = 0; i < articles.size(); i++) {
            opt = isPresentInTable(articles.elementAt(docNumber).getArticleId(), articles.elementAt(i).getArticleId());
            //System.out.println(opt);
            if (true) {
                if (!opt || isAlreadyPresent("cosine_score", articles.elementAt(docNumber).getArticleId(),
                        articles.elementAt(i).getArticleId())) {
                    continue;
                } else {
                    if (opt) {
                        score = dc.getCosineSimilarity(docNumber, i);
                        if (true) {
                            updateCosineScore(articles.elementAt(docNumber).getArticleId(),
                                    articles.elementAt(i).getArticleId(), score);
                        }
                    } else {
                        System.out.println("opt = " + opt);
                    }
                }//else
            }
        }//for
    }

    public void updateTermFrequency(DocumentCollection dc, int docNumber) {
        /*
         * calculate all the cosine similarity of documents with document number docNumber
         * and updates the database; sna_scores
         */
        dc.initializeWeightsArray();
        dc.initializeMatrix();
        double score = 0;
        for (int i = 0; i < articles.size(); i++) {
//            if((i==docNumber)){
//                System.out.println("i is equal to doc Number"+i+","+docNumber);
//                continue;
//            }//fi
//            else 
            if (isAlreadyPresent("idf_score", articles.elementAt(docNumber).getArticleId(),
                    articles.elementAt(i).getArticleId())) {
                // updateEntry();
                continue;
            } else {
                score = dc.getScore(docNumber, i);
                if (true) {//score>0.55
//                    System.out.println("updating score "+articles.elementAt(docNumber).getArticleId()+", "
//                            +articles.elementAt(i).getArticleId()+"score="+score);
                    updateIDFScore(articles.elementAt(docNumber).getArticleId(),
                            articles.elementAt(i).getArticleId(), score);
                }
            }//else
        }//for
    }

    public void updateModifiedCosineScore(DocumentCollection dc, int docNumber) {
        /*
         * calculate all the cosine similarity and inverse term frequency combined into one 
         * ie , cosine similarity based on inverse term frequency
         * of documents with document number docNumber
         * and updates the database; sna_scores
         */
        double score = 0;
        for (int i = 0; i < articles.size(); i++) {
            if (isAlreadyPresent("modified_cosine_score", articles.elementAt(docNumber).getArticleId(),
                    articles.elementAt(i).getArticleId())) {

                continue;
            } else {
                score = dc.getModifiedCosineSimilarity(docNumber, i);
                if (true) {//score>0.55
//                    System.out.println("updating score "+articles.elementAt(docNumber).getArticleId()+", "
//                            +articles.elementAt(i).getArticleId()+"score="+score);
                    updateModifiedCosineScore(articles.elementAt(docNumber).getArticleId(),
                            articles.elementAt(i).getArticleId(), score);
                }
            }//else
        }//for
    }

    public boolean isAlreadyPresent(String colName, int seed_id, int related_id) {
        // System.err.println("isAlreadyPresent "+seedId+","+related_id);
        Database db1 = new Database();
        db1.setConnection(true);
        int i = 0;
        double score = 0;
        ResultSet r = db1.executeAndReturnResult("select " + colName + " from sna_related_articles"
                + " where main_id=" + seed_id + " and related_id=" + related_id + " ");
        try {
            if (r == null || r.next() == false) {
//                String query=String.format("insert into sna_related_articles values (%d,%d,0,0,0,0,0,0)",
//                        seed_id,related_id);
//                //db1.setConnection(true);
//                db1.executeQuery(query);
                return false;//update from the other end
            } else {
                while (r.next()) {
                    score = r.getDouble(1);
                }
                if (score > 0) {
                    return true;
                } else {
                    return false;
                }
                //return true;
            }
        } catch (Exception e) {
            System.out.println("exception from isAlreadyPresent() :" + e.getMessage());
            return false;
        }
    }

    public boolean isPresentInTable(int seed, int related) {
        Database db1 = new Database();
        db1.setConnection(true);
        int i = 0;
        double score = 0;
        String query = "select count(main_id) from sna_related_articles"
                + " where main_id=" + seed + " and related_id=" + related;
        ResultSet r = db1.executeAndReturnResult(query);
        try {
//            
            if (r.next()) {
                i = r.getInt(1);
                if (i > 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("exception from isPresentInTable() :" + e.getMessage());
            return false;
        }
    }

    private void updateCosineScore(int doc1, int doc2, double score) {
        String query = String.format("update sna_related_articles set cosine_score=%f where "
                + "main_id=%d and related_id=%d", score, doc1, doc2);
        db.executeQuery(query);
    }

    private void updateIDFScore(int doc1, int doc2, double score) {
        String query = String.format("update sna_related_articles set idf_score=%f where "
                + "main_id=%d and related_id=%d", score, doc1, doc2);
        //System.out.println(query);
        db.executeQuery(query);
    }

    private void updateModifiedCosineScore(int doc1, int doc2, double score) {
        //String query=String.format("insert into score_cosine_similarity values (%d,%d,%f)",doc1,doc2,score);
        String query = String.format("update sna_related_articles set modified_cosine_score=%f where "
                + "main_id=%d and related_id=%d", score, doc1, doc2);
        //System.out.println(query);
        db.executeQuery(query);
    }
}
