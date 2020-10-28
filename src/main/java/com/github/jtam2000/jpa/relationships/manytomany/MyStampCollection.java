package com.github.jtam2000.jpa.relationships.manytomany;

import com.github.jtam2000.jpa.HasPrimaryKey;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class MyStampCollection implements HasPrimaryKey {

    @Id
    @GeneratedValue
    int collectionId;

    String collectionName;
    String owner;
    LocalDate startDate;

    public MyStampCollection(String collectionName, String owner, LocalDate startDate) {

        this.collectionName = collectionName;
        this.owner = owner;
        this.startDate = startDate;
    }

    public static MyStampCollection randomCollection(String name) {
        return new MyStampCollection(name,"public", LocalDate.now());
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyStampCollection that = (MyStampCollection) o;
        return getCollectionId() == that.getCollectionId() &&
                Objects.equals(collectionName, that.collectionName) &&
                Objects.equals(owner, that.owner) &&
                Objects.equals(startDate, that.startDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(getCollectionId(), collectionName, owner, startDate);
    }

    @Override
    public String toString() {

        return "MyStampCollection{" +
                "collectionId=" + collectionId +
                ", collectionName='" + collectionName + '\'' +
                ", owner='" + owner + '\'' +
                ", startDate=" + startDate +
                '}';
    }

    public int getCollectionId() {

        return collectionId;
    }

    public void setCollectionId(int collectionId) {

        this.collectionId = collectionId;
    }

    @Override
    public Object getPrimaryKey() {

        return getCollectionId();
    }

    @Override
    public String getPrimaryKeyName() {

        return MyStampCollection_.COLLECTION_ID;
    }
}
