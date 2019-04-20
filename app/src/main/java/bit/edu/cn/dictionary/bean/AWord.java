package bit.edu.cn.dictionary.bean;


import static bit.edu.cn.dictionary.bean.State.NOTSAVE;

public class AWord {
    //key是单词本身，ps是音标，pron是MP3的音频
    private String key=null,psE=null,pronE=null,psA=null,pronA=null;

    //origs是例句，trans是例句的翻译,interpret是单词的翻译
    private String interpret=null;
    private String[] Origs,Trans;
    private int SentenceNum=0;

    //state记录这个单词有没有加入生词本
    public State state=NOTSAVE;


    public AWord() {
        super();
        Origs=new String[20];
        Trans=new String[20];
        this.key = "";      //防止空指针异常
        this.psE = "";
        this.pronE = "";
        this.psA = "";
        this.pronA = "";
        this.interpret = "";
        this.Origs[SentenceNum] ="";
        this.Trans[SentenceNum] ="";
        this.state= NOTSAVE;
    }

    public int getSentenceNum(){return SentenceNum;}

    public void setKey(String key){this.key=key;}
    public String getKey(){return key;}

    public void setPsE(String psE){this.psE=psE;}
    public String getPsE(){return psE;}

    public void setPsA(String psA){this.psA=psA;}
    public String getPsA(){return psA;}

    public void setPronA(String pronA){this.pronA=pronA;}
    public String getPronA(){return pronA;}

    public void setPronE(String pronE){this.pronE=pronE;}
    public String getPronE(){return pronE;}

    public void setOrigs(String origs){this.Origs[SentenceNum]=origs;}
    public String getOrigs(int num){return Origs[num];}

    public void setTrans(String trans){this.Trans[SentenceNum++]=trans;}
    public String getTrans(int num){return Trans[num];}

    public void setInterpret(String interpret){this.interpret=interpret;}
    public String getInterpret(){return interpret;}

    public void setState(State state){this.state=state;}
    public  State getState(){return state;}


}