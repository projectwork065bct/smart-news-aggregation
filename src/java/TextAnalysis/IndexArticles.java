/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TextAnalysis;

import ArticleCollection.Article;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Field;

/**
 *
 * @author robik
 */
/**This class creates an index file with the articles stored in a vector. The directory name and article vector should be supplied by another class.*/
public class IndexArticles {

    private Vector<Article> articleVector;
    private String directory = "";

    //constructor
    public IndexArticles() {
    }

    //constructor with arguments
    public IndexArticles(String directory, Vector<Article> articleVector) {
        this.directory = directory;
        this.articleVector = articleVector;
        System.out.println("The directory to be indexed at is: " + directory);
    }

    //create the index file
    public void createIndex() {
        try {
            IndexWriter indexWriter = new IndexWriter(FSDirectory.getDirectory(directory),
                    new StandardAnalyzer(), IndexWriter.MaxFieldLength.LIMITED);

            for (int i = 0; i < this.articleVector.size(); i++) {
                Article article = articleVector.get(i);
                Document document = new Document();
                document.add(new Field("articleId", Integer.toString(article.getArticleId()), Field.Store.YES, Field.Index.NOT_ANALYZED));

                document.add(new Field("title", article.getTitle(), Field.Store.YES, Field.Index.ANALYZED,Field.TermVector.YES));
                System.out.println("Indexing article having title "+article.getTitle());

                document.add(new Field("content", article.getContent().replaceAll("<br>", " "), Field.Store.YES, Field.Index.ANALYZED,Field.TermVector.YES));
                indexWriter.addDocument(document);
            }
            indexWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(IndexArticles.class.getName()).log(Level.SEVERE, null, ex);
        }



    }

    public void setArticleVector(Vector<Article> articleVector) {
        this.articleVector = articleVector;
    }

    public Vector<Article> getArticleVector() {
        return this.articleVector;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getDirectory() {
        return this.directory;
    }
}
