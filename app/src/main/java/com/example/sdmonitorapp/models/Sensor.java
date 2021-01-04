package com.example.sdmonitorapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Sensor implements Parcelable {
    private String _id;
    private String uuid;
    private String owner;
    private String measure;
    private Double latitude;
    private Double longitude;
    private String topic;
    private int __v;

    public Sensor( String _id, String uuid, String owner, String measure, Double latitude, Double longitude, String topic, int __v) {
        this._id = _id;
        this.uuid = uuid;
        this.owner = owner;
        this.measure = measure;
        this.latitude = latitude;
        this.longitude = longitude;
        this.topic = topic;
        this.__v = __v;
    }

    protected Sensor(Parcel in) {
        _id = in.readString();
        uuid = in.readString();
        owner = in.readString();
        measure = in.readString();
        if (in.readByte() == 0) {
            latitude = null;
        } else {
            latitude = in.readDouble();
        }
        if (in.readByte() == 0) {
            longitude = null;
        } else {
            longitude = in.readDouble();
        }
        topic = in.readString();
        __v = in.readInt();
    }

    public static final Creator<Sensor> CREATOR = new Creator<Sensor>() {
        @Override
        public Sensor createFromParcel(Parcel in) {
            return new Sensor(in);
        }

        @Override
        public Sensor[] newArray(int size) {
            return new Sensor[size];
        }
    };

    @Override
    public String toString() {
        return "Sensor{" +
                "uuid='" + uuid + '\'' +
                '}';
    }

    public int get__v() {
        return __v;
    }

    public String get_id() {
        return _id;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(uuid);
        dest.writeString(owner);
        dest.writeString(measure);
        if (latitude == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(latitude);
        }
        if (longitude == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(longitude);
        }
        dest.writeString(topic);
        dest.writeInt(__v);
    }
}
