/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TextAnalysis;

import ArticleCollection.Article;
import DbInformation.QueriesForProcessing;
import ArticleCollection.Keywords;
import java.io.IOException;
import java.util.Locale;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;

/**
 *
 * @author robik
 */
public class SubCategorizer {

    final int MAX_WEIGHT = 5;

    public void setKeywordsVector() {
        QueriesForProcessing db = new QueriesForProcessing();
    }

    public String getDirectoryName(String category) {
        String directoryName = null;
        directoryName = "KEYWORDS_OF_" + category.toUpperCase();
        return directoryName;

    }

    public void createIndexOfKeywords(String category) {
        //get directory name for category
        //get keywords for sub categories under that category
        //create index of keywords under that directory name denoting it by the sub category
        String directory = getDirectoryName(category);
        QueriesForProcessing db = new QueriesForProcessing();
       
        Vector<String> subCategoryVector = db.getSubCategoriesOfCategory(category);


        try {
            IndexWriter indexWriter = new IndexWriter(FSDirectory.getDirectory(directory),
                    new StandardAnalyzer(), IndexWriter.MaxFieldLength.UNLIMITED);


//for each subCategory
            for (int scI = 0; scI < subCategoryVector.size(); scI++) {

                String subCategory = subCategoryVector.get(scI);
                
                if(subCategory.toLowerCase().compareToIgnoreCase("extra news")==0)
                    continue;

                String keywordsString = db.getKeywordsStringOfSubCategory(subCategory);

                Document document = new Document();

                document.add(new Field("subCategory", subCategory, Field.Store.YES, Field.Index.ANALYZED,Field.TermVector.YES));

                if (keywordsString != null) {
                    if (keywordsString.length() > 0) {
                        document.add(new Field("keywordsString", keywordsString, Field.Store.YES, Field.Index.ANALYZED,Field.TermVector.YES));
                        System.out.println("Indexing keywords " + keywordsString + " of sub category " + subCategory + " inside the directory " + directory);
                    }
                }

             indexWriter.addDocument(document);                   
            }
            indexWriter.close();
        } catch (CorruptIndexException ex) {
            Logger.getLogger(SubCategorizer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LockObtainFailedException ex) {
            Logger.getLogger(SubCategorizer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SubCategorizer.class.getName()).log(Level.SEVERE, null, ex);
        }
       


    }
    
    
    
    /*It is the general way of finding out the sub category.
    It assumes that an index file of the category with keywords has already
    been constructed. Then it queries the articles agains those keywords
     to determine the sub category.*/
    
    public Vector<Article> subCategorize(Vector<Article> articleVector, String category)
    {   String directory=getDirectoryName(category);
        for(int i=0;i<articleVector.size();i++)
        {
            Article article=articleVector.get(i);
            BM25Analysis bm25=new BM25Analysis(directory,articleVector);
            articleVector.get(i).setSubCategory(bm25.findSubCategory(directory, article));
        }
        
        return articleVector;
    }

    
    /*This type of sub categorization is used only to sports articles. Words
     * which clearly define the sub category eg: football, basketball are checked.
     If the article belongs to more than one sub category, then it is assigned to
     other sports sub-category.*/
    
    public Vector<Article> subCategorizeSports(Vector<Article> articleVector) {
    
        QueriesForProcessing db = new QueriesForProcessing();
       Vector<String> subCategoryVector = db.getSubCategoriesOfCategory("Sports");
        
        boolean isOtherSportsSet = false;
        //For each article
        Article currentArticle;

        for (int articleIndex = 0; articleIndex < articleVector.size(); articleIndex++) {
            //Article 
            currentArticle = articleVector.get(articleIndex);
            String content = currentArticle.getContent();
            isOtherSportsSet = false;
            
            //For each sub-category
            for (int i = 0; i < subCategoryVector.size(); i++) {
                if (isOtherSportsSet) {
                    break;
                }

                String subCategory = subCategoryVector.get(i);

                if (subCategory.compareToIgnoreCase("Other Sports") == 0) {
                    break;
                }

                Vector<String> keywordVector = db.getKeywords(subCategory, MAX_WEIGHT);

                //For each keyword
                for (int j = 0; j < keywordVector.size(); j++) {

                    String keyword = keywordVector.get(j);
                    if (keyword == null || keyword.length() == 0) {
                        continue;
                    }

                    if (currentArticle.getContent().toLowerCase().contains(keyword.toLowerCase())) {
                        if (currentArticle.getSubCategory() == null) {
                            articleVector.get(articleIndex).setSubCategory(subCategory);
                            break;
                        } else {
                            articleVector.get(articleIndex).setSubCategory("Other Sports");
                            isOtherSportsSet = true;
                            break;
                        }
                    }


                }//for keyword

            }//for subcategory


        }//for category

        for (int i = 0; i < articleVector.size(); i++) {
            if (articleVector.get(i).getSubCategory() == null) {
                articleVector.get(i).setSubCategory("Other Sports");
            }
            
            System.out.println("Title: "+articleVector.get(i).getTitle()+" subCategory "+articleVector.get(i).getSubCategory());
        }
        return articleVector;
    }
    
}
