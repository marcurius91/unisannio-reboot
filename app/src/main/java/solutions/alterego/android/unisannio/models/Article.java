package solutions.alterego.android.unisannio.models;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;
import lombok.experimental.Builder;

@Data
@Builder
public class Article implements Parcelable {

    private String title;

    private String url;

    private String body;

    private String date;

    private String author;

    public Article(String title, String link, String body, String date, String author) {
        this.title = title;
        url = link;
        this.body = body;
        this.date = date;
        this.author = author;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.url);
        dest.writeString(this.body);
        dest.writeString(this.date);
        dest.writeString(this.author);
    }

    private Article(Parcel in) {
        this.title = in.readString();
        this.url = in.readString();
        this.body = in.readString();
        this.date = in.readString();
        this.author = in.readString();
    }

    public static final Parcelable.Creator<Article> CREATOR = new Parcelable.Creator<Article>() {
        public Article createFromParcel(Parcel source) {
            return new Article(source);
        }

        public Article[] newArray(int size) {
            return new Article[size];
        }
    };
}
