package com.github.jtam2000.jpa.relationships.jointableonetoone;


import com.github.jtam2000.jpa.HasPrimaryKey;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import java.time.LocalDate;

import static javax.persistence.CascadeType.ALL;

@SuppressWarnings("unused")
@Entity(name= "JointTableInvestmentUser")
public class BiInvestmentUser implements HasPrimaryKey {


    @Id
    @GeneratedValue
    private long userID;

    private String userName;
    private LocalDate userCreationDate;
    private String referringAgency;

    @OneToOne(cascade = ALL, orphanRemoval = true)
    //LEARNING This 1:1 relationship will create a third table("join table") that contains sourcePK and targetPK
    // from the source table and the target table, you can name the columns using @JoinColumn
    // within the @JoinTable annotation
    @JoinTable(
            name = "JOIN_BiInvestmentUser_BiInvestmentAcct",
            joinColumns = {@JoinColumn(name = "user_id_fk")},
            inverseJoinColumns = {@JoinColumn(name = "acct_id_fk")}
            )
    private BiInvestmentAccount account;

    //required per JPA specification: kept here for compatibility with JPA providers
    protected BiInvestmentUser() {

    }

    @Override
    public Object getPrimaryKey() {

        return userID;
    }

    @Override
    public String getPrimaryKeyName() {

        return com.github.jtam2000.jpa.relationships.jointableonetoone.BiInvestmentUser_.USER_ID;
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

        return "BiInvestmentUser = {" + "\n" +
                "\tuserID = " + userID + "\n" +
                "\tuserName = " + userName + "\n" +
                "\tuserCreationDate = " + userCreationDate + "\n" +
                "\treferringAgency = " + referringAgency + "\n" +
                '}';
    }

    public BiInvestmentUser(String userName, LocalDate userCreationDate, String referringAgency) {

        this.referringAgency = referringAgency;
        this.userCreationDate = userCreationDate;
        this.userName = userName;
    }

    public long getUserID() {

        return userID;

    }

    public void addAccount(BiInvestmentAccount account) {

        this.account = account;

    }

    public BiInvestmentAccount getAccount() {

        return account;

    }
}
