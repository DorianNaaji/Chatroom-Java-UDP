/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import model.Client;
import model.Communication;
import model.Server;
import view.JDisplay;
import view.MessageObservable;

/**
 *
 * @author Dorian
 */
public class Controller implements Observer
{

    private JDisplay _form;
    private MessageObservable _messages;
    private int i = 0;
    private ArrayList<Client> _clients;

    public void start()
    {
        this._clients = new ArrayList<Client>();
        this._clients.add(new Client());
        _messages = new MessageObservable();
        _messages.addObserver(this);
        _form = new JDisplay(_messages);
        this._form.setVisible(true);

        Server s = new Server();
        s.addObserver(this);
        new Thread(s).start();
    }

    // fonction d'update à chaque fois qu'un message est émis
    @Override
    public void update(Observable o, Object arg)
    {
        if (this._clients.get(i).getEstablishedConn())
        {
            this.i++;
            this._clients.add(new Client());
        }
        // if the Server has changed
        if (o instanceof Server)
        {
            Server s = (Server) o;
            if (s.isThereNewCom())
            {
                s.getCommunication().addObserver(this);
            }
            else
            {
                this._form.appendMessageFromController(s.getMessage());
            }
        }
        // if the communication has changed
        else if (o instanceof Communication)
        {
            Communication c = (Communication) o;
            this._form.appendMessageFromController(c.getMessage());
        }
        // if the client has changed
        else if (o instanceof MessageObservable)
        {
            this._clients.get(i).sendMessage(this._messages.getMessage());

        }

    }

}
