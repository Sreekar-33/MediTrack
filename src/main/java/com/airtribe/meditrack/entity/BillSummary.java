package com.airtribe.meditrack.entity;

public final class BillSummary {
    private static final long serialVersionUID = 1L;

    private final int billId;
    private final String patientName;
    private final String doctorName;
    private final double baseAmount;
    private final double totalAmount;
    private final String billingStrategy;

    public BillSummary(int billId, String patientName, String doctorName,
                       double baseAmount, double totalAmount, String billingStrategy) {
        this.billId = billId;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.baseAmount = baseAmount;
        this.totalAmount = totalAmount;
        this.billingStrategy = billingStrategy;
    }

    public int getBillId() {
        return billId;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public double getBaseAmount() {
        return baseAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getBillingStrategy() {
        return billingStrategy;
    }

    @Override
    public String toString() {
        return String.format("BillSummary [%s] %s -> Dr. %s | $%.2f (%s)",
                billId, patientName, doctorName, totalAmount, billingStrategy);
    }
}
