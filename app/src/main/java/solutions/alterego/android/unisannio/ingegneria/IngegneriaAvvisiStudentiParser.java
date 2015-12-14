package solutions.alterego.android.unisannio.ingegneria;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import solutions.alterego.android.unisannio.interfaces.IParser;
import solutions.alterego.android.unisannio.models.Article;
import solutions.alterego.android.unisannio.utils.DateUtils;

public class IngegneriaAvvisiStudentiParser implements IParser {

    public static final String SELECT_HEADER = "#header > h2 > a";

    public static final String URL = "href";

    public static final String SELECT_PARAGRAFS = "p";

    public static final String EMPTY_AUTHOR_PLACEHOLDER = "Presidio didattico";

    public static final String SELECT_PUBLISH_INFO = "#publishinfo";

    public static final String SELECT_BODY = ".avvtext";

    public static final String AUTHOR = "Autore";

    public static String SELECT_ELEMENTS = "#maincontent-block > #item";

    @Override
    public List parse(Document document) {

        Elements elements = getElements(document);

        List<Article> list = new ArrayList<>();

        for (Element element : elements) {
            Element header = element.select(SELECT_HEADER).first();
            String title = header.text();
            String url = header.attr(URL);

            Elements paragraphs = element.select(SELECT_PARAGRAFS);
            String author = "";
            for (Element paragraph : paragraphs) {
                if (paragraph.text().contains(AUTHOR)) {
                    author = paragraph.text().replace("Autore: ", "");
                }
            }
            if ("".equals(author)) {
                author = EMPTY_AUTHOR_PLACEHOLDER;
            }

            String body = element.select(SELECT_BODY).first().text();

            String date = DateUtils.extractingData(element.select(SELECT_PUBLISH_INFO).first().text());

            DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
            DateTime jodatime = dtf.parseDateTime(date);

            list.add(new Article(title, url, body, jodatime, author));
        }
        return list;
    }

    public Elements getElements(Document document) {
        return document.select(SELECT_ELEMENTS);
    }
}
