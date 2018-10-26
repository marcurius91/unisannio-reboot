package solutions.alterego.android.unisannio.dbManager;

import java.util.List;
import solutions.alterego.android.unisannio.models.Article;

 interface ISearchPresenter {

    List<Article> search();
    List<Article> searchByDept(String _dept);
    List<Article> searchByDateAndDept(String _date, String _dept);
}
