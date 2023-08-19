package itmo.progg.managers_client.description_client;

import itmo.progg.managers_client.CustomScanner;

public abstract class Execution<T> {
    protected CustomScanner customScanner = new CustomScanner(System.in);
    public abstract T build();
}
