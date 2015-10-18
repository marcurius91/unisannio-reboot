package solutions.alterego.android.unisannio.cercapersone;

public class Person {

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

}
