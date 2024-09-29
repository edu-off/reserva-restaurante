# language: pt
Funcionalidade: API - Restaurante

  Cenário: Registrar um novo restaurante
    Quando submeter um novo restaurante
    Então o restaurante é registrado com sucesso

  Cenário: Registrar uma nova mesa
    Dado que um restaurante já foi registrado
    Quando requisitar o registro de uma mesa
    Então a mesa é registrada com sucesso