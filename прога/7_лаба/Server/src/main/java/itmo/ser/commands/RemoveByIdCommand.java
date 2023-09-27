package itmo.ser.commands;
/**
 * Класс команды remove_by_id - удаляет элемент из коллекции Movie по его id
 */
import itmo.common.description.err.NoSuchIDExp;
import itmo.common.description.managers.Request;
import itmo.common.description.managers.Response;
import itmo.common.description.managers.ResponseStatus;
import itmo.ser.commands.dateBase.DataBaseHandler;
import itmo.ser.commands.managers.CollectionManager;


public class RemoveByIdCommand extends Command {
    private final CollectionManager collectionManager;
    public RemoveByIdCommand(CollectionManager collectionManager) {
        super("remove_by_id", "remove_by_id id: удалить элемент из коллекции по его id");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request){
        if (CollectionManager.movies.isEmpty()) {
            return new Response(ResponseStatus.ERROR, "Нечего удалять. Коллекция пустая");
        }
        try {
            Integer id = Integer.parseInt(request.getArgs());
            if (!collectionManager.checkExist(id)) throw new NoSuchIDExp();
            if (CollectionManager.checkUsersId(id, request.getUser().getUsername())) {
                if (DataBaseHandler.removeById(id, request.getUser())){
                    collectionManager.removeById(id);
                    return new Response(ResponseStatus.OK, "Элемент удален");
                }
                return new Response(ResponseStatus.ERROR, "Элемент не удален");
            }
            return new Response(ResponseStatus.ERROR, "У вас нет прав на этот объект");
        } catch (NumberFormatException e) {
            return new Response(ResponseStatus.WRONG_ARGUMENTS,"Команда не выполнена. Вы ввели некорректный аргумент");
        } catch (NoSuchIDExp e) {
            return new Response(ResponseStatus.ERROR,"Элемента с таким id нет в коллекции");
        }
    }
}
