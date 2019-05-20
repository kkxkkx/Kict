package bit.edu.cn.dictionary;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import bit.edu.cn.dictionary.adapter.ListAdapter;
import bit.edu.cn.dictionary.bean.RecentWord;
import bit.edu.cn.dictionary.db.SaveWord;
import bit.edu.cn.dictionary.notification.CheckboxChangeListener;
import bit.edu.cn.dictionary.notification.ItemClickListener;
import bit.edu.cn.dictionary.notification.ItemLongClickListener;

import static bit.edu.cn.dictionary.adapter.ListAdapter.words;

public class ListActivity extends AppCompatActivity {

    public static final String TAG="Save_Activity";
    public static ListAdapter ListAdapter;
    public RecyclerView saved_recycler;
    public TextView toolbar_title;
    public static SaveWord ListWord;  //单词本中的单词
    public Toolbar toolbar;
    public static boolean edit_state=false;
    private static boolean mIsDeleteMode = false;

    private MenuItem mDeleteWord;
    private boolean isLong=false;  //激发方式
    private MenuItem mSelectAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list);
        toolbar_title=findViewById(R.id.word_list_title);

        toolbar=findViewById(R.id.word_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_black);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");


        //toolbar的监听
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDeleteMode()){
                    BackToNormal();
                }
                else{
                    finish();
                }
            }
        });


        //生词本的recyclerView
        saved_recycler=findViewById(R.id.save_list);
        saved_recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        saved_recycler.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        saved_recycler.setItemAnimator(new DefaultItemAnimator());

        ListAdapter=new ListAdapter();

        ListAdapter.setmListener(new ItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {
                RecentWord reword=words.get(position);
                if(!mIsDeleteMode){
                    startActivity(new Intent(ListActivity.this, CardActivity.class));
                    Log.v("click", String.valueOf(mIsDeleteMode));

                }else{
                    Log.v("item click","");
                    checkbox(view,position);
                }
            }
        });

        //ListAdapter.set

        //监听长按
        ListAdapter.setMlonglistener(new ItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                isLong=true;
                Log.v("long click", String.valueOf(mIsDeleteMode));
                changeDeleteMode(true);
                toolbar.setNavigationIcon(R.drawable.ic_delete);
                RecentWord reword=words.get(position);
                reword.setChecked(true);
                ListAdapter.SaveViewHolder viewHolder=new ListAdapter.SaveViewHolder(view);
                viewHolder.getCheckBox().setChecked(reword.isChecked());
                ListAdapter.notifyDataSetChanged();
            }
        });


        ListAdapter.setMyCheckboxListener(new CheckboxChangeListener() {
            @Override
            public void onChanged() {
                int checkNum=0;
                for(int i=0;i<words.size();i++){
                    if(words.get(i).isChecked()){
                        checkNum++;
                    }
                }
                if(checkNum==0&&isLong)   //如果是长按激发的，没有勾选时退出
                {
                    BackToNormal();
                }
                else {
                    if(checkNum==0)
                    {
                        mDeleteWord.setEnabled(false);
                        mDeleteWord.setIcon(R.drawable.ic_delete_list_not);
                    }else{
                        mDeleteWord.setEnabled(true);
                        mDeleteWord.setIcon(R.drawable.ic_delete_list);
                    }
                    Log.v(TAG, "checkNum:" + String.valueOf(checkNum));
                    if (mSelectAll != null) {
                        if (checkNum == words.size() && "ALL".equals(mSelectAll.getTitle())) {
                            toolbar_title.setText(R.string.toolbar_delete);
                            mSelectAll.setTitle("NOT ALL");    //下一次只能匹配NOT ALL
                            mSelectAll.setIcon(R.drawable.ic_select_all);
                        }
                        if (checkNum < words.size() && "NOT ALL".equals(mSelectAll.getTitle())) {
                            toolbar_title.setText("");
                            mSelectAll.setTitle("ALL");
                            mSelectAll.setIcon(R.drawable.ic_wait_to_check);
                        }
                    }
                }
                Log.v(TAG, String.valueOf(mSelectAll.getTitle()));
            }

            @Override
            public void onCheckClick(View view, int position) {
                checkbox(view,position);
            }
        });

        saved_recycler.setAdapter(ListAdapter);
        ListWord=new SaveWord(this);
        ListAdapter.refresh(ListWord.LoadFromSavedDB());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        mDeleteWord=menu.findItem(R.id.delete_note);
        mSelectAll=menu.findItem(R.id.select_all);
        mSelectAll.setVisible(false);
        mDeleteWord.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                changeDeleteMode(true);
                toolbar.setNavigationIcon(R.drawable.ic_delete);
                ListAdapter.notifyDataSetChanged();
                isLong=false;
                break;

            case R.id.action_debug:
                startActivity(new Intent(this, DebugActivity.class));
                break;

            case R.id.action_card:
                startActivity(new Intent(this, CardActivity.class));
                break;
            case R.id.delete_note:

                int checkNum=0;   //checkbox勾选的个数
                for(int i=0;i<words.size();i++){
                    if(words.get(i).isChecked())
                        checkNum++;
                }
                if(checkNum!=0)
                createAlertDialog(checkNum);
                break;

            case R.id.select_all:
                Log.v(TAG, String.valueOf(mSelectAll.getTitle()));
                if("NOT ALL".equals(mSelectAll.getTitle())){
                    Log.d(TAG, "select_all！！！！");
                    for(int i=0;i<words.size();i++){
                        words.get(i).setChecked(false);
                    }
                }else {   //全选的时候
                    isLong=false;
                    toolbar_title.setText(R.string.toolbar_delete);
                    for (int i = 0; i < words.size(); i++) {
                        words.get(i).setChecked(true);
                        Log.v(TAG, String.valueOf(words.get(i).isChecked()));
                        View view = saved_recycler.getChildAt(i);
                        if (view != null) {
                            ListAdapter.SaveViewHolder viewHolder = new ListAdapter.SaveViewHolder(view);
                            viewHolder.getCheckBox().setChecked(true);
                        }
                    }
                }
                ListAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    protected void createAlertDialog(int number) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
        builder.setTitle("Delete");
        builder.setMessage("Are you sure you want to delete " + number + (number > 1 ? " items?" : " item?"));
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0; i < words.size(); i++) {
                    if (words.get(i).isChecked()) {
                        ListWord.deleteWord(words.get(i).getWord());
                    }
                }
                ListAdapter.refresh(ListWord.LoadFromSavedDB());
                changeDeleteMode(false);
                toolbar.setNavigationIcon(R.drawable.ic_back_black);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }


    public static boolean isDeleteMode() {
        return mIsDeleteMode;
    }

    private void changeDeleteMode(boolean isDelete) {
        mIsDeleteMode = isDelete;
        mDeleteWord.setVisible(isDelete);
        mSelectAll.setVisible(isDelete);
        if (isDelete) {
            toolbar_title.setText("");
        } else {
            toolbar_title.setText(R.string.toolbar_title);
        }
    }

    public void BackToNormal()
    {
        toolbar.setNavigationIcon(R.drawable.ic_back_black);
        changeDeleteMode(false);
        toolbar_title.setText(R.string.toolbar_title);
        for(int i=0;i<words.size();i++)
        {
            words.get(i).setChecked(false);
        }
        ListAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode== KeyEvent.KEYCODE_BACK) {
            changeDeleteMode(false);
            for (int i = 0; i < words.size(); i++) {
                words.get(i).setChecked(false);
            }
            finish();
        }
        Log.v(TAG,"back");
        return true;
    }

    public void checkbox(View view,int position)
    {
        RecentWord reword=words.get(position);
        reword.setChecked(!reword.isChecked());
        Log.v(TAG, String.valueOf(reword.isChecked()));
        ListAdapter.SaveViewHolder viewHolder=new ListAdapter.SaveViewHolder(view);
        viewHolder.getCheckBox().setChecked(reword.isChecked());
    }

}
