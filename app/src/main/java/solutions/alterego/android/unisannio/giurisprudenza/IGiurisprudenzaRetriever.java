package solutions.alterego.android.unisannio.giurisprudenza;

import org.jsoup.nodes.Document;

import rx.Observable;

public interface IGiurisprudenzaRetriever {
    Observable<Document> get();
}
