package com.finance.dao;

import java.util.List;


public interface BaseDao<T, K> {
    
    public T get(K id);
    
    public Boolean save(T object);
    
    public List<T> findAll();
    
    public Boolean update(T object);
    
    public Boolean delete(K id);

}
