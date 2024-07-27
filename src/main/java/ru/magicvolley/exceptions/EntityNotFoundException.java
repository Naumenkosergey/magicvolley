package ru.magicvolley.exceptions;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotFoundException(Class<?> entityclass, Object id) {

        super(String.format("Не найдена запись с идентификаторм %s для сущности %s", id, entityclass.getName()));
    }

}
