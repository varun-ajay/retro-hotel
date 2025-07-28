package com.example.hotel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CreateCustomerRequest {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "^[0-9\\-+() ]{7,20}$", message = "Invalid phone number format")
    private String phoneNumber;

    @NotBlank(message = "Check-in room cannot be blank")
    private String checkInRoom;

    public String getName() { return name; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getCheckInRoom() { return checkInRoom; }

    public void setName(String name) { this.name = name; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setCheckInRoom(String checkInRoom) { this.checkInRoom = checkInRoom; }
}
