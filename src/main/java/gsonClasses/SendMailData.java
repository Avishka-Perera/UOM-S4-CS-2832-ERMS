package gsonClasses;

import java.util.ArrayList;

public class SendMailData {
    private ArrayList<String> mails;

    public SendMailData(ArrayList<String> mails) {
        this.mails = mails;
    }

    public ArrayList<String> getMails() {
        return mails;
    }

    public void setMails(ArrayList<String> mails) {
        this.mails = mails;
    }

    @Override
    public String toString() {
        return "SendMailData{" +
                "mails=" + mails +
                '}';
    }
}
