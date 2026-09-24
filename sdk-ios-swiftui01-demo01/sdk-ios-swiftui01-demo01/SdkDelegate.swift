//
//  SdkDelegate.swift
//
import App2AppSDK

protocol SdkDelegate {
    
    // Il metodo viene implementato nella Classe AppState
    // che essendo un Observable informa gli oggetti in
    // ascolto che è arrivato un messaggio dall'SDK in
    // seguito ad una operazione avviata
    func resultFromSDK(message : String)
  
    
}
