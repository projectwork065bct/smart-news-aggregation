/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ArticleCollection;

import DbInformation.QueriesForProcessing;
import TextAnalysis.BM25Analysis;
import TextAnalysis.IndexArticles;
import TextAnalysis.SubCategorizer;
import java.util.Vector;
import java.util.Calendar;

/**
 *
 * @author robik
 */
public class Controller {

    public void automaticallyInsertArticles() {
        QueriesForProcessing db = new QueriesForProcessing();


        Vector<RSSLink> rssLinks = db.getRSSLinks();
        Vector<Article> articleVector = new Vector();
        for (int i = 0; i < rssLinks.size(); i++) {

            System.out.println("THE CURRENT RSS LINK BEING READ IS: " + rssLinks.get(i).getLink());
            RSSReader rssReader = new RSSReader();
            RSSLink rssLink = rssLinks.get(i);

            rssReader.setRSSLink(rssLink.getLink());
            rssReader.setCategory(rssLink.getCategory());
            rssReader.setSubCategory(rssLink.getSubCategory());
            rssReader.setChannel(rssLink.getChannel());

            rssReader.automaticStart();
            articleVector = rssReader.getArticleVector();
            System.out.println("Before filtration, the no. of articles collected is: " + articleVector.size());
            Filter filter = new Filter();
            filter.setArticleVector(articleVector);
            filter.automaticStart();
            articleVector = filter.getArticleVector();
            System.out.println("After filtration, the no. of articles to be stored is: " + articleVector.size());
            db.insertArticles(articleVector);
        }
    }

    public void automaticallySubCategorizeArticles() {
        SubCategorizer sc = new SubCategorizer();
        QueriesForProcessing db = new QueriesForProcessing();

        //Sub categorize articles belonging to national category
        Vector<Article> articleVectorNational = new Vector();
        articleVectorNational = db.getArticleVectorOfNoSubCategory("National");
        sc.subCategorize(articleVectorNational, "National");
        db.updateSubCategory(articleVectorNational);

        //Sub categorize articles belonging to sports category
        Vector<Article> articleVectorSports = new Vector();
        articleVectorSports = db.getArticleVectorOfNoSubCategory("Sports");
        sc.subCategorizeSports(articleVectorSports);
        db.updateSubCategory(articleVectorSports);

    }

    /////needs to be modified so that it generates the previous dates based on the input given by the user
    ////////////NEEDS TO BE CHANGED IN SUCH A WAY THAT THE DATES ARE THOSE DATES WHICH THE ADMIN WANTS TO BE INDEXED!!!!!!!!!
    public void automaticIndexing(int days) {
        Vector<String> previousDates = new Vector();

        previousDates.add(getCurrentDate());
        if (days != 0) {
            for (int i = 1; i <= days; i++) {
                previousDates.add(getPastOrFutureDate(-1 * i));
            }
        }
        automaticallyIndexArticles(previousDates);
    }

    public void automaticallyIndexArticles(Vector<String> previousDates) {
        QueriesForProcessing db = new QueriesForProcessing();

        Vector<String> categoryVector = db.getCategories();
        Vector<Article> articleVectorToBeIndexed;

        //for each category
        for (int i = 0; i < categoryVector.size(); i++) {
            String category = categoryVector.get(i);
            Vector<String> subCategoryVector = db.getSubCategoriesOfCategory(category);

            //for each sub category
            for (int j = 0; j < subCategoryVector.size(); j++) {
                articleVectorToBeIndexed = new Vector();
                String subCategory = subCategoryVector.get(j);

                //for each date
                for (int k = 0; k < previousDates.size(); k++) {
                    //add all the articles of that sub-category and previous dates
                    articleVectorToBeIndexed.addAll(db.getArticleVectorOfDateAndSubCategory(previousDates.get(k), subCategory));
                }


                //insert a directory name into database and use it to create index
                String date = getCurrentDate();
                String directoryName = db.getDirectoryName(subCategory, previousDates, date);
                System.out.println("The name of the directory (index file) to be created is: " + directoryName);
                IndexArticles indexArticles = new IndexArticles(directoryName, articleVectorToBeIndexed);
                System.out.println("The no. of articles to be indexed is: " + articleVectorToBeIndexed.size());
                indexArticles.createIndex();

            }
        }
    }

    public void automaticallyCreateIndexOfKeywords() {
        SubCategorizer sc = new SubCategorizer();
        sc.createIndexOfKeywords("National");
    }

    public void automaticallyFindBM25Score(int days) {

        QueriesForProcessing db = new QueriesForProcessing();

        Vector<String> subCategoryVector = db.getSubCategories();
        Vector<String> dates = new Vector();

        dates.add(getCurrentDate());

        if (days > 0) {
            for (int i = 1; i <= days; i++) {
                dates.add(getPastOrFutureDate(-1 * i));
            }
        }


        for (int i = 0; i < subCategoryVector.size(); i++) {

            String subCategory = subCategoryVector.get(i);

            System.out.println("Analyzing articles of sub-category " + subCategory);


            Vector<String> directoryVector = db.getDirectoryFromDateAndSubCategory(subCategory, getCurrentDate());

            for (int j = 0; j < directoryVector.size(); j++) {

                Vector<Article> articleVectorToQuery = new Vector();

                //Use articles grabbed today as the query
                //////////MODIFICATION MIGHT BE REQUIRED!!!!!
                ////Modification would allow admin to choose the dates 
                //and sub category which are to be queries

                for (int k = 0; k < dates.size(); k++) {
                    articleVectorToQuery.addAll(db.getArticleVectorOfDateAndSubCategory(dates.get(k), subCategory));
                }

                String directory = directoryVector.get(j);

                System.out.println("Analyzing articles of the directory: " + directory);

                BM25Analysis bm25Analysis = new BM25Analysis();
                Vector<Article> articleVectorWithScore = new Vector();
                if (subCategory.compareToIgnoreCase("Accidents and Crime") != 0 && subCategory.compareToIgnoreCase("Other Sports") != 0 && subCategory.compareToIgnoreCase("Extra News") != 0) {
                    articleVectorWithScore = bm25Analysis.getScoredArticles(directory, articleVectorToQuery, subCategory);
                }



                System.out.println("Now i am trying to store in database");
                db.updateScore(articleVectorWithScore);
            }
        }

    }

    //////////////////////////////////////////////////////////////////////////
    //////These are the functions to retrieve current and past/future dates///
    public String getCurrentDate() {
        Calendar now = Calendar.getInstance();
        String date = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + (now.get(Calendar.DATE));
        return date;
    }

    //Yesterday: days=-1 Tomorrow: days=1;
    public String getPastOrFutureDate(int days) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DATE, days);
        String date;
        date = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DATE);
        return date;
    }
    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////
}
