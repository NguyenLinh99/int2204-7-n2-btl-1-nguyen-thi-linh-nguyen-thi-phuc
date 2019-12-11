/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


/**
 *
 * @author linh_gao_bac
 */
public class DictionaryManagement {

    
    private Map<String, String> mapWord = new HashMap<String, String>();
    public ArrayList<Word> mapFind = new ArrayList();
   

    public Map<String, String> getMapWord() {
        return mapWord;
    }

    public void setMapWord(Map<String, String> mapWord) {
        this.mapWord = mapWord;
    }
    
     public void insertFromCommandline() {
        Scanner cin = new Scanner(System.in);
        System.out.print("Nhập số lượng từ ");
        int n = cin.nextInt();
        cin.nextLine();
        for (int i = 0; i < n; i++) {
            Word word = new Word();
            System.out.print((i + 1) + " Tiếng Anh: ");
            word.setWord_target(cin.nextLine());
            System.out.print("Nghĩa Tiếng Việt: ");
            word.setWord_explain(cin.nextLine());
            mapWord.put(word.getWord_target(), word.getWord_explain());
        }
    }
     
     public void showAllWord(){
        System.out.println("STT"+"\t\t\t"+ "Tiếng Anh"+ "\t\t\t"+"Tiếng Việt");
        final int[] k = {0};
        Set<String> keySet= mapWord.keySet();
        for(String i: keySet) {
            System.out.print(++k[0]);
          //  k[0]++;
            System.out.print( "\t\t\t|"+ i + " : \t\t\t|" + mapWord.get(i)+"\n");
        }
    }

    public void insertFromFile(){
        
        try {
            Scanner sc = new Scanner(new FileInputStream("dictionaries.txt"), "UTF-8");
            String line;
            while(sc.hasNextLine()){
                line = sc.nextLine();
                String[] s = line.split("\\s", 2);
                String target = s[0];
                String explain = s[1];
                mapWord.put(target, explain);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DictionaryManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public String dictionaryLookup(String Eng){
        Eng = Eng.toLowerCase();
        Set<String> keySet= mapWord.keySet();
        for(String i: keySet) {
            if(i.equals(Eng)) {
                //System.out.print( "|"+i + " : \t\t\t|" + dic.words.get(i) + "\n");
                String s= mapWord.get(i);
                return s;
            }
        }
        //System.out.println("không tìm thấy");
        return "không tìm thấy";
    }
     
     public int addWord(String Eng, String mean){
        Word newWord = new Word();
        this.insertFromFile();
        newWord.setWord_target(Eng.toLowerCase()); 
        newWord.setWord_explain(mean.toLowerCase()); 
        if(mapWord.containsKey(Eng.toLowerCase())){
            System.out.println("Từ này đã có trong từ điển!");
            return 1;
        }
        else{
            mapWord.put(newWord.getWord_target(), newWord.getWord_explain());
            this.dictionaryExportToFile("dictionaries.txt");
            return 0;
        }
    }
      
    public int  deleteWord(String Eng){
        Eng = Eng.toLowerCase();
        if(mapWord.containsKey(Eng)){
            
            mapWord.remove(Eng);
            this.dictionaryExportToFile("dictionaries.txt");
            System.out.println("Đã xóa thành công!");
            return 1;
        }
        
        else{
            System.out.println("Không có trong từ điển! ");
            return 0;
        }
    }
    
    public int updateWord(String Eng, String mean){
        Eng = Eng.toLowerCase();
        if(mapWord.containsKey(Eng)){
            mapWord.replace(Eng, mean);
            this.dictionaryExportToFile("dictionaries.txt");
          //  System.out.println("Sửa thành công");
            return 1;
        }
        
        else{
            System.out.println("không tìm thấy từ cần sửa");
            return 0;
        }
        
        
    }
    
    public int dictionarySearcher(String word){
        int i=0;
        mapFind.clear();
        Map<String, String> treeMap = new TreeMap<String, String>(mapWord);
        for(Map.Entry<String, String> entry :  treeMap.entrySet()){
            if(word.length() <= entry.getKey().length()){
                String a = entry.getKey().substring(0, word.length());
                if(a.equals(word)){
                    i++;
                   // System.out.println(entry.getKey());
                    mapFind.add(new Word(entry.getKey(),entry.getValue()));
                   
                }
            }
        }
       
        if(i!=0)
            return 1;
        else
            return 0;
    }
    
    public void dictionaryExportToFile(String fileName){
        try {
            
            File f = new File(fileName);
            FileWriter fw = new FileWriter(f);
           Map<String, String> treeMap = new TreeMap<String, String>(mapWord);
            Set<String> keySet= treeMap.keySet();
            for(String i: keySet) {
                //Bước 2: Ghi dữ liệu
                String s= i+" "+ treeMap.get(i)+"\r\n";
                fw.write(s);                
            }
           
            fw.close();
            
        } catch (IOException ex) {
            System.out.println("Can't write to file " + ex);
        }
    }
    
    
}
