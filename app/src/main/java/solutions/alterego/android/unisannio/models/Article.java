package solutions.alterego.android.unisannio.models;

import org.joda.time.DateTime;
import org.parceler.Parcel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Parcel
@Accessors(prefix = "m")
@AllArgsConstructor
public class Article {

    String mTitle;

    String mUrl;

    String mBody;

    DateTime mDate;

    String mAuthor;

    public Article() {
    }
}
