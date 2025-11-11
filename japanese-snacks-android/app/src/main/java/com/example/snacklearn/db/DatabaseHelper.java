package com.example.snacklearn.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.snacklearn.config.AppConfig;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "snacks.db";
    private static String DB_PATH = "";
    private static final int DB_VERSION = 1;

    private SQLiteDatabase mDatabase;
    private final Context mContext;
    private boolean mNeedUpdate = false;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        AppConfig config = AppConfig.getInstance(context);
        DB_NAME = config.getDatabaseName();
        DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        this.mContext = context;

        copyDataBase();

        this.getReadableDatabase();
    }

    public void updateDataBase() throws IOException {
        if (mNeedUpdate) {
            File dbFile = new File(DB_PATH + DB_NAME);
            if (dbFile.exists())
                dbFile.delete();

            copyDataBase();

            mNeedUpdate = false;
        }
    }

    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    private void copyDataBase() {
        if (!checkDataBase()) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDBFile();
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    private void copyDBFile() throws IOException {
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        OutputStream mOutput = new FileOutputStream(DB_PATH + DB_NAME);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0)
            mOutput.write(mBuffer, 0, mLength);
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    public boolean openDataBase() throws SQLException {
        mDatabase = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return mDatabase != null;
    }

    @Override
    public synchronized void close() {
        if (mDatabase != null)
            mDatabase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            mNeedUpdate = true;
    }

    // 获取零食列表（分页）
    public List<Snack> getSnacks(int page) {
        List<Snack> snackList = new ArrayList<>();
        int pageSize = AppConfig.getInstance(mContext).getPageSize();
        int offset = (page - 1) * pageSize;

        String query = "SELECT * FROM snacks ORDER BY id LIMIT ? OFFSET ?";
        Cursor cursor = mDatabase.rawQuery(query, new String[]{String.valueOf(pageSize), String.valueOf(offset)});

        if (cursor.moveToFirst()) {
            do {
                Snack snack = new Snack();
                snack.setId(cursor.getInt(cursor.getColumnIndex("id")));
                snack.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                snack.setJapanese(cursor.getString(cursor.getColumnIndex("japanese")));
                snack.setEnglish(cursor.getString(cursor.getColumnIndex("english")));
                snack.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                snack.setImageName(cursor.getString(cursor.getColumnIndex("image_name")));

                snackList.add(snack);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return snackList;
    }

    // 获取总记录数
    public int getTotalCount() {
        String query = "SELECT COUNT(*) FROM snacks";
        Cursor cursor = mDatabase.rawQuery(query, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count;
    }
}