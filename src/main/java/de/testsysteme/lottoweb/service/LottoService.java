package de.testsysteme.lottoweb.service;


import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import de.testsysteme.lottoweb.model.LottoErgebnis;
import de.testsysteme.lottoweb.model.LottoHistorie;
import de.testsysteme.lottoweb.repository.LottoHistoryRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LottoService {
	
	private final LottoHistoryRepository historyRepository;
	
	private List<Integer> eingabe;
	private List<Integer> ziehung;
	
	public LottoService(LottoHistoryRepository historyRepository) {
		this.historyRepository = historyRepository;
	}

	private String formatiere(List<Integer> liste) {
	    return liste.toString().replace("[", "").replace("]", "");
	}
	
	public List<Integer> generiereZiehung(){
		Random rnd = new Random();
		ziehung = new ArrayList<>();
		
		while (ziehung.size() < 6) {
			int zahl = rnd.nextInt(49) + 1;
			if (!ziehung.contains(zahl)) {
				ziehung.add(zahl);
			}
		}
		Collections.sort(ziehung);
		return ziehung;
	}

	public void speicherEingabe(List<Integer> eingabe) {
		Set<Integer> set = eingabe.stream().collect(Collectors.toSet());
		

		
		if (set.size() != eingabe.size()) {
			throw new IllegalArgumentException("Keine Duplikate erlaubt!");
		}
		
		
		
		for (int zahl : eingabe) {
			if (zahl < 1 || zahl > 49) {
				throw new IllegalArgumentException("Zahlen müssen zwischen 1 und 49 liegen");
			}
		}
		
		if (eingabe.size() != 6) {
			throw new IllegalArgumentException("Genau 6 Zahlen eingeben!");
		}
		Collections.sort(eingabe);
		this.eingabe = eingabe;
		
	}
	
	public LottoErgebnis pruefen() {
		
		if (eingabe == null) {
			throw new IllegalStateException("Bitte zuerst alle Zahlen eingeben!");
		}
		
		List<Integer> treffer = new ArrayList<>();
		for (int e : eingabe) {
			if (ziehung.contains(e)) {
				treffer.add(e);
			}
		}
		historyRepository.save(new LottoHistorie(
				formatiere(eingabe),
				formatiere(ziehung),
				formatiere(treffer),
				LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))));
		
		return new LottoErgebnis(eingabe, ziehung, treffer);
	}

	public List<Integer> getEingabe() {
	    return eingabe;
	}
	
	public List<LottoHistorie> getHistorie(){
		return historyRepository.findAll();
	}
	
	public void deleteHistorie(Long id) {
		historyRepository.deleteById(id);
	}
	
    public void reset() {
    	eingabe = null;
    	ziehung = null;
    }
}
