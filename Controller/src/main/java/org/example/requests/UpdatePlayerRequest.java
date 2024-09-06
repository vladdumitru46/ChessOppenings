package org.example.requests;

public record UpdatePlayerRequest(String token,String name, String userName, String email, String password) {
}
