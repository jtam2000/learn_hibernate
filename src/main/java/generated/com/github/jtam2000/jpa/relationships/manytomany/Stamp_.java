package com.github.jtam2000.jpa.relationships.manytomany;

import java.time.LocalDate;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Stamp.class)
public abstract class Stamp_ {

	public static volatile SingularAttribute<Stamp, String> country;
	public static volatile SetAttribute<Stamp, MyStampCollection> collections;
	public static volatile SingularAttribute<Stamp, Long> stampID;
	public static volatile SingularAttribute<Stamp, Double> faceValue;
	public static volatile SingularAttribute<Stamp, String> title;
	public static volatile SingularAttribute<Stamp, LocalDate> issueDate;

	public static final String COUNTRY = "country";
	public static final String COLLECTIONS = "collections";
	public static final String STAMP_ID = "stampID";
	public static final String FACE_VALUE = "faceValue";
	public static final String TITLE = "title";
	public static final String ISSUE_DATE = "issueDate";

}

