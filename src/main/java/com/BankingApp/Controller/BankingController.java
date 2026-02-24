  package com.BankingApp.Controller;

import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.BankingApp.Entity.AccountEntity;
import com.BankingApp.Exception.InvalidedException;
import com.BankingApp.ServiceLayer.BankingServiceLayer;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class BankingController {

    @Autowired
    private BankingServiceLayer serviceLayer;

    @PostMapping("/create")
    public ResponseEntity<AccountEntity> createAccount(@RequestBody AccountEntity account) {
        if (account.getAge() < 12 || account.getAge() > 100) {
            throw new InvalidedException("Age must be between 12 and 100");
        }
        AccountEntity createdAccount = serviceLayer.createAccount(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
    }

    @GetMapping("/{accountNumber}")
    public AccountEntity getAccountDetails(@PathVariable String accountNumber) {
        return serviceLayer.getAccountByNumber(accountNumber);
    }

    @GetMapping("/getall")
    public List<AccountEntity> getAllAccounts() {
        return serviceLayer.getAllAccountDetails();
    }

    @PutMapping("/deposit/{accountNumber}")
    public AccountEntity deposit(@PathVariable String accountNumber, @RequestBody AccountEntity ae) {
        return serviceLayer.depositMoney(accountNumber, ae.getAcbalance(), ae.getPin());
    }
    
    @PutMapping("/withdraw/{accountNumber}")
    public AccountEntity withdraw(@PathVariable String accountNumber, @RequestBody AccountEntity ae) {
        return serviceLayer.withdrawMoney(accountNumber, ae.getAcbalance(), ae.getPin());
    }
    
    @PutMapping("/transfer/{fromAccountNumber}")
    public ResponseEntity<String> transfer(@PathVariable String fromAccountNumber, @RequestBody AccountEntity ae) {
        serviceLayer.transferAmount(fromAccountNumber, ae.getAccountNumber(), ae.getAcbalance());
        return ResponseEntity.ok("Transfer successful");
    }
    
    @DeleteMapping("/delete/{accountNumber}")
    public ResponseEntity<String> closeAccount(@PathVariable String accountNumber) {
        serviceLayer.closeAccount(accountNumber);
        return ResponseEntity.ok("Account closed successfully");
    }

    @PutMapping("/updatePan/{accountNumber}")
    public ResponseEntity<String> updatePan(@PathVariable String accountNumber, @RequestBody AccountEntity ae) {
        serviceLayer.updatePancard(accountNumber, ae.getPancard());
        return ResponseEntity.ok("PAN updated successfully");
    }

    @PostMapping("/balance/{accountNumber}")
    public ResponseEntity<Double> getBalance(@PathVariable String accountNumber, @RequestBody AccountEntity ae) {
        double balance = serviceLayer.checkBalance(accountNumber, ae.getPin());
        return ResponseEntity.ok(balance);
    }
    
    @PostMapping("/mini_statement/{accountNumber}")
    public ResponseEntity<String> sendMiniStatement(@PathVariable String accountNumber) {
        serviceLayer.sendMiniStatement(accountNumber);
        return ResponseEntity.ok("Mini statement sent to registered email ✅");
    }
    
    @PostMapping("/login")
    public String validateLogin(@RequestBody AccountEntity ae) {
        return serviceLayer.checkLoginDetails(ae);
    }
    
    @PutMapping("/phone/{accountNumber}")
    public ResponseEntity<String> updatePhoneNumber(
            @PathVariable String accountNumber,
            @RequestBody AccountEntity ae
    ) {
        serviceLayer.linkPhoneNumber(accountNumber, ae);
        return ResponseEntity.ok("Phone number linked successfully ✅");
    }
    
        
    @GetMapping("/me")
	public AccountEntity me(Principal principal) {
	    String username = principal.getName();
	    return serviceLayer.getAccountByUsername(username);
	}
}