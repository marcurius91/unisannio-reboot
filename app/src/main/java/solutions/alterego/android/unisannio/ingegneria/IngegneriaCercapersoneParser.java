package solutions.alterego.android.unisannio.ingegneria;

import org.jsoup.nodes.Document;

import java.util.List;

import javax.inject.Inject;

import solutions.alterego.android.unisannio.interfaces.IParser;

public class IngegneriaCercapersoneParser implements IParser<Document> {

    @Inject
    public IngegneriaCercapersoneParser() {
    }

    @Override
    public List<Document> parse(Document document) {
        return null;
    }
}
