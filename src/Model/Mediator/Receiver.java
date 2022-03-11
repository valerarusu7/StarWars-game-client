package Model.Mediator;

import Model.Domain.Packages.InGamePackage;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;

public class Receiver implements Runnable {

    private RemoteClient client;
    private BufferedReader in;
    private Gson json;

    public Receiver(BufferedReader in, RemoteClient client) {
        this.in = in;
        this.client = client;
        json = new Gson();
    }

    @Override
    public void run() {
        while (client.isInGame())
            try {
                if (in.ready()) {
                    String msg = in.readLine();
                    InGamePackage parcel = json.fromJson(msg, InGamePackage.class);
                    client.receivedPackage(parcel);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}