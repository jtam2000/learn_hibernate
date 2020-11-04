package com.github.jtam2000.jpa.relationships.jointableonetomany;

import com.github.jtam2000.jpa.HasPrimaryKey;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static com.github.jtam2000.jpa.relationships.jointableonetomany.StampCollection_.*;
import static javax.persistence.CascadeType.ALL;

@Entity(name = "JT_StampCollectionOneToMany")
public class StampCollection implements HasPrimaryKey {

    @SuppressWarnings("unused")
    @Id
    @GeneratedValue
    private int Id;

    @OneToMany(cascade = ALL)
    //LEARNING:
    //  the join column in the [MANY] side
    //  the inverseJoin column is the [ONE] side
    @JoinTable(name = "JointTable1M_StampCollectionOneDirection",
            joinColumns = {@JoinColumn(name = "fk_collection_id")},
            inverseJoinColumns = {@JoinColumn(name = "fk_stamp_id")}
    )
    private final List<PostageStamp> collection = new LinkedList<>();

    @Column(name = "collection")
    private String collectionName;

    //Required by JPA specification
    @SuppressWarnings("unused")
    protected StampCollection() {

    }

    public StampCollection(String collectionName) {

        this.collectionName = collectionName;
    }

    public void addStamp(List<PostageStamp> additions) {

        collection.addAll(additions);
    }

    public List<PostageStamp> getCollection() {

        return collection;
    }

    @Override
    public boolean equals(Object o) {

        if (this==o) return true;
        if (o==null || getClass()!=o.getClass()) return false;
        StampCollection that = (StampCollection) o;
        return Id==that.Id &&
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

        return ID;
    }
}
