package bit.edu.cn.dictionary.search;


import bit.edu.cn.dictionary.bean.RecentWord;

public interface RecentOperator {

    void deleteWord(RecentWord word);
    void changeFragment(String s);
}
