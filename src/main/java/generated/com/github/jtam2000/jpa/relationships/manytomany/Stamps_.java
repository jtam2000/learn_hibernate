package com.github.jtam2000.jpa.relationships.manytomany;

import java.time.LocalDate;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Stamps.class)
public abstract class Stamps_ {

	public static volatile SingularAttribute<Stamps, String> country;
	public static volatile SingularAttribute<Stamps, Long> stampID;
	public static volatile SingularAttribute<Stamps, Double> faceValue;
	public static volatile SingularAttribute<Stamps, String> title;
	public static volatile SingularAttribute<Stamps, LocalDate> issueDate;

	public static final String COUNTRY = "country";
	public static final String STAMP_ID = "stampID";
	public static final String FACE_VALUE = "faceValue";
	public static final String TITLE = "title";
	public static final String ISSUE_DATE = "issueDate";

}

