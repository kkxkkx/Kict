package bit.edu.cn.dictionary.bean;

public enum Page {
    EMPTY(0),WORDINFO(1),RECENTINFO(2);

    public final int intValue;

    Page(int Value){
        this.intValue=Value;
    }
}