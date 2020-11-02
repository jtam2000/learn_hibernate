package com.github.jtam2000.jpa.relationships.bidirectiononetoone;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(BiInvestmentAccount.class)
public abstract class BiInvestmentAccount_ {

	public static volatile SingularAttribute<BiInvestmentAccount, Long> accountID;
	public static volatile SingularAttribute<BiInvestmentAccount, Double> balance;
	public static volatile SingularAttribute<BiInvestmentAccount, String> accountName;
	public static volatile SingularAttribute<BiInvestmentAccount, String> accountPurpose;
	public static volatile SingularAttribute<BiInvestmentAccount, BiInvestmentUser> user;

	public static final String ACCOUNT_ID = "accountID";
	public static final String BALANCE = "balance";
	public static final String ACCOUNT_NAME = "accountName";
	public static final String ACCOUNT_PURPOSE = "accountPurpose";
	public static final String USER = "user";

}

