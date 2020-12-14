package com.zainimtiaz.nagarro.enums;



public enum DateFormatEnum {

        dd_MM_yyyy {
            @Override
            public String toString() {
                return "dd-MM-yyyy";
            }
        },
        yyyy_dd_MM {
            @Override
            public String toString() {
                return "yyyy-dd-MM";
            }
        },
        yyyy_MM_dd {
        @Override
        public String toString() {
            return "yyyy-MM-dd";
        }
        },

        yyyyMMdd {
        @Override
        public String toString() {
            return "yyyy/MM/dd";
        }
        },
        ddMMYYYY {
        @Override
        public String toString() {
            return "dd/MM/YYYY";
        }
        },
        yyyyddMM {
        @Override
        public String toString() {
            return "yyyy/dd/MM";
        }
        },
        yyyyMMdd0000{
        @Override
        public String toString() {
            return "yyyy-MM-dd-00:00";
        }
     },

   }



