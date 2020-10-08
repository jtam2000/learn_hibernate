package com.github.jtam2000.jpa.compositeprimarykey;

import com.github.jtam2000.jpa.compositeprimarykey.CompositeKeyWithIdClassAnnotation.InvestmentStrategy;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CompositeKeyWithIdClassAnnotation.class)
public abstract class CompositeKeyWithIdClassAnnotation_ {

	public static volatile SingularAttribute<CompositeKeyWithIdClassAnnotation, Double> netWorth;
	public static volatile SingularAttribute<CompositeKeyWithIdClassAnnotation, InvestmentStrategy> investmentType;
	public static volatile SingularAttribute<CompositeKeyWithIdClassAnnotation, Long> account;
	public static volatile SingularAttribute<CompositeKeyWithIdClassAnnotation, Short> subAccount;

	public static final String NET_WORTH = "netWorth";
	public static final String INVESTMENT_TYPE = "investmentType";
	public static final String ACCOUNT = "account";
	public static final String SUB_ACCOUNT = "subAccount";

}

