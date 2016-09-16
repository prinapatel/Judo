import java.io.*;
import java.util.*;

public class judo{

    public static void main(String[] args){
        try{
            String fileName=args[0];
            String line=null;           //read the data
            FileReader fileReader=new FileReader(fileName);
            BufferedReader BufferedReader=new BufferedReader(fileReader);
            ArrayList<String> words=new ArrayList<String>();
            while((line=BufferedReader.readLine()) != null){
                if(line.trim().length()>0){  //ignore the empty lines and all puctuation etc, make everything lower case
                    for(String word : line.split("\\s+")){
                        words.add(word.replaceAll("[^a-zA-Z ]", "").toLowerCase());
                    }
                }
            }
            
            //build a unique word list
            Set<String> uniquewords = new LinkedHashSet<>(words);
            String[] uwords = uniquewords.toArray(new String[uniquewords.size()]);
 
            BufferedReader.close();
            
            Arrays.sort(uwords);  //sort and print to screen the alphabetised list
            for(int i=0;i<uwords.length;i++){
                System.out.println(uwords[i]);
            }
            
            ArrayList<String> all = new ArrayList<String>();
            all=make6(uwords);  //all the 6 letter words
            
            String[] perms=all.toArray(new String[all.size()]);
            
            ArrayList<String> asix=new ArrayList<String>();
            asix=filt(uwords,6);
            String[] six = asix.toArray(new String[asix.size()]);
            
            List x=Arrays.asList(perms);
            List y=Arrays.asList(six);

            System.out.println("Part 2:");

            for(int i=0;i<six.length;i++){  //which 6 letter word is a combination?
                if(x.contains(y.get(i))){
                    System.out.println(y.get(i));
                }
            }

            
        }
        
        catch(FileNotFoundException ex){
            System.out.println("File not found");
        }
        
        catch(IOException ex){
            System.out.println("Error reading file");
        }
        
        
    }
    
    public static ArrayList<String> filt(String[] words, int n){
        //function that takes an array of words and returns the subset of length n
        ArrayList<String> list=new ArrayList<String>();
        for(int i=0;i<words.length;i++){
            if(words[i].length()==n){
                list.add(words[i]);
            }
        }
        
        return list;
    }
    
    public static ArrayList<String> make6(String[] words){
        //function that takes an array of words and makes all possible combinations of length 6 - really ugly!
        ArrayList<String> aone=new ArrayList<String>();
        ArrayList<String> atwo=new ArrayList<String>();
        ArrayList<String> athree=new ArrayList<String>();
        ArrayList<String> afour=new ArrayList<String>();
        ArrayList<String> afive=new ArrayList<String>();
        
        aone=filt(words,1);
        atwo=filt(words,2);
        athree=filt(words,3);
        afour=filt(words,4);
        afive=filt(words,5);
        
        String[] one = aone.toArray(new String[aone.size()]);
        String[] two = atwo.toArray(new String[atwo.size()]);
        String[] three = athree.toArray(new String[athree.size()]);
        String[] four = afour.toArray(new String[afour.size()]);
        String[] five = afive.toArray(new String[afive.size()]);
        
        ArrayList<String> new_words=new ArrayList<String>();
        
        for(int i=0;i<one.length;i++){
            for(int j=0;j<five.length;j++){
                new_words.add(one[i]+five[j]);
                new_words.add(five[j]+one[i]);
            }
        }
        
        for(int i=0;i<two.length;i++){
            for(int j=0;j<four.length;j++){
                new_words.add(two[i]+four[j]);
                new_words.add(four[j]+two[i]);
            }
        }
        
        for(int i=0;i<three.length;i++){
            for(int j=0;j<three.length;j++){
                new_words.add(three[i]+three[j]);
            }
        }
        return new_words;
    }
}