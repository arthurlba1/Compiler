package src.semantics;

import javafx.util.Pair;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

public class DictionarySemantics  {
    Dictionary<String, Pair<String, String>> dic;

    public DictionarySemantics(){
        this.dic = new Hashtable<String,Pair<String, String>>();
    }
    public DictionarySemantics(DictionarySemantics base){
        this.dic = new Hashtable<String,Pair<String, String>>((Map<String,Pair<String, String>>)base.getDic());
    }
    public void put(String a, Pair<String, String> b){
        this.dic.put(a, b);
    }
    public Pair<String, String> get(String key){
        return dic.get(key);
    }
    public Dictionary getDic(){
        return this.dic;
    }
}
