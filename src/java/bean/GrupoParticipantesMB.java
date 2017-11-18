/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import data.GrupoDB;
import data.GrupoParticipantesDB;
import data.PessoaDB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.el.ELResolver;
import javax.faces.context.FacesContext;
import model.Grupo;
import model.GrupoParticipantes;
import model.Pessoa;

/**
 *
 * @author mi
 */
@Named(value = "grupoParticipantesMB")
@SessionScoped
public class GrupoParticipantesMB implements Serializable {

    /**
     * Creates a new instance of GrupoParticipantesMB
     */
    private List<GrupoParticipantes> listaGrupoparticipantes;
    private GrupoParticipantes grupoParticipantes;
    private GrupoParticipantesDB grupoParticipanteDB;
    private String msgError;
    private Pessoa pessoa;
    private Grupo grupo;
    private GrupoMB grupoMB;
    private GrupoDB grupoDB;
    private PessoaMB pessoaMB;
    
    public GrupoParticipantesMB() {
        listaGrupoparticipantes = new ArrayList<GrupoParticipantes>();
        grupoParticipantes = new GrupoParticipantes();
        grupoParticipanteDB = new GrupoParticipantesDB();
        grupo = new Grupo();
        grupoDB = new GrupoDB();
        grupoMB = grupoBean();
        pessoaMB = pessoaBean();
        pessoa = new Pessoa();
        msgError = "";
    }
    
    public List<GrupoParticipantes> getListaGrupoparticipantes() {
        return listaGrupoparticipantes;
    }

    public void setListaGrupoparticipantes(List<GrupoParticipantes> listaGrupoparticipantes) {
        this.listaGrupoparticipantes = listaGrupoparticipantes;
    }

    public GrupoParticipantes getGrupoParticipantes() {
        return grupoParticipantes;
    }

    public void setGrupoParticipantes(GrupoParticipantes grupoParticipantes) {
        this.grupoParticipantes = grupoParticipantes;
    }

    public GrupoParticipantesDB getGrupoParticipanteDB() {
        return grupoParticipanteDB;
    }

    public void setGrupoParticipanteDB(GrupoParticipantesDB grupoParticipanteDB) {
        this.grupoParticipanteDB = grupoParticipanteDB;
    }

    public String getMsgError() {
        return msgError;
    }

    public void setMsgError(String msgError) {
        this.msgError = msgError;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public GrupoDB getGrupoDB() {
        return grupoDB;
    }

    public void setGrupoDB(GrupoDB grupoDB) {
        this.grupoDB = grupoDB;
    }

    public GrupoMB getGrupoMB() {
        return grupoMB;
    }

    public void setGrupoMB(GrupoMB grupoMB) {
        this.grupoMB = grupoMB;
    }
    
    public GrupoMB grupoBean(){
        FacesContext context = FacesContext.getCurrentInstance();
        ELResolver resolver = context.getApplication().getELResolver();
        return (GrupoMB) resolver.getValue(context.getELContext(), null, "grupoMB");
    }
    
    public PessoaMB pessoaBean(){
        FacesContext context = FacesContext.getCurrentInstance();
        ELResolver resolver = context.getApplication().getELResolver();
        return (PessoaMB) resolver.getValue(context.getELContext(), null, "pessoaMB");
    }
    
    public String voltarAdicionaParticipante(){
        return "principal";
    }
    
    public String excluiParticipante(Integer idGrupo, Integer idParticipante){
        msgError = "";
        GrupoParticipantes gp = grupoParticipanteDB.retornaGrupoParticipantePorGrupoPessoa(idGrupo, idParticipante);
        PessoaDB pessoaDB = new PessoaDB();
        
        Grupo g = grupoDB.retornaGrupoPorId(idGrupo);
        Pessoa p = pessoaDB.retornaPessoaPorId(idParticipante);
        
        grupoParticipanteDB.deletar(gp);
        if(g.getIdAdm().equals(pessoaMB.getPessoa().getId())){
            return grupoMB.detalheGrupo(g);
        } else {
            return pessoaMB.segapp(p);
        }
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
    
    public String adicionarParticipante(Grupo g){
        grupo = grupoDB.retornaGrupoPorId(g.getId());
        pessoa = new Pessoa();
        return "adicionarParticipante";
    }
    
    public String voltar(){
        msgError = "";
        return grupoMB.detalheGrupo(grupoMB.getGrupo());
    }
    
    public String sairGrupo(){
        grupo = null;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("grupoMB");
        return pessoaMB.segapp(pessoaMB.getPessoa());
    }
    
    public String adicionar(){
        msgError = "";
        try {
            Pessoa p = new Pessoa();
            PessoaDB pessoaDB = new PessoaDB();
            String retPessoa = "";

            if(pessoaDB.existeTelefone(pessoa.getTelefone())){
                pessoa = pessoaDB.retornaPessoaPorTelefone(pessoa.getTelefone());
                retPessoa = "";
            }
            else {
                pessoa.setNome("Participante");
                retPessoa = pessoaMB.criaPessoa(pessoa);
            }
            
            if(!retPessoa.equals("")){
                msgError = retPessoa;
                return "adicionarParticipante";
            }
            
            if(!grupoParticipanteDB.pessoaParticipaGrupo(pessoa.getId(), grupo.getId())){

                grupoParticipantes.setIdPessoa(pessoa.getId());
                grupoParticipantes.setIdGrupo(grupo.getId());
                Date data = new Date(System.currentTimeMillis());
                grupoParticipantes.setDtInclusao(data);

                grupoParticipanteDB.criar(grupoParticipantes);

                msgError = "";
            }
            else {
                msgError = "Telefone já cadastrado.";
                return "adicionarParticipante";
            }
            return grupoMB.detalheGrupo(grupoMB.getGrupo());
        } catch (Exception e) {
            //msgError = "Erro ao incluir Participante.";
            msgError = "Erro ao inserir participante.";
            return "adicionarParticipante";
        }
    }
    
    public String style(){
        return "";
    }
    
}
