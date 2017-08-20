package solutions.alterego.android.unisannio.map;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.maps.model.LatLng;

public class UniPoint implements Parcelable {

    public static Creator<UniPoint> CREATOR = new Creator<UniPoint>() {
        public UniPoint createFromParcel(Parcel source) {
            return new UniPoint(source);
        }

        public UniPoint[] newArray(int size) {
            return new UniPoint[size];
        }
    };

    private String mFaculty;

    private String mName;

    private String mAddress;

    private double mLat;

    private double mLng;

    private LatLng mGeopoint;

    public UniPoint(String namefaculty, String name, String address, double lat, double lng) {
        mFaculty = namefaculty;
        mName = name;
        mAddress = address;
        mLat = lat;
        mLng = lng;
        mGeopoint = new LatLng(lat, lng);
    }

    private UniPoint(Parcel in) {
        this.mFaculty = in.readString();
        this.mName = in.readString();
        this.mAddress = in.readString();
        this.mLat = in.readDouble();
        this.mLng = in.readDouble();
        this.mGeopoint = in.readParcelable(LatLng.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mFaculty);
        dest.writeString(this.mName);
        dest.writeString(this.mAddress);
        dest.writeDouble(this.mLat);
        dest.writeDouble(this.mLng);
        dest.writeParcelable(this.mGeopoint, flags);
    }

    public String getFaculty() {
        return this.mFaculty;
    }

    public String getName() {
        return this.mName;
    }

    public String getAddress() {
        return this.mAddress;
    }

    public double getLat() {
        return this.mLat;
    }

    public double getLng() {
        return this.mLng;
    }

    public LatLng getGeopoint() {
        return this.mGeopoint;
    }

    public void setFaculty(String mFaculty) {
        this.mFaculty = mFaculty;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public void setAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public void setLat(double mLat) {
        this.mLat = mLat;
    }

    public void setLng(double mLng) {
        this.mLng = mLng;
    }

    public void setGeopoint(LatLng mGeopoint) {
        this.mGeopoint = mGeopoint;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof UniPoint)) return false;
        final UniPoint other = (UniPoint) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$mFaculty = this.getFaculty();
        final Object other$mFaculty = other.getFaculty();
        if (this$mFaculty == null ? other$mFaculty != null : !this$mFaculty.equals(other$mFaculty)) return false;
        final Object this$mName = this.getName();
        final Object other$mName = other.getName();
        if (this$mName == null ? other$mName != null : !this$mName.equals(other$mName)) return false;
        final Object this$mAddress = this.getAddress();
        final Object other$mAddress = other.getAddress();
        if (this$mAddress == null ? other$mAddress != null : !this$mAddress.equals(other$mAddress)) return false;
        if (Double.compare(this.getLat(), other.getLat()) != 0) return false;
        if (Double.compare(this.getLng(), other.getLng()) != 0) return false;
        final Object this$mGeopoint = this.getGeopoint();
        final Object other$mGeopoint = other.getGeopoint();
        if (this$mGeopoint == null ? other$mGeopoint != null : !this$mGeopoint.equals(other$mGeopoint)) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $mFaculty = this.getFaculty();
        result = result * PRIME + ($mFaculty == null ? 43 : $mFaculty.hashCode());
        final Object $mName = this.getName();
        result = result * PRIME + ($mName == null ? 43 : $mName.hashCode());
        final Object $mAddress = this.getAddress();
        result = result * PRIME + ($mAddress == null ? 43 : $mAddress.hashCode());
        final long $mLat = Double.doubleToLongBits(this.getLat());
        result = result * PRIME + (int) ($mLat >>> 32 ^ $mLat);
        final long $mLng = Double.doubleToLongBits(this.getLng());
        result = result * PRIME + (int) ($mLng >>> 32 ^ $mLng);
        final Object $mGeopoint = this.getGeopoint();
        result = result * PRIME + ($mGeopoint == null ? 43 : $mGeopoint.hashCode());
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof UniPoint;
    }

    public String toString() {
        return "solutions.alterego.android.unisannio.map.UniPoint(mFaculty="
            + this.getFaculty()
            + ", mName="
            + this.getName()
            + ", mAddress="
            + this.getAddress()
            + ", mLat="
            + this.getLat()
            + ", mLng="
            + this.getLng()
            + ", mGeopoint="
            + this.getGeopoint()
            + ")";
    }
}
