package com.cuellar.transactionservice.service;

import com.cuellar.transactionservice.domain.Transaction;
import com.cuellar.transactionservice.domain.TransactionType;
import com.cuellar.transactionservice.dto.PagoServicioRequest;
import com.cuellar.transactionservice.dto.TransactionResponse;
import com.cuellar.transactionservice.dto.TransferenciaRequest;
import com.cuellar.transactionservice.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public TransactionResponse createTransferencia(TransferenciaRequest request) {
        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.TRANSFERENCIA);
        transaction.setMonto(request.getMonto());
        transaction.setCuentaOrigen(request.getCuentaOrigen());
        transaction.setCuentaClave(request.getCuentaClave());
        transaction.setCuentaDestino(request.getCuentaDestino());
        transaction.setConcepto(request.getConcepto());
        transaction.setFechaHora(LocalDateTime.now());
        transaction.setStatus("COMPLETADA");

        Transaction saved = transactionRepository.save(transaction);
        return mapToResponse(saved);
    }

    @Transactional
    public TransactionResponse createPagoServicio(PagoServicioRequest request) {
        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.PAGO_SERVICIO);
        transaction.setMonto(request.getMonto());
        transaction.setTipoServicio(request.getTipoServicio());
        transaction.setReferenciaServicio(request.getReferenciaServicio());
        transaction.setFechaHora(LocalDateTime.now());
        transaction.setStatus("COMPLETADA");

        Transaction saved = transactionRepository.save(transaction);
        return mapToResponse(saved);
    }

    public List<TransactionResponse> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public TransactionResponse getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada con id: " + id));
        return mapToResponse(transaction);
    }

    public List<TransactionResponse> getTransactionsByType(TransactionType type) {
        List<Transaction> transactions = transactionRepository.findByTransactionType(type);
        return transactions.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private TransactionResponse mapToResponse(Transaction tx) {
        TransactionResponse response = new TransactionResponse();
        response.setId(tx.getId());
        response.setTransactionId(tx.getTransactionId());
        response.setTransactionType(tx.getTransactionType());
        response.setMonto(tx.getMonto());
        response.setFechaHora(tx.getFechaHora());
        response.setStatus(tx.getStatus());

        // Campos de TRANSFERENCIA
        response.setCuentaOrigen(tx.getCuentaOrigen());
        response.setCuentaClave(tx.getCuentaClave());
        response.setCuentaDestino(tx.getCuentaDestino());
        response.setConcepto(tx.getConcepto());

        // Campos de PAGO_SERVICIO
        response.setTipoServicio(tx.getTipoServicio());
        response.setReferenciaServicio(tx.getReferenciaServicio());

        return response;
    }
}
