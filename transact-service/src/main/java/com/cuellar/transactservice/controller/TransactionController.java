package com.cuellar.transactservice.controller;

import com.cuellar.transactservice.domain.TransactionType;
import com.cuellar.transactservice.dto.PagoServicioRequest;
import com.cuellar.transactservice.dto.TransactionResponse;
import com.cuellar.transactservice.dto.TransferenciaRequest;
import com.cuellar.transactservice.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transferencia")
    public ResponseEntity<TransactionResponse> createTransferencia(@Valid @RequestBody TransferenciaRequest request) {
        TransactionResponse response = transactionService.createTransferencia(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/pago-servicio")
    public ResponseEntity<TransactionResponse> createPagoServicio(@Valid @RequestBody PagoServicioRequest request) {
        TransactionResponse response = transactionService.createPagoServicio(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getAllTransactions() {
        List<TransactionResponse> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getTransactionById(@PathVariable Long id) {
        TransactionResponse transaction = transactionService.getTransactionById(id);
        return ResponseEntity.ok(transaction);
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<TransactionResponse>> getTransactionsByType(@PathVariable TransactionType tipo) {
        List<TransactionResponse> transactions = transactionService.getTransactionsByType(tipo);
        return ResponseEntity.ok(transactions);
    }
}
