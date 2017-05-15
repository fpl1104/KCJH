package kechuang.mr.com.kcjh.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by keith_fan on 2016/11/15.
 */

public class ColorPicture implements Parcelable {
    public String name;
    public String pic1;
    public String pic2;
    public String pic13x;
    public String pic23x;


    public ColorPicture(String info) {
        try {
            JSONObject jsonObject = new JSONObject(info);
            name = jsonObject.optString("color");
            pic1 = jsonObject.optString("picture_urlone");
            pic2 = jsonObject.optString("picture_tagone");
            pic13x=jsonObject.optString("picture_urltwo");
            pic23x=jsonObject.optString("picture_tagtwo");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ColorPicture(Parcel in) {
        name = in.readString();
        pic1 = in.readString();
        pic2 = in.readString();
        pic13x=in.readString();
        pic23x=in.readString();
    }

    public ColorPicture() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(pic1);
        parcel.writeString(pic2);
        parcel.writeString(pic13x);
        parcel.writeString(pic23x);
    }
    public static final Creator<ColorPicture> CREATOR = new Creator<ColorPicture>() {
        @Override
        public ColorPicture createFromParcel(Parcel in) {
            return new ColorPicture(in);
        }

        @Override
        public ColorPicture[] newArray(int size) {
            return new ColorPicture[size];
        }
    };
}





