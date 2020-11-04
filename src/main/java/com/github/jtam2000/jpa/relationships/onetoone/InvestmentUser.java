package com.github.jtam2000.jpa.relationships.onetoone;

import com.github.jtam2000.jpa.HasPrimaryKey;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.LocalDate;

import static javax.persistence.CascadeType.ALL;

@Entity(name ="unionetoone_InvestmentUser")
public class InvestmentUser implements HasPrimaryKey {


    @Id
    @GeneratedValue
    private long userID;

    private String userName;
    private LocalDate userCreationDate;
    private String referringAgency;

    @OneToOne(cascade = ALL, orphanRemoval = true)
    @JoinColumn(name = "acct_id")
    private InvestmentAccount account;

    //required per JPA specification
    protected InvestmentUser(){}

    @Override
    public Object getPrimaryKey() {

        return userID;
    }

    @Override
    public String getPrimaryKeyName() {

        return com.github.jtam2000.jpa.relationships.onetoone.InvestmentUser_.USER_ID;
    }


    public String getUserName() {

        return userName;
    }

    public LocalDate getUserCreationDate() {

        return userCreationDate;
    }

    public String getReferringAgency() {

        return referringAgency;
    }

    @Override
    public String toString() {

        return "InvestmentUser = {" + "\n" +
                "\tuserID = " + userID + "\n" +
                "\tuserName = " + userName + "\n" +
                "\tuserCreationDate = " + userCreationDate + "\n" +
                "\treferringAgency = " + referringAgency + "\n" +
                "\t" + account + "\n" +
                '}';
    }

    public InvestmentUser(String userName, LocalDate userCreationDate, String referringAgency) {

        this.referringAgency = referringAgency;
        this.userCreationDate = userCreationDate;
        this.userName = userName;
    }

    public long getUserID() {
        return userID;

    }

    public void addAccount(InvestmentAccount account) {
        this.account= account;

    }

    public InvestmentAccount getAccount() {
        return account;

    }
}
