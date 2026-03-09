package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.interfaces.BillingStrategy;
import com.airtribe.meditrack.interfaces.Payable;

public class Bill extends MedicalEntity implements Payable {

    private int billId;

    private final BillSummary billSummary;

    private double baseAmount;
    private double taxAmount;
    private double totalAmount;

    public int getBillId() {
        return billId;
    }

    public double getBaseAmount() {
        return baseAmount;
    }

    public void setBaseAmount(double baseAmount) {
        this.baseAmount = baseAmount;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public BillingStrategy getBillingStrategy() {
        return billingStrategy;
    }

    public void setBillingStrategy(BillingStrategy billingStrategy) {
        this.billingStrategy = billingStrategy;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }

    Appointment appointment;
    private transient BillingStrategy billingStrategy;
    private String strategyName;

    public Bill(int billId, Appointment appointment, BillingStrategy strategy) {
        super(billId);
        this.appointment = appointment;
        this.billingStrategy = strategy;
        this.strategyName = strategy.getStrategyName();
        this.baseAmount = appointment.getDoctor().getConsultationFee();
        this.totalAmount = strategy.calculate(baseAmount);
        this.taxAmount = totalAmount - baseAmount;
        this.billSummary = new BillSummary(getBillId(), appointment.getPatient().getName(),
                appointment.getDoctor().getName(), baseAmount, totalAmount, strategyName);

    }

    @Override
    public double calculatePayment(){
        return totalAmount;
    }

    @Override
    public String generateBill(){
        //needs to be generated
        return String.format(
                "========== BILL ==========\n" +
                        "  Bill ID      : %s\n" +
                        "  Patient      : %s\n" +
                        "  Doctor       : Dr. %s (%s)\n"+
                        "  Strategy     : %s\n" +
                        "  Base Amount  : $%.2f\n" +
                        "  Tax/Adj.     : $%.2f\n" +
                        "  Total        : $%.2f\n" +
                        "==========================",
                getBillId(),
                appointment.getPatient().getName(),
                appointment.getDoctor().getName(),
                appointment.getDoctor().getSpecialization().name(),
                strategyName,
                baseAmount, taxAmount, totalAmount);
    }

    //immutable summary snapshot of bill
    public BillSummary toSummary() {
        return  this.billSummary;
    }

    @Override
    public String getDetails() {
        return String.format("Bill [%s] | Appointment: %s | Strategy: %s | Base: %.2f | Tax: %.2f | Total: %.2f",
                getId(), appointment.getId(), strategyName, baseAmount, taxAmount, totalAmount);
    }
    @Override
    public String getEntityType(){
        return "Bill";
    }
}
