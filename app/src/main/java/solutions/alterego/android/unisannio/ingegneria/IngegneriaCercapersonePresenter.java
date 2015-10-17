package solutions.alterego.android.unisannio.ingegneria;

import android.support.annotation.NonNull;

import java.util.List;

import solutions.alterego.android.unisannio.cercapersone.ICercapersonePresenter;
import solutions.alterego.android.unisannio.cercapersone.Person;
import solutions.alterego.android.unisannio.interfaces.IParser;
import solutions.alterego.android.unisannio.interfaces.IRetriever;

public class IngegneriaCercapersonePresenter implements ICercapersonePresenter {

    private IParser mParser;

    private IRetriever mRetriever;

    @Override
    public void setParser(@NonNull IParser parser) {
        mParser = parser;
    }

    @Override
    public void setRetriever(@NonNull IRetriever retriever) {
        mRetriever = retriever;
    }

    @Override
    public List<Person> getPeople() {
        //TODO ADD LOGIC
        return null;
    }
}
