package com.springboot.cruddemo.rest;

public class DataNotFoundException extends RuntimeException{
        public DataNotFoundException(String message) {
        super(message);
    }

}
