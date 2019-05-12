/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramSocket;
import java.util.Observable;

/**
 *
 * @author Dorian
 */
public class Client extends Observable implements Runnable
{

    private static int _serverPort;
    private static boolean _running = true;
    private static DatagramSocket _ds;

    @Override
    public void run()
    {
        try
        {
            // On crée un nouveau socket
            _ds = new DatagramSocket();
            // On envoie un message au serveur sur le port d'écoute du serveur connu part tout le monde
            Utils.sendMessage(_ds, "CO", Utils.getServerListenedPort());
            try
            {
                // On récupère la réponse du serveur
                String reponseServeur = Utils.receiveMessage(_ds);
                // Le serveur répond avec le port sur lequel il faut désormais communiquer.
                System.out.println("Serveur : " + reponseServeur);
                // on récupère le port
                _serverPort = Integer.parseInt(reponseServeur);
            }
            catch (IOException exReception)
            {
                System.out.println("Client - Error occured while sending (run) : " + exReception);
            }
            _ds.close();
        }
        catch (IOException exEnvoi)
        {
            System.out.println("Client - Error occured while sending (run) : " + exEnvoi);
        }
    }

    public void sendMessage(String message)
    {
        try
        {
            Utils.sendMessage(_ds, message, _serverPort);
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

    public static void stopClient()
    {
        _running = false;
        closeSocket();
    }

    public static void closeSocket()
    {
        _ds.close();
    }
}
