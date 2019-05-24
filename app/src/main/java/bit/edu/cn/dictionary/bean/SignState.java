package bit.edu.cn.dictionary.bean;

public enum SignState {

    NOTYET(0),ALREADY(1);

    public final int intValue;

    SignState(int Value){
        this.intValue=Value;
    }
}
