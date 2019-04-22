package bit.edu.cn.dictionary.search;

import bit.edu.cn.dictionary.bean.AWord;

public class SearchFromInternet {
    private final static String TAG="SearchFromInternet";
    public  static AWord aWord;
    public static boolean flag;

//    public void getWordFromInternet() {
//
//        final String Word_temp = searchword;
//        if (Word_temp == null || Word_temp.equals(""))
//            return ;
//        char[] array = Word_temp.toCharArray();
//        if (array[0] > 256)
//            return ;
//
//        final String URL_temp = NetworkUtils.Search_Word1 + Word_temp + NetworkUtils.Search_Word2;
//        Log.v(TAG,URL_temp.toString());
//        Thread thread = new Thread(new Runnable() {
//
//            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//            @Override
//            public void run() {
//
//                try {
//                    Log.v(TAG, "start thread");
//                    Word_Now = NetworkUtils.getInputStreamByUrl(URL_temp, searchword);
//                    if (Word_Now == null)
//                    {
//                        switchFragment(ERROR);
//                    } else {
//                        Runnable updateUIControl=new Runnable() {
//                            @Override
//                            public void run() {
//
//                                if(saveWord.IsSaved(searchword))
//                                {
//                                    Log.v(TAG,"refreshUI");
//                                    wordFragment.iv_state.setImageResource(R.drawable.saved);
//                                }
//                                else {
//                                    Log.v(TAG,"refreshUI");
//                                    wordFragment.iv_state.setImageResource(R.drawable.tosave);
//
//                                }
//                                wordFragment.refresh();
//                            }
//                        };
//                        SearchActivity.this.runOnUiThread(updateUIControl);
//                        HistoryWord history=new HistoryWord(getBaseContext());
//
//                        if(!history.IsExistDB(Word_Now))
//                            history.saveInfoDatabase(Word_Now);
//                        Word_Now.setState(NOTSAVE);
//                        Log.v(TAG, String.valueOf(Word_Now.getState()));
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (ParserConfigurationException e) {
//                    e.printStackTrace();
//                } catch (SAXException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//        thread.start();
//    }
}
