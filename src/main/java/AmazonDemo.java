import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
        import org.openqa.selenium.WebDriver;
        import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//import java.util.HashMap.Entry;


/**
 * Created by zhenqingzhao on 6/6/17.
 */
public class AmazonDemo {
    String url;
    public  void sellerHunter(String isbn, String condition){

        System.setProperty("webdriver.chrome.driver", "webdriver/chromedriver.exe");
        WebDriver webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        if(condition == "new") {
            url = "https://www.amazon.com/gp/offer-listing/" + isbn + "/ref=olp_f_new?ie=UTF8&f_new=true&qid=1497138595&sr=8-1";
        }else{
            url = "https://www.amazon.com/gp/offer-listing/"+isbn +
                    "/ref=olp_f_new?ie=UTF8&f_used=true&f_usedAcceptable=true&f_" +
                    "usedGood=true&f_usedLikeNew=true&f_usedVeryGood=true&qid=1497138595&sr=8-1";
        }
        webDriver.get(url);
        //webDriver.findElement(By.id("olpOfferList"));
        //webDriver.quit();
        //webDriver.close();
    }
    public void sellerCarwler() throws IOException {
       try { //url = this.url;
           Document document = null;
           HashMap hashMap =  new HashMap();
           for (int page = 0; page < 3; page++) {

               String detailUrl = this.url + "&startIndex=" + 10 * page;
               document = Jsoup.connect(detailUrl).get();
               Elements sellers = document.select(".olpOffer ");
               for (Element seller : sellers) {
                   String sellerName = seller.select(".olpSellerName a").get(0).text();
                   String price = seller.select(".olpOfferPrice").get(0).text();
                   String rating = seller.select(".olpSellerColumn b").get(0).text();
                   String totalRating = seller.select(".olpSellerColumn P").get(0).text();

                   //List<String> ratingString =  new ArrayList<String>();
                   String ratingString = getStrignNum(totalRating.substring(50));
                   Double totalRatingValue = Double.valueOf(ratingString);
                   Double ratingValue = Double.valueOf(rating.substring(0,2));
                   Double priceValue = Double.valueOf(price.substring(1));
                    if(ratingValue >= 98 && totalRatingValue >=100) {
                        hashMap.put(sellerName, priceValue);
                    }
                   try {
                       Thread.sleep(1000);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }

//                   System.out.println(sellerName);
//                   System.out.println(price);
//                   System.out.println(totalRating);
//                   System.out.println(totalRating.substring(50));
//                   System.out.println(ratingString);
//                   System.out.println(ratingValue);
//                  System.out.println(totalRatingValue);
               }
           }
           Iterator<Double> iterator = hashMap.values().iterator();
           Double min = 10000.0;
           while(iterator.hasNext()){
               Double a = iterator.next();
               if(a<min){
                   min = a;
               }
           }
           System.out.println(getKeyByValue(hashMap,min));
           System.out.println(min);
           System.out.println(hashMap);
       }catch (IOException e){
           e.printStackTrace();
       }
    }
    public static String getKeyByValue(Map map, Object value) {
        String keys="";
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Object obj = entry.getValue();
            if (obj != null && obj.equals(value)) {
                keys=(String) entry.getKey();
            }

        }
        return keys;
    }
    public static String getStrignNum(String ratingString){
       // List<String> digitList = new ArrayList<String>();
        Pattern p = Pattern.compile("[^0-9]");
        Matcher m = p.matcher(ratingString);
        String result = m.replaceAll("");
//        for (int i = 0; i < result.length(); i++) {
//            digitList.add(result.substring(i, i+1));
//
//        }

        return result;
    }
    public static void main(String[] args) throws IOException {
        AmazonDemo sellHunter = new AmazonDemo();
        //sellHunter.sellerHunter("0375842209","new");
        sellHunter.sellerHunter("0375842209","used");
            sellHunter.sellerCarwler();
//        Double intPrice = Double.valueOf("1245");
//        System.out.println(intPrice);


    }
}