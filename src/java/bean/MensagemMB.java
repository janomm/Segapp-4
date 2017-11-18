/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import com.sun.faces.application.resource.LibraryInfo;
import com.sun.xml.rpc.processor.modeler.j2ee.xml.messageDestinationLinkType;
import data.MensagemDB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.el.ELResolver;
import javax.faces.context.FacesContext;
import model.Mensagem;

/**
 *
 * @author mi
 */
@Named(value = "mensagemMB")
@SessionScoped
public class MensagemMB implements Serializable {

    /**
     * Creates a new instance of MensagemMB
     */
    private List<Mensagem> listaMensagens;
    private List<Mensagem> listaAlertas;
    private String msgError;
    private MensagemDB mensagemDB;
    private PessoaMB pessoaMB;
    private GrupoMB grupoMB;
    private Mensagem mensagem;
    
    public MensagemMB() {
        pessoaMB = pessoaBean();
        grupoMB = grupoBean();
        mensagemDB = new MensagemDB();
        msgError = "";
        mensagem = new Mensagem();
        listaMensagens = retornaListaMensagens();
        //listaAlertas = retornaListaAlertas();
        listaAlertas = new ArrayList<Mensagem>();
    }

    public List<Mensagem> getListaMensagens() {
        return listaMensagens;
    }

    public void setListaMensagens(List<Mensagem> listaMensagens) {
        this.listaMensagens = listaMensagens;
    }

    public List<Mensagem> getListaAlertas() {
        return listaAlertas;
    }

    public void setListaAlertas(List<Mensagem> listaAlertas) {
        this.listaAlertas = listaAlertas;
    }

    public String getMsgError() {
        return msgError;
    }

    public void setMsgError(String msgError) {
        this.msgError = msgError;
    }

    public MensagemDB getMensagemDB() {
        return mensagemDB;
    }

    public void setMensagemDB(MensagemDB mensagemDB) {
        this.mensagemDB = mensagemDB;
    }

    public PessoaMB getPessoaMB() {
        return pessoaMB;
    }

    public void setPessoaMB(PessoaMB pessoaMB) {
        this.pessoaMB = pessoaMB;
    }

    public GrupoMB getGrupoMB() {
        return grupoMB;
    }

    public void setGrupoMB(GrupoMB grupoMB) {
        this.grupoMB = grupoMB;
    }

    public Mensagem getMensagem() {
        return mensagem;
    }

    public void setMensagem(Mensagem mensagem) {
        this.mensagem = mensagem;
    }
    
    
    public PessoaMB pessoaBean(){
        FacesContext context = FacesContext.getCurrentInstance();
        ELResolver resolver = context.getApplication().getELResolver();
        return (PessoaMB) resolver.getValue(context.getELContext(), null, "pessoaMB");
    }
    
    public GrupoMB grupoBean(){
        FacesContext context = FacesContext.getCurrentInstance();
        ELResolver resolver = context.getApplication().getELResolver();
        return (GrupoMB) resolver.getValue(context.getELContext(), null, "grupoMB");
    }
    
    public List<Mensagem> retornaListaMensagens(){
        List<Mensagem> lMensagens = mensagemDB.retornaListaMensagens(grupoMB.getGrupo().getId());
        for(Mensagem m : lMensagens){
            if(!m.getTipo())
                lMensagens.remove(m);
        }
        //return mensagemDB.retornaListaMensagens(grupoMB.getGrupo().getId());
        return lMensagens;
    }
    
    public List<Mensagem> retornaListaAlertas(){
        List<Mensagem> lAlertas = mensagemDB.retornaListaMensagens(grupoMB.getGrupo().getId());
        for(Mensagem m : lAlertas){
            if(m.getTipo())
                lAlertas.remove(m);
        }            
        //return mensagemDB.retornaListaMensagens(grupoMB.getGrupo().getId());
        return lAlertas;
    }
    
    public void atualizaListaMensagens(Boolean flag){
        for(Mensagem m : listaMensagens){
            if(m.getTipo() != flag)
                listaMensagens.remove(m);
        }
        listaMensagens = retornaListaMensagens();
    }
    
    public void enviarMensagem(){
        msgError = "";
            if(mensagem.getMensagem().length() > 0){
            Date data = new Date(System.currentTimeMillis());
            mensagem.setDtEnvio(data);
            mensagem.setIdGrupo(grupoMB.getGrupo().getId());
            mensagem.setIdPessoa(pessoaMB.getPessoa().getId());
            mensagem.setTipo(Boolean.TRUE);
            msgError = mensagemDB.criar(mensagem);
            mensagem = new Mensagem();
            atualizaListaMensagens(Boolean.TRUE);
        }
    }
    
    public String voltar(){
//        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
//        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("mensagemMB");
        return "principal";
    }
}
