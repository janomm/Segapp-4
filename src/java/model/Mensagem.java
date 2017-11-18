/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import data.PessoaDB;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author mi
 */
@Entity
public class Mensagem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private Integer idGrupo;
    private Integer idPessoa;
    private Date dtEnvio;
    private String hrEnvio;
    private String mensagem;
    private Boolean tipo;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }

    public Integer getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Integer idPessoa) {
        this.idPessoa = idPessoa;
    }

    public Date getDtEnvio() {
        return dtEnvio;
    }

    public void setDtEnvio(Date dtEnvio) {
        setHrEnvio(dtEnvio);
        this.dtEnvio = dtEnvio;
    }

    public String getHrEnvio() {
        return hrEnvio;
    }

    public void setHrEnvio(Date dataHora) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        this.hrEnvio = sdf.format(dataHora);        
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Boolean getTipo() {
        return tipo;
    }

    public void setTipo(Boolean tipo) {
        this.tipo = tipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mensagem)) {
            return false;
        }
        Mensagem other = (Mensagem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Mensagem[ id=" + id + " ]";
    }
    
    public String dtHrCriacao(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return(sdf.format(this.dtEnvio) + getHrEnvio());
    }
    
    public String retNomePessoa(){
        PessoaDB pDB = new PessoaDB();
        return pDB.retornaPessoaPorId(idPessoa).getNome();
    }
    
}
