package de.testsysteme.lottoweb.model;

import java.util.List;

public class LottoErgebnis {

    private List<Integer> eingabe;
    private List<Integer> ziehung;
    private List<Integer> treffer;

    public LottoErgebnis(List<Integer> eingabe, List<Integer> ziehung, List<Integer> treffer) {
        this.eingabe = eingabe;
        this.ziehung = ziehung;
        this.treffer = treffer;
    }

    public List<Integer> getEingabe() {
    	return eingabe;
    	}
    public List<Integer> getZiehung() { 
    	return ziehung;
    	}
    public List<Integer> getTreffer() {
    	return treffer; 
    	}
}