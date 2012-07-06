/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TextAnalysis;

import ArticleCollection.Article;
import DbInformation.Database;
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
        QueriesForProcessing db = new QueriesForProcessing();
        Vector<String> sub = db.getSubCategories();
        //Article seed;
        DocumentCollection dc;//=new DocumentCollection();//
        //System.out.println(sub);
        String str;
        int size = sub.size();//size of subcategory vector
        for (int i = 0; i < size; i++) {//taking one sub-category at a time
            /*fetching the articles of certain sub category only*/
            str = sub.elementAt(i);
            //System.err.println("going to fetch articles of subCategory "+str);
            articles = db.getArticleVectorOfSubCategory2(str, days);
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
                this.updateCosineSimilarity(dc, j);//cosine
                //   this.updateTermFrequency(dc,j);
                //  this.updateModifiedCosineScore(dc, j);
            }
        }
    }

    public void AutoCalculateIDF(int days) {
        QueriesForProcessing db = new QueriesForProcessing();
        Vector<String> sub = db.getSubCategories();
        //Article seed;
        DocumentCollection dc;//=new DocumentCollection();//
        //System.out.println(sub);
        String str;
        int size = sub.size();//size of subcategory vector
        for (int i = 0; i < size; i++) {//taking one sub-category at a time
            /*fetching the articles of certain sub category only*/
            str = sub.elementAt(i);
            //System.err.println("going to fetch articles of subCategory "+str);
            articles = db.getArticleVectorOfSubCategory2(str, 30);

            //this.displayArticlesId(articles);
            //System.err.println("articles of "+str+" fetched");
            //System.out.println(articles);
            System.out.println("this much is ok:::");
            dc = new DocumentCollection();
            dc.addDocumentsVector(articles);//make the matrices and other structures
            dc.initializeWeightsArray();
            dc.initializeMatrix();
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
        QueriesForProcessing db = new QueriesForProcessing();
        Vector<String> sub = db.getSubCategories();
        //Article seed;
        DocumentCollection dc;//=new DocumentCollection();//
        System.out.println(sub);
        String str;
        int size = sub.size();//size of subcategory vector
        for (int i = 0; i < size; i++) {//taking one sub-category at a time
            /*fetching the articles of certain sub category only*/
            str = sub.elementAt(i);
            //System.err.println("going to fetch articles of subCategory "+str);
            articles = db.getArticleVectorOfSubCategory2(str, days);
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
                    System.out.println("goinh to continue");
                    continue;
                } else {
                    if (opt) {
                        score = dc.getCosineSimilarity(docNumber, i);
                        if (true) {//score>0.55
                            //                    System.out.println("updating score "+articles.elementAt(docNumber).getArticleId()+", "
                            //                            +articles.elementAt(i).getArticleId()+"score="+score);
                            //System.out.println("updating cosine score");
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
                String query = String.format("insert into sna_related_articles values (%d,%d,0,0,0,0,0,0)",
                        seed_id, related_id);
                //db1.setConnection(true);
                db1.executeQuery(query);
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
        System.out.println(query);
        ResultSet r = db1.executeAndReturnResult(query);
        try {
//            if(r==null||r.next()==false){
//                return false;//update from the other end
//            }else{
//                return true;
//            }
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
        Database db1 = new Database();
        db1.setConnection(true);
        //String query=String.format("insert into score_cosine_similarity values (%d,%d,%f)",doc1,doc2,score);
        String query = String.format("update sna_related_articles set cosine_score=%f where "
                + "main_id=%d and related_id=%d", score, doc1, doc2);
        System.out.println(query);
        db1.executeQuery(query);
    }

    private void updateIDFScore(int doc1, int doc2, double score) {
        Database db1 = new Database();
        db1.setConnection(true);
        String query = String.format("update sna_related_articles set idf_score=%f where "
                + "main_id=%d and related_id=%d", score, doc1, doc2);
        //System.out.println(query);
        db1.executeQuery(query);
    }

    private void updateModifiedCosineScore(int doc1, int doc2, double score) {
        Database db1 = new Database();
        db1.setConnection(true);
        //String query=String.format("insert into score_cosine_similarity values (%d,%d,%f)",doc1,doc2,score);
        String query = String.format("update sna_related_articles set modified_cosine_score=%f where "
                + "main_id=%d and related_id=%d", score, doc1, doc2);
        //System.out.println(query);
        db1.executeQuery(query);
    }
    /*This part of code is recoded by rajan 
     * today at 16 kartik
     */

    public Vector<Integer> getSubcategories() {
        Vector<Integer> v = new Vector();
        String q = "select id from sna_sub_category";
        Database db1 = new Database();
        db1.setConnection(true);
        ResultSet r1 = db1.executeAndReturnResult(q);
        try {
            if (r1 != null) {
                while (r1.next()) {
                    v.add(r1.getInt("id"));
                }
                // return v;
            }
        } catch (Exception e) {
        }
        return v;
    }

    public Vector<Integer> getArticleIdsInTable() {
        Vector<Integer> v = new Vector();
        String q = "select article_id from a1_table";
        Database db1 = new Database();
        db1.setConnection(true);
        ResultSet r1 = db1.executeAndReturnResult(q);
        try {
            if (r1 != null) {
                while (r1.next()) {
                    v.add(r1.getInt("article_id"));
                }
                // return v;
            }
        } catch (Exception e) {
        }
        return v;
    }

    public void copyArticlesToTable(int subCategoryId, int days) {
        Database db = new Database();
        db.setConnection(true);
        String query0 = "TRUNCATE table a1_table;";
        db.executeQuery(query0);
        String query1 = " "
                + " Insert into a1_table "
                + " select a.article_id as article_id, "
                + " a.title as title, "
                + " a.content as content "
                + " from sna_article a "
                + " where a.sub_category= " + subCategoryId + " "
                + " and a.date>=DATE_SUB(CURDATE(),INTERVAL " + days + " DAY)";

        db.executeQuery(query1);//articles of subcategory inserted
        db.setConnection(false);
    }

    public Article getArticleById(int id) {
        /*Pass an article_id and get the corresponding article|| working fine*/
        String Query = "select * from sna_article where article_id=" + id;//+" or article_id="+id2;
        Database db1 = new Database();
        db1.setConnection(true);
        System.out.println(Query);
        ResultSet r1 = db1.executeAndReturnResult(Query);
        QueriesForProcessing q = new QueriesForProcessing();
        Vector<Article> v;
        try {
            if (true) {
                if (true) {
                    v = q.getArticleVectorFromResultSet(r1);
                    if (v.size() == 0) {
                        System.out.println("the vector is of zero size;");
                    }

                    return v.elementAt(0);
                } else {
                    System.out.println("throwing null in else");
                    return null;
                }
            } else {
                System.out.println("returning null in getArticlesById()");
                return null;
            }
        } catch (Exception e) {
            System.out.println("exception " + e.getMessage());
            return null;
        }

    }

    public String getContentOfArticleId(int articleId) {
        String content = "";
        Database db = new Database();
        db.setConnection(true);
        try {
            ResultSet rs = db.executeAndReturnResult("SELECT content FROM sna_article WHERE article_id = " + articleId
                    + " ");
            if (rs == null) {
                return content;
            }
            if (rs.next()) {
                content = rs.getString("content");
            }
        } catch (Exception ex) {
            ///Logger.getLogger(QueriesForProcessing.class.getName()).log(Level.SEVERE, null, ex);
        }
        return content;
    }

    private void insertCosineScore(int doc1, int doc2, double score) {
        Database db1 = new Database();
        db1.setConnection(true);
        //String query=String.format("insert into score_cosine_similarity values (%d,%d,%f)",doc1,doc2,score);
        String query = String.format("insert into sna_related_articles values(%d,%d,0,0,0,%f,0,0) ",
                doc1, doc2, score);
        System.out.println(query);
        db1.executeQuery(query);
    }

    private void insertIDFScore(int doc1, int doc2, double score) {
        Database db1 = new Database();
        db1.setConnection(true);
        //String query=String.format("insert into score_cosine_similarity values (%d,%d,%f)",doc1,doc2,score);
        String query = String.format("insert into sna_related_articles values(%d,%d,0,0,0,0,0,%f) ",
                doc1, doc2, score);
        System.out.println(query);
        db1.executeQuery(query);
    }

    private void insertMCSScore(int doc1, int doc2, double score) {
        Database db1 = new Database();
        db1.setConnection(true);
        //String query=String.format("insert into score_cosine_similarity values (%d,%d,%f)",doc1,doc2,score);
        String query = String.format("insert into sna_related_articles values(%d,%d,0,0,0,0,%f,0) ",
                doc1, doc2, score);
        System.out.println(query);
        db1.executeQuery(query);
    }

    private void insertRelevenceScore(ResultSet r) {
        /*
         * leave the first row and insert remaining five rows to the database
         */
        int i = 0;
        int id1 = 0, id2 = 0;
        try {
            if (r != null) {
                while (r.next()) {
                    i++;
                    if (i == 1) {
                        id1 = r.getInt("article_id");
                        continue;
                    }
                    id2 = r.getInt("id");
                    updateRelevenceScore(id1, id2, r.getDouble("score"));
                }
            }
        } catch (Exception e) {
        }
    }

    private void updateRelevenceScore(int id1, int id2, double score) {
        Database db = new Database();
        db.setConnection(true);
        String q = String.format("insert into sna_related_article values(%d,%d,%f,0,0,0,0,0)", id1, id2, score);
    }

    public void AutoUpdateCosinesAlreadyPresentInTable() {
        /*calculates the cosine scores of those pairs, already present in the database and having cosine_score=0
         * working fine,,
         */
        Database db1 = new Database();
        db1.setConnection(true);
        // String query1="select count(cosine_score) from sna_related_articles where cosine_score=0";
        String query2 = "select main_id,related_id from sna_related_articles where cosine_score=0";
        //System.out.println(query2);
        ResultSet r1 = db1.executeAndReturnResult(query2);
        //System.out.println("fetched the query2 from database;");
        int numberOfEntries = 0;
        DocumentCollection dc;
        int d1 = 0, d2 = 0;
        double score = 0;
        Article a;
        try {
            if (r1 != null) {
                while (r1.next()) {
                    dc = new DocumentCollection();
                    d1 = r1.getInt(1);
                    d2 = r1.getInt(2);
                    System.out.println("updating " + d1 + " , " + d2);
                    a = getArticleById(d1);
                    dc.addOneDocument(new Document(a.getContent()));
                    a = getArticleById(d2);
                    dc.addOneDocument(new Document(a.getContent()));
                    score = dc.getCosineSimilarity(0, 1);
                    System.out.println(" " + score);
                    this.updateCosineScore(d1, d2, score);
                }
                System.out.println("finished updating");
            } else {
                System.out.println("The resultset is null");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
    }

    public void AutoCalculateAndUpdateAllCosines() {
        Database db1 = new Database();
        db1.setConnection(true);
        //String query1="select count(cosine_score) from sna_related_articles where cosine_score=0";
        String query2 = "select a.article_id,b.article_id from sna_article a,sna_article b"
                + " where a.sub_category=b.sub_category and (a.article_id,b.article_id) not in"
                + "(select main_id,related_id from sna_related_articles) and a.sub_category=11";
        //System.out.println(query2);
        //return;
        ResultSet r1 = db1.executeAndReturnResult(query2);
        System.out.println("fetched the query2 from database;");
        int numberOfEntries = 0;
        DocumentCollection dc;
        int d1 = 0, d2 = 0;
        double score = 0;
        Article a;
        try {
            if (r1 != null) {
                while (r1.next()) {
                    dc = new DocumentCollection();
                    d1 = r1.getInt(1);
                    d2 = r1.getInt(2);
                    System.out.println("updating " + d1 + " , " + d2);
                    a = getArticleById(d1);
                    dc.addOneDocument(new Document(a.getContent()));
                    a = getArticleById(d2);
                    dc.addOneDocument(new Document(a.getContent()));
                    score = dc.getCosineSimilarity(0, 1);
                    System.out.println(" " + score);
                    this.insertCosineScore(d1, d2, score);
                }
                System.err.println("finished updating");
            } else {
                System.err.println("The resultset is null");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return;
        }
    }

    public void AutoCalculateRelevenceScore(int days) {
        Vector<Integer> v = this.getSubcategories();//sub_category vector
        //System.out.println(v);
        //return;
        String content = "rajan";
        String q = "";
        ResultSet r;
        if (v.size() <= 0) {
            return;
        }
        int size = v.size();
        Vector<Integer> id;

        for (int i = 0; i < size; i++) {//sub_category loop

            this.copyArticlesToTable(v.elementAt(i), days);//articles copied
            id = this.getArticleIdsInTable();
            System.out.println(id.size() + " reached here spot1, suto calculate" + i);
            for (int j = 0; j < id.size(); j++) {//article loop

                content = getContentOfArticleId(id.elementAt(j));
                // System.out.println(content);
                // continue;
                q = ("select article_id,match(content) against('" + content + "') as score"
                        + " from a1_table where match(content) against('" + content + "')");
                r = db.executeAndReturnResult(q);//resultset of article_id vs relevence score
                insertRelevenceScore(r);
            }
        }
    }
    /*18 th kartik*/

    public Vector<Article> getArticleVectorFromId(int sub_cat) {
        Vector<Article> a;//=new Vector();
        Database db = new Database();
        db.setConnection(true);
        QueriesForProcessing qp = new QueriesForProcessing();
        String query = "select * from sna_article where sub_category=" + sub_cat;
        ResultSet r = db.executeAndReturnResult(query);
        a = qp.getArticleVectorFromResultSet(r);
        return a;
    }

    public void insertOrUpdateIDF(Vector<Article> a, DocumentCollection dc, int seed) {
        int size = dc.allDocuments.size();
        double score = 0;
        for (int i = 0; i < size; i++) {
            score = dc.getScore(seed, i);
            this.insertIDFScore(a.elementAt(seed).getArticleId(), a.elementAt(i).getArticleId(), score);
        }
    }

    public void insertOrUpdateMCS(Vector<Article> a, DocumentCollection dc, int seed) {
        int size = dc.allDocuments.size();
        double score = 0;
        for (int i = 0; i < size; i++) {
            score = dc.getModifiedCosineSimilarity(seed, i);
            this.insertMCSScore(a.elementAt(seed).getArticleId(), a.elementAt(i).getArticleId(), score);
        }
    }

    public void AutoCalculateAndUpdateAllidf(int days) {
        Vector<Integer> v = this.getSubcategories();//sub_category vector
        //System.out.println(v);
        Vector<Article> article;//=new Vector();
        QueriesForProcessing qfp = new QueriesForProcessing();
        DocumentCollection dc;//=new DocumentCollection();
        if (v.size() <= 0) {
            return;
        }
        int size = v.size();
        Vector<Integer> id;
        float score = 0;

        for (int i = 0; i < size; i++) {//sub_category loop
            article = getArticleVectorFromId(v.elementAt(i));
            //System.out.println(article);
            dc = new DocumentCollection();
            dc.addDocumentsVector(article);
            //System.out.println("working");


            dc.initializeWeightsArray();
            dc.initializeMatrix();

            for (int j = 0; j < article.size(); j++) {//seed loop
                System.out.println("working");
                insertOrUpdateIDF(article, dc, j);
            }
        }

    }

    public void AutoCalculateAndUpdateAllmcs(int days) {
        Vector<Integer> v = this.getSubcategories();//sub_category vector
        //System.out.println(v);
        Vector<Article> article;//=new Vector();
        QueriesForProcessing qfp = new QueriesForProcessing();
        DocumentCollection dc;//=new DocumentCollection();
        if (v.size() <= 0) {
            return;
        }
        int size = v.size();
        Vector<Integer> id;
        float score = 0;
        for (int i = 0; i < size; i++) {//sub_category loop
            article = getArticleVectorFromId(v.elementAt(i));
            //System.out.println(article);
            dc = new DocumentCollection();
            dc.addDocumentsVector(article);
            //System.out.println("working");


            dc.initializeWeightsArray();
            dc.initializeMatrix();

            for (int j = 0; j < article.size(); j++) {//seed loop
                System.out.println("working");
                insertOrUpdateMCS(article, dc, j);
            }
        }

    }

    private int getDocIdFromArticleId(Vector<Article> a, int articleId) {
        //int id=0;
        for (int i = 0; i < a.size(); i++) {
            // System.err.println("hello");
            if (a.elementAt(i).getArticleId() == articleId) {
                System.out.println(a.elementAt(i).getArticleId() + "returning " + i);
                return i;
            } else {
                // System.out.println(a.elementAt(i).getArticleId());
            }
        }
        return -1;
        //return id;
    }

    private void updateScore(int main, int related, double score, String colName) {
        String q = "update sna_related_articles set " + colName + " =" + score
                + " where main_id=" + main + " and related_id=" + related;
        Database db = new Database();
        db.setConnection(true);
        System.out.println(q);
        db.executeQuery(q);
    }

    public void AutoUpdateIDFAlreadyPresentInTable() {
        Vector<Integer> v = this.getSubcategories();//sub_category vector
        //System.out.println(v);
        double score = 0;
        Database db = new Database();
        db.setConnection(true);
        Vector<Article> article;//=new Vector();
        QueriesForProcessing qfp = new QueriesForProcessing();
        DocumentCollection dc;//=new DocumentCollection();
        if (v.size() <= 0) {
            return;
        }
        int size = v.size();
        Vector<Integer> id;
        // float score=0;
        int d1 = 0, d2 = 0;//article_id
        int d3 = 0, d4 = 0;//document_id
        String query = "";
        ResultSet r1;

        for (int i = 0; i < size; i++) {//sub_category loop
            article = getArticleVectorFromId(v.elementAt(i));
            //System.out.println(article);
            dc = new DocumentCollection();
            dc.addDocumentsVector(article);
            //System.out.println("working");
            query = "select main_id,related_id from sna_related_articles "
                    + "where main_id in ( select article_id from sna_article where"
                    + " sub_category=" + v.elementAt(i) + " ) and "
                    + " idf_score=0";
            System.out.println(query);
            if (true) {
                //return;
            }
            r1 = db.executeAndReturnResult(query);
            dc.initializeWeightsArray();
            dc.initializeMatrix();
            //matrix mde upto here
            try {
                if (r1 != null) {
                    while (r1.next()) {
                        //dc=new DocumentCollection();
                        d1 = r1.getInt(1);//article_id
                        d2 = r1.getInt(2);//related_id
                        System.out.println("updating " + d1 + " , " + d2);
                        d3 = getDocIdFromArticleId(article, d1);
                        // 
                        d4 = getDocIdFromArticleId(article, d2);
                        System.out.println(d3 + " " + d4);

                        score = dc.getScore(d3, d4);
                        System.out.println(" " + score);
                        // if(true)return;
                        updateScore(d1, d2, score, "idf_score");
                    }
                    System.out.println("finished updating");
                } else {
                    System.out.println("The resultset is null");
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
                //return;
            }
        }
    }

    public void AutoUpdateMCSAlreadyPresentInTable() {
        Vector<Integer> v = this.getSubcategories();//sub_category vector
        //System.out.println(v);
        double score = 0;
        Database db = new Database();
        db.setConnection(true);
        Vector<Article> article;//=new Vector();
        QueriesForProcessing qfp = new QueriesForProcessing();
        DocumentCollection dc;//=new DocumentCollection();
        if (v.size() <= 0) {
            return;
        }
        int size = v.size();
        Vector<Integer> id;
        // float score=0;
        int d1 = 0, d2 = 0;//article_id
        int d3 = 0, d4 = 0;//document_id
        String query = "";
        ResultSet r1;

        for (int i = 0; i < size; i++) {//sub_category loop
            article = getArticleVectorFromId(v.elementAt(i));
            //System.out.println(article);
            dc = new DocumentCollection();
            dc.addDocumentsVector(article);
            //System.out.println("working");
            query = "select main_id,related_id from sna_related_articles "
                    + "where main_id in ( select article_id from sna_article where"
                    + " sub_category=" + v.elementAt(i) + " ) and "
                    + " modified_cosine_score=0";
            System.out.println(query);
            if (true) {
                //return;
            }
            r1 = db.executeAndReturnResult(query);
            dc.initializeWeightsArray();
            dc.initializeMatrix();
            //matrix mde upto here
            try {
                if (r1 != null) {
                    while (r1.next()) {
                        //dc=new DocumentCollection();
                        d1 = r1.getInt(1);//article_id
                        d2 = r1.getInt(2);//related_id
                        System.out.println("updating " + d1 + " , " + d2);
                        d3 = getDocIdFromArticleId(article, d1);
                        // 
                        d4 = getDocIdFromArticleId(article, d2);
                        System.out.println(d3 + " " + d4);

                        score = dc.getModifiedCosineSimilarity(d3, d4);
                        System.out.println(" " + score);
                        // if(true)return;
                        updateScore(d1, d2, score, "modified_cosine_score");
                    }
                    System.out.println("finished updating");
                } else {
                    System.out.println("The resultset is null");
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
                //return;
            }
        }
    }
}
