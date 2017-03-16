package com.gary.search.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * Created by gaozhicheng on 2017/3/6.
 * github util (add commit push)
 */
public class GithubUtil {
    private static String git_add = " git add {fileName} ";
    private static String git_commit = " git commit -m {date} ";
    private static String git_push = " git push -u origin master ";
    private static Logger logger = LoggerFactory.getLogger(GithubUtil.class);

    public static void add_Commit_push(String date, String fileName) {
        try {
            Process addExec = Runtime.getRuntime().exec(git_add.replace("{fileName}", fileName));
            String addResult = GithubUtil.outputResult(addExec);
            logger.info("git add " + fileName + " result : " + addResult);
            Process commitExec = Runtime.getRuntime().exec(git_commit.replace("{date}", date));
            String commitResult = GithubUtil.outputResult(commitExec);
            logger.info("git commit -m " + date + " result : "+commitResult);
            Process pushExec = Runtime.getRuntime().exec(git_push);
            String pushResult = GithubUtil.outputResult(pushExec);
            logger.info("git push result : " + pushResult);
        } catch (IOException e) {
            logger.error("Github Util error", e);
        }
    }

    private static String outputResult(Process exec) throws IOException {
        InputStream inputStream = exec.getInputStream();
        byte[] b = new byte[1024];
        inputStream.read(b);
        String result = new String(b, Charset.forName("UTF-8"));
        return result;
    }
}
