package br.ulbra.model;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Erik
 */
public class AgendaDao{
    Connection con;
    
    public AgendaDao() throws SQLException{
        con = ConnectionFactory.getConnection();
    }
    
    
    public boolean checkLogin(String email, String senha) throws NoSuchAlgorithmException{
        PreparedStatement stmt = null;
        Agenda a = new Agenda();
        ResultSet rs = null;
        boolean check = false;
        
        BigInteger cripto;
        cripto = a.criptografarSenha(senha);
        
        try{
            stmt = con.prepareStatement("SELECT * FROM tbusuario WHERE email = ? AND senha = ?");
            stmt.setString(1, email);
            stmt.setString(2, senha);
            
            rs = stmt.executeQuery();
            
            if(rs.next()){
                check = true;
            }
        }
        catch (SQLException e){
             JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());       
        }
        return check;
    }
    
    
    public void create(Agenda a) throws NoSuchAlgorithmException{
        PreparedStatement stmt = null;
        BigInteger cripto;
        try{
            stmt = con.prepareStatement("INSERT INTO tbusuario(nome,email,senha,telefone,recado) VALUE (?,?,?,?,?)");
            
            stmt.setString(1, a.getNome());
            stmt.setString(2, a.getEmail());
            cripto = a.criptografarSenha(a.getSenha());
            stmt.setString(3, cripto.toString());
            stmt.setString(4, a.getTelefone());
            stmt.setString(5, a.getRecado());
            
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Usuario " + a.getNome() + " salvo com sucesso");
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());    
        }finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    
    
    public void update(Agenda a) throws NoSuchAlgorithmException{
       PreparedStatement stmt = null;
       BigInteger cripto;
        try{
            stmt = con.prepareStatement("UPDATE tbusuario SET nome = ?, email = ?, senha = ?, telefone = ?, recado = ? WHERE id = ?");
            
            stmt.setString(1, a.getNome());
            stmt.setString(2, a.getEmail());
            cripto = a.criptografarSenha(a.getSenha());
            stmt.setString(3, cripto.toString());
            stmt.setString(4, a.getTelefone());
            stmt.setString(5, a.getRecado());
            
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Usuario " + a.getNome() + " modificado com sucesso");
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());   
        }finally{
            ConnectionFactory.closeConnection(con, stmt);
        } 
    }
    
    
    public void delete(Agenda a){
        PreparedStatement stmt = null;
        try{
            stmt = con.prepareStatement("DELETE FROM tbusuario WHERE id = ?");
            
            stmt.setInt(1, a.getId());
            
            if (JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir este Usuario(a)", "Exclusão", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                JOptionPane.showMessageDialog(null, "Usuario " + a.getNome() + " excluido com sucesso");
                stmt.executeUpdate();
            }else{
                JOptionPane.showMessageDialog(null, "Exclusão cancelada");
            }
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());   
        }finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
    }  
    
    
    public ArrayList<Agenda> read(){
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        List<Agenda> agendas = new ArrayList<>();
        try{
            stmt = con.prepareStatement("SELECT * FROM tbusuario ORDER BY id ASC");
            rs = stmt.executeQuery();
            while(rs.next()){
                Agenda agenda = new Agenda();
                
                agenda.setId(rs.getInt("id"));
                agenda.setNome(rs.getString("nome"));
                agenda.setEmail(rs.getString("email"));
                agenda.setSenha(rs.getString("senha"));
                agenda.setTelefone(rs.getString("telefone"));
                agenda.setRecado(rs.getString("recado"));
                
                agendas.add(agenda);
            }
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        }finally{
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return (ArrayList<Agenda>) agendas;
    }
    
    
    public ArrayList<Agenda> readPesq(String nome){
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        List<Agenda> agendas = new ArrayList<>();
        try{
            stmt = con.prepareStatement("SELECT * FROM tbusuario WHERE nome LIKE ?");
            
            stmt.setString(1, "%" + nome + "%");
            
            rs = stmt.executeQuery();
            
            while(rs.next()){
                Agenda agenda = new Agenda();
                
                agenda.setId(rs.getInt("id"));
                agenda.setNome(rs.getString("nome"));
                agenda.setEmail(rs.getString("email"));
                agenda.setSenha(rs.getString("senha"));
                agenda.setTelefone(rs.getString("telefone"));
                agenda.setRecado(rs.getString("recado"));
                
                agendas.add(agenda);
            }
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        }finally{
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return (ArrayList<Agenda>) agendas;
    }
}
