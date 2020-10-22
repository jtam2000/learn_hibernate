package com.github.jtam2000.jpa.relationships.manytoone;

import java.time.LocalDate;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PostageStamp.class)
public abstract class PostageStamp_ {

	public static volatile SingularAttribute<PostageStamp, PostalCountry> country;
	public static volatile SingularAttribute<PostageStamp, Integer> stampID;
	public static volatile SingularAttribute<PostageStamp, Double> faceValue;
	public static volatile SingularAttribute<PostageStamp, String> title;
	public static volatile SingularAttribute<PostageStamp, LocalDate> issueDate;

	public static final String COUNTRY = "country";
	public static final String STAMP_ID = "stampID";
	public static final String FACE_VALUE = "faceValue";
	public static final String TITLE = "title";
	public static final String ISSUE_DATE = "issueDate";

}

