package de.testsysteme.lottoweb.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.testsysteme.lottoweb.model.LottoErgebnis;
import de.testsysteme.lottoweb.service.LottoService;

@Controller
public class LottoController {

    private final LottoService lottoService;

    public LottoController(LottoService lottoService) {
        this.lottoService = lottoService;
    }
    
    
    
    private String formatiere(List<Integer> liste) {
        return liste.toString().replace("[", "").replace("]", "");
    }

    @GetMapping("/")
    public String index(Model model) {
    	model.addAttribute("historie", lottoService.getHistorie());
        return "lotto";
    }
    
    @GetMapping("/historie")
    public String historie(Model model) {
    	model.addAttribute("historie", lottoService.getHistorie());
    	return "lotto";
    }

    @PostMapping("/eingabe")
    public String eingabe(@RequestParam int zahl1, @RequestParam int zahl2,
                          @RequestParam int zahl3, @RequestParam int zahl4,
                          @RequestParam int zahl5, @RequestParam int zahl6,
                          Model model) {
        List<Integer> zahlen = new ArrayList<>(List.of(zahl1, zahl2, zahl3, zahl4, zahl5, zahl6));

        try {
            lottoService.speicherEingabe(zahlen);
            model.addAttribute("eingabe", formatiere(zahlen));
            model.addAttribute("historie", lottoService.getHistorie());
        } catch (IllegalArgumentException e) {
            model.addAttribute("fehler", e.getMessage());
        }
        return "lotto";
    }

    @PostMapping("/ziehung")
    public String ziehung(Model model) {
        
    	try {
    	lottoService.generiereZiehung();
        LottoErgebnis ergebnis = lottoService.pruefen();

        model.addAttribute("eingabe", formatiere(ergebnis.getEingabe()));
        model.addAttribute("ziehung", formatiere(ergebnis.getZiehung()));
        model.addAttribute("treffer", formatiere(ergebnis.getTreffer()));
        model.addAttribute("historie", lottoService.getHistorie());
        lottoService.reset();
        }catch(IllegalStateException e) {
        	model.addAttribute("fehler", e.getMessage());
        	model.addAttribute("historie", lottoService.getHistorie());
        }
        return "lotto";
    }
    
    @PostMapping("/historie/delete/{id}")
    public String deleteHistorie(@PathVariable Long id){
    	lottoService.deleteHistorie(id);
    	return "redirect:/historie";
    }
    

}