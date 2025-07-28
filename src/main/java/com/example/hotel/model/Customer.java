package com.example.hotel.model;

public class Customer {
    private String id;
    private String name;
    private String phoneNumber;
    private String checkInRoom;

    public Customer() {}

    public Customer(String id, String name, String phoneNumber, String checkInRoom) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.checkInRoom = checkInRoom;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getCheckInRoom() { return checkInRoom; }
    public void setCheckInRoom(String checkInRoom) { this.checkInRoom = checkInRoom; }
}
