/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ArticleCollection;

/**
 *
 * @author robik
 */
public class RelatedArticle {

    private int id;
    private float score;

    public RelatedArticle() {
    }

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public RelatedArticle(int id, float score) {
        setId(id);
        setScore(score);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public float getScore() {
        return score;
    }
}
