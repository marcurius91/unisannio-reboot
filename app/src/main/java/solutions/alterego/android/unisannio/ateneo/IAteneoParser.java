package solutions.alterego.android.unisannio.ateneo;

import org.jsoup.nodes.Document;

import java.util.List;

public abstract interface IAteneoParser {

    public abstract List<AteneoNews> parse(Document document);
}
