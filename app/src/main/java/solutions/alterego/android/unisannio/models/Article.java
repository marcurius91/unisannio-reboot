package solutions.alterego.android.unisannio.models;

import org.joda.time.DateTime;
import org.parceler.Parcel;

@Parcel
public class Article {

    String mTitle;

    String mUrl;

    String mBody;

    DateTime mDate;

    String mAuthor;

    public Article() {
    }

    public Article(String title, String url, String body, DateTime date, String author) {
        this.mTitle = title;
        this.mUrl = url;
        this.mBody = body;
        this.mDate = date;
        this.mAuthor = author;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public String getUrl() {
        return this.mUrl;
    }

    public String getBody() {
        return this.mBody;
    }

    public DateTime getDate() {
        return this.mDate;
    }

    public String getAuthor() {
        return this.mAuthor;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public void setBody(String mBody) {
        this.mBody = mBody;
    }

    public void setDate(DateTime mDate) {
        this.mDate = mDate;
    }

    public void setAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Article)) return false;
        final Article other = (Article) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$mTitle = this.getTitle();
        final Object other$mTitle = other.getTitle();
        if (this$mTitle == null ? other$mTitle != null : !this$mTitle.equals(other$mTitle)) return false;
        final Object this$mUrl = this.getUrl();
        final Object other$mUrl = other.getUrl();
        if (this$mUrl == null ? other$mUrl != null : !this$mUrl.equals(other$mUrl)) return false;
        final Object this$mBody = this.getBody();
        final Object other$mBody = other.getBody();
        if (this$mBody == null ? other$mBody != null : !this$mBody.equals(other$mBody)) return false;
        final Object this$mDate = this.getDate();
        final Object other$mDate = other.getDate();
        if (this$mDate == null ? other$mDate != null : !this$mDate.equals(other$mDate)) return false;
        final Object this$mAuthor = this.getAuthor();
        final Object other$mAuthor = other.getAuthor();
        if (this$mAuthor == null ? other$mAuthor != null : !this$mAuthor.equals(other$mAuthor)) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $mTitle = this.getTitle();
        result = result * PRIME + ($mTitle == null ? 43 : $mTitle.hashCode());
        final Object $mUrl = this.getUrl();
        result = result * PRIME + ($mUrl == null ? 43 : $mUrl.hashCode());
        final Object $mBody = this.getBody();
        result = result * PRIME + ($mBody == null ? 43 : $mBody.hashCode());
        final Object $mDate = this.getDate();
        result = result * PRIME + ($mDate == null ? 43 : $mDate.hashCode());
        final Object $mAuthor = this.getAuthor();
        result = result * PRIME + ($mAuthor == null ? 43 : $mAuthor.hashCode());
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof Article;
    }

    public String toString() {
        return "solutions.alterego.android.unisannio.models.Article(mTitle="
            + this.getTitle()
            + ", mUrl="
            + this.getUrl()
            + ", mBody="
            + this.getBody()
            + ", mDate="
            + this.getDate()
            + ", mAuthor="
            + this.getAuthor()
            + ")";
    }
}
