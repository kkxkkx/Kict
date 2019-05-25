package bit.edu.cn.dictionary.bean;

public enum SaveState {
    FORSAVE(0),FORNOTIFICATION(1);

    public final int intValue;

    SaveState(int Value){
        this.intValue=Value;
    }
}
