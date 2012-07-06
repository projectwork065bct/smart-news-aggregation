/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ArticleCollection;

import DbInformation.QueriesForProcessing;
import java.util.Vector;

/**
 *
 * @author robik
 */
public class Filter {

    private Vector<Article> articleVector = new Vector();

    public void setArticleVector(Vector<Article> articleVector) {
        this.articleVector = articleVector;
    }

    public void automaticStart() {
        System.out.println("Non-Nepali and other undesirable articles are being filtered out....");
        removeDuplicateArticles();
        removeInternationalArticles();

    }

    public void removeDuplicateArticles() {
        for (int i = 0; i < articleVector.size(); i++) {
            Article article = articleVector.get(i);
            QueriesForProcessing db = new QueriesForProcessing();
            if (db.isTitleDuplicate(article.getTitle())) {
                articleVector.remove(i);
                i--;
            }
        }
    }
//Nepal needed

    public void removeInternationalArticles() {
        QueriesForProcessing db = new QueriesForProcessing();
        boolean found = false;
        Vector<String> places = db.getPlaces();

        for (int i = 0; i < articleVector.size(); i++) {
            found = false;
            for (int j = 0; j < places.size(); j++) {
                String content = articleVector.get(i).getContent();
                content = getFirstSentence(content);
                if (content.toLowerCase().contains(places.get(j).toLowerCase())) {
                    System.out.println(articleVector.get(i).getTitle() + " is nepali because it contains the word: " + places.get(j));
                    articleVector.get(i).setPlace(places.get(j));
                    found = true;
                    break;
                }//if
            }//for j
            if (found) {
                continue;
            } else {
                QueriesForProcessing a = new QueriesForProcessing();
                a.deleteArticle(articleVector.get(i).getArticleId());
                System.out.println("title: " + articleVector.get(i).getTitle() + " was removed");
                articleVector.remove(i);
                i--;
            }//else
        }//for i
    }

    public String getFirstSentence(String s) {
        String a = "";
        if (s.contains(".")) {

            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == '.') {
                    break;
                } else {
                    a += c;
                }

            }
        }
        return a;
    }

    public void extraFilterIfAny() {
    }

    public Vector<Article> getArticleVector() {
        return articleVector;
    }
}
