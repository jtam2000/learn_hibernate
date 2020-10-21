package com.github.jtam2000.jpa.relationships.onetoone;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(InvestmentAccount.class)
public abstract class InvestmentAccount_ {

	public static volatile SingularAttribute<InvestmentAccount, Long> accountID;
	public static volatile SingularAttribute<InvestmentAccount, Double> balance;
	public static volatile SingularAttribute<InvestmentAccount, String> accountName;
	public static volatile SingularAttribute<InvestmentAccount, String> accountPurpose;

	public static final String ACCOUNT_ID = "accountID";
	public static final String BALANCE = "balance";
	public static final String ACCOUNT_NAME = "accountName";
	public static final String ACCOUNT_PURPOSE = "accountPurpose";

}

