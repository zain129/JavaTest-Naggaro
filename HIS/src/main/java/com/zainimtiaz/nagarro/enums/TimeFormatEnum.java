package com.zainimtiaz.nagarro.enums;

public enum TimeFormatEnum {


    HHmmssA{
        @Override
        public String toString() {
            return "HH:mm:ss a";
        }
    },
    HHmm{
        @Override
        public String toString() {
            return "HH:mm";
        }
    },
    HHmmss{
        @Override
        public String toString() {
            return "HH:mm:ss";
        }
    },
   /* mmss{
        @Override
        public String toString() {
            return "mm:ss";
        }
    },*/
}
