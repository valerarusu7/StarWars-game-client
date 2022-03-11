package Model.Mediator;

import Model.Domain.Packages.Package;
import com.google.gson.Gson;

import java.io.PrintWriter;

public class PackageWriter implements Runnable {

    private PrintWriter out;
    private Package parcel;
    private Gson json;

    public PackageWriter(PrintWriter out, Package parcel) {
        this.out = out;
        this.parcel = parcel;
        this.json = new Gson();
    }

    @Override
    public void run() {
        out.println(json.toJson(parcel));
        out.flush();
    }
}