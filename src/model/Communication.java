/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.Utils;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Observable;

/**
 *
 * @author Dorian
 */
public class Communication extends Observable implements Runnable
{

    private int _portDestinataire;
    private InetAddress _ipDestinataire;

    public Communication(int port, InetAddress ipDestinataire)
    {
        this._portDestinataire = port;
        this._ipDestinataire = ipDestinataire;
    }
    //datagram packet du client
    //répondre au client dans la com
    //Il faut stocker ici le datagramPacket envoyé par le client
    // Une fois qu'il est stocké, on peut récupérer le port et l'ip du client
    // Quand le serveur va reparler avec le client, il va parler sur un nouveau port
    // décidé par le serveur. La discussion doit alors continuer sur ce nouveau port.

    @Override
    public void run()
    {
        try
        {
            DatagramSocket ds = new DatagramSocket();
            String portPourContinuerEchange = "" + ds.getLocalPort();
            Utils.sendMessage(ds, portPourContinuerEchange, _portDestinataire);
            try
            {
                String reception = Utils.receiveMessage(ds);
            }
            catch (Exception exReception)
            {
                System.out.println("COMMUNICATION - ERROR WHILE RECEIVING " + exReception);
            }
            ds.close();
        }
        catch (Exception exEnvoi)
        {
            System.out.println("COMMUNICATION - ERROR WHILE SENDING " + exEnvoi);
        }
    }

    private void manageNewCommunication()
    {

    }

}
