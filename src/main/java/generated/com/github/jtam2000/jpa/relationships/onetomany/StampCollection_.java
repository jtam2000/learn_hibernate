package com.github.jtam2000.jpa.relationships.onetomany;

import com.github.jtam2000.jpa.relationships.manytoone.PostageStamp;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(StampCollection.class)
public abstract class StampCollection_ {

	public static volatile SingularAttribute<StampCollection, Integer> Id;
	public static volatile MapAttribute<StampCollection, PostageStamp, Integer> collection;
	public static volatile SingularAttribute<StampCollection, String> collectionName;

	public static final String ID = "Id";
	public static final String COLLECTION = "collection";
	public static final String COLLECTION_NAME = "collectionName";

}

