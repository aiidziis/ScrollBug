package aigarscibulskis.scrollbug;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by aigars.cibulskis on 4/21/2016.
 */
public class League implements Parcelable{

    private int id;
    private String name;
    private String iconUrl;


    protected League(Parcel in) {
        id = in.readInt();
        name = in.readString();
        iconUrl = in.readString();
    }

    public static final Creator<League> CREATOR = new Creator<League>() {
        @Override
        public League createFromParcel(Parcel in) {
            return new League(in);
        }

        @Override
        public League[] newArray(int size) {
            return new League[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(iconUrl);
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setIconUrl(String iconUrl){
        this.iconUrl = iconUrl;
    }

    public String getIconUrl(){
        return this.iconUrl;
    }
}
