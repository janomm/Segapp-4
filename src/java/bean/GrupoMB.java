/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import model.Grupo;
import model.Pessoa;
import data.GrupoDB;
import data.GrupoParticipantesDB;
import data.PessoaDB;
import java.util.ArrayList;
import java.util.Date;
import javax.el.ELResolver;
import javax.faces.context.FacesContext;
import model.GrupoParticipantes;

/**
 *
 * @author mi
 */
@Named(value = "grupoMB")
@SessionScoped
public class GrupoMB implements Serializable {

    /**
     * Creates a new instance of GrupoMB
     */
    private List<Grupo> listaGrupos;
    private String msgError;
    private GrupoDB grupoBD;
    private PessoaMB pessoaMB;
    private Pessoa pessoa;
    private Grupo grupo;
    private List<Pessoa> listaParticipantes;
    //private List<GrupoParticipantes> listaParticipantes;
    //private GrupoParticipantes grupoParticipantes;
    //GrupoParticipantesDB grupoParticipantesDB;

    public GrupoMB() {
        pessoaMB = pessoaBean();
        pessoa = pessoaMB.getPessoa();
        grupoBD = new GrupoDB();
        listaGrupos = retornaListaGrupos();
        msgError = "";
        grupo = new Grupo();
        listaParticipantes = new ArrayList<Pessoa>();
        //atualizaListaParticipantes();
    }
    
    public List<Grupo> getListaGrupos() {
        return listaGrupos;
    }

    public void setListaGrupos(List<Grupo> listaGrupos) {
        this.listaGrupos = listaGrupos;
    }

    public String getMsgError() {
        return msgError;
    }

    public void setMsgError(String msgError) {
        this.msgError = msgError;
    }

    public GrupoDB getGrupoBD() {
        return grupoBD;
    }

    public void setGrupoBD(GrupoDB grupoBD) {
        this.grupoBD = grupoBD;
    }

    public PessoaMB getPessoaMB() {
        return pessoaMB;
    }

