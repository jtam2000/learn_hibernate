package com.github.jtam2000.jpa.relationships.jointableonetoone;

import com.github.jtam2000.jpa.HasPrimaryKey;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Objects;

import static com.github.jtam2000.jpa.relationships.jointableonetoone.BiInvestmentAccount_.ACCOUNT_ID;
import static com.github.jtam2000.jpa.relationships.jointableonetoone.BiInvestmentAccount_.ACCOUNT_NAME;
import static com.github.jtam2000.jpa.relationships.jointableonetoone.BiInvestmentAccount_.ACCOUNT_PURPOSE;
import static com.github.jtam2000.jpa.relationships.jointableonetoone.BiInvestmentAccount_.BALANCE;
import static com.github.jtam2000.jpa.relationships.jointableonetoone.BiInvestmentAccount_.USER;

@SuppressWarnings("unused")
@Entity(name= "JointTableInvestmentAccount")
public class BiInvestmentAccount implements HasPrimaryKey {

    @Id
    @GeneratedValue
    @Column(name = "acct_id")
    private long accountID;

    private String accountName;
    private String accountPurpose;
    private double balance;


    @OneToOne(mappedBy = "account") //LEARNING: value of mappedBy = variable name of the owner of the relationship
    private BiInvestmentUser user;

    //required per JPA specification: kept here for compatibility with JPA providers
    protected BiInvestmentAccount() {

    }

    @Override
    public String toString() {

        return "BiInvestmentAccount = {" + "\n" +
                "\t" + ACCOUNT_ID + "=" + accountID + "\n" +
                "\t" + ACCOUNT_NAME + "=" + accountName + "\n" +
                "\t" + ACCOUNT_PURPOSE + "=" + accountPurpose + "\n" +
                "\t" + BALANCE + "=" + String.format("%,.2f", balance) + "\n" +
                "\t" + USER + "=" + user + "\n"  +
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
        BiInvestmentAccount that = (BiInvestmentAccount) o;
        return accountID==that.accountID &&
                Double.compare(that.balance, balance)==0 &&
                Objects.equals(accountName, that.accountName) &&
                Objects.equals(accountPurpose, that.accountPurpose) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {

        return Objects.hash(accountID, accountName, accountPurpose, balance, user);
    }

    public BiInvestmentAccount(String accountName, String accountPurpose, double balance, BiInvestmentUser user) {

        this.accountName = accountName;
        this.accountPurpose = accountPurpose;
        this.balance = balance;
        this.user = user;
    }

    @Override
    public Object getPrimaryKey() {

        return accountID;
    }

    @Override
    public String getPrimaryKeyName() {

        return ACCOUNT_ID;
    }

    public BiInvestmentUser getUser() {

        return user;
    }

    public void setUser(BiInvestmentUser user) {

        this.user = user;
    }
}
