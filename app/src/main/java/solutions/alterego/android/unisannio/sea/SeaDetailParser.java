package solutions.alterego.android.unisannio.sea;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import solutions.alterego.android.unisannio.interfaces.Parser;



public class SeaDetailParser implements Parser<String> {
    ArrayList<String> bodyelements = new ArrayList<>();

    @Override
    public List<String> parse(Document document) {

        Elements newsBodys = document.select("div.item-page");

        for(Element element : newsBodys){
            String str = element.select("div.item-page").text();

            bodyelements.add(0,str);
        }

        return bodyelements;
    }
}