package solutions.alterego.android.unisannio.dbManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import solutions.alterego.android.unisannio.models.Article;

public class DbManager extends SQLiteOpenHelper implements IDbManager{

    public static final String DATABASE_NAME = "Unisannio_reboot";
    public static final String TABLE_ARTICLE = "Article";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ARTICLE_TITLE = "Title";
    public static final String COLUMN_ARTICLE_URL = "Url";
    public static final String COLUMN_ARTICLE_BODY = "Body";
    public static final String COLUMN_ARTICLE_DATE = "Date";
    public static final String COLUMN_ARTICLE_AUTHOR = "Author";
    public static final String COLUMN_ARTICLE_DEPARTMENT = "Department";
    public static final int DATABASE_VERSION = 1;
    String message_returned = null;

    public DbManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Database creation sql statement
        final String DATABASE_CREATE = "CREATE TABLE "
                + TABLE_ARTICLE + "(" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_ARTICLE_TITLE + " TEXT,"
                + COLUMN_ARTICLE_URL + " TEXT,"
                + COLUMN_ARTICLE_BODY + " TEXT,"
                + COLUMN_ARTICLE_DATE + " TEXT,"
                + COLUMN_ARTICLE_AUTHOR + " TEXT,"
                + COLUMN_ARTICLE_DEPARTMENT + " TEXT" + ")";

        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.w(DbManager.class.getName(), "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLE);
        onCreate(db);
    }

    //Method that add a single article to the DB
    @Override
    public boolean addArticle(Article article) {

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DbManager.COLUMN_ARTICLE_TITLE,article.getTitle());
        values.put(DbManager.COLUMN_ARTICLE_URL,article.getUrl());
        values.put(DbManager.COLUMN_ARTICLE_BODY,article.getBody());
        DateTime dt = article.getDate();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy/MM/dd");
        String dtStr = fmt.print(dt);
        Log.e("DATA INIZIALE",dtStr);
        values.put(DbManager.COLUMN_ARTICLE_DATE, dtStr);
        values.put(DbManager.COLUMN_ARTICLE_AUTHOR, article.getAuthor());
        values.put(DbManager.COLUMN_ARTICLE_DEPARTMENT, article.getDepartment());

        database.insert(TABLE_ARTICLE, null, values);

