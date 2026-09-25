# SoftPOS Base Kotlin demo

> Nexi integration for SoftPos Base payments using kotlin.
---

## 1. Introduction

This project manages the entire transaction lifecycle via the Nexi POS:

- **Payments** .
- **Reversals**.
- **Last Transaction**.
- **Accounting Closure**.

## 3. Architecture

The features provided by Nexi POS are launched using Intents. 
Transaction results are communicated by Nexi POS to the calling app via deep links.

## 4. Requirements

- **The latest available version of the Nexi POS app, provided by Nexi,
  must be installed on the device used.**
- **Language**: Kotlin
- **Minimum SDK**: API 26 (Android 8.0)
- **Hardware**: Physical device with NFC support required (emulators are not supported).

---
*Developed for Nexi SoftPOS Base integration.*