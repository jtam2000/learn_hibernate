package com.github.jtam2000.jpa.relationships.manytoone;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PostalCountry.class)
public abstract class PostalCountry_ {

	public static volatile SingularAttribute<PostalCountry, String> countryName;
	public static volatile SingularAttribute<PostalCountry, Integer> countryID;

	public static final String COUNTRY_NAME = "countryName";
	public static final String COUNTRY_ID = "countryID";

}

