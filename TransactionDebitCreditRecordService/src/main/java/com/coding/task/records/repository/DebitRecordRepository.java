package com.coding.task.records.repository;

import org.springframework.data.repository.CrudRepository;

import com.coding.task.records.model.DebitRecord;

public interface DebitRecordRepository extends CrudRepository<DebitRecord, String> {

}
