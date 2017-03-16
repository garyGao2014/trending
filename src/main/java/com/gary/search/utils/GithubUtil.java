package com.gary.search.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by gaozhicheng on 2017/3/6.
 * github util (add commit push)
 */
public class GithubUtil {
    private static volatile int push_times = 0 ;
    private static String git_add = " git add {fileName} ";
    private static String git_commit = " git commit -m {date} ";
    private static String git_firstPush = " git push -u origin master ";
    private static String git_push = " git push origin master ";
    private static Logger logger = LoggerFactory.getLogger(GithubUtil.class);

    public static void add_Commit_push(String date, String fileName) {
        try {
            Process addExec = Runtime.getRuntime().exec(git_add.replace("{fileName}", fileName));
            String addResult = GithubUtil.outputResult(addExec);
            logger.info("git add " + fileName + " result : " + addResult);
            Process commitExec = Runtime.getRuntime().exec(git_commit.replace("{date}", date));
            String commitResult = GithubUtil.outputResult(commitExec);
            logger.info("git commit -m " + date + " result : " + commitResult);
            Process pushExec = null ;
            logger.info("git push times "+String.valueOf(push_times));
            if (push_times>0){
                pushExec = Runtime.getRuntime().exec(git_push);
            }else {
                pushExec = Runtime.getRuntime().exec(git_firstPush);
            }
            String pushResult = GithubUtil.outputResult(pushExec);
            logger.info("git push result : " + pushResult);
            push_times+=1;
        } catch (IOException e) {
            logger.error("Github Util error", e);
        }
    }

    private static String outputResult(Process exec) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(exec.getInputStream()));
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        String result = sb.toString();
        return result ;
    }
}
