package solutions.alterego.android.unisannio.dbManager;

import java.util.List;

import solutions.alterego.android.unisannio.models.Article;

public interface IDbManager {

    boolean addArticle(Article article);
    boolean addArticles(List<Article> articleList);
    Article getArticle(int id);
    List<Article> searchArticleByDept();
    List<Article> searchArticleByDeptAndDate();
    int updateArticle(Article article);
    int deleteArticle(Article article);

}
