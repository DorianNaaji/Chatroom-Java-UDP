/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author Dorian
 */
public class Communication extends AServer implements Runnable
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
            this._message = "Server : Connexion established on PORT " + portPourContinuerEchange + " with Client " + _portDestinataire;
            this.somethingChanged();
            try
            {
                this._message = Utils.receiveMessage(ds);
                this.somethingChanged();

                // ON POURRAIT CONTINUER ICI LES ECHANGES
                //Utils.sendMessage(ds, "Serveur : Ok on est sur la même longueur d'onde", _portDestinataire);
                //System.out.println(Utils.receiveMessage(ds));
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

}
