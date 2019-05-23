package bit.edu.cn.dictionary.adapter;

public enum Card {
    WORD(0),INTERPRET(1);

    public final int intValue;

    Card(int Value){
        this.intValue=Value;
    }
}
