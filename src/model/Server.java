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
public class Server extends Observable implements Runnable
{

    private boolean _running = true;
    private String _message = "";
    Communication _com;

    public void stopServer()
    {
        this._running = false;
    }

    private void somethingChanged()
    {
        this.setChanged();
        this.notifyObservers();
    }

    public String getMessage()
    {
        return this._message;
    }

    @Override
    public void run()
    {
        try
        {

            // Boucle infinie pour que le serveur tourne en permanence, comme le ferait un serveur dans la réalité
            while (_running)
            {
                DatagramSocket ds = new DatagramSocket(Utils.getServerListenedPort());
                byte[] buffer = new byte[500];
                DatagramPacket dr = new DatagramPacket(buffer, buffer.length);
                ds.receive(dr);
                String reception = new String(dr.getData(), 0, dr.getLength());
                this._message = "Client : " + reception;
                this.somethingChanged();

                // On stocke l'ip du client
                InetAddress ipClient = dr.getAddress();
                // On stocke le port du client
                int portClient = dr.getPort();
                // Si le message envoyé par le client est bien "CO" qui correspond à une demande de connexion
                if ("CO".equals(reception))
                {
                    // On crée un nouveau thread de communication, avec le port d'écoute du client et l'ip du client (qui en réalité est localhost pour notre cas)
                    this._com = new Communication(portClient, ipClient);
                    this._com.run();
                    this.somethingChanged();
                }
                else
                {
                    this._message = "Server : Unknow message from client. Ending transmissions.";
                }
                ds.close();
            }
        }
        catch (Exception ex)
        {
            System.err.println("Exception - ERROR WHILE RECEIVING " + ex);
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
