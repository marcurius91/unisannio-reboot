package solutions.alterego.android.unisannio.ingegneria;

import org.jsoup.nodes.Document;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import solutions.alterego.android.unisannio.interfaces.IRetriever;

public class IngegneriaCercapersoneRetriever implements IRetriever<Document> {

    @Inject
    public IngegneriaCercapersoneRetriever() {
    }

    @Override
    public List<Document> retrieve(@NonNull String url) {
        return null;
    }
}
