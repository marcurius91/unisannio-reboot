package solutions.alterego.android.unisannio.ingegneria;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import solutions.alterego.android.unisannio.URLS;
import solutions.alterego.android.unisannio.interfaces.IParser;
import solutions.alterego.android.unisannio.models.Article;

public class IngegneriaAvvisiDipartimentoParser implements IParser {

    @Override
    public List parse(Document document) {
        Elements elements = document.select(".items-leading");

        List<Article> list = new ArrayList<>();

        for (Element element : elements) {
            String title = element.select(".leading-0 > h2").first().text();

            String body = element.select(".leading-0 > p").first().text();

            String date = element.select(".published").first().text().replace("Pubblicato ", "");
            DateTimeFormatter dtf = DateTimeFormat.forPattern("EEEE, dd MMMM yyyy");
            DateTime jodatime = dtf.withLocale(Locale.ITALIAN).parseDateTime(date.toLowerCase());

            list.add(new Article(title, URLS.INGEGNERIA_NEWS_DIPARTIMENTO, body, jodatime, ""));
        }
        return list;
    }
}
