package bit.edu.cn.dictionary;


import bit.edu.cn.dictionary.bean.RecentWord;

public interface RecentOperator {

    void deleteWord(RecentWord word);

    void updateWord(RecentWord word);
}
