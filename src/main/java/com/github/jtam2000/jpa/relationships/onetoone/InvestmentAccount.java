package com.github.jtam2000.jpa.relationships.onetoone;

import com.github.jtam2000.jpa.HasPrimaryKey;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity(name ="unionetoone_InvestmentAccount")
public class InvestmentAccount implements HasPrimaryKey {

    @Id
    @GeneratedValue
    @Column(name = "fk_acct_id")
    private long accountID;

    private String accountName;
    private String accountPurpose;
    private double balance;

    //required per JPA specification: kept here for compatibility with JPA providers
    protected InvestmentAccount() {

    }

    @Override
    public String toString() {

        return "InvestmentAccount = {" + "\n" +
                "\t" + com.github.jtam2000.jpa.relationships.onetoone.InvestmentAccount_.ACCOUNT_ID + "=" + accountID + "\n" +
                "\t" + com.github.jtam2000.jpa.relationships.onetoone.InvestmentAccount_.ACCOUNT_NAME + "=" + accountName + "\n" +
                "\t" + com.github.jtam2000.jpa.relationships.onetoone.InvestmentAccount_.ACCOUNT_PURPOSE + "=" + accountPurpose + "\n" +
                "\t" + com.github.jtam2000.jpa.relationships.onetoone.InvestmentAccount_.BALANCE + "=" + String.format("%,.2f", balance) + "\n" +
                '}';
    }

    public long getAccountID() {

        return accountID;
    }

    public String getAccountName() {

        return accountName;
    }

    public String getAccountPurpose() {

        return accountPurpose;
    }

    public double getBalance() {

        return balance;
    }

    public void incrementBalance(double addAmount) {

        this.balance += addAmount;
    }

    @Override
    public boolean equals(Object o) {

        if (this==o) return true;
        if (o==null || getClass()!=o.getClass()) return false;
        InvestmentAccount that = (InvestmentAccount) o;
        return accountID==that.accountID &&
                Double.compare(that.balance, balance)==0 &&
                Objects.equals(accountName, that.accountName) &&
                Objects.equals(accountPurpose, that.accountPurpose);
    }

    @Override
    public int hashCode() {

        return Objects.hash(accountID, accountName, accountPurpose, balance);
    }

    public InvestmentAccount(String accountName, String accountPurpose, double balance) {

        this.accountName = accountName;
        this.accountPurpose = accountPurpose;
        this.balance = balance;
    }

    @Override
    public Object getPrimaryKey() {

        return accountID;
    }

    @Override
    public String getPrimaryKeyName() {

        return com.github.jtam2000.jpa.relationships.onetoone.InvestmentAccount_.ACCOUNT_ID;
    }
}
