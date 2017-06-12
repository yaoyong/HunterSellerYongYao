import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

import java.io.IOException;

/**
 * Created by Yong Yao} on 2017/6/12.
 */
public class test {
    public static void main(String[] args){
        WebClient webclient = new WebClient();

        // 这里是配置一下不加载css和javaScript,配置起来很简单，是不是
        webclient.getOptions().setCssEnabled(false);
        webclient.getOptions().setJavaScriptEnabled(false);

        // 做的第一件事，去拿到这个网页，只需要调用getPage这个方法即可
        HtmlPage htmlpage = null;
        try {
            htmlpage = webclient
                    .getPage("https://www.amazon.com/gp/offer-listing/0375842209/ref=olp_page_1?ie=UTF8&f_used=true&f_usedAcceptable=true&f_usedGood=true&f_usedLikeNew=true&f_usedVeryGood=true&qid=1497138595&sr=8-1");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 根据名字得到一个表单，查看上面这个网页的源代码可以发现表单的名字叫“f”
        final HtmlForm form = htmlpage.("f");
        System.out.println(form);
        // 同样道理，获取”百度一下“这个按钮
            final HtmlSubmitInput button = form.getInputByValue("百度一下");
        System.out.println(button);
        // 得到搜索框
        final HtmlTextInput textField = form.getInputByName("q1");

        System.out.println(textField);

        // 最近周星驰比较火呀，我这里设置一下在搜索框内填入”周星驰“
        textField.setValueAttribute("周星驰");
        // 输入好了，我们点一下这个按钮
         HtmlPage nextPage=null;
        try {
            nextPage = button.click();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 我把结果转成String
        System.out.println(nextPage);

        String result = nextPage.asXml();

        System.out.println(result);

    }
}
