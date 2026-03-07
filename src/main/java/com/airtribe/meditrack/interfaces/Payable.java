package com.airtribe.meditrack.interfaces;

import com.airtribe.meditrack.constants.Constants;

public interface Payable {
    double calculatePayment();

    String generateBill();

    default double applyTax(double amount) {

        return amount + (amount * Constants.TAX);
    }
}
