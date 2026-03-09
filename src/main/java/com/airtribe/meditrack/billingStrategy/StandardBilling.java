package com.airtribe.meditrack.billingStrategy;

import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.interfaces.BillingStrategy;

public class StandardBilling implements BillingStrategy {
    @Override
    public double calculate(double baseAmount) {
        return baseAmount + (baseAmount * Constants.TAX);
    }

    @Override
    public String getStrategyName() {
        return "Standard";
    }
}
