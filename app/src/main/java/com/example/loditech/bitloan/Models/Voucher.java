package com.example.loditech.bitloan.Models;

public class Voucher {
    public int ID;
    public String Name;
    public short PercentDiscount;
    public String Description;
    public String Code;
    public int Points;
    public int NumberofTimeRedeemed;

    public Voucher(int ID, String name, short percentDiscount, String description, String code, int points, int numberofTimeRedeemed) {
        this.ID = ID;
        Name = name;
        PercentDiscount = percentDiscount;
        Description = description;
        Code = code;
        Points = points;
        NumberofTimeRedeemed = numberofTimeRedeemed;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public short getPercentDiscount() {
        return PercentDiscount;
    }

    public void setPercentDiscount(short percentDiscount) {
        PercentDiscount = percentDiscount;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public int getPoints() {
        return Points;
    }

    public void setPoints(int points) {
        Points = points;
    }

    public int getNumberofTimeRedeemed() {
        return NumberofTimeRedeemed;
    }

    public void setNumberofTimeRedeemed(int numberofTimeRedeemed) {
        NumberofTimeRedeemed = numberofTimeRedeemed;
    }
}
