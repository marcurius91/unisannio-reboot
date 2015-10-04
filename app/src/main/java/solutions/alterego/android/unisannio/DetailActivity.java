package solutions.alterego.android.unisannio;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import solutions.alterego.android.unisannio.models.Article;


public class DetailActivity extends AppCompatActivity {

    @Bind(R.id.detail_date)
    TextView mDate;

    @Bind(R.id.detail_title)
    TextView mTitle;

    @Bind(R.id.detail_body)
    TextView mBody;

    @Bind(R.id.detail_author)
    TextView mAuthor;

    @Bind(R.id.toolbar_actionbar)
    Toolbar mToolbar;

    private Article mArticle;

    private int mCurrentApiVersion;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_arrow_left));
        mToolbar.setNavigationOnClickListener(v -> onBackPressed());

        mArticle = getIntent().getParcelableExtra("ARTICLE");
        mTitle.setText(mArticle.getTitle());
        mDate.setText(mArticle.getDate());
        mAuthor.setText(mArticle.getAuthor());
        mBody.setText(mArticle.getBody());
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

        if (id == R.id.action_share) {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = mArticle.getTitle() + " - " + mArticle.getUrl();
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
