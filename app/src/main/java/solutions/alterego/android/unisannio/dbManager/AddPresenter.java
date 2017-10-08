package solutions.alterego.android.unisannio.dbManager;

/*
public class AddPresenter implements IAddPresenter {
    private DbManager mDbManager;
    String department;

    public AddPresenter(Context _context,String _dept){
        mDbManager = new DbManager(_context);
        this.department = _dept;
    }

    @Override
    public boolean addArticle(Article Article) {
        return false;
    }

    @Override
    public boolean addArticles(List<Article> art) {
        boolean article_exist = false;

        ArrayList<Article> articles = (ArrayList<Article>) setUpDepartment(art);

        for(int i = 0; i<articles.size(); i++){
            article_exist = mDbManager.checkIfArticleExist(articles.get(i));

            if (article_exist){
                Log.e("ERROR ON ADD","Article in DB");
                article_exist = false;
            }
            else {
                mDbManager.addArticles(articles);

            }
        }

        return false;
    }

    @Override
    public List<Article> setUpDepartment(List<Article> articles) {

        for(int i = 0; i<articles.size(); i++) {
            //articles.get(i).setDepartment(department);
        }

        return articles;
    }
}
*/
