package com.github.jtam2000.jpa.relationships.bidirectiononetoone;

import java.time.LocalDate;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(BiInvestmentUser.class)
public abstract class BiInvestmentUser_ {

	public static volatile SingularAttribute<BiInvestmentUser, String> referringAgency;
	public static volatile SingularAttribute<BiInvestmentUser, LocalDate> userCreationDate;
	public static volatile SingularAttribute<BiInvestmentUser, String> userName;
	public static volatile SingularAttribute<BiInvestmentUser, Long> userID;
	public static volatile SingularAttribute<BiInvestmentUser, BiInvestmentAccount> account;

	public static final String REFERRING_AGENCY = "referringAgency";
	public static final String USER_CREATION_DATE = "userCreationDate";
	public static final String USER_NAME = "userName";
	public static final String USER_ID = "userID";
	public static final String ACCOUNT = "account";

}

