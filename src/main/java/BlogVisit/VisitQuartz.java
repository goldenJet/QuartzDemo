package BlogVisit;

import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by jet.chen on 2017/3/21.
 */
public class VisitQuartz implements Job{
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        visit();
    }

    private void visit() {
        String url1 = "http://www.jetchen.cn/springboot-staticfiles/";
        String url2 = "http://www.jetchen.cn/bugfixed-unsupportedoperationexception/";
        String url3 = "http://www.jetchen.cn/bugfixed-aessecurerandom/";
        String url4 = "http://www.jetchen.cn/bugfixed-filepath/";
        List<String> urlList = Arrays.asList(url1, url2, url3, url4);

        List<String> stringList = doGet(urlList);

        // 打印输出到文件
        FileWriter fw = null;
        try {
            //如果文件存在，则追加内容；如果文件不存在，则创建文件
            File f = new File("D:\\myPlace\\logs\\blog\\visit.txt");
            fw = new FileWriter(f, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);
        pw.println(stringList.toString());
        pw.flush();

        System.out.println(stringList.toString());
    }

    private List<String> doGet(List<String> urlList) {

        HttpClient httpClient = null;
        HttpGet httpGet = null;
        JSONObject jsonObject = null;
        List<String> responseList = new ArrayList<String>();
        String format = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(new Date());
        responseList.add(format);

        for (String url: urlList) {
            httpGet = new HttpGet(url);
            try {
                httpClient = new DefaultHttpClient();
                HttpResponse httpResponse = httpClient.execute(httpGet);
                int status = httpResponse.getStatusLine().getStatusCode();
                //HttpEntity httpEntity = httpResponse.getEntity();
                //jsonObject = JSONObject.fromObject(EntityUtils.toString(httpEntity));
                //String status = (String) jsonObject.get("status");

                responseList.add("  " + url + ", status:" + status);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return responseList;
    }


}
