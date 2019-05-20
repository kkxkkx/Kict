package bit.edu.cn.dictionary.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static bit.edu.cn.dictionary.SearchActivity.StoragePath;


/**
 * @author Wang Kexin
 * @date 2019.03.17  21:04
 * @function  将输入流写入文件
 */
public class ResourceUtils {
    public static String FilePath;
    public static final String TAG="PATH";

    public ResourceUtils()
    {
        //获得外部存储的根目录
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            FilePath= Environment.getExternalStorageDirectory()+StoragePath;
        }

        Log.v(TAG,Environment.getExternalStorageDirectory()+"/");
    }

    //创建对应的文件
    public File CreatFile(String path, String FileName) throws IOException {
        CreatDirectory(path);
        File file=new File(path+FileName+".mp3");
        if(file.exists()&&file.isFile())  //文件存在时返回空
        {
            return file;
        }
        file.createNewFile();  //创建文件
        return file;
    }

    //创建一个目录
    public File CreatDirectory(String DirectoryName)
    {
        File dir=new File(DirectoryName);
        if(dir.exists()&&dir.isDirectory())  //存在而且是一个文件夹
        {
            return dir;
        }
        dir.mkdirs();  //可创建多级
        return dir;
    }

    //获得文件输入流
    public InputStream getInputStreamFromFile(String path,String FileName)
    {
        InputStream inputStream=null;
        File file=new File(FilePath+path+FileName);
        if(!file.exists())
        {
            return null;
        }
        try {
            inputStream=new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return inputStream;
    }

    public boolean SaveInputStream(InputStream inputStream,String FileName) throws IOException {
        File file=CreatFile(FilePath,FileName);
        byte[] bytes=new byte[1024];
        FileOutputStream fileOutputStream=new FileOutputStream(file);
        int length=0;
        Log.v(TAG,FileName);
        while ((length=inputStream.read(bytes))!=-1)
        {
            fileOutputStream.write(bytes,0,length);
        }
        fileOutputStream.close();
        inputStream.close();
        return true;
    }

    public String getExactPath()
    {
        Log.v(TAG,Environment.getExternalStorageDirectory().getAbsolutePath());
        return Environment.getExternalStorageDirectory().getAbsolutePath()+"/";
    }

    public String getFilePath()
    {
        return FilePath;
    }
    public void setFilePath(String filePath)
    {
        FilePath=filePath;
    }

}
