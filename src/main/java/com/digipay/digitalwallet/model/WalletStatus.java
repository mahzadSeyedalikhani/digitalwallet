package com.digipay.digitalwallet.model;

public enum WalletStatus {
    /*
    in the condition that wallet is available and user can work with that, the wallet status is active
     */
    ACTIVE,
    /*
    in the condition that the user deactivates the wallet , the wallet status will change to inactive
     */
    INACTIVE;

}