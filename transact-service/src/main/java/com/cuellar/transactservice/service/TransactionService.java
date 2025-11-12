package com.cuellar.transactservice.service;

import com.cuellar.transactservice.domain.Transaction;
import com.cuellar.transactservice.domain.TransactionType;
import com.cuellar.transactservice.domain.User;
import com.cuellar.transactservice.dto.PagoServicioRequest;
import com.cuellar.transactservice.dto.TransactionResponse;
import com.cuellar.transactservice.dto.TransferenciaRequest;
import com.cuellar.transactservice.repository.AccountRepository;
import com.cuellar.transactservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final UserService userService;

    @Transactional
    public TransactionResponse createTransferencia(TransferenciaRequest request) {
        // Buscar cuentas por CLABE
        User cuentaOrigen = accountRepository.findByCuentaClave(request.getCuentaOrigen())
                .orElseThrow(() -> new RuntimeException("Cuenta origen no encontrada: " + request.getCuentaOrigen()));
        User cuentaDestino = accountRepository.findByCuentaClave(request.getCuentaDestino())
                .orElseThrow(() -> new RuntimeException("Cuenta destino no encontrada: " + request.getCuentaDestino()));

        // Validaciones con UserService (antes en AccountService)
        userService.validateAccountCanSend(cuentaOrigen);
        userService.validateAccountCanReceive(cuentaDestino);

        // Validar saldo suficiente
        userService.validateSufficientFunds(cuentaOrigen, request.getMonto());

        // Actualizar saldos
        userService.debit(cuentaOrigen, request.getMonto());
        userService.credit(cuentaDestino, request.getMonto());

        // Persistir cuentas
        accountRepository.save(cuentaOrigen);
        accountRepository.save(cuentaDestino);

        // Registrar transacción
        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.TRANSFERENCIA);
        transaction.setMonto(request.getMonto());
        transaction.setCuentaOrigen(request.getCuentaOrigen());
        transaction.setCuentaClave(request.getCuentaClave());
        transaction.setCuentaDestino(request.getCuentaDestino());
        transaction.setConcepto(request.getConcepto());
        transaction.setFechaHora(LocalDateTime.now());
        transaction.setStatus("COMPLETADA");

        transaction.setCuentaOrigenUser(cuentaOrigen);
        transaction.setCuentaDestinoUser(cuentaDestino);

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

    @Transactional(readOnly = true)
    public List<TransactionResponse> getAllTransactions() {
        return transactionRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TransactionResponse getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada con id: " + id));
        return mapToResponse(transaction);
    }

    @Transactional(readOnly = true)
    public List<TransactionResponse> getTransactionsByType(TransactionType type) {
        return transactionRepository.findByTransactionType(type).stream()
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

        // TRANSFERENCIA
        response.setCuentaOrigen(tx.getCuentaOrigen());
        response.setCuentaClave(tx.getCuentaClave());
        response.setCuentaDestino(tx.getCuentaDestino());
        response.setConcepto(tx.getConcepto());

        // PAGO_SERVICIO
        response.setTipoServicio(tx.getTipoServicio());
        response.setReferenciaServicio(tx.getReferenciaServicio());

        return response;
    }
}
