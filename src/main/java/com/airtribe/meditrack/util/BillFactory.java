package com.airtribe.meditrack.util;

import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.interfaces.BillingStrategy;

public class BillFactory {
    // Factory method — creates a Bill using the specified strategy type
    public static Bill createBill(Appointment appointment, BillingStrategy strategy) {
        int billId = IdGenerator.getInstance().generateId();
        return new Bill(billId, appointment, strategy);
    }
}
