package com.github.jtam2000.jpa.relationships.bidirectiononetomany;

import com.github.jtam2000.jpa.HasPrimaryKey;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static com.github.jtam2000.jpa.relationships.bidirectiononetomany.BiDirectionStampCollection_.ID;
import static javax.persistence.CascadeType.ALL;

@Entity(name = "BiStampCollection")
public class BiDirectionStampCollection implements HasPrimaryKey {

    @Id
    @GeneratedValue
    private int Id;

    //LEARNING: [bidirectional one-to-many]
    //  ...
    //  we want to establish a [bidirectional one-to-many] relationship
    //  between stamp and collection, we need to do both of these two items:
    //      One-to-Many:  [One]= source, [Many] = target; source = Owner of relationship, target = parent table
    //      _
    //     a) [Cardinality]: One-to-Many: ONE  Collections(Source) can have MANY Stamps(Target)
    //              use @OneToMany(mappedBy = "nameOfTargetVariable") on the POJO that has the [Many](target)
    //      _
    //     b) [Direction]: Bidirectional: the Target(stamp) has a handle pointing back to Source(collection)
    //              use @ManyToOne without parameters on the POJO that has the [One](the source)
    //  ..
    // In this particular case:
    //  source = owner  = PostageStamp.class, target = parent = BiDirectionStampCollection(the class in  this file)
    //  ...
    @OneToMany(mappedBy = "keptInCollection", cascade = ALL)
    private List<PostageStamp> collection = new LinkedList<>();

    @Column(name = "collection")
    private String collectionName;

    //Required by JPA specification
    protected BiDirectionStampCollection() {

    }

    public BiDirectionStampCollection(String collectionName) {

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
        BiDirectionStampCollection that = (BiDirectionStampCollection) o;
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
