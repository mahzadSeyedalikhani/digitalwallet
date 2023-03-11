package com.digipay.digitalwallet.mapper;

import com.digipay.digitalwallet.model.dto.CashOutDto;
import com.digipay.digitalwallet.model.dto.CashOutRequest;
import com.digipay.digitalwallet.model.entity.WalletTransaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface CashOutMapper {

    CashOutDto cashOutRequestToCashOutDto(CashOutRequest cashOutRequest);
    WalletTransaction CashOutDtoToWalletTransaction(CashOutDto cashOutDto);
}
