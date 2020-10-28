package com.github.jtam2000.jpa.relationships.manytomany;

import java.time.LocalDate;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MyStampCollection.class)
public abstract class MyStampCollection_ {

	public static volatile SingularAttribute<MyStampCollection, String> owner;
	public static volatile SingularAttribute<MyStampCollection, Integer> collectionId;
	public static volatile SingularAttribute<MyStampCollection, LocalDate> startDate;
	public static volatile SingularAttribute<MyStampCollection, String> collectionName;

	public static final String OWNER = "owner";
	public static final String COLLECTION_ID = "collectionId";
	public static final String START_DATE = "startDate";
	public static final String COLLECTION_NAME = "collectionName";

}

