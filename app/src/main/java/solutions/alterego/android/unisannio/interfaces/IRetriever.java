package solutions.alterego.android.unisannio.interfaces;

import android.support.annotation.NonNull;

import org.jsoup.nodes.Document;

import java.util.List;

public interface IRetriever {

    Document retrieve(@NonNull String url);
}
