/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TextAnalysis;

import ArticleCollection.Article;
import ArticleCollection.RelatedArticle;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.TermFreqVector;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.TopDocs;
import org.ninit.models.bm25.BM25Parameters;
import org.ninit.models.bm25.BM25BooleanQuery;

/**
 *
 * @author robik
 */
public class BM25Analysis {

    final float THRESHOLD = (float) 3.6;
    static String fileName = null;
    static Vector<Article> articleVector = new Vector();
    final int NO_OF_TOP_SCORES = 5;//Don't put 0 here!!!

    //for subcategorization
    //article vector is the query
    //index file name should be given
    //content of the articles are queried
    //top document's sub category is read and 
    //if there is any problem, then the sub category is set as "extra news"
    public String findSubCategory(String fileName, Article article) {

        String subCategory = "Extra News";


        IndexReader reader = null;


        try {
            reader = IndexReader.open(fileName);
            float avgLength = getAvgLength(reader, "keywordsString");
            if (avgLength == 0) {
                avgLength = 138;
            }

            IndexSearcher searcher = new IndexSearcher(fileName);
            Analyzer analyzer = new StandardAnalyzer();
            BM25Parameters.setAverageLength("keywordsString", avgLength);
            //BM25Parameters.setB(0.75f);
            //BM25Parameters.setK1(2f);

            try {

                String content = article.getContent().replaceAll("<br>", " ").toLowerCase();

                content = content.replace(":", " ");

                BM25BooleanQuery query = new BM25BooleanQuery(content, "keywordsString", analyzer);

                if (searcher == null) {
                    return "Extra News";
                }

                if (query == null) {
                    return "Extra News";
                }


                TopDocs top = searcher.search(query, null, 10);//, 10);


                ScoreDoc[] docs = top.scoreDocs;


                if (docs.length == 0) {
                    System.out.println("BM25: No documents matched with the query " + article.getTitle().toLowerCase());
                    return "Extra News";
                } else {
                    Document currentDocument = searcher.doc(top.scoreDocs[0].doc);
                    float score = top.scoreDocs[0].score;
                    subCategory = currentDocument.get("subCategory");
                    if (score >= THRESHOLD) {
                        subCategory = currentDocument.get("subCategory");
                    } else {
                        subCategory = "Extra News";
                    }
                    System.out.println("Title is " + article.getTitle() + " sub category is " + subCategory + " score is " + score);
                    return subCategory;
                }

            } catch (Exception ex) {
                Logger.getLogger(BM25Analysis.class.getName()).log(Level.SEVERE, null, ex);
                return subCategory;
            }

        } catch (CorruptIndexException ex) {
            Logger.getLogger(BM25Analysis.class.getName()).log(Level.SEVERE, null, ex);
            return subCategory;
        } catch (IOException ex) {
            Logger.getLogger(BM25Analysis.class.getName()).log(Level.SEVERE, null, ex);
            return subCategory;
        }
        //return subCategory;

    }

