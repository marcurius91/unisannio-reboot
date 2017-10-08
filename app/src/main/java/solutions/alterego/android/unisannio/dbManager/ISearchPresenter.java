package solutions.alterego.android.unisannio.dbManager;

import java.util.List;
import solutions.alterego.android.unisannio.models.Article;

 interface ISearchPresenter {

    public List<Article> search();
    public List<Article> searchByDept(String _dept);
    public List<Article> searchByDateAndDept(String _date, String _dept);
}
