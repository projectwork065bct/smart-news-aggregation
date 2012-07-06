/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Idf_Package;

import java.util.StringTokenizer;
import java.util.Vector;

/**
 *
 * @author rajan
 */
/*
 * This class holds one piece of text document. It splits the text into 
 * words along with their count, so that distinct words could be easily found 
 * out. used for indexing, and calculating similarity between documents in collection
 * class
 */
public class Document {
    String text;
    public Vector<String> words=new Vector();
    public Vector<StringCounter> count=new Vector();
    public void sentenceSplitter(String speech){
        /*default found in java, not used here*/
        /*This will break the text into tokens, words*/
        //String speech = "My name is Rajan Prasad Upadhyay.I am twenty-two years old.";
        StringTokenizer st = new StringTokenizer(speech,".");
        System.out.println(st.countTokens());
        //st.nextToken(".");
        while (st.hasMoreTokens()) {
            System.out.println(st.nextToken());
        }
    }
    
    public Document(String text){
        this.text=text;
        parse();
    }
    public Document(){}//never use like this
    /*internal operations methods not needed outside*/
    private boolean isValid(char c){
        //
        if(c=='.'||c=='\''||c==','||c=='?'||c=='!'||c==':'||c=='\t'||c=='\n'){
            return false;
        }else{
            return true;
        }
    }
    private void parse(){
        /*split the text into words and store in vector*/
        String word="";
        char c;
        int i=0;
        int n=text.length();
        while (i<n){
            c=text.charAt(i++);
            //System.out.print(c);
            if(c!=' '&& isValid(c)){
                word+=c;
                if(i==n){
                    this.insertWord(word);
                    word="";
                }
                continue;
            }
            else{
                if(word.length()>0){
                    //System.out.println(word);
                    this.insertWord(word);
                    word="";
                }else{
                    //ie word.length=0
                    continue;
                }
            }//end else
        }//while
        
    }
    /*
     * These methods may be needed outside 
     */
    public void insertWord(String word){
        /*
         * if word is not present in the vector then insert it into the vector
         * and allocate count 1 , otherwise increase the count associated with 
         * the word by 1.
         */
        if(!words.contains(word)){
                words.add(word);
                count.add(new StringCounter(word));
            }
            else{
                count.elementAt(words.indexOf(word)).increaseCount();
            }
    }
    public void printWeighedWords(){
        /*
         * This is just a debugging method
         */
        int max=getDistinctWordsCount();
        for(int i=0;i<max;i++){
            System.out.println(words.elementAt(i)+" :"+count.elementAt(i).count);
        }
    }
    public int getDistinctWordsCount(){
        /*
         * returns the total number of distinct words
         * ie. if the word "rain" is repeated many times
         * it returns as only one word
         */
        return words.size();
    }
    public int getTotalWordsCount(){
        /*
         * returns total number of words, counts
         * same word multiple times if it is repeated 
         * multiple times
         */
        int i=0;
        for(int j=0;j<count.size();j++){
            i+=count.elementAt(j).count;
        }
        return i;
    }
    public int getCountOfTerm(String term){
        /*
         * returns the count of term if it is present
         * otherwise returns zero
         */
        int ind=words.indexOf(term);
        if(ind>0){
            return count.elementAt(ind).count;
        }else{
            return 0;
        }
    }
}
