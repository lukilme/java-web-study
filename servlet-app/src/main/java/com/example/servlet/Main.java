package com.example.servlet;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Teste Local do Módulo Servlet ===");
        
        // Espaço para testar regras de negócio, utilitários ou conexões
        String mensagem = formatarMensagem("Dev");
        System.out.println(mensagem);
    }

    private static String formatarMensagem(String nome) {
        return "Ambiente configurado com sucesso para: " + nome;
    }
}
