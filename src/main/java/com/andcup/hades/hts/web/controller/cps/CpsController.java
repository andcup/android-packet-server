package com.andcup.hades.hts.web.controller.cps;

import com.andcup.hades.hts.web.controller.base.BaseController;
import com.andcup.hades.hts.web.controller.cps.model.CpsTaskEntity;
import com.andcup.hades.hts.web.controller.cps.model.ResponseEntity;
import com.andcup.hades.hts.core.MqBroker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Amos
 * Date : 2017/4/13 17:57.
 * Description:
 */

@Controller
@RequestMapping("/api")
public class CpsController extends BaseController {

    /**
     * 日志框架.
     */
    Logger logger = LoggerFactory.getLogger(CpsController.class.getName());

    @ResponseBody
    @RequestMapping(value = {"/pack"}, produces = {"text/html;charset=UTF-8;", "application/json;"}, method = RequestMethod.POST)
    public String start(@RequestBody CpsTaskEntity body) {

        try {
            /**检查参数.*/
            check(body);

            //产生消息.
            MqBroker.getInstance().produce(new CpsMqFactory(body));

            return new ResponseEntity(ResponseEntity.SUCCESS, "commit task success.", getIp()).toString();
        } catch (Exception e) {
            String paramError = StringUtils.isEmpty(e.getMessage()) ? "param error." : e.getMessage();
            return new ResponseEntity(ResponseEntity.ERR_PARAM, paramError, getIp()).toString();
        }
    }

    private void check(CpsTaskEntity entity) throws RuntimeException {
        if (entity.channelPackRemoteDir.length() <= 0) {
            throw new RuntimeException("channelPackRemoteDir is null.");
        }
        if (entity.originPackLocalPath.length() <= 0) {
            throw new RuntimeException("originPackLocalPath is null.");
        }
        if (entity.feedbackApiAddress.length() <= 0) {
            throw new RuntimeException("feedbackApiAddress is null.");
        }
        if (entity.channels.size() <= 0) {
            throw new RuntimeException("channel size is 0.");
        }
    }

}
