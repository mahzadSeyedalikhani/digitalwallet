package com.digipay.digitalwallet.mapper;

import com.digipay.digitalwallet.model.dto.WalletTransactionDto;
import com.digipay.digitalwallet.model.dto.WalletTransactionRequest;
import com.digipay.digitalwallet.model.entity.WalletTransaction;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface WalletTransactionMapper {

   List<WalletTransactionDto> walletTransactionToWalletTransactionDto (List<WalletTransaction> walletTransactions);
}
