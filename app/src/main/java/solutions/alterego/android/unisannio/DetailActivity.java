package solutions.alterego.android.unisannio;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import java.util.Locale;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.parceler.Parcels;
import solutions.alterego.android.unisannio.models.Article;

public class DetailActivity extends AppCompatActivity {

    private DateTimeFormatter localDateFormatter = DateTimeFormat.fullDate().withLocale(Locale.getDefault());

    TextView mDate;
    TextView mTitle;
    TextView mBody;
    TextView mAuthor;
    Toolbar mToolbar;

    private Article mArticle;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mTitle = (TextView) findViewById(R.id.detail_title);

        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_arrow_left));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                DetailActivity.this.onBackPressed();
            }
        });

        mArticle = Parcels.unwrap(getIntent().getParcelableExtra("ARTICLE"));

        //TODO Sometimes this part of code going to NullPointerException on Android 4.3.1, isolate problem if possible
        if(mArticle != null){
            if((mArticle.getTitle() != null)){
                mTitle.setText(mArticle.getTitle());
            }
            else
            {
                Log.e("Detail On Create","Null Article Title");
            }
        }
        else {
            Log.e("Detail On Create", "Null Article parsed");
        }

        mDate = (TextView) findViewById(R.id.detail_date);
        mDate.setText(localDateFormatter.print(mArticle.getDate()));

        mAuthor = (TextView) findViewById(R.id.detail_author);
        mAuthor.setText(mArticle.getAuthor());

        mBody = (TextView) findViewById(R.id.detail_body);
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
