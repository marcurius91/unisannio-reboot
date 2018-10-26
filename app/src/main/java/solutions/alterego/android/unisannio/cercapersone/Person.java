package solutions.alterego.android.unisannio.cercapersone;


import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable{

    String nome,ruolo,email,telefono,ufficio,webPage,tutoring;

    public Person(String nome,String ruolo,String email,String telefono,String ufficio,String webPage,String tutoring){
        this.nome = nome;
        this.ruolo = ruolo;
        this.email = email;
        this.telefono = telefono;
        this.ufficio = ufficio;
        this.webPage = webPage;
        this.tutoring = tutoring;

    }

    public Person(Parcel in) {
        String[] single_person = new String[7];
        in.readStringArray(single_person);
        this.nome = single_person[0];
        this.ruolo = single_person[1];
        this.email = single_person[2];
        this.telefono = single_person[3];
        this.ufficio = single_person[4];
        this.webPage = single_person[5];
        this.tutoring = single_person[6];
    }

    public void setNome(String name){
        nome = name;
    }

    public void setRuolo(String role){
        ruolo = role;
    }

    public void setEmail(String mail){
        email = mail;
    }

    public void setTelefono(String phone){
        telefono = phone;
    }

    public void setUfficio(String office){
        ufficio = office;
    }

    public void setWebPage(String page){
        webPage = page;
    }

    public void setTutoring(String tutor){ tutoring = tutor; }


    public String getNome(){
        return nome;
    }

    public String getRuolo(){
        return ruolo;
    }

    public String getEmail(){
        return email;
    }

    public String getTelefono(){
        return telefono;
    }

    public String getUfficio(){ return ufficio; }

    public String getWebPage(){
        return webPage;
    }

    public String getTutoring(){ return tutoring; }

    public Person getPerson(){
        return new Person(nome,ruolo,email,telefono,ufficio,webPage,tutoring);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {
                this.nome,
                this.ruolo,
                this.email,
                this.telefono,
                this.ufficio,
                this.webPage,
                this.tutoring});

    }

    public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}
