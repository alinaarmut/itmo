package itmo.ser.commands.managers;

import itmo.common.description.managers.Response;

import java.io.ObjectOutputStream;

public record ConnectionManagerPool(Response response, ObjectOutputStream objectOutputStream){};
