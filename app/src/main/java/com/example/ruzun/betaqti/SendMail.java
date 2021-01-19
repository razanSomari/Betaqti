package com.example.ruzun.betaqti;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaCas;
import android.os.AsyncTask;
import android.se.omapi.Session;
import android.view.View;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail extends AsyncTask<Void, Void, Void> {

    private Context context;
    private javax.mail.Session session;


    private String email;
    private String subject;
    private String message;

    private ProgressDialog progressDialog;

    public SendMail(Context context, String email, String subject, String message){
        this.context = context;
        this.email = email;
        this.subject = subject;
        this.message = message;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        progressDialog = progressDialog.show(context, "sending message", "please wait . . ", false, false);
    }


    @Override
    protected void onPostExecute(Void aVoid){
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
        Toast.makeText(context, "Message sent", Toast.LENGTH_LONG).show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        session = javax.mail.Session.getDefaultInstance(props, new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(Config.Email, Config.PASSWORD);
            }
        });
        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("emailsendapptest@gmail.com"));
            message.setSubject(subject);
            message.setContent(message, "text/html; charset=utf-8");
            Transport.send(message);
        }
        catch(MessagingException e){
            e.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
