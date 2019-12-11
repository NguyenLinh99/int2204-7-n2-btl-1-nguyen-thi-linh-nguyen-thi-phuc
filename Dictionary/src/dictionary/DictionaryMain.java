/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary;

/**
 *
 * @author linh_gao_bac
 */
public class DictionaryMain {
    
    public static void main(String[] args) {
        DictionaryManagement dm = new DictionaryManagement();
     //   dm.insertFromCommandline();
        dm.insertFromFile();
        dm.showAllWord();
        System.out.println(dm.dictionaryLookup("Vietnamese")); 
        dm.addWord("Vietnamese", "Việt Nam");
        dm.deleteWord("vietnamese");
        dm.updateWord("hi", "xin chao");
        dm.dictionarySearcher("ab");
    }
}
