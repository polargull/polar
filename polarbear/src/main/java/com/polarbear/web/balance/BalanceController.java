package com.polarbear.web.balance;

import static com.polarbear.util.Constants.ResultState.SUCCESS;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.polarbear.dao.DaoException;
import com.polarbear.service.balance.BalanceService;
import com.polarbear.util.JsonResult;

@Controller
@RequestMapping("/balance")
public class BalanceController {
    private Log log = LogFactory.getLog(BalanceController.class);
    @Autowired
    BalanceService balanceSvc;

    @RequestMapping(value = { "/balance.json" }, method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public Object balance(@RequestParam("pids") Long[] pids, @RequestParam("nums") Integer[] nums) throws DaoException {
        return new JsonResult(SUCCESS).put(balanceSvc.balance(pids, nums));
    }

}