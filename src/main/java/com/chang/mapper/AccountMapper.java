package com.chang.mapper;

import com.chang.model.AccountStatement;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountMapper {
    List<AccountStatement> getAll();
    AccountStatement getOne(String pid);
    void insert(AccountStatement accountStatement);
    void update(AccountStatement accountStatement);
    void delete(String id);
}
