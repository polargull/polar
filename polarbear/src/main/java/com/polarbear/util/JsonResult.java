package com.polarbear.util;

import java.util.HashMap;
import java.util.Map;

import com.polarbear.util.Constants.ResultState;

public class JsonResult {
    /**
     * 消息头
     */
    private MsgHead head = new MsgHead();

    /**
     * 消息体
     */
    private Map<String, Object> body = new HashMap<String, Object>();

    public JsonResult() {
    }

    public JsonResult(ResultState state) {
        this.head = new MsgHead();
        setEcode(state.ecode());
        setEmsg(state.emsg());
    }

    public void setEcode(String ecode) {
        this.head.setEcode(ecode);
    }

    public void setEmsg(String emsg) {
        this.head.setEmsg(emsg);
    }

    public void setErr(String ecode, String emsg) {
        this.head.setEcode(ecode);
        this.head.setEmsg(emsg);
    }

    public MsgHead getHead() {
        return head;
    }

    public void setHead(MsgHead head) {
        this.head = head;
    }

    public JsonResult put(String key, Object value) {
        this.body.put(key.toLowerCase(), value);
        return this;
    }

    public Map<String, Object> getBody() {
        return body;
    }

    public void setBody(Map<String, Object> body) {
        this.body = body;
    }

    public static class MsgHead {
        private String ecode = ResultState.SUCCESS.ecode();
        private String emsg = ResultState.SUCCESS.emsg();

        public MsgHead() {
        }

        public MsgHead(ResultState state) {
            this.ecode = state.ecode();
            this.emsg = state.emsg();
        }

        public String getEcode() {
            return ecode;
        }

        public void setEcode(String ecode) {
            this.ecode = ecode;
        }

        public String getEmsg() {
            return emsg;
        }

        public void setEmsg(String emsg) {
            this.emsg = emsg;
        }

    }
}
