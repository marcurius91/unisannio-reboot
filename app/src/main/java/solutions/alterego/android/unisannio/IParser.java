package solutions.alterego.android.unisannio;

import org.jsoup.nodes.Document;

import java.util.List;

public abstract interface IParser<T> {

    public abstract List<T> parse(Document document);
}
