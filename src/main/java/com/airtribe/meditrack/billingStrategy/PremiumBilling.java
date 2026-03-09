package com.airtribe.meditrack.billingStrategy;

import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.interfaces.BillingStrategy;

public class PremiumBilling implements BillingStrategy {
    public double calculate(double baseAmount) {
        double surcharge = baseAmount + (baseAmount * Constants.PREMIUM_CHARGE);
        return surcharge + (surcharge * Constants.TAX);
    }

    @Override
    public String getStrategyName() {
        return "Premium";
    }
}
