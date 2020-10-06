package com.github.jtam2000.jpa.compositeprimarykey;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.Objects;

@Entity
@IdClass(CompositeKeyWithIdClassAnnotation.AccountCompositeKey.class)
public class CompositeKeyWithIdClassAnnotation{

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
    public int hashCode() {
        return Objects.hash(account, subAccount, investmentType);
    }

    public CompositeKeyWithIdClassAnnotation(long account, short subAccount, InvestmentStrategy investmentType) {
        this.account = account;
        this.subAccount = subAccount;
        this.investmentType = investmentType;
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
}
