package com.cuellar.transactservice.service;

import com.cuellar.transactservice.domain.Transaction;
import com.cuellar.transactservice.domain.TransactionType;
import com.cuellar.transactservice.domain.User;
import com.cuellar.transactservice.dto.PagoServicioRequest;
import com.cuellar.transactservice.dto.TransactionResponse;
import com.cuellar.transactservice.dto.TransferenciaRequest;
import com.cuellar.transactservice.repository.TransactionRepository;
import com.cuellar.transactservice.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public TransactionResponse createTransferencia(TransferenciaRequest request) {
        // Buscar cuenta origen por CLABE
        User cuentaOrigen = userRepository.findByCuentaClabe(request.getCuentaOrigen())
                .orElseThrow(() -> new RuntimeException("Cuenta origen no encontrada: " + request.getCuentaOrigen()));

//        // Buscar cuenta destino por CLABE
//        User cuentaDestino = userRepository.findByCuentaClave(request.getCuentaDestino())
//                .orElseThrow(() -> new RuntimeException("Cuenta destino no encontrada: " + request.getCuentaDestino()));
//
//        // Validar que la cuenta origen esté activa (puede enviar dinero)
//        userService.validateAccountCanSend(cuentaOrigen);

        // Validar que haya saldo suficiente
        if (cuentaOrigen.getSaldo().compareTo(request.getMonto()) < 0) {
            throw new RuntimeException("Saldo insuficiente. Saldo actual: " + cuentaOrigen.getSaldo() + ", Monto a transferir: " + request.getMonto());
        }
//
//        // Actualizar saldos
//        cuentaOrigen.setSaldo(cuentaOrigen.getSaldo().subtract(request.getMonto()));
//        cuentaDestino.setSaldo(cuentaDestino.getSaldo().add(request.getMonto()));
//
//        // Guardar cuentas actualizadas
//        userRepository.save(cuentaOrigen);
//        userRepository.save(cuentaDestino);

        // Crear la transacción
        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.TRANSFERENCIA);
        transaction.setMonto(request.getMonto());
        transaction.setCuentaOrigen(request.getCuentaOrigen());
        transaction.setCuentaClave(request.getCuentaClave());
        transaction.setCuentaDestino(request.getCuentaDestino());
        transaction.setConcepto(request.getConcepto());
        transaction.setFechaHora(LocalDateTime.now());
        transaction.setStatus("COMPLETADA");

//        // Relacionar con las cuentas
//        transaction.setCuentaOrigenAccount(cuentaOrigen);
//        transaction.setCuentaDestinoAccount(cuentaDestino);

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
