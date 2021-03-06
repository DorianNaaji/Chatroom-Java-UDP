/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 *
 * @author Dorian
 */
public class Utils
{

    public static ArrayList<Integer> scan(int min, int max)
    {
        ArrayList<Integer> ports = new ArrayList<Integer>();
        for (Integer portNumber = min; portNumber <= max; portNumber++)
        {
            try
            {
                DatagramSocket ds = new DatagramSocket(portNumber);
                ports.add(portNumber);
                ds.close();
            }
            catch (SocketException ex)
            {
                System.out.println("Erreur - Le port : " + portNumber + " est déjà utilisé.");
            }
        }
        return ports;
    }

    /**
     * Permet d'envoyer un message
     *
     * @param dsSend Le socket utilisé
     * @param message le message à envoyer
     * @param serverPort le port du serveur
     * @throws UnknownHostException
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    public static void sendMessage(DatagramSocket dsSend, String message, int serverPort) throws UnknownHostException, UnsupportedEncodingException, IOException
    {
        InetAddress ia = InetAddress.getByName("localhost");
        DatagramPacket dp = new DatagramPacket(message.getBytes("ascii"), message.length(), ia, serverPort);
        dsSend.send(dp);
    }

    /**
     * Permet de recevoir un message
     *
     * @param dsReception le socket à utiliser pour la réception
     * @return
     * @throws IOException
     */
    public static String receiveMessage(DatagramSocket dsReception) throws IOException
    {
        byte[] buffer = new byte[500];
        DatagramPacket serverResponse = new DatagramPacket(buffer, buffer.length);
        dsReception.receive(serverResponse);
        return new String(serverResponse.getData(), 0, serverResponse.getLength());
    }

    public static int getServerListenedPort()
    {
        return 5000;
    }
}
