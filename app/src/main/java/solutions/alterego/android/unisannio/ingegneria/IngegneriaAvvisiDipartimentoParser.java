package solutions.alterego.android.unisannio.ingegneria;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import solutions.alterego.android.unisannio.URLS;
import solutions.alterego.android.unisannio.interfaces.IParser;
import solutions.alterego.android.unisannio.models.Article;
import solutions.alterego.android.unisannio.utils.DateUtils;
import solutions.alterego.android.unisannio.utils.ExtensionKt;

public class IngegneriaAvvisiDipartimentoParser implements IParser {

    @Override public List parse(Document document) {
        List<Article> list = new ArrayList<>();

        Element body_element = document.body();
        Elements elements = body_element.select("*");

         /*for (Element element : elements) {
            String title = element.select(".leading-0 > h2").first().text();

            String body = element.select(".leading-0 > p").first().text();

            String date = element.select(".published").first().text().replace("Pubblicato ", "");
            DateTimeFormatter dtf = DateTimeFormat.forPattern("EEEE, dd MMMM yyyy");
            DateTime jodatime = dtf.withLocale(Locale.ITALIAN).parseDateTime(date.toLowerCase());

            list.add(new Article(title, URLS.INGEGNERIA_NEWS_DIPARTIMENTO, body, jodatime, ""));
        }
        return list;
    }*/

        //XML OF THE PAGE SUCK.
        //Tag between two news is <div class="leading-XXX" with XXX incremental integer.
        //I use the header tag to count the news and use this index for select query

        Elements index_element = elements.select("div.page-header").select("h2");
        int index_news = index_element.size();

        for (int j = 0; j < index_news; j++) {
            String search_query = ".leading-".concat(String.valueOf(j));
            Element news = elements.select(search_query).first();

            String title = news.select("div.page-header").select("h2").text();
            String body = news.select("p").text();
            String date = DateUtils.convertMonth(news.select("dd.published").select("time").text());

            DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
            DateTime jodatime = dtf.parseDateTime(date);

            list.add(new Article(UUID.randomUUID().toString(), title, "", URLS.INGEGNERIA_NEWS_DIPARTIMENTO, body, ExtensionKt.toIso8601(jodatime)));
        }

        return list;
    }
}
