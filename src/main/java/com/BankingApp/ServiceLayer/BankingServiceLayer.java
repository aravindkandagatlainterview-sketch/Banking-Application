package com.BankingApp.ServiceLayer;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.BankingApp.Entity.AccountEntity;
import com.BankingApp.Entity.MiniStatementEntity;
import com.BankingApp.Exception.InvalidedException;
import com.BankingApp.Exception.InvaliedPin;
import com.BankingApp.RepoLayer.BankingRepo;
import com.BankingApp.RepoLayer.MiniStatementRepo;

@Service
public class BankingServiceLayer implements UserDetailsService {

	@Autowired
	private BankingRepo repo;

	@Autowired
	private MailService ms;

	@Autowired
	private MiniStatementRepo miniRepo;

	@Autowired
	private MiniStatementService miniStatementService;

	private AccountEntity findAccount(String accountNumber) {
		AccountEntity acc = repo.findByAccountNumber(accountNumber);
		if (acc == null)
			throw new InvalidedException("Account " + accountNumber + " not found");
		return acc;
	}

	private AccountEntity findAccountOwnedBy(String accountNumber, String loggedInUsername) {
		AccountEntity acc = findAccount(accountNumber);

		if (!acc.getAcholdername().equals(loggedInUsername)) {
			throw new InvalidedException("Access denied ❌");
		}

		return acc;
	}

	public AccountEntity createAccount(AccountEntity account) {
		if (account.getAccountNumber() == null) {
			account.setAccountNumber("ZEN" + (100000 + (long) (Math.random() * 900000)));
		}

		String type = (account.getAge() < 18) ? "Zero Account" : "Saving Account";
		account.setAccount_type(type);
		account.setAcbalance(type.equals("Zero Account") ? 0 : 500);

		AccountEntity saved = repo.save(account);
		ms.sendSimpleMessage(saved.getEmail(), "Welcome to ZenBanking",
				"Your Account Number is: " + saved.getAccountNumber());
		return saved;
	}

	public AccountEntity getAccountByNumber(String accountNumber) {
		return findAccount(accountNumber);
	}

	public List<AccountEntity> getAllAccountDetails() {
		return repo.findAll();
	}

	public AccountEntity depositMoney(String accNum, double amount, short pin) {
		AccountEntity acc = findAccount(accNum);
		if (acc.getPin() != pin)
			throw new InvaliedPin("Invalid PIN");

		acc.setAcbalance(acc.getAcbalance() + amount);
		repo.save(acc);

		miniRepo.save(
				new MiniStatementEntity(acc.getId(), acc.getAcholdername(), "DEPOSIT", amount, acc.getAcbalance()));
		return acc;
	}
	
	public AccountEntity withdrawMoney(String accNum, double amount, short pin) {
		AccountEntity acc = findAccount(accNum);
		if (acc.getPin() != pin)
			throw new InvaliedPin("Invalid PIN");
		if (acc.getAcbalance() < amount)
			throw new RuntimeException("Insufficient Funds");

		acc.setAcbalance(acc.getAcbalance() - amount);
		repo.save(acc);

		miniRepo.save(
				new MiniStatementEntity(acc.getId(), acc.getAcholdername(), "WITHDRAW", amount, acc.getAcbalance()));
		return acc;
	}

	public void transferAmount(String fromAcc, String toAcc, double amount) {
		AccountEntity from = findAccount(fromAcc);
		AccountEntity to = findAccount(toAcc);

		if (from.getAcbalance() < amount)
			throw new RuntimeException("Insufficient balance");

		from.setAcbalance(from.getAcbalance() - amount);
		to.setAcbalance(to.getAcbalance() + amount);

		repo.save(from);
		repo.save(to);

		miniRepo.save(new MiniStatementEntity(from.getId(), from.getAcholdername(), "TRANSFER-DEBIT", amount,
				from.getAcbalance()));
		miniRepo.save(new MiniStatementEntity(to.getId(), to.getAcholdername(), "TRANSFER-CREDIT", amount,
				to.getAcbalance()));
	}

	public void updatePancard(String accNum, String pan) {
		AccountEntity acc = findAccount(accNum);
		acc.setPancard(pan);
		repo.save(acc);
	}

	public void closeAccount(String accNum) {
		AccountEntity acc = findAccount(accNum);
		repo.delete(acc);
	}

	public double checkBalance(String accNum, short pin) {
		AccountEntity acc = findAccount(accNum);
		if (acc.getPin() != pin)
			throw new InvaliedPin("Invalid PIN");
		return acc.getAcbalance();
	}

	public String checkLoginDetails(AccountEntity ae) {
		AccountEntity user = repo.findByAccountNumber(ae.getAccountNumber());
		if (user != null && user.getPassword().equals(ae.getPassword())) {
			return "Login Successful";
		}
		return "Invalid Credentials";
	}

	public void sendMiniStatement(String accNum) {
		AccountEntity acc = findAccount(accNum);
		byte[] pdf = miniStatementService.generateMiniStatementPdf(acc);
		ms.sendMiniStatementMail(acc.getEmail(), pdf);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AccountEntity ac = repo.findByAcholdername(username);
		if (ac == null)
			throw new UsernameNotFoundException("User not found");
		return new User(ac.getAcholdername(), ac.getPassword(), Collections.emptyList());
	}

	public AccountEntity linkPhoneNumber(String accountNumber, AccountEntity ae) {
		AccountEntity acc = findAccount(accountNumber);
		acc.setPhonenumber(ae.getPhonenumber());
		return repo.save(acc);

	}

	public AccountEntity getAccountByUsername(String username) {
		AccountEntity acc = repo.findByAcholdername(username);
		if (acc == null)
			throw new InvalidedException("User not found");
		return acc;
	}

}