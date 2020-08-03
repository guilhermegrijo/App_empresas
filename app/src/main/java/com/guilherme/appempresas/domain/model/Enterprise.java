package com.guilherme.appempresas.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Enterprise implements Parcelable {

    public Integer id;
    public Boolean ownEnterprise;
    @SerializedName("enterprise_name")
    public String enterpriseName;
    @SerializedName("photo")
    public String photo;
    @SerializedName("description")
    public String description;
    @SerializedName("country")
    public String country;
    public Integer value;
    public Integer sharePrice;
    @SerializedName("enterprise_type")
    public EnterpriseType enterpriseType;


    public Enterprise(Integer id, Boolean ownEnterprise, String enterpriseName, String photo, String description, String city, String country, Integer value, Integer sharePrice, EnterpriseType enterpriseType) {
        this.id = id;
        this.ownEnterprise = ownEnterprise;
        this.enterpriseName = enterpriseName;
        this.photo = photo;
        this.description = description;
        this.country = country;
        this.value = value;
        this.sharePrice = sharePrice;
        this.enterpriseType = enterpriseType;
    }

    protected Enterprise(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        byte tmpOwnEnterprise = in.readByte();
        ownEnterprise = tmpOwnEnterprise == 0 ? null : tmpOwnEnterprise == 1;
        enterpriseName = in.readString();
        photo = in.readString();
        description = in.readString();
        country = in.readString();
        if (in.readByte() == 0) {
            value = null;
        } else {
            value = in.readInt();
        }
        if (in.readByte() == 0) {
            sharePrice = null;
        } else {
            sharePrice = in.readInt();
        }
    }

    public static final Creator<Enterprise> CREATOR = new Creator<Enterprise>() {
        @Override
        public Enterprise createFromParcel(Parcel in) {
            return new Enterprise(in);
        }

        @Override
        public Enterprise[] newArray(int size) {
            return new Enterprise[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getOwnEnterprise() {
        return ownEnterprise;
    }

    public void setOwnEnterprise(Boolean ownEnterprise) {
        this.ownEnterprise = ownEnterprise;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getSharePrice() {
        return sharePrice;
    }

    public void setSharePrice(Integer sharePrice) {
        this.sharePrice = sharePrice;
    }

    public EnterpriseType getEnterpriseType() {
        return enterpriseType;
    }

    public void setEnterpriseType(EnterpriseType enterpriseType) {
        this.enterpriseType = enterpriseType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        parcel.writeByte((byte) (ownEnterprise == null ? 0 : ownEnterprise ? 1 : 2));
        parcel.writeString(enterpriseName);
        parcel.writeString(photo);
        parcel.writeString(description);
        parcel.writeString(country);
        if (value == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(value);
        }
        if (sharePrice == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(sharePrice);
        }
    }
}
