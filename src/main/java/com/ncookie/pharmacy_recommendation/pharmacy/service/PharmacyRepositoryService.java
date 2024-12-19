package com.ncookie.pharmacy_recommendation.pharmacy.service;

import com.ncookie.pharmacy_recommendation.pharmacy.entity.Pharmacy;
import com.ncookie.pharmacy_recommendation.pharmacy.repository.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class PharmacyRepositoryService {
    private final PharmacyRepository pharmacyRepository;

    // self invocation test
//    @Transactional
    public void bar(List<Pharmacy> pharmacyList) {
        log.info("bar CurrentTransactionName: {}", TransactionSynchronizationManager.getCurrentTransactionName());
        foo(pharmacyList);
    }

    // self invocation test
    @Transactional
    public void foo(List<Pharmacy> pharmacyList) {
        log.info("foo CurrentTransactionName: {}", TransactionSynchronizationManager.getCurrentTransactionName());
        pharmacyList.forEach(pharmacy -> {
            pharmacyRepository.save(pharmacy);
            throw new RuntimeException("error"); // 예외가 발생했으므로 트랜잭션이 정상적으로 동작한다면 위의 save 내용이 롤백되어야 한다.
        });
    }

    // read only test
    @Transactional(readOnly = true)
    public void startReadOnlyMethod(Long id) {
        pharmacyRepository.findById(id).ifPresent(pharmacy ->
                pharmacy.changePharmacyAddress("서울 특별시 광진구"));
    }

    @Transactional
    public void updateAddress(Long id, String address) {
        Pharmacy entity = pharmacyRepository.findById(id).orElse(null);

        if (Objects.isNull(entity)) {
            log.error("[PharmacyRepositoryService.updateAddress] not found id: {}", id);
            return;
        }

        entity.changePharmacyAddress(address);
    }

    // @Transactional 어노테이션이 없으면 Dirty Checking이 일어나지 않는지 확인하는 용도의 메소드
    public void updateAddressWithoutTransaction(Long id, String address) {
        Pharmacy entity = pharmacyRepository.findById(id).orElse(null);

        if (Objects.isNull(entity)) {
            log.error("[PharmacyRepositoryService.updateAddress] not found id: {}", id);
            return;
        }

        entity.changePharmacyAddress(address);
    }

    @Transactional(readOnly = true)
    public List<Pharmacy> findAll() {
        return pharmacyRepository.findAll();
    }
}