    public void setPessoaMB(PessoaMB pessoaMB) {
        this.pessoaMB = pessoaMB;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public List<Pessoa> getListaParticipantes() {
        return listaParticipantes;
    }

    public void setListaParticipantes(List<Pessoa> listaParticipantes) {
        this.listaParticipantes = listaParticipantes;
    }
    
    public PessoaMB pessoaBean(){
        FacesContext context = FacesContext.getCurrentInstance();
        ELResolver resolver = context.getApplication().getELResolver();
        return (PessoaMB) resolver.getValue(context.getELContext(), null, "pessoaMB");
    }
    
    public List<Grupo> retornaListaGrupos(){
        List<Grupo> lstGrupos = new ArrayList<Grupo>();
        List<Grupo> lstRetorno = new ArrayList<Grupo>();

        lstGrupos = grupoBD.retornaListaGruposPorPessoa(pessoa.getId());
        for(Grupo lg: lstGrupos){
            lstRetorno.add(lg);
        }
        lstGrupos = grupoBD.retornaListaGruposPorAdm(pessoa.getId());
        for(Grupo lg: lstGrupos){
            lstRetorno.add(lg);
        }
        return lstRetorno;
    }
    
    public String voltar(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("grupoMB");
        
        return "index";
    }

    public String criarGrupo(){
        msgError = "";
        grupo = new Grupo();
        return "criaGrupo";
    }
    
    public String editarGrupo(Grupo g){
        grupo = g;
        msgError = "";
        return "editarGrupo";
    }
    
    public String criaGrupo(){
        msgError = "";
        if(grupo.getNome().trim().isEmpty()){
            msgError = "Nome do grupo não pode ser vazio.";
            return "criaGrupo";
        }
        grupo.setIdAdm(pessoa.getId());
        Date data = new Date(System.currentTimeMillis());
        grupo.setDtCriacao(data);
        
        if(!grupoBD.criar(grupo).equals("")){
            msgError = "Erro na Criação do Grupo.";
            return "criaGrupo";
        }
        msgError = "";
        return "grupo";
    }
    
    public String editaGrupo(){
        msgError = "";
        if(!grupoBD.editar(grupo).trim().equals("")){
            msgError = "Erro ao alterar nome do grupo";
            return "editarGrupo";
        }
        msgError = "";
        return "grupo";
    }
    
    public void excluirGrupo(Grupo g){
        msgError = "";
        GrupoParticipantesDB gpDB = new GrupoParticipantesDB();
        //if(!gpDB.)
        
        if(!grupoBD.deletar(g).trim().isEmpty()){
            msgError = "Erro ao Excluir Grupo.";
        }
    }
    
    public String detalheGrupo(Grupo g){
        GrupoParticipantesDB gpDB = new GrupoParticipantesDB();
        PessoaDB pDB = new PessoaDB();
        Pessoa p = new Pessoa();
        listaParticipantes.clear();
       
        
        listaParticipantes.add(pDB.retornaPessoaPorId(g.getIdAdm()));
        List<GrupoParticipantes> listaGp = gpDB.retornaGrupoParticipantesPorGrupo(g.getId());
        for(GrupoParticipantes gp: listaGp){
            p = pDB.retornaPessoaPorId(gp.getIdPessoa());
            if(!p.equals(null))
                listaParticipantes.add(p);
        }
        grupo = grupoBD.retornaGrupoPorId(g.getId());
        return "principal";
    }
    
    public String mensagens(){
        return "mensagens";
    }
    
    public String style(Grupo grupo){
        if(grupo.getIdAdm().equals(pessoa.getId()))
            return "display:";
        else
            return "display:none";
    }
    
    public String tornarAdministrador(){
        msgError = "";
        try {
            grupo.setIdAdm(pessoa.getId());
            grupoBD.editar(grupo);
            
            GrupoParticipantesDB gpDB = new GrupoParticipantesDB();
            GrupoParticipantes gp = new GrupoParticipantes();
            gp = gpDB.retornaGrupoParticipantePorGrupoPessoa(pessoa.getId(), grupo.getId());
            gpDB.deletar(gp);
            return detalheGrupo(grupo);
            
        } catch (Exception e) {
            msgError = "Não é possível tornar-se administrador.";
        }
        
        return "principal";
    }
    
    public String deixarDeSerAdministrador(){
        msgError = "";
        try {
            GrupoParticipantesDB gpDB = new GrupoParticipantesDB();
            GrupoParticipantes gp = new GrupoParticipantes();
            if(gpDB.pessoaParticipaGrupo(pessoa.getId(), grupo.getId()))
                gp = gpDB.retornaGrupoParticipantePorGrupoPessoa(pessoa.getId(), grupo.getId());
            else {
                Date data = new Date(System.currentTimeMillis());
                gp.setIdGrupo(grupo.getId());
                gp.setIdPessoa(pessoa.getId());
                gp.setDtInclusao(data);
                msgError = gpDB.criar(gp);
                
                if(msgError == ""){
                    grupo.setIdAdm(-1);
                    grupoBD.editar(grupo);
                }
            }
        } catch (Exception e) {
            msgError = "Não foi possível deixar de ser administrador.";
        }
        return "principal";
    }
    
    public String styleGrupo(Integer idPessoa){
        if(grupo.getIdAdm().equals(pessoa.getId()))
            if(pessoa.getId().equals(idPessoa))
                return "display:none";
            else return "display:";
        else {
            if(pessoa.getId().equals(idPessoa))
                return "display:";
            else
                return "display:none";
        }
    }
    
    public String styleAdmin(){
        if(eAdministrador())
            return "display:";
        else
            return "display:none";
    }
    
    public String styleBtnTornarAdm(){
        if(grupo.getIdAdm() > 0)
            return "display:none";
        else return "display:";
    }
    
    public Boolean eAdministrador(){
        return pessoaMB.getPessoa().getId().equals(grupo.getIdAdm());
    }
    
//    public String stylePessoa(Pessoa p){
//        if(p.equals(pessoa))
//            return "style:";
//        else
//            return "style:none";
//    }
}
