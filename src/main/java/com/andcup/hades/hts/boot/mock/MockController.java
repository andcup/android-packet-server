package com.andcup.hades.hts.boot.mock;

import com.andcup.hades.hts.controller.cps.CpsMqFactory;
import com.andcup.hades.hts.controller.cps.model.CpsTaskEntity;
import com.andcup.hades.hts.controller.cps.model.ResponseEntity;
import com.andcup.hades.hts.core.MqBroker;
import com.andcup.hades.hts.core.model.Message;
import com.andcup.hades.hts.core.model.MqMessage;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by Amos
 * Date : 2017/5/5 20:36.
 * Description:
 */


@RequestMapping(name = "/api/pack")
public class MockController extends Controller<CpsTaskEntity> {

    protected ResponseEntity onHandle(CpsTaskEntity body) {
        try{
            /**检查参数.*/
            check(body);
            //产生消息.
            MqBroker.getInstance().produce(new CpsMqFactory(body));

            return new ResponseEntity(ResponseEntity.SUCCESS, "commit task success.", getIp());
        }catch (Exception e){
            String paramError = StringUtils.isEmpty(e.getMessage()) ? "param error." : e.getMessage();
            return new ResponseEntity(ResponseEntity.ERR_PARAM, paramError, getIp());
        }
    }

    private void check(CpsTaskEntity entity) throws RuntimeException{
        if(entity.channelPackRemoteDir.length()<= 0){
            throw new RuntimeException("channelPackRemoteDir is null.");
        }
        if(entity.originPackLocalPath.length() <= 0){
            throw new RuntimeException("originPackLocalPath is null.");
        }
        if(entity.feedbackApiAddress.length() <= 0){
            throw new RuntimeException("feedbackApiAddress is null.");
        }
        if(entity.channels.size() <= 0){
            throw new RuntimeException("channel size is 0.");
        }
    }

    protected Class<CpsTaskEntity> getModel() {
        return CpsTaskEntity.class;
    }
}
