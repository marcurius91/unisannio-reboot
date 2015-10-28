package solutions.alterego.android.unisannio.interfaces;

import org.jsoup.nodes.Document;

import java.util.List;

import nl.matshofman.saxrssreader.Feed;
import nl.matshofman.saxrssreader.FeedItem;
import rx.Observable;

public interface IRetriever {

    Observable<List<FeedItem>> retrieve();
}
