package bit.edu.cn.dictionary.bean;

import java.util.Date;

public class RecentWord {
    private String interpret=null;
    private String word=null;
    private Date date;
    public long id;
    public boolean isFlipped=false;
    private boolean isChecked=false;
    private boolean isNoti=false;  //判断有没有在通知栏显示过
    private String pron;

    public RecentWord()
    {
        this.interpret="";
        this.word="";
        this.pron="";
    }
    public void setChecked(boolean checked) {
        isChecked = checked;
    }
    public boolean isChecked(){return isChecked;}

    public RecentWord(long id) { this.id = id; }

    public Date getDate(){return date;}
    public void setDate(Date date){this.date=date;}

    public void setInterpret(String interpret){this.interpret=interpret;}
    public String getInterpret(){return interpret;}

    public void setWord(String word){this.word=word;}
    public String getWord(){return word;}

    public  void setpron(String pron){this.pron=pron;}
    public  String getpron(){return pron;}

    public void setNoti(Boolean bool){isNoti=bool;}
    public Boolean getNoti(){return  isNoti;}

}
