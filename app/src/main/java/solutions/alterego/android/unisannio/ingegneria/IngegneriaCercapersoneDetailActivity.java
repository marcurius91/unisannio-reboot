package solutions.alterego.android.unisannio.ingegneria;


import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;
import solutions.alterego.android.unisannio.R;
import solutions.alterego.android.unisannio.cercapersone.Person;
import solutions.alterego.android.unisannio.models.Article;

public class IngegneriaCercapersoneDetailActivity extends AppCompatActivity {

    @Bind(R.id.detail_cercapersone_email)
    TextView mEmail;

    @Bind(R.id.detail_cercapersone_role)
    TextView mRole;

    @Bind(R.id.detail_cercapersone_office)
    TextView mOffice;

    @Bind(R.id.detail_cercapersone_webpage)
    TextView mWebpage;

    @Bind(R.id.detail_cercapersone_phone)
    TextView mPhone;

    @Bind(R.id.detail_cercapersone_name)
    TextView mName;

    @Bind(R.id.toolbar_actionbar)
    Toolbar mToolbar;

    public Person mPerson;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingegneria_cercapersone_detail);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_arrow_left));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IngegneriaCercapersoneDetailActivity.this.onBackPressed();
            }
        });

        Bundle data = getIntent().getExtras();
        mPerson = data.getParcelable("PERSON");

        mEmail.setText(mPerson.getEmail());
        mRole.setText(mPerson.getRuolo());
        mOffice.setText(mPerson.getUfficio());
        mWebpage.setText(mPerson.getWebPage());
        mPhone.setText(mPerson.getTelefono());
        mName.setText(mPerson.getNome());

        //Check if email String is empty and launch the relative Intent
        if (!mPerson.getEmail().isEmpty()) {
            mEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto", mPerson.getEmail(), null));
                    startActivity(Intent.createChooser(emailIntent, "Invio Email alla Persona"));
                }
            });
        }

        //Check if phone String is empty and launch the relative Intent
        if (!mPerson.getTelefono().isEmpty()) {
            mPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mPerson.getTelefono()));
                    //TODO Fix with a PermissionManager Class
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    startActivity(intent);
                }
            });
        }

        //Check if WebPage String is empty and launch the relative Intent
        if(!mPerson.getWebPage().isEmpty()) {
            mWebpage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mPerson.getWebPage()));
                    startActivity(intent);
                }
            });
        }
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
            String shareBody = mPerson.getNome() + " - " + mPerson.getTelefono() + " - " + mPerson.getWebPage() + " - " + mPerson.getUfficio();
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
