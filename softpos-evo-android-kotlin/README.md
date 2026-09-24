# SoftPOS Evolution Kotlin Demo

> Nexi SDK integration for SoftPos Evolution payments using kotlin.

---

## 1. Introduction

This project manages the entire transaction lifecycle via the Nexi SDK:

- **SDK Initialization** and authentication.
- **Payments** .
- **Reversals**.
- **Last Transaction**.
- **Accounting Closure**.

## 2. Mandatory Configuration ⚠️

The identification parameters provided by Nexi must be entered in the following file:
`/domain/Constant.kt`

Fill in the following fields:

* `CLIENT_ID`: Application Identifier.
* `POINT_OF_SALE_ID`: Merchant Identifier.
* `REDIRECT_URI`: Configured Redirect URI.
* `DEEP_LINK_SCHEMA`: Must match the entry in the Android Manifest.

Additionally, implement the following method in the file:
`/services/BackendService.kt`

Method:
`suspend fun getRequestUri(deviceId: String): String`

## 3. Architecture

The project follows a **reactive** model based on `LiveData`:

1. **Service Layer**: `SDKNexiService` handles all asynchronous logic.
2. **Data Layer**: `OperationResult` carries operation outcomes to the UI.
3. **UI Layer**: Activities observe the Service and update the user interface accordingly.

## 4. Requirements

- **The latest available version of the Nexi POS app, provided by Nexi,
  must be installed on the device used.**
- **Language**: Kotlin
- **Minimum SDK**: API 26 (Android 8.0)
- **Hardware**: Physical device with NFC support required (emulators are not supported).

---
*Developed for Nexi SoftPOS Evolution integration.*