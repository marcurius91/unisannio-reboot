package solutions.alterego.android.unisannio.ingegneria;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(prefix = "m")
public class IngegneriaDidatticaItem {

    private String mTitle;

    private String mUrl;

    private String mAuthor;

    private String mBody;

    private final String mDate;

    public IngegneriaDidatticaItem(String title, String url, String author, String body, String date) {
        mTitle = title;
        mUrl = url;
        mAuthor = author;
        mBody = body;
        mDate = date;
    }
}
