package solutions.alterego.android.unisannio.ingegneria;

import org.jsoup.select.Elements;

import java.text.ParseException;
import java.util.List;

public interface IIngegneriaParser {

    abstract <T> List<T> parse(Elements elements) throws ParseException;
}
