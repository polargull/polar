package com.polarbear.service.shopcart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.polarbear.ValidateException;
import com.polarbear.dao.DaoException;

@Service
public class ShopcartService {
    @Autowired
    ModifyShopcartService modifyShopcartService;

    @Transactional
    public int addProductToShopcart(long pid) throws DaoException, ValidateException {
        return modifyShopcartService.addShopcart(pid);
    }

}