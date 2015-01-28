package solutions.alterego.android.unisannio.ateneo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(prefix = "m")
public class AteneoNews {

    private String mId;

    private String mBody;

    private String mDate;

    public AteneoNews(String date, String body, String id) {
        mDate = date;
        mBody = body;
        mId = id;
    }
}
