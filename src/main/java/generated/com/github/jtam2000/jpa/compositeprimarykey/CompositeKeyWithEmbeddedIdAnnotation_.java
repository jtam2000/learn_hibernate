package com.github.jtam2000.jpa.compositeprimarykey;

import com.github.jtam2000.jpa.compositeprimarykey.CompositeKeyWithEmbeddedIdAnnotation.AccountCompositeKey;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CompositeKeyWithEmbeddedIdAnnotation.class)
public abstract class CompositeKeyWithEmbeddedIdAnnotation_ {

	public static volatile SingularAttribute<CompositeKeyWithEmbeddedIdAnnotation, Double> netWorth;
	public static volatile SingularAttribute<CompositeKeyWithEmbeddedIdAnnotation, AccountCompositeKey> primaryKey;

	public static final String NET_WORTH = "netWorth";
	public static final String PRIMARY_KEY = "primaryKey";

}

