package com.yazilimokulu.mvc.services;

public class AlreadyVotedException extends Exception {

    public AlreadyVotedException(String message) {
        super(message);
    }
}
