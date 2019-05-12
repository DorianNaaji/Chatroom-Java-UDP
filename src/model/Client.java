/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Observable;

/**
 *
 * @author Dorian
 */
public class Client extends Observable
{

    private String _name;
    private int _serverPort;
    private DatagramSocket _ds;
    private boolean _establishedConnexion = false;
    private String _message;

    public boolean getEstablishedConn()
    {
        return this._establishedConnexion;
    }

    public Client()
    {
        try
        {
            _ds = new DatagramSocket();
            this._name = "Client " + this._ds.getLocalPort();
        }
        catch (SocketException ex)
        {
            System.out.println(ex);
        }
    }

    public void sendMessage(String message)
    {
        try
        {

            // le client envoie au serveur sur le port connu
            Utils.sendMessage(_ds, message, Utils.getServerListenedPort());

            try
            {
                // le serveur lui répond (accusé de réception) par un numéro de port si le message était bon
                this._serverPort = Integer.parseInt(Utils.receiveMessage(_ds));
                // si le try passe, alors la connexion est établie
                this._establishedConnexion = true;
                // on fait répondre le client
                Utils.sendMessage(_ds, this._name + " : Understood. I'll use port " + _serverPort, _serverPort);

                // ON POURRAIT CONTINUER ICI LES ECHANGES
                //System.out.println(Utils.receiveMessage(_ds));
                //Utils.sendMessage(_ds, "Client : Oui j'espère bien", _serverPort);
                // on pourrait alors continuer le dialogue entre le client et le serveur ici dans une autre fenêtre par exemple
            }
            catch (NumberFormatException nfe)
            {
                // Sinon, c'est que la conenxion est refusée
                System.out.println("Connexion refusée !!!");
            }

        }
        catch (UnsupportedEncodingException ex)
        {
            System.out.println("Error while sending - " + ex);
        }
        catch (IOException ex)
        {
            System.out.println("IO Error - " + ex);
        }
    }

    public String getName()
    {
        return this._name;
    }

}
