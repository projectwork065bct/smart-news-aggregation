/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Idf_Package;

/**
 *
 * @author rajan
 */
/*This class is made as a data-structure for holding the words along with their
 * counts in the text when the text is splitted into words in vector.
 */
public class StringCounter {
    public String word="";
    public int count=1;
    public StringCounter(String str){
        word=str;
    }
    public void increaseCount(){
        count++;
    }
    
}
