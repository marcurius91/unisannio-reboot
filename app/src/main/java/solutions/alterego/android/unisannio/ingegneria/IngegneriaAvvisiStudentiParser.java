package solutions.alterego.android.unisannio.ingegneria;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import solutions.alterego.android.unisannio.interfaces.Parser;
import solutions.alterego.android.unisannio.models.Article;
import solutions.alterego.android.unisannio.utils.ExtensionKt;

public class IngegneriaAvvisiStudentiParser implements Parser<Article> {

    public static final String EMPTY_AUTHOR_PLACEHOLDER = "Presidio didattico";

    public static String SELECT_ELEMENTS = "#maincontent-block > #item";

    @Override
    public List<Article> parse(Document document) {


        /*Elements elements = getElements(document);

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
        return list;*/

        Elements elements = document.select("div.item");

        List<Article> list = new ArrayList<>();

        for (int i = 0; i<elements.size(); i++){

            //get the title of the news
            Elements title_element = elements.get(i).select("h2").select("a").select("span.avvtitle");
            String title = title_element.text();

            //get the url of the news
            Element url_element = elements.get(i).select("h2").select("a").first();
            String url = url_element.attr("href");

            //get the body of the news
            Element body_element = elements.get(i).select("div.avvtext").first();
            String body = body_element.text();

            //Extract date from publish field of the news
            Element publish_element = elements.get(i).select("div").select("p.publishinfo").first();
            String publish = publish_element.text();
            StringTokenizer st = new StringTokenizer(publish," ");
            String st1 = st.nextToken();
            String st2 = st.nextToken();
            String date = st.nextToken();

            DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
            DateTime jodatime = dtf.parseDateTime(date);

            //get the author of the news
            Element authors_elements = elements.get(i).select("div").select("p").last();
            String author = null;

            if(authors_elements.text().contains("Autore")){
                author = authors_elements.text().substring(7);
            }

            else author = EMPTY_AUTHOR_PLACEHOLDER;

            list.add(new Article(UUID.randomUUID().toString(), title, author, url, body, ExtensionKt.toIso8601(jodatime)));
        }

        return list;
    }

    public Elements getElements(Document document) {
        return document.select(SELECT_ELEMENTS);
    }
}
