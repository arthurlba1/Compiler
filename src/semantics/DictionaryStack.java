package src.semantics;

import java.util.Stack;  
//import src.semantics.DictionarySemantics;

public class DictionaryStack {
    Stack<DictionarySemantics> stack = new Stack<>();

    public void add (DictionarySemantics a){
        stack.add(a);
    }

    public void pop () {
        if(!stack.empty())
            stack.pop();
    }

    public DictionarySemantics peek (){
        return stack.peek();
    }
}
