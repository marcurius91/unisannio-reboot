package solutions.alterego.android.unisannio.ingegneria;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.matshofman.saxrssreader.Feed;
import nl.matshofman.saxrssreader.FeedItem;
import nl.matshofman.saxrssreader.FeedReader;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import solutions.alterego.android.unisannio.URLS;
import solutions.alterego.android.unisannio.interfaces.IRetriever;

public class IngegneriaCercapersoneRetriever implements IRetriever {

    @Inject
    public IngegneriaCercapersoneRetriever() {
    }

    /*@Override
    public Observable<Document> retrieve() {
        return Observable
                .create(new Observable.OnSubscribe<Document>() {
                    @Override
                    public void call(Subscriber<? super Document> subscriber) {

                        Document doc = null;
                        try {
                            doc = Jsoup.connect(URLS.RSS_FEED_INGEGNERIA).get();
                        } catch (IOException e) {
                            subscriber.onError(e);
                        }

                        if(doc != null){
                        subscriber.onNext(doc);
                        subscriber.onCompleted();
                        }
                    }
                }).subscribeOn(Schedulers.io());

    }*/

    @Override
    public Observable<List<FeedItem>> retrieve() {

        return Observable
                .create(new Observable.OnSubscribe<List<FeedItem>>() {
                    @Override
                    public void call(Subscriber<? super List<FeedItem>> subscriber) {
                        Feed feed = null;
                        URL url = null;
                        try {
                            url = new URL("http://www.ding.unisannio.it/html/rss/rssIVAN.php?key=kjsdh2cijnhGVIUBiGd598oyagdo");
                        } catch (MalformedURLException e) {
                            subscriber.onError(e);
                        }
                        try {
                            feed = FeedReader.read(false,url);
                        } catch (SAXException e) {
                            subscriber.onError(e);
                        } catch (IOException e) {
                            subscriber.onError(e);
                        }
                        List<FeedItem> feedItems = feed.getItems();
                        for(FeedItem feedItem : feedItems) {
                            Log.i("Atom & RSS Reader", feedItem.getTitle());
                        }

                        subscriber.onNext(feedItems);
                        subscriber.onCompleted();
                    }

                }).subscribeOn(Schedulers.io());

        }
    }


