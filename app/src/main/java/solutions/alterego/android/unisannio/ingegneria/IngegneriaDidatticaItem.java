package solutions.alterego.android.unisannio.ingegneria;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(prefix = "m")
public class IngegneriaDidatticaItem implements Parcelable {

    public static final Parcelable.Creator<IngegneriaDidatticaItem> CREATOR = new Parcelable.Creator<IngegneriaDidatticaItem>() {
        public IngegneriaDidatticaItem createFromParcel(Parcel source) {
            return new IngegneriaDidatticaItem(source);
        }

        public IngegneriaDidatticaItem[] newArray(int size) {
            return new IngegneriaDidatticaItem[size];
        }
    };

    private final String mDate;

    private String mTitle;

    private String mUrl;

    private String mAuthor;

    private String mBody;

    public IngegneriaDidatticaItem(String title, String url, String author, String body, String date) {
        mTitle = title;
        mUrl = url;
        mAuthor = author;
        mBody = body;
        mDate = date;
    }

    private IngegneriaDidatticaItem(Parcel in) {
        this.mTitle = in.readString();
        this.mUrl = in.readString();
        this.mAuthor = in.readString();
        this.mBody = in.readString();
        this.mDate = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mTitle);
        dest.writeString(this.mUrl);
        dest.writeString(this.mAuthor);
        dest.writeString(this.mBody);
        dest.writeString(this.mDate);
    }
}
