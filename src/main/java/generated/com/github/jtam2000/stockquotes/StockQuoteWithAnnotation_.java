package com.github.jtam2000.stockquotes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(StockQuoteWithAnnotation.class)
public abstract class StockQuoteWithAnnotation_ {

	public static volatile SingularAttribute<StockQuoteWithAnnotation, String> ticker;
	public static volatile SingularAttribute<StockQuoteWithAnnotation, Float> ask;
	public static volatile SingularAttribute<StockQuoteWithAnnotation, Integer> changedFields;
	public static volatile SingularAttribute<StockQuoteWithAnnotation, Double> outstanding_shares;
	public static volatile SingularAttribute<StockQuoteWithAnnotation, LocalDateTime> quote_timestamp;
	public static volatile SingularAttribute<StockQuoteWithAnnotation, String> currency;
	public static volatile SingularAttribute<StockQuoteWithAnnotation, Double> available_shares;
	public static volatile SingularAttribute<StockQuoteWithAnnotation, Float> bid;
	public static volatile ListAttribute<StockQuoteWithAnnotation, LocalDate> dividend_date;

	public static final String TICKER = "ticker";
	public static final String ASK = "ask";
	public static final String CHANGED_FIELDS = "changedFields";
	public static final String OUTSTANDING_SHARES = "outstanding_shares";
	public static final String QUOTE_TIMESTAMP = "quote_timestamp";
	public static final String CURRENCY = "currency";
	public static final String AVAILABLE_SHARES = "available_shares";
	public static final String BID = "bid";
	public static final String DIVIDEND_DATE = "dividend_date";

}

