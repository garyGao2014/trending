package com.gary.search.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * http utils
 */
public class HttpUtil {
    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    /**
     * Open A Connection
     *
     * @param url
     * @return
     */
    private static HttpURLConnection createConnect(String url) {
        try {
            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            return conn;
        } catch (Exception e) {
            logger.error("Open A Http Connection Error", e);
        }
        return null;
    }
    /**
     * Set Common Request Property Infomation
     *
     * @param conn
     * @return
     */
    private static HttpURLConnection setCommonRequestProperty(HttpURLConnection conn) {
        try {
            if (conn != null) {
                conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) ...");
                conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
            } else {
                throw new NullPointerException("HttpURLConnection is Null");
            }
        } catch (Exception e) {
            logger.error(" http setCommonRequestProperty Error ", e);
        }
        return conn;
    }
    /**
     * Http Get Method
     *
     * @return
     */
    public static String httpGet(String requestUrl) {
        try {
            HttpURLConnection conn = HttpUtil.createConnect(requestUrl);
            conn = HttpUtil.setCommonRequestProperty(conn);
            conn.setRequestMethod("GET");
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                String responseBody = readResponseBody(conn.getInputStream());
                return responseBody;
            }
        } catch (Exception e) {
            logger.error(" HttpGet Error  ", e);
        }
        return null;
    }
    /**
     * Stream To String
     * @param inputStream
     * @return
     * @throws IOException
     */
    private static String readResponseBody(InputStream inputStream) throws IOException {
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(inputStream,"utf-8"));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            logger.info("##输出流文本##"+response.toString());
            return response.toString();
        } catch (Exception e) {
            logger.error(" Body Stream To String Error", e);
        }
        return null ;
    }
}