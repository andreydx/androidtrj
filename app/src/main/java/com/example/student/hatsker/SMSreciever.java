package com.example.student.hatsker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by student on 05.07.17.
 */

public class SMSreciever extends BroadcastReceiver
{
    final SmsManager sms = SmsManager.getDefault();
    @Override
    public void onReceive (Context context, Intent intent)
    {
        final Bundle bundle = intent.getExtras();

        Log.d("bubug", "ama one of them");

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();

                    Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message: " + message);


                    // Show alert
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, "senderNum: "+ senderNum + ", message: " + message, duration);
                    toast.show();
                    getContacts();
                }

                //Описываем метод:
            public void getContacts() {

                String phoneNumber = null;

                //Связываемся с контактными данными и берем с них значения id контакта, имени контакта и его номера:
                Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
                String _ID = ContactsContract.Contacts._ID;
                String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
                String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;

                Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
                String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;



                StringBuffer output = new StringBuffer();
                ContentResolver contentResolver = getContentResolver();
                Cursor cursor = contentResolver.query(CONTENT_URI, null,null, null, null);

                //Запускаем цикл обработчик для каждого контакта:
                if (cursor.getCount() > 0) {

                    //Если значение имени и номера контакта больше 0 (то есть они существуют) выбираем
                    //их значения в приложение привязываем с соответствующие поля "Имя" и "Номер":
                    while (cursor.moveToNext()) {
                        String contact_id = cursor.getString(cursor.getColumnIndex( _ID ));
                        String name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));
                        int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)));

                        //Получаем имя:
                        if (hasPhoneNumber > 0) {
                            output.append("\n Имя: " + name);
                            Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null,
                                    Phone_CONTACT_ID + " = ?", new String[] { contact_id }, null);

                            //и соответствующий ему номер:
                            while (phoneCursor.moveToNext()) {
                                phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                                output.append("\n Телефон: " + phoneNumber);
                            }
                        }
                        output.append("\n");
                    }
                } // end for loop
            } // bundle is null
        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);

        }
    }
}