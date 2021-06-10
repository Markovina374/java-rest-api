package com.cvtmarkov.javarestapi.repository;

import java.util.List;

/**
 * Параметризованный интерфейс для репозиториев с CRUD методами
 * @param <T> - "дженерик"
 */
public interface CRUDRepository<T> {
    /**
     * Метод возвращает список всех объектов
     * @return
     */
    List<T> findAll();

    /**
     * Метод ищет объект по идентификационному номеру
     * @param id - идентификационный номер
     * @return - найденный объект
     */
    T findById(long id);

    /**
     * Метод удаления объекта
     * @param id - идентификационный номер
     * @return - сообщение
     */
    String deleteById(long id);

    /**
     * Метод сохранения объекта
     * @param object - новый объект
     * @return - вновь сохраненный объект
     */
    T save(T object);

    /**
     * Метод обновляет объект
     * @param id - идентификационный номер объекта который хотим обновить
     * @param object - объект на который меняем
     * @return - обновленный объект
     */
    T update(long id, T object);

}
