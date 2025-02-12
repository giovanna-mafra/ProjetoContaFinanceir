package Conta;

public class Usuario implements RetornarTexto {
   private int id;
   private String nome;
   private String email;
   private String senha;

   public Usuario(int id, String nome, String email,String senha){
      this.id = id;
      this.nome = nome;
      this.email = email;
      this.senha = senha;

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

   public boolean verificarSenha(String senhaDigitada){
      return this.senha.equals(senhaDigitada);
   }


   @Override
   public String toString() {
      return "ID: " + id + ", NOME:" + nome + ", Email " + email;
   }

}
