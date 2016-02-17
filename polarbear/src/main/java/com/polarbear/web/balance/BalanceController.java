package com.polarbear.web.balance;

import static com.polarbear.util.Constants.ResultState.SUCCESS;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.polarbear.ValidateException;
import com.polarbear.dao.DaoException;
import com.polarbear.util.JsonResult;

@Controller
@RequestMapping("/balance")
public class BalanceController {
    private Log log = LogFactory.getLog(BalanceController.class);

    @RequestMapping(value = { "/balance.json" }, method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public Object getProduct(@RequestParam("pids") String[] pids) throws ValidateException, DaoException {
        return new JsonResult(SUCCESS);
    }

}