        database.close();
        return true;
    }

    //Method for adding more than one article from a list
    @Override
    public boolean addArticles(List<Article> articleList) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        for(int i = 0; i<articleList.size(); i++){
            Article article = articleList.get(i);

            values.put(DbManager.COLUMN_ARTICLE_TITLE,article.getTitle());
            values.put(DbManager.COLUMN_ARTICLE_URL,article.getUrl());
            values.put(DbManager.COLUMN_ARTICLE_BODY,article.getBody());
            DateTime dt = article.getDate();
            DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy/MM/dd");
            String dtStr = fmt.print(dt);
            Log.e("DATA INIZIALE",dtStr);
            values.put(DbManager.COLUMN_ARTICLE_DATE, String.valueOf(dtStr));
            values.put(DbManager.COLUMN_ARTICLE_AUTHOR, article.getAuthor());
            values.put(DbManager.COLUMN_ARTICLE_DEPARTMENT, article.getDepartment());
            database.insert(TABLE_ARTICLE, null, values);
        }

        database.close();
        return true;
    }

    // Getting single Article by id
    @Override
    public Article getArticle(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Article article = null;

        Cursor cursor = db.query(TABLE_ARTICLE, new String[]{COLUMN_ID, COLUMN_ARTICLE_TITLE,
                        COLUMN_ARTICLE_URL, COLUMN_ARTICLE_BODY, COLUMN_ARTICLE_DATE, COLUMN_ARTICLE_AUTHOR, COLUMN_ARTICLE_DEPARTMENT}, COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();

            DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy/MM/dd");
            DateTime jodatime = dtf.parseDateTime(cursor.getString(4));
            article = new Article(cursor.getString(1),cursor.getString(2),cursor.getString(3),jodatime,cursor.getString(5),cursor.getString(6));

        }

        cursor.close();
        return article;
    }

    //Method for searching Article by department
    @Override
    public List<Article> searchArticleByDept(String department) {
        SQLiteDatabase db = this.getReadableDatabase();
        
        List<Article> articles = new ArrayList<Article>();
        String query =(" SELECT _rowid_, * FROM Article WHERE Department = ? ");
        Cursor c = db.rawQuery(query,new String[] {department});
        if(c.moveToFirst()){
            do{
                //assing values
                String title = c.getString(2);
                String url = c.getString(3);
                String body = c.getString(4);
                DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy/MM/dd");
                DateTime jodatime = dtf.parseDateTime(c.getString(5));
                String author = c.getString(6);
                String dept = c.getString(7);

                Article article = new Article(title,url,body,jodatime,author,dept);
                boolean isAdded = articles.add(article);

            }while(c.moveToNext());
        }

        c.close();
        db.close();

        return articles;
    }

    //Method for searching an article by Department and Date
    @Override
    public List<Article> searchArticleByDeptAndDate(String department, String date) {

        Log.e("SEARCH DEPT AND DATE",department + " " + date);
        SQLiteDatabase db = this.getReadableDatabase();

        List<Article> articles = new ArrayList<Article> ();

        String query =(" SELECT _rowid_, * FROM Article WHERE Date = ? and Department =? ");
        Cursor c = db.rawQuery(query, new String[] {date,department});

        if(c.moveToFirst()){
            do{
                //assing values
                String title = c.getString(2);
                String url = c.getString(3);
                String body = c.getString(4);
                DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy/MM/dd");
                DateTime jodatime = dtf.parseDateTime(c.getString(5));
                String author = c.getString(6);
                String dept = c.getString(7);

                Article article = new Article(title,url,body,jodatime,author,dept);
                articles.add(article);

            }while(c.moveToNext());
        }
        c.close();
        db.close();

        return articles;
    }

    //Method for update article
    @Override
    public int updateArticle(Article article) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbManager.COLUMN_ARTICLE_TITLE,article.getTitle());
        values.put(DbManager.COLUMN_ARTICLE_URL,article.getUrl());
        values.put(DbManager.COLUMN_ARTICLE_BODY,article.getBody());
        DateTime dt = article.getDate();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        String dtStr = fmt.print(dt);
        values.put(DbManager.COLUMN_ARTICLE_DATE, dtStr);
        values.put(DbManager.COLUMN_ARTICLE_AUTHOR, article.getAuthor());
        values.put(DbManager.COLUMN_ARTICLE_DEPARTMENT, article.getDepartment());

        // updating row
        return db.update(TABLE_ARTICLE, values, COLUMN_ID + " = ?",
                new String[] { String.valueOf(article.getId())});
    }

    @Override
    public void deleteArticle(Article article) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ARTICLE, COLUMN_ARTICLE_TITLE + "=? and " + COLUMN_ARTICLE_URL + "=?" , new String[]{article.getTitle(),article.getUrl()});
        db.close();
    }

    //Method for check if article existing in db.
    @Override
    public boolean checkIfArticleExist(Article article){
        SQLiteDatabase db = this.getReadableDatabase();

        boolean exist = false;

        String query =(" SELECT _rowid_, * FROM Article WHERE Title = ? and Url =? and Body =? and Author =? and Department =? ");
        Cursor c = db.rawQuery(query, new String[] {article.getTitle(),article.getUrl(),article.getBody(),article.getAuthor(),article.getDepartment()});


        if(c.moveToFirst()){
            do{
                //assing values
                String title = c.getString(2);
                String url = c.getString(3);
                String body = c.getString(4);
                DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy/MM/dd");
                DateTime jodatime = dtf.parseDateTime(c.getString(5));
                String author = c.getString(6);
                String dept = c.getString(7);

                if(title != null && url != null && body != null && author != null && dept != null){
                    exist = true;
                }
            }while(c.moveToNext());
        }
        c.close();
        db.close();

        return exist;
    }


}
