package solutions.alterego.android.unisannio.ingegneria;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import solutions.alterego.android.unisannio.URLS;
import solutions.alterego.android.unisannio.interfaces.IRetriever;

public class IngegneriaCercapersoneRetriever implements IRetriever {

    @Inject
    public IngegneriaCercapersoneRetriever() {
    }

    @Override
    public Document retrieve(@NonNull String url) {

        Document doc = null;

        try {
            doc = Jsoup.connect(URLS.RSS_FEED_INGEGNERIA).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(doc != null){
            return doc;
        }

        else {
            Log.e("DOCUMENT RETRIVE", "Error in document retriving");
            return doc;
        }
    }
}
