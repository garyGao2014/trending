package com.gary.search.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by gaozhicheng on 2017/3/6.
 * gen {yyyy-MM-dd}.md file
 */
public class MdFile implements OutputFile {
    private Logger logger = LoggerFactory.getLogger(MdFile.class);
    public  volatile static String date;
    public  volatile static String fileName; //file name by day
    private String htmlStr;//stream to string result
    private String language ="\n####{language}\n"; //trending language
    private String formatMd = "* [{title}]({url}):{description}\n";

    public MdFile(String htmlStr, String language) {
        this.htmlStr = htmlStr;
        this.date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        this.fileName = date + ".md";
        this.language = this.language.replace("{language}", language);
    }

    public File genFile() {
        try {
            String dirPath = System.getProperty("user.dir");
            String absPath = dirPath + File.separator;
            File file = new File(absPath + fileName);
            if (!file.exists()) {// not exist
                file.createNewFile();
                filePrivilege(file);
                fileHeader(file.getName());
            }
            List<GitHubInfo> infos = findValidHtml();
            String validHtmlStr = transListToStr(infos);
            writeHtmlStrToFile(file.getName(), validHtmlStr);
        } catch (Exception e) {
            logger.error("Create File Error fileName :" + fileName + " htmlStr : " + htmlStr, e);
        }
        return null;
    }

    private void filePrivilege(File file) {
        file.setWritable(true, true);
        file.setReadable(true, false);
    }

    private String transListToStr(List<GitHubInfo> infos) {
        StringBuffer validObjStr = new StringBuffer();
        for (GitHubInfo info : infos) {
            String line = formatMd
                    .replace("{title}", info.getTitle())
                    .replace("{url}", info.getUrl())
                    .replace("{description}", info.getDesc());
            validObjStr.append(line);
        }
        return validObjStr.toString();
    }

    private void writeHtmlStrToFile(String file, String content) {
        FileWriter fileWriter = null;
        BufferedWriter bufferWritter = null;
        try {
            fileWriter = new FileWriter(file, true);
            bufferWritter = new BufferedWriter(fileWriter);
            bufferWritter.write(this.language);
            bufferWritter.write(content);
        } catch (IOException e) {
            logger.error("IO Error", e);
        } finally {
            try {
                bufferWritter.close();
                fileWriter.close();
            } catch (IOException e) {
                logger.error("IO Close Error", e);
            }

        }
    }

    private List<GitHubInfo> findValidHtml() {
        List<GitHubInfo> results = new ArrayList<GitHubInfo>();
        Document doc = Jsoup.parse(htmlStr);
        Elements lis = doc.select("ol.repo-list > li");
        for (Element li : lis) {
            Element ele = li.select("h3 > a ").first();
            String title = ele.text();
            String url = ele.attr("href");
            String owner = ele.select("span").text();
            String desc = li.select(".py-1 > p ").text();
            GitHubInfo info = new GitHubInfo(title, owner, desc, url);
            results.add(info);
        }
        return results;
    }

    private void fileHeader(String file) {
        FileWriter fileWriter = null;
        BufferedWriter bufferWritter = null;
        String title = "###" + date + " \n";
        try {
            fileWriter = new FileWriter(file, true);
            bufferWritter = new BufferedWriter(fileWriter);
            bufferWritter.write(title);
        } catch (IOException e) {
            logger.error("IO Error", e);
        } finally {
            try {
                bufferWritter.close();
                fileWriter.close();
            } catch (IOException e) {
                logger.error("IO Close Error", e);
            }
        }
    }

    class GitHubInfo {

        private String title;
        private String owner;
        private String desc;
        private String url = "https://github.com";

        public GitHubInfo(String title, String owner, String desc, String url) {
            this.title = title;
            this.owner = owner;
            this.desc = desc;
            this.url += url;
        }

        public String getTitle() {
            return title;
        }

        public String getOwner() {
            return owner;
        }

        public String getDesc() {
            return desc;
        }

        public String getUrl() {
            return url;
        }
    }

    public String getDate() {
        return date;
    }

    public String getFileName() {
        return fileName;
    }

}
