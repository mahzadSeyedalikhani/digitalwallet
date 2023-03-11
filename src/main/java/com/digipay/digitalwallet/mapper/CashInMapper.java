package com.digipay.digitalwallet.mapper;

import com.digipay.digitalwallet.model.dto.CashInDto;
import com.digipay.digitalwallet.model.dto.CashInRequest;
import com.digipay.digitalwallet.model.entity.WalletTransaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface CashInMapper {

    CashInDto cashInRequestToCashInDto(CashInRequest cashInRequest);
    WalletTransaction CashInDtoToWalletTransaction(CashInDto cashInDto);
}
