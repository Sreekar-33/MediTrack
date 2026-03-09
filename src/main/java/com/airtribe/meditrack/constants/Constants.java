package com.airtribe.meditrack.constants;

public final class Constants {


        // Tax and billing constants
        public static final double TAX;
        public static final double PREMIUM_CHARGE;
        public static final double INSURANCE_DISCOUNT;

        static {
                TAX = 0.18;
                PREMIUM_CHARGE = 0.25;
                INSURANCE_DISCOUNT = 0.30;
        }


        private Constants() {
            throw new UnsupportedOperationException("Constants class cannot be instantiated.");
        }
}


