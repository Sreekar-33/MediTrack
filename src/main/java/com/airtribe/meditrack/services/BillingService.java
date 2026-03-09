package com.airtribe.meditrack.services;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.entity.BillSummary;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import com.airtribe.meditrack.interfaces.BillingStrategy;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.util.IdGenerator;

import java.util.Collection;

public class BillingService {

    private BillingStrategy billingStrategy;
    private DataStore<Bill> billStore = new DataStore<>();
    private IdGenerator idGenerator = IdGenerator.getInstance();

    public BillingService(BillingStrategy billingStrategy) {
        this.billingStrategy = billingStrategy;
    }

    public BillSummary generateBill(Appointment appointment) throws AppointmentNotFoundException{

        if (appointment == null) {
            throw new AppointmentNotFoundException("Cannot generate bill. Appointment not found.");
        }

        int billId = idGenerator.generateId();


        Bill bill = new Bill(billId, appointment, this.billingStrategy);

        billStore.add(billId, bill);

        return bill.toSummary();
    }

    public void setBillingStrategy(BillingStrategy billingStrategy){
        this.billingStrategy = billingStrategy;
    }


    public Bill getBill(int billId) {
        return billStore.get(billId);
    }

    public Collection<Bill> getAllBills() {
        return billStore.getAll();
    }

}