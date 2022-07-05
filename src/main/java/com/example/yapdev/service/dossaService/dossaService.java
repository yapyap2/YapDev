package com.example.yapdev.service.dossaService;

import com.example.yapdev.service.dossaService.dto.productDto;
import com.example.yapdev.service.sseService;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.servlet.mvc.method.annotation.SseEmitter.event;

@Service
@RequiredArgsConstructor
public class dossaService extends Thread{

    String firstEle = null;
    String lastCheckProduct = null;
    List<String> keywords;
    SseEmitter emitter;
    boolean startSwitch = false;


    public void startSse(List<String> keywords, SseEmitter emitter){

        this.keywords = keywords;
        this.emitter = emitter;
        System.out.println("@dossaService@      search for " + keywords);

        start();
    }




    public void run(){

        startSwitch = true;

        while(startSwitch) {

            Elements list = getProductString();

            if(list==null){
                try {
                    Thread.sleep(10000);
                    continue;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            List<productDto> l = makeDto(list, keywords);

            if(l.isEmpty()){
                try {
                    emitter.send(event().data("sse404"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            else{
                l.forEach(item -> {
                    try {
                        System.out.println(item.toString());
                        emitter.send(event().data(item));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("\n\n");
        }

        System.out.println("thread is stopped");

    }


    public Elements getProductString(){

        Connection conn = Jsoup.connect("https://corearoadbike.com/board/board.php?t_id=Menu01Top6");

        Document html = null;
        try {
            html = conn.get();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("dossa web connection failed");
        }

        Elements productSoup = html.getElementsByAttributeValue("onmouseover","list_over(this,'#F9FCE4')");

        if(firstEle != null){
            if(firstEle.equals(productSoup.get(4)
                    .getElementsByClass("list_title_B")
                    .first().getElementsByTag("font").get(1).text())) {
                System.out.println("new product doesn't uploaded,,,");
                return null;
            }
            lastCheckProduct = firstEle;
        }


        System.out.println(productSoup.get(4).getElementsByClass("list_title_B").first().getElementsByTag("font").get(1).text());
        firstEle = productSoup.get(4).getElementsByClass("list_title_B").first().getElementsByTag("font").get(1).text();

        return productSoup;
    }

    public List<productDto> makeDto(Elements list, List<String> keywords) {

        List result = new ArrayList();

        for (Element e : list) {

            boolean isThat = false;

            String title = e.getElementsByClass("list_title_B").text();

            if(title.equals(lastCheckProduct)){
                break;
            }

            if (String.valueOf(title.charAt(1)).equals("구")) {
                System.out.println("구매 항목 입니다");
                continue;
            }
            if (String.valueOf(title.charAt(0)).equals("공")) {
                System.out.println("공지 입니다");
                continue;
            }

            for (String k : keywords) {
                if (title.contains((CharSequence) k)) {
                    isThat = true;
                    break;
                }
            }

            if (isThat == false) {
                System.out.println("키워드에 해당하지 않는 항목 입니다.");
                continue;
            }

            List compliedString = null;
            try {
                compliedString = processDetail(e.getElementsByClass("list_content_B").text());
            } catch (Exception e1) {
                System.out.println("양식을 지키지 않음.");
                continue;
            }

            String link = e.getElementsByTag("a").first().attr("href").substring(1);
            String titleTest = e.getElementsByTag("font").get(1).text();

            String realLink = "https://corearoadbike.com/board" + link;


            productDto object = new productDto();
            object.setTitle(title);
            object.setPrice(compliedString.get(0).toString());
            object.setSize(compliedString.get(1).toString());
            object.setLifeTime(compliedString.get(2).toString());
            object.setDetail(compliedString.get(3).toString());
            object.setLink(realLink);

            result.add(object);

        }

        return result;
    }

    public List processDetail(String str) throws Exception{
        List returnList = new ArrayList();

        int pricePoint = str.indexOf("1.");
        int sizePoint = str.indexOf("2.");
        int lifetimePoint = str.indexOf("3.");
        int detailPoint = str.indexOf("4.");


        returnList.add(str.substring(pricePoint, sizePoint));
        returnList.add(str.substring(sizePoint, lifetimePoint));
        returnList.add(str.substring(lifetimePoint, detailPoint));
        returnList.add(str.substring(detailPoint));


        return returnList;
    }


    public void stopThread(){
        this.startSwitch = false;
    }

}
