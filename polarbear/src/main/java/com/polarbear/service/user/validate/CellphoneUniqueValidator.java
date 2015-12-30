package com.polarbear.service.user.validate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polarbear.ValidateException;
import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.User;

@Service
public class CellphoneUniqueValidator {
    @Autowired
    BaseDao<User> userDao;

    public void validate(long cellphone) throws ValidateException, DaoException {
        List<User> lst = userDao.findByNamedQuery("queryUname", cellphone);
        if (!lst.isEmpty())
            throw new ValidateException(ValidateException.UNAME_UNIQUE_ERR);
    }
}
