package Conta.usuarios;
import Conta.contas.Conta;
import Conta.transacoes.Transacao;

import java.util.ArrayList;
import java.util.List;

public class Usuario implements RetornarTexto {
   private int id;
   private String nome;
   private String email;
   private String senha;
   private Conta conta;
   private List<Transacao> transacoes;

   public Usuario(int id, String nome, String email, String senha, Conta conta){
      this.id = id;
      this.nome = nome;
      this.email = email;
      this.senha = senha;
      this.conta = conta;
      this.transacoes = new ArrayList<>();
   }

   public int getId(){
      return id;
   }

   public String getNome(){
      return nome;
   }

   public String getEmail(){
      return email;
   }

   public Conta getConta() {
      return conta;
   }

   public boolean verificarSenha(String senhaDigitada){
      return this.senha.equals(senhaDigitada);
   }

   public void adicionarTransacao(Transacao transacao) {
      transacoes.add(transacao);
   }

   public List<Transacao> getTransacoes() {
      return transacoes;
   }

   @Override
   public String toString() {
      return "ID: " + id + ", NOME: " + nome + ", Email: " + email;
   }
}
