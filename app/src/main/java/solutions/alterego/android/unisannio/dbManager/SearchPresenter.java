package solutions.alterego.android.unisannio.dbManager;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import solutions.alterego.android.unisannio.models.Article;
import timber.log.Timber;

public class SearchPresenter implements ISearchPresenter {

    Context context;
    String department,date;
    ArrayList<Article> articles;
//    DbManager mDbManager;

    public SearchPresenter(Context _context,String _department,String _date){

 //       mDbManager = new DbManager(_context);
        this.context = _context;
        this.department = _department;
        this.date = _date;
    }

    //Global method that select type of search.
    @Override
    public List<Article> search() {
        if(department != null && date != null){
            articles = (ArrayList<Article>) searchByDateAndDept(date,department);
            Log.e("SEARCHED DATE DEPT",String.valueOf(articles.size()));
        }
        else {
            articles = (ArrayList<Article>) searchByDept(department);
            Log.e("SEARCHED DEPT",String.valueOf(articles.size()));
        }

        return articles;
    }

    //method for searching articles by department
    @Override
    public List<Article> searchByDept(String _dept) {
        //ArrayList<Article> searchedDeptArticles = (ArrayList<Article>) mDbManager.searchArticleByDept("Ingegneria_Studenti");
        //return searchedDeptArticles;

        Timber.e("Fake implementation");
        return new ArrayList<>();
    }

    //method for searching articles by department and date
    //N.B. format for date string is yyyy/mm/dd (ex. 2017/07/28)
    @Override
    public List<Article> searchByDateAndDept(String _date, String _dept) {
        //ArrayList<Article> searchedDateDeptArticles = (ArrayList<Article>) mDbManager.searchArticleByDeptAndDate("Ingegneria_Studenti","2017/07/28");
        //return searchedDateDeptArticles;

        Timber.e("Fake implementation");
        return new ArrayList<>();
    }
}
