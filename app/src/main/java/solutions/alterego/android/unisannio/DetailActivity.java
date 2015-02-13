package solutions.alterego.android.unisannio;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import solutions.alterego.android.unisannio.ingegneria.IngegneriaDidatticaItem;


public class DetailActivity extends ActionBarActivity {

    @InjectView(R.id.detail_date)
    TextView mDate;

    @InjectView(R.id.detail_title)
    TextView mTitle;

    @InjectView(R.id.detail_body)
    TextView mBody;

    @InjectView(R.id.detail_author)
    TextView mAuthor;

    @InjectView(R.id.toolbar_actionbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.inject(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        IngegneriaDidatticaItem article = getIntent().getParcelableExtra("ARTICLE");
        mTitle.setText(article.getTitle());
        mDate.setText(article.getDate());
        mAuthor.setText(article.getAuthor());
        mBody.setText(article.getBody());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
