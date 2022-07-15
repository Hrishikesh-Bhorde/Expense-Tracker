package com.expense_tracker.Model;

public class Data {

    private int mamount;
    private String mtype;
    private String mid;
    private String mnote;
    private  String mdate;

    public Data() {}  //Needed for Firebase

    public Data(int amount, String type, String id, String note, String date) {
        mamount = amount;
        mtype = type;
        mid = id;
        mnote = note;
        mdate = date;
    }

    public int getAmount() {
        return mamount;
    }

    public String getType() {
        return mtype;
    }

    public String getId() {
        return mid;
    }

    public String getNote() {
        return mnote;
    }

    public String getDate() {
        return mdate;
    }

    public void setAmount(int amount) {
        this.mamount = mamount;
    }

    public void setType(String mtype) {
        this.mtype = mtype;
    }

    public void setId(String mid) {
        this.mid = mid;
    }

    public void setNote(String mnote) {
        this.mnote = mnote;
    }

    public void setDate(String mdate) {
        this.mdate = mdate;
    }
}
