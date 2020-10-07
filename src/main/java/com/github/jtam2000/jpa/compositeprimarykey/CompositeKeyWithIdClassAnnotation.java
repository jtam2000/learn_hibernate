package com.github.jtam2000.jpa.compositeprimarykey;

import com.github.jtam2000.jpa.HasPrimaryKey;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

@Entity
@IdClass(CompositeKeyWithIdClassAnnotation.AccountCompositeKey.class)
public class CompositeKeyWithIdClassAnnotation implements HasPrimaryKey {

    @Override
    public Object getPrimaryKey() {
        return new AccountCompositeKey(account, subAccount, investmentType);
    }

    @Override
    public String getPrimaryKeyName() {
        return null;
    }

    public enum InvestmentStrategy {
        CUSTODY,
        CASH,
        STABLE,
        INCOME,
        GROWTH,
        AGGRESSIVE,
        PRIVATE
    }

    //LEARNING: if you are using @IdClass you need to repeat the Ids in the
    //  composing class(here)
    @Id
    private long account;
    @Id
    private short subAccount;
    @Id
    private InvestmentStrategy investmentType;

    public CompositeKeyWithIdClassAnnotation(long account,
                                             short subAccount,
                                             InvestmentStrategy investmentType) {
        this.account = account;
        this.subAccount = subAccount;
        this.investmentType = investmentType;
    }

    public CompositeKeyWithIdClassAnnotation of() {

        InvestmentStrategy[] investTypes = InvestmentStrategy.values();

        return new CompositeKeyWithIdClassAnnotation(
                ThreadLocalRandom.current().nextLong(),
                (short) ThreadLocalRandom.current().nextInt(Short.MAX_VALUE),
                investTypes[ThreadLocalRandom.current().nextInt(investTypes.length)]
                );
    }

    //LEARNING: primary key has to be a separate class and used by the Entity class
    // that contains the composite primary key
    public  static class AccountCompositeKey   implements Serializable {

        //LEARNING: Notice these are repeated in the user of this class
        private long account;
        private short subAccount;
        private InvestmentStrategy investmentType;

        public AccountCompositeKey(long account, short subAccount, InvestmentStrategy inv){
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
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompositeKeyWithIdClassAnnotation that = (CompositeKeyWithIdClassAnnotation) o;
        return account == that.account &&
                subAccount == that.subAccount &&
                investmentType == that.investmentType;
    }

    @Override
    public String toString() {
        return "CompositeKeyWithIdClassAnnotation{" + "\n" +
                "account=" + account +
                ", subAccount=" + subAccount + "\n" +
                ", investmentType=" + investmentType +  "\n" +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, subAccount, investmentType);
    }

}
