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
 * Created by JoshuaYao on 6/6/17.
 */
public class AmazonDemo {
    String url;
    String condition;
    HashMap hashMap =  new HashMap();
    Double min = 10000.0;
    public  void sellerHunter(String isbn, String condition){
        this.condition = condition;
        System.setProperty("webdriver.chrome.driver", "webdriver/chromedriver.exe");
        WebDriver webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        if(condition == "New") {
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
    public String sellerCarwler() throws IOException {
        try { //url = this.url;
            Document document = null;

            for (int page = 0; page < 2; page++) {
                String detailUrl = this.url + "&startIndex=" + 10 * page;
                document = Jsoup.connect(detailUrl).get();
                Elements sellers = document.select(".olpOffer ");
                for (Element seller : sellers) {
                    String sellerName = null;
                    String rating;
                    String totalRating;
                    String priceString = seller.select(".olpOfferPrice").get(0).text();
                    String shipPrice = null;
                    String shipInfor = seller.select(".olpShippingInfo").get(0).text();

                    if (seller.select(".olpSellerName span").hasClass("a-size-medium")) {
                        sellerName = seller.select(".olpSellerName a").get(0).text();
                        rating = seller.select(".olpSellerColumn b").get(0).text();
                        totalRating = seller.select(".olpSellerColumn P").get(0).text();
                    }else {
                        sellerName = "AP";
                        rating = "100% positive";
                        totalRating = "5 out of 5 stars 100% positive over the past 12 months. (1000 total ratings)";

                    }

                    //List<String> ratingString =  new ArrayList<String>();
                    Double shipPriceValue = 0.0;
                    String ratingString = null;
                    Double totalRatingValue = 0.0;
                    Double ratingValue = 0.0;
                    Double priceValue = Double.valueOf(priceString.substring(1));
                    Double price = 0.0;

                    if(totalRating.length()>50) {
                        ratingString = getStrignNum(totalRating.substring(50));
                        totalRatingValue = Double.valueOf(ratingString);
                    }
                    if(rating.contains("positive")) {
                        ratingValue = Double.valueOf(rating.substring(0, 2));

                    }
                    if(shipInfor.contains("FREE")){
                        shipPriceValue = 0.0;
                    }else {
                        shipPriceValue = Double.valueOf(getStrignNum(shipInfor))/100;
                    }
                    price= priceValue+shipPriceValue;

                    if(ratingValue >= 98 && totalRatingValue >=100) {
                        hashMap.put(sellerName, price);
                    }

//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }

//                    System.out.println(sellerName);
//                    System.out.println(priceString);
//                    System.out.println(totalRating);
//                    //  System.out.println(totalRating.substring(50));
//                    System.out.println(ratingString);
//                    System.out.println(rating);
//                    System.out.println(ratingValue);
//                    System.out.println(totalRatingValue);
//                    System.out.println(shipPriceValue);
//                    System.out.println(price);
                }
            }
            Iterator<Double> iterator = hashMap.values().iterator();

            while(iterator.hasNext()){
                Double a = iterator.next();
                if(a<min){
                    min = a;
                }
            }
            System.out.println(getKeyByValue(hashMap,min));
            System.out.println(min);
            System.out.println(condition);
            System.out.println(hashMap);
        }catch (IOException e){
            e.printStackTrace();
        }
        return "   hunting: "+ getKeyByValue(hashMap,min)+"  " +min+ "  "+condition;
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
//    public static void main(String[] args) throws IOException {
//        AmazonDemo sellHunter = new AmazonDemo();
//        sellHunter.sellerHunter("0375842209","new");
//        //sellHunter.sellerHunter("0375842209","used");
//        sellHunter.sellerCarwler();
////        Double intPrice = Double.valueOf("1245");
////        System.out.println(intPrice);
//
//
//    }
}
