# SoftPOS Cloud Authentication SwiftUI Demo

## 1. Introduction
This project manages the entire transaction lifecycle via the Nexi iOS SDK:

- SDK Initialization and authentication.
- Payment.
- Refunds.
- Recepit.
- Transactions.

## 2. Mandatory Configuration ⚠️
The identification parameters provided by Nexi must be entered in the following file: `Domain/Constant.swift`

Fill in the following fields:
* **CLIENT_ID:** Application Identifier.
* **POINT_OF_SALE_ID:** Point of sale Identifier.
* **REDIRECT_URI:** Configured Redirect URI.
* **MERCHANT_USER_NAME:** Merchant Identifier.

Additionally, implement the following method in the file: `Services/BackendService.swift`

```swift
static func getRequestUri(deviceId : String) async throws -> String 
```
## 3. Architecture

For proper implementation, please refer to the following documentation:

[Nexi iOS SDK documentation](https://developer.nexigroup.com/softposmobilepos/en-EU/docs/beta-sdk-ios-softpos-only/sdk-overview/)

[Cloud Authentication](https://developer.nexigroup.com/softposmobilepos/en-EU/docs/web-portal-configuration/)

---
*Developed for Nexi SoftPOS Cloud Authentication integration.*
