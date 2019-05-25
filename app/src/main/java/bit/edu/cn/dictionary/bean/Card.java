package bit.edu.cn.dictionary.bean;

public enum Card {
    WORD(0),INTERPRET(1);

    public final int intValue;

    Card(int Value){
        this.intValue=Value;
    }
}
