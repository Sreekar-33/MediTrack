package com.airtribe.meditrack.billingStrategy;

import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.interfaces.BillingStrategy;

public class InsuranceBilling implements BillingStrategy {
    public double calculate(double baseAmount) {
        double afterDiscount = baseAmount - (baseAmount * Constants.INSURANCE_DISCOUNT);
        return afterDiscount + (afterDiscount * Constants.TAX);
    }

    @Override
    public String getStrategyName() {
        return "Insurance";
    }
}
