package solutions.alterego.android.unisannio.ingegneria;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import solutions.alterego.android.unisannio.URLS;

public class IngegneriaAvvisiDipartimentoParser implements IIngegneriaParser {

    @Override
    public List parse(Elements elements) throws ParseException {

        List<IngegneriaDidatticaItem> list = new ArrayList<>();

        for (Element element : elements) {
            String title = element.select(".leading-0 > h2").first().text();

            String body = element.select(".leading-0 > p").first().text();

            String date = element.select(".published").first().text().replace("Pubblicato ", "");

            list.add(new IngegneriaDidatticaItem(title, URLS.INGEGNERIA_NEWS_DIPARTIMENTO, "", body, date));
        }
        return list;
    }
}
