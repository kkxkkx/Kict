package bit.edu.cn.dictionary.bean;

import java.util.Date;

public class RecentWord {
    private String interpret=null;
    private String word=null;
    private Date date;
    public long id;

    public RecentWord(long id) { this.id = id; }

    public Date getDate(){return date;}
    public void setDate(Date date){this.date=date;}

    public void setInterpret(String interpret){this.interpret=interpret;}
    public String getInterpret(){return interpret;}

    public void setWord(String word){this.word=word;}
    public String getWord(){return word;}

}
