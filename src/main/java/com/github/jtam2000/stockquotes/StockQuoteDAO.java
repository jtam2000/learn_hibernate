package com.github.jtam2000.stockquotes;

import java.util.LinkedList;
import java.util.List;

public class StockQuoteDAO implements DataAccessObject<StockQuoteWithAnnotation>{

    StockQuoteWithAnnotation oneQuote;

    public StockQuoteDAO(StockQuoteWithAnnotation oneQuote) {
        this.oneQuote = oneQuote;
    }

    @Override
    public List<StockQuoteWithAnnotation> create() {
        List<StockQuoteWithAnnotation> rtn = new LinkedList<StockQuoteWithAnnotation>();
        rtn.add(new StockQuoteWithAnnotation());
        return rtn;
    }

    @Override
    public List<StockQuoteWithAnnotation> read() {
        return null;
    }

    @Override
    public List<StockQuoteWithAnnotation> update() {
        return null;
    }

    @Override
    public void delete() {

    }
}
