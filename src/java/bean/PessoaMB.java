/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import data.PessoaDB;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.List;
import javax.servlet.http.Part;
import model.Pessoa;
import util.Upload;

/**
 *
 * @author mi
 */
@Named(value = "pessoaMB")
@SessionScoped
public class PessoaMB implements Serializable {

    /**
     * Creates a new instance of PessoaMB
     */
    private List<Pessoa> listaPessoas;
    private Pessoa pessoa;
    private PessoaDB pessoaDB;
    private String msgError;
    //private Part uploadedPhoto;
    private String senha1;
    private String senha2;
    private String telefone;
       
    public PessoaMB() {
        pessoaDB = new PessoaDB();
        pessoa = new Pessoa();
        listaPessoas = retornaListaPessoa();
        msgError = "";
    }

    public List<Pessoa> getListaPessoas() {
        return listaPessoas;
    }

    public void setListaPessoas(List<Pessoa> listaPessoas) {
        this.listaPessoas = listaPessoas;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public PessoaDB getPessoaDb() {
        return pessoaDB;
    }

    public void setPessoaDB(PessoaDB pessoaDB) {
        this.pessoaDB = pessoaDB;
    }

    public String getMsgError() {
        return msgError;
    }

    public void setMsgError(String msgError) {
        this.msgError = msgError;
    }

    public String getSenha1() {
        return senha1;
    }

    public void setSenha1(String senha1) {
        this.senha1 = senha1;
    }

    public String getSenha2() {
        return senha2;
    }

    public void setSenha2(String senha2) {
        this.senha2 = senha2;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    public List<Pessoa> retornaListaPessoa(){
        return pessoaDB.retornaListaPessoas();
    }
    
    public String criarPessoa() {
        msgError = "";
        telefone = "";
        pessoa = new Pessoa();
        return "criaPessoa";
    }
    
    public String editarPessoa(Pessoa p) {
        msgError = "";
        pessoa = p;
        return "editaPessoa";
    }
    
    public String alteraPessoa(){
        String ret = "";
        msgError = "";
        //pessoa.setTelefone(telefone);
        ret = pessoaDB.editar(pessoa);
        if (ret.length() != 0) {
            msgError = ret;
            return "editaPessoa";
        } else {
            msgError = "";
            return "index";
        }
    }
    
    public void excluirPessoa(Pessoa p) {
        String retorno = "";
        msgError = "";
        retorno = pessoaDB.deletar(p);
        if (retorno.length() != 0) {
            msgError = retorno;
        }
    }

    public void excluiPessoa(Pessoa p){
        String ret = "";
        msgError = "";
        Pessoa pessoa = new Pessoa();
        pessoa = pessoaDB.retornaPessoaPorTelefone(p.getTelefone());
        if(pessoa != null)
            ret = pessoaDB.deletar(pessoa);
        if(!ret.isEmpty())
            msgError = ret;
    }
    
    public String criaPessoa(){
        msgError = "";
        String ret = criaPessoa(pessoa);
        if(ret.equals(""))
            return "index";
        
        msgError = ret;
        return "criaPessoa"; 
    }
    
    public String criaPessoa(Pessoa p){
        if(pessoaDB.existePessoaPorTelefone(p.getTelefone()))
            return "Telefone já cadastrado.";

        if(pessoaDB.criar(p).length() != 0)
            return "Erro ao cadastrado pessoa. ";

        return "";
    }
    
    public String segapp(Pessoa p){
        msgError = "";
        pessoa = pessoaDB.retornaPessoaPorId(p.getId());
        return "grupo";
    }
    
    public Boolean validaSenha(String s1, String s2){
        return (s1.equals(s2));
    }
    
    public Boolean validaTelefone(String numeroTelefone) {
        return numeroTelefone.matches(".((10)|([1-9][1-9]).)\\s9?[6-9][0-9]{3}-[0-9]{4}") ||
                numeroTelefone.matches(".((10)|([1-9][1-9]).)\\s[2-5][0-9]{3}-[0-9]{4}");
    }
    
    /*public void upload() {
        try {
            Upload upload = Upload.getInstance();
            upload.write(uploadedPhoto);
            pessoa.setPic(upload.extractFileName(uploadedPhoto));
            

        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    
}
