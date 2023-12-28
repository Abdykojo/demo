package transfer.demo.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import transfer.demo.configuration.Auditable;
import transfer.demo.exception.ServiceException;
import transfer.demo.models.entity.CashBox;
import transfer.demo.models.entity.Transaction;
import transfer.demo.models.enums.ResponseMessage;
import transfer.demo.repository.CashBoxRepository;
import transfer.demo.repository.TransactionRepository;
import transfer.demo.request.FilterRequest;
import transfer.demo.service.FilteringService;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class FilterRepository implements FilteringService {
    @PersistenceContext
    EntityManager entityManager;
    TransactionRepository transactionRepository;

    @Override
    public List<Transaction> findAll(PageRequest pageRequest) {
        return transactionRepository.findAllByPageRequest(pageRequest);
    }
    @Auditable
    @Override
    public List<Transaction> getFilteredData(FilterRequest request) {

        try {

            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Transaction> criteriaQuery = criteriaBuilder.createQuery(Transaction.class);
            Root<Transaction> root = criteriaQuery.from(Transaction.class);
            List<Predicate> predicates = new ArrayList<>();

            if (!request.getCurrency().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("currency"), request.getCurrency()));
            }
            if (request.getStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), request.getStatus()));
            }
            if (!request.getReceiveCashBoxName().isEmpty()) {
                Join<Transaction, CashBox> receiveCashBoxJoin = root.join("receiveCashBoxId");
                predicates.add(criteriaBuilder.equal(receiveCashBoxJoin.get("name"), request.getReceiveCashBoxName()));
            }
            if (!request.getSendCashBoxName().isEmpty()) {
                Join<Transaction, CashBox> sendCashBoxIdCashBoxJoin = root.join("sendCashBoxId");
                predicates.add(criteriaBuilder.equal(sendCashBoxIdCashBoxJoin.get("name"), request.getSendCashBoxName()));
            }
            if (request.getStartDate() != null && request.getEndDate() != null) {
                predicates.add(criteriaBuilder.between(root.get("date"), request.getStartDate(), request.getEndDate()));
            }


            criteriaQuery.where(predicates.toArray(new Predicate[0]));
            TypedQuery<Transaction> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setFirstResult((request.getPageNumber() - 1) * request.getPageSize());
            typedQuery.setMaxResults(request.getPageSize());
            return typedQuery.getResultList();

        }
        catch (Exception e) {
            throw new ServiceException(ResponseMessage.INTERNAL_SERVER_ERROR.getMessage());
        }
    }

}
