package pl.solix.model;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class ToScreenConnection {
    public final static byte [] MI = {(byte)0xAA, (byte)0x14, (byte)0xFE, (byte)0x01,
            (byte)0x60, (byte)0x73};
    public final static byte [] DVI = {(byte)0xAA, (byte)0x14, (byte)0xFE, (byte)0x01,
            (byte)0x18, (byte)0x2B};
    public final static byte [] HDMI1 = {(byte)0xAA, (byte)0x14, (byte)0xFE, (byte)0x01,
            (byte)0x21, (byte)0x34};
    public final static byte [] HDMI2 = {(byte)0xAA, (byte)0x14, (byte)0xFE, (byte)0x01,
            (byte)0x23, (byte)0x36};
    private Socket conn;
    private final static String filePath  = System.getProperty("user.dir");
    private static String ipAddress;
    private Scanner scanner;
    private static BufferedWriter bufferedWriter,bufferedWriterIp;
    private static SimpleDateFormat timeNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static Date nowDate = new Date();
    private static Socket s;
    private static DataOutputStream dataOutputStream;


    public ToScreenConnection(){
        File ipFile = new File(filePath + "/ip.txt");
        File logFile = new File(filePath + "/log.txt");


        boolean ipFileExists = ipFile.exists();
        boolean logFileExists = logFile.exists();
        if (!ipFileExists) {
            try {
                ipFile.createNewFile();
                bufferedWriterIp = new BufferedWriter(new FileWriter(filePath + "/ip.txt"));
                bufferedWriterIp.write("0.0.0.0");
                bufferedWriterIp.flush();
                bufferedWriterIp.close();

            } catch (IOException e) {
                System.out.println("File create Error " + e);
            }
        }
        if (!logFileExists) {
            try {
                logFile.createNewFile();

            } catch (IOException e) {
                System.out.println("File create Error " + e);
            }
        }

        try {
            scanner = new Scanner(ipFile);
            bufferedWriter = new BufferedWriter(new FileWriter(filePath + "/log.txt", true));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

         ipAddress = scanner.nextLine();

        scanner.close();

    }

    public static void sendUdpCommand(byte[] command){
        try {
            s = new Socket(ipAddress,1515);
            dataOutputStream = new DataOutputStream(s.getOutputStream());
            dataOutputStream.write(command);
            dataOutputStream.flush();
            dataOutputStream.close();
            bufferedWriter.write("Polaczono z monitorem -> zmiana zrodla" +  " - " + timeNow.format(nowDate));
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e1) {
            try {
                bufferedWriter.write("Blad polaczenia z monitorem ->" + e1.getMessage() + " - " + timeNow.format(nowDate));
                bufferedWriter.newLine();
                bufferedWriter.flush();
            } catch (IOException e2) {
                e2.printStackTrace();
            }

        }


    }

    public static void coundDown(){
        try {
            bufferedWriter.write("Rozpoczeto odliczanie do automatycznej zmiany zrodla " + " - " + timeNow.format(nowDate));
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        sendUdpCommand(MI);
                        try {
                            bufferedWriter.write("Wywolano automatyczną zmiane zrodla " + " - " + timeNow.format(nowDate));
                            bufferedWriter.newLine();
                            bufferedWriter.flush();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                },
                1200000
        );
    }

    public static void close(){
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
