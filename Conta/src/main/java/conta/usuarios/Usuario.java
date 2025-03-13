package conta.usuarios;

import conta.contas.Conta;
import conta.enums.TipoContaEnum;

import java.util.Objects;

public class Usuario {
   private int id;
   private String nome;
   private String email;
   private String senha;
   private TipoContaEnum tipoConta;
   private Conta conta;



   public Usuario(int id, String nome, String email, String senha, TipoContaEnum tipoConta, Conta conta) {
      this.id = id;
      this.nome = nome;
      this.email = email;
      setSenha(senha);  // Usando setter para validar senha
      setEmail(email);  // Usando setter para validar email
      this.tipoConta = tipoConta;
      this.conta = conta;
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

   public void setSenha(String senha) {
      if (senha == null || senha.isEmpty()) {
         throw new IllegalArgumentException("A senha não pode ser vazia ou nula.");
      }
      this.senha = senha;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      if (email == null || !email.contains("@")) {
         throw new IllegalArgumentException("O email fornecido não é válido.");
      }
      this.email = email;
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
      return "Usuario{id=" + id + ", nome='" + nome + "', email='" + email + "', tipoConta=" + tipoConta + ", conta=" + conta + "}";
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Usuario usuario = (Usuario) o;
      return id == usuario.id && Objects.equals(nome, usuario.nome) && Objects.equals(email, usuario.email);
   }

   @Override
   public int hashCode() {
      return Objects.hash(id, nome, email);
   }
}
