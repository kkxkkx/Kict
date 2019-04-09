package bit.edu.cn.dictionary.bean;

public enum State {

    EMPTY(0),WORDINFO(1),RECENTINFO(2);

    public final int intValue;

    State(int Value){
        this.intValue=Value;
    }
}
