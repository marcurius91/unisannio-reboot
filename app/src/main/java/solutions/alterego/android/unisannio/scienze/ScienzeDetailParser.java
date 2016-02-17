package solutions.alterego.android.unisannio.scienze;

import android.util.Log;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import solutions.alterego.android.unisannio.interfaces.IParser;

public class ScienzeDetailParser implements IParser<String>{
    ArrayList<String> bodyelements = new ArrayList<>();

    @Override
    public List<String> parse(Document document) {

        Elements newsBodys = document.select("div.rt-grid-9");

        for(Element element : newsBodys){
            String str = element.select("p").text();
            String str1 = str.replace(" Various","");

            bodyelements.add(0,str1);
        }

        return bodyelements;
    }
}