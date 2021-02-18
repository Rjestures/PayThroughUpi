package model;

public class UpiMaker {
    String upiId;
    String upiName;
    String upiRemarkNote;
    String upiAmount;
    public UpiMaker(String upiId, String upiName, String upiRemarkNote, String upiAmount) {
        this.upiId = upiId;
        this.upiName = upiName;
        this.upiRemarkNote = upiRemarkNote;
        this.upiAmount = upiAmount;
    }

    public String getUpiId() {
        return upiId;
    }

    public void setUpiId(String upiId) {
        this.upiId = upiId;
    }

    public String getUpiName() {
        return upiName;
    }

    public void setUpiName(String upiName) {
        this.upiName = upiName;
    }

    public String getUpiRemarkNote() {
        return upiRemarkNote;
    }

    public void setUpiRemarkNote(String upiRemarkNote) {
        this.upiRemarkNote = upiRemarkNote;
    }

    public String getUpiAmount() {
        return upiAmount;
    }

    public void setUpiAmount(String upiAmount) {
        this.upiAmount = upiAmount;
    }
}
