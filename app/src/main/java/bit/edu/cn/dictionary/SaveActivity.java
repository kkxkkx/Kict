package bit.edu.cn.dictionary;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import bit.edu.cn.dictionary.bean.RecentWord;
import bit.edu.cn.dictionary.db.SaveWord;
import bit.edu.cn.dictionary.adapter.SaveAdapter;
import bit.edu.cn.dictionary.notification.CheckboxChangeListener;
import bit.edu.cn.dictionary.notification.ItemClickListener;
import bit.edu.cn.dictionary.notification.ItemLongClickListener;

import static bit.edu.cn.dictionary.adapter.SaveAdapter.words;

public class SaveActivity extends AppCompatActivity {

    public static final String TAG="Save_Activity";
    public  SaveAdapter saveAdapter;
    public RecyclerView saved_recycler;
    public TextView toolbar_title;
    public SaveWord saveWord;
    public Toolbar toolbar;
    public static boolean edit_state=false;
    private static boolean mIsDeleteMode = false;

    private MenuItem mDeleteWord;
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

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDeleteMode()){
                    toolbar.setNavigationIcon(R.drawable.ic_back_black);
                    changeDeleteMode(false);
                    for(int i=0;i<words.size();i++)
                    {
                        words.get(i).setChecked(false);
                    }
                    saveAdapter.notifyDataSetChanged();
                }
                else{
                    finish();
                }
            }
        });




        saved_recycler=findViewById(R.id.save_list);
        saved_recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        saved_recycler.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        saved_recycler.setItemAnimator(new DefaultItemAnimator());

        saveAdapter=new SaveAdapter();

        saveAdapter.setmListener(new ItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {
                RecentWord reword=words.get(position);
                if(!mIsDeleteMode){
                    //todo 正常情况没有写
                    Log.v("click", String.valueOf(mIsDeleteMode));
                }else{
                    reword.setChecked(!reword.isChecked());
                    SaveAdapter.SaveViewHolder viewHolder=new SaveAdapter.SaveViewHolder(view);
                    viewHolder.getCheckBox().setChecked(reword.isChecked());
                }
            }
        });

        saveAdapter.setMlonglistener(new ItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                Log.v("long click", String.valueOf(mIsDeleteMode));
                changeDeleteMode(true);
                toolbar.setNavigationIcon(R.drawable.ic_delete);
                RecentWord reword=words.get(position);
                reword.setChecked(true);
                saveAdapter.notifyDataSetChanged();
            }
        });


        saveAdapter.setMyCheckboxListener(new CheckboxChangeListener() {
            @Override
            public void onChanged() {
                int checkNum=0;
                for(int i=0;i<words.size();i++){
                    if(words.get(i).isChecked()){
                        checkNum++;
                    }
                }
                Log.v(TAG, checkNum+":"+String.valueOf(checkNum));
                if(mSelectAll!=null){
                    if(checkNum==words.size()&&"ALL".equals(mSelectAll.getTitle())){
                        toolbar_title.setText(R.string.toolbar_delete);
                        mSelectAll.setTitle("NOT ALL");
                        mSelectAll.setIcon(R.drawable.ic_all_select_already);
                    }
                    if(checkNum<words.size()&&"NOT ALL".equals(mSelectAll.getTitle())){
                        toolbar_title.setText("");
                        mSelectAll.setTitle("ALL");
                        mSelectAll.setIcon(R.drawable.ic_all_select);
                    }
                }
                Log.v(TAG, String.valueOf(mSelectAll.getTitle()));
            }
        });

        saved_recycler.setAdapter(saveAdapter);
        saveWord=new SaveWord(this);
        saveAdapter.refresh(saveWord.LoadFromSavedDB());

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
                //TODO 点击edit进行编辑有问题
                changeDeleteMode(true);
                toolbar.setNavigationIcon(R.drawable.ic_delete);

                break;
            case R.id.action_debug:
                return true;
            case R.id.action_card:
                return true;
            case R.id.delete_note:

                int checkNum=0;   //checkbox勾选的个数
                for(int i=0;i<words.size();i++){
                    if(words.get(i).isChecked())
                        checkNum++;
                }
                createAlertDialog(checkNum);
                break;

            case R.id.select_all:
                Log.v(TAG, String.valueOf(mSelectAll.getTitle()));
                if("NOT ALL".equals(mSelectAll.getTitle())){
                    Log.d(TAG, "select_all！！！！");
                    for(int i=0;i<words.size();i++){
                        words.get(i).setChecked(false);
                        toolbar.setNavigationIcon(R.drawable.ic_back_black);
                        changeDeleteMode(false);
                    }
                }else {   //全选的时候
                    toolbar_title.setText(R.string.toolbar_delete);
                    for (int i = 0; i < words.size(); i++) {
                        words.get(i).setChecked(true);
                        Log.v(TAG, String.valueOf(words.get(i).isChecked()));
                        View view = saved_recycler.getChildAt(i);
                        if (view != null) {
                            SaveAdapter.SaveViewHolder viewHolder = new SaveAdapter.SaveViewHolder(view);
                            viewHolder.getCheckBox().setChecked(true);
                        }
                    }
                }
                saveAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    protected void createAlertDialog(int number) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SaveActivity.this);
        builder.setTitle("Delete");
        builder.setMessage("Are you sure you want to delete " + number + (number > 1 ? " items?" : " item?"));
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0; i < words.size(); i++) {
                    if (words.get(i).isChecked()) {
                        saveWord.deleteWord(words.get(i).getWord());
                    }
                }
                saveAdapter.refresh(saveWord.LoadFromSavedDB());
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

}
