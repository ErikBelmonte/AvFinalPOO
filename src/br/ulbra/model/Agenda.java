package br.ulbra.model;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Erik
 */
public class Agenda{
    private int id;
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private String recado;
    
    public Agenda(){   
        
    }

    public BigInteger criptografarSenha(String senha) throws NoSuchAlgorithmException{
        MessageDigest md5;
        BigInteger senhacrip = null;
        try{
            md5 = MessageDigest.getInstance("MD5");
            md5.reset();
            md5.update(senha.getBytes(), 0, senha.length());
            senhacrip = new BigInteger(1, md5.digest());
        }catch (NoSuchAlgorithmException ex){
            Logger.getLogger(Agenda.class.getName()).log(Level.SEVERE, null, ex);
        }
        return senhacrip;
    }
    
    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getSenha(){
        return senha;
    }

    public void setSenha(String senha){
        this.senha = senha;
    }

    public String getTelefone(){
        return telefone;
    }

    public void setTelefone(String telefone){
        this.telefone = telefone;
    }

    public String getRecado(){
        return recado;
    }

    public void setRecado(String recado){
        this.recado = recado;
    }

    @Override
    public String toString(){
        return "Agenda{" + "id=" + id + ", nome=" + nome + ", email=" + email + ", senha=" + senha + ", telefone=" + telefone + ", recado=" + recado + '}';
    }
}
