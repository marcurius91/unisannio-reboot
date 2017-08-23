package solutions.alterego.android.unisannio.dbManager;

import java.util.List;

import solutions.alterego.android.unisannio.models.Article;

public interface IDbManager {

    boolean addArticle(Article article);
    boolean addArticles(List<Article> articleList);
    Article getArticle(int id);
    List<Article> searchArticleByDept(String dept);
    List<Article> searchArticleByDeptAndDate(String dept,String date);
    int updateArticle(Article article);
    void deleteArticle(Article article);

}
