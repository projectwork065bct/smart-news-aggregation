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
            String content = articleVector.get(i).getTitle() + " " + articleVector.get(i).getContent();
            for (int j = 0; j < places.size(); j++) {
                String place = places.get(j);
                if (hasPlace(content, place)) {
                    System.out.println(articleVector.get(i).getTitle() + " is Nepali because it contains the word " + place);
                    found = true;
                    break;
                }
            }
            if (!found) {
                articleVector.remove(i);
                i--;
            }
        }
    }

    /**
     *
     * @author ramsharan
     */
    public boolean hasPlace(String content, String place) {
        content = content.toLowerCase();
        place = place.toLowerCase();
        int lengthOfContent = content.length();
        int lengthOfPlace = place.length();
        int end = 0;
        boolean hit = false;
        boolean hasPlace = false;

        for (int i = 0; i < lengthOfContent; i++) {

            if (content.charAt(i) != place.charAt(0)) {
                continue;
            }//end if
            end = i + 1;
            hit = true;
            for (int j = 1; (j < lengthOfPlace) && (end < lengthOfContent); j++, end++) {
                if (content.charAt(end) == place.charAt(j)) {
                    continue;
                } else {
                    hit = false;
                    break;
                }
            }//end inner for

            if (hit == true) {
                if ((i == 0) || (!isAlphabetOrNumber(content.charAt(i - 1)))) {
                    if ((end == lengthOfContent) || (!isAlphabetOrNumber(content.charAt(end)))) {
                        return true;
                    }//end inner if
                }//end middle if
            }//end outer if

        }//end outer for
        return false;
    }//end of function

    public boolean isAlphabetOrNumber(char c) {
        if (c >= 'a' && c <= 'z') {
            return true;
        } else if (c >= '0' && c <= '9') {
            return true;
        } else if (c >= 'A' && c <= 'Z') {
            return true;
        } else {
            return false;
        }
    }//end of function

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
