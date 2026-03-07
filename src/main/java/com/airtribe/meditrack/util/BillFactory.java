package com.airtribe.meditrack.util;

import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.interfaces.BillingStrategy;

public class BillFactory {
    public static class StandardBilling implements BillingStrategy {
        @Override
        public double calculate(double baseAmount) {
            return baseAmount + (baseAmount * Constants.TAX);
        }

        @Override
        public String getStrategyName() {
            return "Standard";
        }
    }

    public static class PremiumBilling implements BillingStrategy {
        @Override
        public double calculate(double baseAmount) {
            double surcharge = baseAmount + (baseAmount * Constants.PREMIUM_CHARGE);
            return surcharge + (surcharge * Constants.TAX);
        }

        @Override
        public String getStrategyName() {
            return "Premium";
        }
    }

    public static class InsuranceBilling implements BillingStrategy {
        @Override
        public double calculate(double baseAmount) {
            double afterDiscount = baseAmount - (baseAmount * Constants.INSURANCE_DISCOUNT);
            return afterDiscount + (afterDiscount * Constants.TAX);
        }

        @Override
        public String getStrategyName() {
            return "Insurance";
        }
    }

    // Factory method — creates a Bill using the specified strategy type
    public static Bill createBill(Appointment appointment, String strategyType) {
        BillingStrategy strategy;

        switch (strategyType.toLowerCase()) {
            case "premium":
                strategy = new PremiumBilling();
                break;
            case "insurance":
                strategy = new InsuranceBilling();
                break;
            case "standard":
            default:
                strategy = new StandardBilling();
                break;
        }

        int billId = IdGenerator.getInstance().generateId();
        return new Bill(billId, appointment, strategy);
    }
}
