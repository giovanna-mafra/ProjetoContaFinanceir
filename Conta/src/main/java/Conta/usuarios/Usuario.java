package Conta.usuarios;

import Conta.contas.Conta;
import Conta.enums.TipoContaEnum;
import Conta.sistema.GeradorId;

public class Usuario {
   private int id;
   private String nome;
   private String email;
   private String senha;
   private TipoContaEnum tipoConta;
   private Conta conta;

   public Usuario(String nome, String email, String senha, TipoContaEnum tipoConta) {
      this.id = new GeradorId().gerarId();
      this.nome = nome;
      this.email = email;
      this.senha = senha;
      this.tipoConta = tipoConta;
      this.conta = null;
   }

   public int getId() {
      return id;
   }


   public String getNome() {
      return nome;
   }

   public void setNome(String nome) {
      this.nome = nome;
   }

   public String getSenha() {
      return senha;
   }

   public TipoContaEnum getTipoConta() {
      return tipoConta;
   }

   public Conta getConta() {
      return conta;
   }

   public void setConta(Conta conta) {
      this.conta = conta;
   }

   @Override
   public String toString() {
      return "Categoria{id=" + id + ", nome='" + nome + "'}";
   }
}