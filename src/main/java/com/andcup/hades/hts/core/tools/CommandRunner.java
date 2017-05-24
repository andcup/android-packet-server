package com.andcup.hades.hts.core.tools;

import com.andcup.hades.hts.core.model.Message;
import com.andcup.hades.hts.core.model.State;
import com.andcup.hades.hts.core.model.Task;
import com.thoughtworks.studios.javaexec.CommandExecutor;
import com.thoughtworks.studios.javaexec.CommandExecutorException;
import com.thoughtworks.studios.javaexec.LineHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Created by Amos
 * Date : 2017/5/24 18:33.
 * Description:
 */
public class CommandRunner {

    Logger logger;
    int outLineCount = 0;
    Message<Task> message;
    String tag ;

    public CommandRunner(String tag, Message<Task> message){
        this.message = message;
        logger = LoggerFactory.getLogger(tag);
    }

    public State exec(String command) {
        CommandExecutor executor = new CommandExecutor(Arrays.asList(command.split(" ")));
        try {
            executor.run(new LineHandler() {
                public void handleLine(String line) {
                    if (outLineCount++ >= 20) {
                        return;
                    }
                    logger.info(message.getName()  + " line : " + line + " out line count = " + outLineCount);
                }
            });
            return State.SUCCESS;
        } catch (CommandExecutorException e) {
            logger.info(message.getName()  + " error : " + e.getCause().getMessage());
            return State.FAILED;
        }
    }
}
