package de.testsysteme.lottoweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.testsysteme.lottoweb.model.LottoHistorie;

public interface LottoHistoryRepository extends JpaRepository<LottoHistorie, Long> {


}
