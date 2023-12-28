package transfer.demo.service;


import org.springframework.data.domain.PageRequest;
import transfer.demo.models.entity.Transaction;
import transfer.demo.request.FilterRequest;

import java.util.List;

public interface FilteringService {

    List<Transaction> findAll (PageRequest pageRequest);

    List<Transaction> getFilteredData(FilterRequest request);
}
