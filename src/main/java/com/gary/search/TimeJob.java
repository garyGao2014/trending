package com.gary.search;

import com.gary.search.utils.GithubUtil;
import com.gary.search.utils.HttpUtil;
import com.gary.search.utils.MdFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gaozhicheng on 2017/3/7.
 */
public class TimeJob implements Runnable {
    private final static String requestGithub = "https://github.com/trending/{language}" ;
    private static Logger logger  = LoggerFactory.getLogger(TimeJob.class);
    private String [] languages ;

    public TimeJob(String[] languages) {
        this.languages = languages;
    }

    @Override
    public void run() {
        try {
            logger.info("Start exec Job !!");
            for (String language : languages){
                logger.info("get language "+ language);
                String html = HttpUtil.httpGet(requestGithub.replace("{language}", language));
                MdFile mdFile = new MdFile(html,language);
                mdFile.genFile();
            }
            logger.info("exec Github !");
            GithubUtil.add_Commit_push(MdFile.date,MdFile.fileName);
            logger.info("Finish Job !!");
        }catch (Exception e){
            logger.error("Time Job exception",e);
        }
    }
}
