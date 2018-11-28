package com.coding.task.records.repository;

import org.springframework.data.repository.CrudRepository;

import com.coding.task.records.model.TransactionRecord;

public interface TransactionRecordRepository extends CrudRepository<TransactionRecord, String> {

}
