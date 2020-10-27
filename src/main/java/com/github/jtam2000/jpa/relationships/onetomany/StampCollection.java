package com.github.jtam2000.jpa.relationships.onetomany;

import com.github.jtam2000.jpa.HasPrimaryKey;
import com.github.jtam2000.jpa.relationships.manytoone.PostageStamp;

import javax.persistence.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static javax.persistence.CascadeType.ALL;

@Entity
public class StampCollection implements HasPrimaryKey {

    @Id
    @GeneratedValue
    private int Id;

    @OneToMany(cascade = ALL)
    @JoinColumn
    private List<PostageStamp> collection = new LinkedList<>();

    @Column(name = "collection")
    private String collectionName;

    //Required by JPA specification
    protected StampCollection() {

    }

    public StampCollection(String collectionName) {

        this.collectionName = collectionName;
    }

    public void addStamp(List<PostageStamp> additions) {
        collection.addAll(additions);
        //additions.forEach(k -> collection.merge(k, 1, Integer::sum));
    }

    public List<PostageStamp> getCollection() {

        return collection;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StampCollection that = (StampCollection) o;
        return Id == that.Id &&
                Objects.equals(collection, that.collection) &&
                Objects.equals(collectionName, that.collectionName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(Id, collection, collectionName);
    }

    @Override
    public String toString() {

        return "StampCollection{" +
                "Id=" + Id +
                ", collection=" + collection +
                ", collectionName='" + collectionName + '\'' +
                '}';
    }

    @Override
    public Object getPrimaryKey() {

        return Id;
    }

    @Override
    public String getPrimaryKeyName() {

        return com.github.jtam2000.jpa.relationships.onetomany.StampCollection_.ID;
    }
}
