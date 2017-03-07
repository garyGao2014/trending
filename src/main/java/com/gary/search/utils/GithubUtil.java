package com.gary.search.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

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
            Runtime.getRuntime().exec(git_add.replace("{fileName}", fileName));
            logger.info("git add " + fileName + " success ");
            Runtime.getRuntime().exec(git_commit.replace("{date}", date));
            logger.info("git commit -m " + date + " success ");
            Runtime.getRuntime().exec(git_push);
            logger.info("git push success ");
        } catch (IOException e) {
            logger.error("Github Util error", e);
        }
    }

    public static void main(String[] args) {
        Process dir = null;
        try {
            String [] cmd = new String[]{"cmd.exe","/c","dir"};
            dir = Runtime.getRuntime().exec(cmd);
            byte[] b = new byte[1024];
            dir.getInputStream().read(b);
            String str = new String(b,"utf-8");
            System.out.println(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
