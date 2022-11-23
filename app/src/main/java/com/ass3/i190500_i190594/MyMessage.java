package com.ass3.i190500_i190594;

public class MyMessage {

    String Message, SenderName, ReceiverName;
    String time1;


    public MyMessage(String message,String time1,String SendName){

        this.Message=message;
        this.time1=time1;
        this.SenderName=SendName;
    }
    public MyMessage(){}

    public String getMessage() {
        return Message;
    }
    public String getTime1(){

        return time1;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getSenderName() {
        return SenderName;
    }

    public void setSenderName(String senderName) {
        SenderName = senderName;
    }

    public String getReceiverName() {
        return ReceiverName;
    }

    public void setReceiverName(String receiverName) {
        ReceiverName = receiverName;
    }
}
