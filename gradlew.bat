package bit.edu.cn.directionary;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import bit.edu.cn.directionary.bean.AWord;
import bit.edu.cn.directionary.db.LocalWord;
import bit.edu.cn.directionary.db.WordSQLHelper;
import bit.edu.cn.directionary.utils.AudioUtils;
import bit.edu.cn.directionary.utils.NetworkUtils;

public class MainActivity extends AppCompatActivity {

    public  final static String StoragePath="/kkdir/pron/";
    public static final int REQUEST_CODE=1;
    private String TAG = "SEARCHWORD";
    private final int WRONG = 1;
    public static String searchword = null;
    public WordSQLHelper wordSQLHelper;
    public SQLiteDatabase db ;
    private RecyclerView recyclerView;
    private NoteListAdapter notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE);

        wordSQLHelper = new WordSQLHelper(this);
        db = wordSQLHelper.getWritableDatabase();
        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView2);



    }

    public void Play(View view) {
        Thread thread = new Thread(new Runnable() {
            public void run() {
           //     AudioPlay audioPlay=new AudioPlay();
        