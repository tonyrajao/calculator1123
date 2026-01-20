package com.example.calculatrice

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {
    // Texte affiché à l'écran
    var displayText by mutableStateOf("0")
        private set
    
    // Nombre en cours de saisie
    private var currentNumber = ""
    // Nombre précédent pour le calcul
    private var previousNumber = ""
    // Opération en cours
    private var currentOperation: Operation? = null
    // Indique si on doit réinitialiser l'affichage
    private var shouldResetDisplay = false
    
    // Énumération des opérations
    enum class Operation(val symbol: String) {
        ADD("+"),
        SUBTRACT("-"),
        MULTIPLY("×"),
        DIVIDE("÷"),
        MODULO("%")
    }
    
    // Gère le clic sur un chiffre
    fun onNumberClick(number: String) {
        if (shouldResetDisplay) {
            displayText = number
            currentNumber = number
            shouldResetDisplay = false
        } else {
            if (displayText == "0") {
                displayText = number
                currentNumber = number
            } else {
                displayText += number
                currentNumber += number
            }
        }
    }
    
    // Gère le clic sur une opération
    fun onOperationClick(operation: Operation) {
        if (currentNumber.isNotEmpty() && previousNumber.isNotEmpty() && currentOperation != null) {
            // Calcule d'abord l'opération existante
            calculateResult()
            previousNumber = displayText
            currentNumber = ""
        } else if (currentNumber.isNotEmpty()) {
            previousNumber = currentNumber
            currentNumber = ""
        }
        
        currentOperation = operation
        displayText += " ${operation.symbol} "
        shouldResetDisplay = false
    }
    
    // Gère le clic sur le bouton égal
    fun onEqualsClick() {
        if (currentNumber.isNotEmpty() && previousNumber.isNotEmpty() && currentOperation != null) {
            calculateResult()
            previousNumber = ""
            currentOperation = null
            shouldResetDisplay = true
        }
    }
    
    // Calcule le résultat de l'opération
    private fun calculateResult() {
        try {
            val num1 = previousNumber.toDoubleOrNull() ?: return
            val num2 = currentNumber.toDoubleOrNull() ?: return
            
            val result = when (currentOperation) {
                Operation.ADD -> num1 + num2
                Operation.SUBTRACT -> num1 - num2
                Operation.MULTIPLY -> num1 * num2
                Operation.DIVIDE -> {
                    if (num2 == 0.0) {
                        displayText = "Erreur"
                        currentNumber = ""
                        previousNumber = ""
                        currentOperation = null
                        return
                    }
                    num1 / num2
                }
                Operation.MODULO -> {
                    if (num2 == 0.0) {
                        displayText = "Erreur"
                        currentNumber = ""
                        previousNumber = ""
                        currentOperation = null
                        return
                    }
                    num1 % num2
                }
                null -> return
            }
            
            // Formate le résultat pour enlever les décimales inutiles
            displayText = if (result % 1.0 == 0.0) {
                result.toLong().toString()
            } else {
                result.toString()
            }
            
            currentNumber = displayText
        } catch (e: Exception) {
            displayText = "Erreur"
            currentNumber = ""
            previousNumber = ""
            currentOperation = null
        }
    }
    
    // Gère le clic sur +/- (négation)
    fun onNegateClick() {
        if (currentNumber.isNotEmpty() && currentNumber != "0") {
            currentNumber = if (currentNumber.startsWith("-")) {
                currentNumber.substring(1)
            } else {
                "-$currentNumber"
            }
            
            // Met à jour l'affichage
            val parts = displayText.split(" ")
            if (parts.size == 1) {
                displayText = currentNumber
            } else if (parts.size >= 3) {
                displayText = "${parts[0]} ${parts[1]} $currentNumber"
            }
        }
    }
    
    // Gère le clic sur effacer (supprime le dernier caractère)
    fun onDeleteClick() {
        if (displayText.isEmpty() || displayText == "0" || displayText == "Erreur") {
            return
        }
        
        if (shouldResetDisplay) {
            clear()
            return
        }
        
        // Supprime le dernier caractère
        if (displayText.length == 1) {
            displayText = "0"
            currentNumber = ""
            return
        }
        
        val lastChar = displayText.last()
        
        if (lastChar == ' ') {
            // Supprime l'opération (ex: " + ")
            displayText = displayText.dropLast(3)
            currentOperation = null
            currentNumber = previousNumber
            previousNumber = ""
        } else {
            // Supprime un chiffre
            displayText = displayText.dropLast(1)
            
            // Met à jour le nombre courant
            val parts = displayText.split(" ")
            if (parts.size == 1) {
                currentNumber = parts[0]
            } else if (parts.size >= 3) {
                currentNumber = parts[2]
            }
            
            // Gère les nombres négatifs à un chiffre
            if (currentNumber == "-") {
                displayText = displayText.dropLast(1)
                currentNumber = ""
            }
        }
        
        if (displayText.isEmpty()) {
            displayText = "0"
            currentNumber = ""
        }
    }
    
    // Efface tout (reset)
    fun clear() {
        displayText = "0"
        currentNumber = ""
        previousNumber = ""
        currentOperation = null
        shouldResetDisplay = false
    }
    
    // Gère le clic sur le point décimal
    fun onDecimalClick() {
        if (shouldResetDisplay) {
            displayText = "0."
            currentNumber = "0."
            shouldResetDisplay = false
        } else if (!currentNumber.contains(".")) {
            if (currentNumber.isEmpty()) {
                displayText += "0."
                currentNumber = "0."
            } else {
                displayText += "."
                currentNumber += "."
            }
        }
    }
    
    // Retourne le texte à copier dans le presse-papier
    fun getDisplayTextForClipboard(): String {
        return displayText
    }
}
