package com.github.jtam2000.jpa.compositeprimarykey;

import com.github.jtam2000.jpa.HasPrimaryKey;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import static com.github.jtam2000.jpa.compositeprimarykey.CompositeKeyWithEmbeddedIdAnnotation_.*;

@Entity
public class CompositeKeyWithEmbeddedIdAnnotation implements HasPrimaryKey {

    @EmbeddedId
    private AccountCompositeKey primaryKey;

    private double netWorth;

    @Override
    public Object getPrimaryKey() {
        return primaryKey;
    }

    @Override
    public String getPrimaryKeyName() {
        return PRIMARY_KEY;

    }

    //LEARNING: JPA Requires a default no-arg constructor
    private CompositeKeyWithEmbeddedIdAnnotation() {
    }

    public CompositeKeyWithEmbeddedIdAnnotation(AccountCompositeKey primaryKey) {

        this.primaryKey = primaryKey;
    }

    public static CompositeKeyWithEmbeddedIdAnnotation of() {

        CompositeKeyWithEmbeddedIdAnnotation of = new CompositeKeyWithEmbeddedIdAnnotation(AccountCompositeKey.of());
        of.setNetWorth(ThreadLocalRandom.current().nextDouble(1_000_000_000.01D));
        return of;
    }

    public static CompositeKeyWithEmbeddedIdAnnotation of(CompositeKeyWithEmbeddedIdAnnotation copy) {

        CompositeKeyWithEmbeddedIdAnnotation clone = new CompositeKeyWithEmbeddedIdAnnotation(copy.primaryKey);
        clone.setNetWorth(copy.netWorth);
        return clone;
    }

    public double getNetWorth() {

        return netWorth;
    }

    public void setNetWorth(double netWorth) {

        this.netWorth = netWorth;
    }


    //LEARNING: Use of @Embeddable defines the object that can be the Composite primary key
    // that contains the composite primary key
    @Embeddable
    public static class AccountCompositeKey implements Serializable {

        //LEARNING: Notice these are repeated in the user of this class
        private long account;
        private short subAccount;

        //LEARNING: use this Annotation if you want to store the String, not the Enum ordinal
        // other type to store enum id: @Enumerated(EnumType.ORDINAL);
        @Enumerated(EnumType.STRING)
        private InvestmentStrategy investmentType;

        public enum InvestmentStrategy {
            CUSTODY,
            CASH,
            STABLE,
            INCOME,
            GROWTH,
            AGGRESSIVE,
            PRIVATE
        }

        public AccountCompositeKey() {
        }

        public static AccountCompositeKey of() {

            InvestmentStrategy[] investTypes = InvestmentStrategy.values();

            return new AccountCompositeKey(
                    ThreadLocalRandom.current().nextLong(),
                    (short) ThreadLocalRandom.current().nextInt(Short.MAX_VALUE),
                    investTypes[ThreadLocalRandom.current().nextInt(investTypes.length)]);

        }

        public AccountCompositeKey(long account, short subAccount, InvestmentStrategy inv) {
            this.account = account;
            this.subAccount = subAccount;
            this.investmentType = inv;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AccountCompositeKey that = (AccountCompositeKey) o;
            return account == that.account &&
                    subAccount == that.subAccount &&
                    investmentType == that.investmentType;
        }

        @Override
        public int hashCode() {
            return Objects.hash(account, subAccount, investmentType);
        }

        @Override
        public String toString() {
            return "AccountCompositeKey{" + "\n" +
                    "account=" + account +
                    ", subAccount=" + subAccount + "\n" +
                    ", investmentType=" + investmentType + "\n" +
                    '}';
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompositeKeyWithEmbeddedIdAnnotation that = (CompositeKeyWithEmbeddedIdAnnotation) o;
        return primaryKey == that.primaryKey &&
                netWorth == that.netWorth;
    }

    @Override
    public String toString() {
        return "CompositeKeyWithEmbeddedIdAnnotation{" + "\n" +
                "Composite PrimaryKey =" + primaryKey +
                ", netWorth=" + netWorth + "\n" +
                '}';
    }

}
