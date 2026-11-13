package com.fleetflow.dao;

import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class JpaDao {

	@PersistenceContext
	private EntityManager entityManager;

	private final TransactionTemplate transactionTemplate;

	public JpaDao(PlatformTransactionManager transactionManager) {
		this.transactionTemplate = new TransactionTemplate(transactionManager);
	}

	public void dalamTransaksi(Consumer<EntityManager> kerja) {
		transactionTemplate.executeWithoutResult(status -> kerja.accept(entityManager));
	}

	public <T> T baca(Function<EntityManager, T> kerja) {
		return transactionTemplate.execute(status -> kerja.apply(entityManager));
	}

}
