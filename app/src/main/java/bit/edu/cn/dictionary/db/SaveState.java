package bit.edu.cn.dictionary.db;

public enum SaveState {
    FORSAVE(0),FORNOTIFICATION(1);

    public final int intValue;

    SaveState(int Value){
        this.intValue=Value;
    }
}
