package com.github.jtam2000.jpa.relationships.onetoone;

import java.time.LocalDate;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(InvestmentUser.class)
public abstract class InvestmentUser_ {

	public static volatile SingularAttribute<InvestmentUser, String> referringAgency;
	public static volatile SingularAttribute<InvestmentUser, LocalDate> userCreationDate;
	public static volatile SingularAttribute<InvestmentUser, String> userName;
	public static volatile SingularAttribute<InvestmentUser, Long> userID;
	public static volatile SingularAttribute<InvestmentUser, InvestmentAccount> account;

	public static final String REFERRING_AGENCY = "referringAgency";
	public static final String USER_CREATION_DATE = "userCreationDate";
	public static final String USER_NAME = "userName";
	public static final String USER_ID = "userID";
	public static final String ACCOUNT = "account";

}

