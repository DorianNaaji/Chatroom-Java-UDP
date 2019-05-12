/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.Observable;
import java.util.Observer;
import model.Client;
import model.Server;
import view.JDisplay;
import view.MessageObservable;

/**
 *
 * @author Dorian
 */
public class Controller implements Observer
{

    JDisplay _form;
    MessageObservable _messages;

    public void start()
    {
        _messages = new MessageObservable();
        _messages.addObserver(this);
        _form = new JDisplay(_messages);
        this._form.setVisible(true);

        Server s = new Server();
        s.addObserver(this);
        new Thread(s).start();

        Client c = new Client();
        c.addObserver(this);
        new Thread(c).start();
    }

    @Override
    public void update(Observable o, Object arg)
    {
        // if the Server has changed
        if (o instanceof Server)
        {
            this._form.appendMessageFromController(" --- Établissement de la connexion ---");
            Server s = (Server) o;
            this._form.appendMessageFromController(s.getMessage());
        }
        // if the client has changed
        else
        {

        }

    }

}
