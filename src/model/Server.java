/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dorian
 */
public class Server extends AServer implements Runnable
{

    Communication _com;
    private boolean _newCom = false;

    @Override
    public void run()
    {
        try
        {
            DatagramSocket ds = new DatagramSocket(Utils.getServerListenedPort());
            // Boucle infinie pour que le serveur tourne en permanence, comme le ferait un serveur dans la réalité
            while (_running)
            {
                this._message = "--------------------------------------------------"
                        + "------------------------------"
                        + "------------------ \n Establishing new connexion with a new client | Please give the correct signal";
                this.somethingChanged();
                byte[] buffer = new byte[500];
                DatagramPacket dr = new DatagramPacket(buffer, buffer.length);
                ds.receive(dr);
                String reception = new String(dr.getData(), 0, dr.getLength());

                // On stocke l'ip du client
                InetAddress ipClient = dr.getAddress();
                // On stocke le port du client
                int portClient = dr.getPort();
                // Si le message envoyé par le client est bien "CO" qui correspond à une demande de connexion
                if ("CO".equals(reception))
                {
                    // On crée un nouveau thread de communication, avec le port d'écoute du client et l'ip du client (qui en réalité est localhost pour notre cas)
                    this._com = new Communication(portClient, ipClient);
                    this._newCom = true;
                    this.somethingChanged();
                    new Thread(this._com).run();
                    this._newCom = false;
                }
                else
                {
                    Utils.sendMessage(ds, "Demande de connexion non prise en compte. Mauvaise authentification", portClient);
                    this._message = "Server : Unknow message from client. Ending transmissions.";
                    this.somethingChanged();
                }
            }
            ds.close();
        }
        catch (Exception ex)
        {
            System.err.println("Exception - ERROR WHILE RECEIVING " + ex);
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Communication getCommunication()
    {
        return this._com;
    }

    public boolean isThereNewCom()
    {
        return this._newCom;
    }
}
