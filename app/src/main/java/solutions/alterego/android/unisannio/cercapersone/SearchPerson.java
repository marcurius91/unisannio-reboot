package solutions.alterego.android.unisannio.cercapersone;

import java.util.ArrayList;

public class SearchPerson {


    //Method for searching Person from name or Surname or with both
    public static ArrayList<Person> searchPerson(String personToSearch, ArrayList<Person> persons){

        ArrayList<Person> searchedPers = new ArrayList<>();

        for(int i=0;i<persons.size();i++){

            Person person = persons.get(i);

            String p = personToSearch.toLowerCase();
            String nomepers = person.getNome().toLowerCase();

            if(nomepers.contains(p)){
                searchedPers.add(person);
            }
        }

        return searchedPers;
    }
}
