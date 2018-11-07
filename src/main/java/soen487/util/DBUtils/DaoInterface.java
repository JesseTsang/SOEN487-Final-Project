/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soen487.util.DBUtils;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ethanshen
 * @param <T>
 */
public interface DaoInterface<T> {
    
    public List<T> fetchAll() throws SQLException;
    public List<T> fetchAll(long upper, long lower) throws SQLException;
    public T fetch(int id) throws SQLException;
    public void insert(T t) throws SQLException;
    public void update(T t) throws SQLException;
    public void delete(T t) throws SQLException;
    
}
