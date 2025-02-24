package Conta.usuarios;

import Conta.contas.Conta;
import Conta.sistema.GeradorId;

public class Usuario {
   private int id;
   private String nome;
   private String email;
   private String senha;
   private Conta conta;


   public Usuario(String nome, String email, String senha) {
      this.id = new GeradorId().gerarId();
      this.nome = nome;
      this.email = email;
      this.senha = senha;
      this.conta = null;
   }

   public Usuario(int id, String nome, String email, String senha, Conta conta) {
      this.id = id;
      this.nome = nome;
      this.email = email;
      this.senha = senha;
      this.conta = conta;
   }

   public int getId() {
      return id;
   }

   public String getNome() {
      return nome;
   }

   public String getEmail() {
      return email;
   }

   public String getSenha() {
      return senha;
   }

   public Conta getConta() {
      return conta;
   }

   public void setConta(Conta conta) {
      this.conta = conta;
   }

   @Override
   public String toString() {
      return "ID: " + id + ", Nome: " + nome + ", Email: " + email;
   }
}