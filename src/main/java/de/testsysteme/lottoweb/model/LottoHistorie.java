package de.testsysteme.lottoweb.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class LottoHistorie {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String eingabe;
	private String ziehung;
	private String treffer;
	private String datum;
	
	
	
	public LottoHistorie(String eingabe, String ziehung, String treffer, String datum) {
	
		this.eingabe = eingabe;
		this.ziehung = ziehung;
		this.treffer = treffer;
		this.datum = datum;
	}
	
	public LottoHistorie() {}
	
	public long getId() {
		return id;
	}
	public String getEingabe() {
		return eingabe;
	}
	public String getZiehung() {
		return ziehung;
	}
	public String getTreffer() {
		return treffer;
	}
	public String getDatum() {
		return datum;
	}
	
	
}
