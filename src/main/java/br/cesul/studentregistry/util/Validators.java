package br.cesul.studentregistry.util;

public class Validators {
    //denota q esta classe é estatica e nn precisa de instancias
    private Validators(){}

    public static boolean notBlank(String s){
        //1- checar null
        //2- remover espacos das pontas (com o trim())
        //3- verifico se sobrou algo
        return s != null && !s.trim().isEmpty();

    }
}