    public Vector<Article> getScoredArticles(String fileName, Vector<Article> articleVector, String subCategory) {

        float averageLength = 200;
        float maxScore = 0;
        Vector<Float> scoreVector = new Vector();
        System.out.println("Scoring the articles of sub category " + subCategory);
        try {

            IndexReader reader = null;
            try {
                reader = IndexReader.open(fileName);
                averageLength = getAvgLength(reader, "content");
            } catch (CorruptIndexException e1) {
            } catch (IOException e1) {
            }

            String field = "content";
            IndexSearcher searcher = new IndexSearcher(fileName);
            Analyzer analyzer = new StandardAnalyzer();
            //the second parameter calls the getAvgLength method and automatically calculates the   Average Length value
            //BM25BooleanQuery b;
            //System.out.println("avg length " + getAvgLength(reader, field));
            if (averageLength == 0) {
                averageLength = 200;
            }

            System.out.println("Average Length of the document is " + averageLength);
            BM25Parameters.setAverageLength(field, averageLength);////MODIFICATION NEEDED!!
            //modification should find out the average length automatically
            BM25Parameters.setB(0.75f);
            BM25Parameters.setK1(2f);


            for (int articleIndex = 0; articleIndex < articleVector.size(); articleIndex++) {
                Article article = articleVector.get(articleIndex);
                String content = article.getContent().replaceAll("<br>", " ");
                content = content.replace(":", " ");
                content = content.replace("-", " ");


                BM25BooleanQuery query = new BM25BooleanQuery(content, field, new StandardAnalyzer());


                System.out.println("BM25: The content of: " + article.getTitle() + " is being queried.");

                if (searcher == null) {
                    continue;
                }

                if (query == null) {
                    continue;
                }


                TopDocs top = searcher.search(query, 5);//, 10);

                ScoreDoc[] docs = top.scoreDocs;
                if (docs.length == 0) {
                    System.out.println("BM25: The article is not related to any other document");
                    continue;
                }


                for (int i = 0; i < docs.length; i++) {
                    Document matchedDocument = searcher.doc(docs[i].doc);

                    System.out.println("BM25: The article with title : " + matchedDocument.get("title") + " has score =" + docs[i].score);

                    if (matchedDocument == null) {
                        continue;
                    }
                    if (matchedDocument.get("articleId") == null) {
                        continue;
                    }
                    int articleId = Integer.parseInt(matchedDocument.get("articleId"));

                    if (articleId == article.getArticleId()) {
                        continue;
                    }

                    float score = docs[i].score;
                    scoreVector.add(score);
                    if (score > maxScore) {
                        maxScore = score;
                    }
                    RelatedArticle ra = new RelatedArticle(articleId, score);

                    articleVector.get(articleIndex).addRelatedArticle(ra);

                }

            }
        } catch (ParseException ex) {
            Logger.getLogger(BM25Analysis.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CorruptIndexException ex) {
            Logger.getLogger(BM25Analysis.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BM25Analysis.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(BM25Analysis.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("An exception was thrown when relating the articles of " + subCategory);
        }

        float threshold;
        float averageHighScore = 0;

        //Finding out the average Highest Score
        if (scoreVector.size() < NO_OF_TOP_SCORES) {
            for (int i = 0; i < scoreVector.size(); i++) {
                averageHighScore += scoreVector.get(i);
            }
            if (!scoreVector.isEmpty()) {
                averageHighScore /= scoreVector.size();
            }
        } else {
            Comparator r = Collections.reverseOrder();
            Collections.sort(scoreVector, r);
            for (int i = 0; i < NO_OF_TOP_SCORES; i++) {
                averageHighScore += scoreVector.get(i);

            }
            averageHighScore /= NO_OF_TOP_SCORES;
        }
        if (subCategory.compareToIgnoreCase("Accidents and Crime") == 0 || subCategory.compareToIgnoreCase("Business") == 0 || subCategory.compareToIgnoreCase("Other Sports")==0 || subCategory.compareToIgnoreCase("Extra News")==0) {
            threshold = (float) (0.95 * averageHighScore);
        } 
        else if(subCategory.compareToIgnoreCase("Football")==0)
        {
            threshold=(float)(0.8*averageHighScore);
        }
        else if (subCategory.compareToIgnoreCase("Politics") == 0) {
            threshold = (float) (0.4 * averageHighScore);
        } else {
            threshold = (float) (0.6 * averageHighScore);
        }

        System.out.println("Threshold for " + subCategory + " = " + threshold);

        if(threshold==0)
            return articleVector;
        
        for (int l = 0; l < articleVector.size(); l++) {
            Article article = articleVector.get(l);
            Vector<RelatedArticle> relatedArticleVector = article.getRelatedArticleVector();
            for (int m = 0; m < relatedArticleVector.size(); m++) {
                RelatedArticle relatedArticle = relatedArticleVector.get(m);
                if (relatedArticle.getScore() < (threshold)) {
                    articleVector.get(l).getRelatedArticleVector().remove(m);
                    m--;
                }
            }
        }

        return articleVector;
    }

    //gets the average length of a field
    public static float getAvgLength(IndexReader reader, String field) {
        int sum = 0;
        for (int i = 0; i < reader.numDocs(); i++) {
            TermFreqVector tfv;
            try {
                tfv = reader.getTermFreqVector(i, field);
                if (tfv != null) {
                    int[] tfs = tfv.getTermFrequencies();

                    for (int j = 0; j < tfs.length; j++) {
                        sum = sum + tfs[j];
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(BM25Analysis.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        float avg = (float) sum / reader.numDocs();
        return avg;
    }

    public void group() {
        //System.out.println("Grouping");
    }

    public void automaticStart() {
        //findScore();
        group();
        DB_updateRelatedArticles();
    }

    public static void setArticleVector(Vector<Article> articleVector) {
        BM25Analysis.articleVector = articleVector;
        //System.out.println("BM25 now has the vector of articles to query with.");
    }

    public static void setIndexFile(String fileName) {
        BM25Analysis.fileName = fileName;
    }

    public BM25Analysis() {
    }

    public BM25Analysis(String fileName, Vector<Article> articleVector) {
        setArticleVector(articleVector);
        setIndexFile(fileName);
        //findScore();
    }

    public void groupBasedOnThreshold() {
    }

    public void groupBasedOnTopScores() {
        //find out the highest score of the day
        //find out b% of that score
        //if other top scores above b% of highest score, take avergaes
        //group 1: 
        //group 2:
        // score =  sum of (weight of word * no. of words)
    }

    public void groupBasedOnAverage() {
    }

    public void DB_updateRelatedArticles() {
        // System.out.println("Storing information about the related articles into the database");
    }
}
