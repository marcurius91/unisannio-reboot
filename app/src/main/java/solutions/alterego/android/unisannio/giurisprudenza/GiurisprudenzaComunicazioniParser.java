package solutions.alterego.android.unisannio.giurisprudenza;

import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import solutions.alterego.android.unisannio.interfaces.Parser;
import solutions.alterego.android.unisannio.models.Article;

public class GiurisprudenzaComunicazioniParser implements Parser{

    @NotNull
    @Override
    public List parse(@NotNull Document document) {
        List<Article> list = new ArrayList<>();

        Elements elements = document.select("item");


        for (Element element: elements){
            String title = element.select("title").text();
            Log.e("Title",title);
            String body = element.select("description").text();
            Log.e("Body",body);
            String author = element.select("author").text();
            Log.e("Author",author);

            //DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
            //DateTime jodatime = dtf.parseDateTime(DateUtils.convertMonth("01/Gennaio/2000"));

            list.add(new Article(UUID.randomUUID().toString(), title, author, null, body, null));
        }

        return list;
    }
}
