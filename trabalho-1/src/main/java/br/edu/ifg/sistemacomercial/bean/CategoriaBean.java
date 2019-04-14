package br.edu.ifg.sistemacomercial.bean;

import br.edu.ifg.sistemacomercial.dao.CategoriaDAO;
import br.edu.ifg.sistemacomercial.dao.ProdutoDAO;
import br.edu.ifg.sistemacomercial.entity.Categoria;
import br.edu.ifg.sistemacomercial.util.JsfUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

@SessionScoped
@Named
public class CategoriaBean extends JsfUtil{
    
    private Categoria categoria;
    private List<Categoria> categorias;
    private Status statusTela;
    
    private CategoriaDAO categoriaDAO;
    
    private enum Status {
        INSERINDO,
        EDITANDO,
        PESQUISANDO
    }
    
    @PostConstruct
    public void init(){
        categoria = new Categoria();
        categorias = new ArrayList<>();   
        statusTela = Status.PESQUISANDO;
        categoriaDAO = new CategoriaDAO();
    }
    
    public void novo(){
        statusTela = Status.INSERINDO;
        categoria = new Categoria();
    }

    public void adicionarCategoria(){
        try {
            categoriaDAO.salvar(categoria);
            categoria = new Categoria();
            addMensagem("Salvo com sucesso!");
            pesquisar();
        } catch (SQLException ex) {
            addMensagemErro(ex.getMessage());
        }
    }
    
    public void remover(Categoria categoria){
        try {
            categoriaDAO.deletar(categoria);
            categorias.remove(categoria);
            addMensagem("Deletado com sucesso!");
        } catch (SQLException ex) {
            addMensagemErro(ex.getMessage());
        }
    }
    public void editar(Categoria categoria){
        //remover(usuario);
        this.categoria = categoria;
        statusTela = Status.EDITANDO;
    }
    
    public void pesquisar(){
        try {
            if(!statusTela.equals(Status.PESQUISANDO)){
                statusTela = Status.PESQUISANDO;
                return;
            }
            categorias = categoriaDAO.listar();
            if(categorias == null || categorias.isEmpty()){
                addMensagemAviso("Nenhum usuário cadastrado.");
            }
        } catch (SQLException ex) {
            addMensagemErro(ex.getMessage());
        }
    }
    
    public CategoriaBean() {
        Categoria c1 = new Categoria();
        c1.setId(1);
        c1.setNome("Higiene");
        Categoria c2 = new Categoria();
        c2.setId(2);
        c2.setNome("Limpeza");
        Categoria c3 = new Categoria();
        c3.setId(3);
        c3.setNome("Industrializado");
        
        categorias = Arrays.asList(c1,c2,c3);
      
    }

    public Categoria getProduto() {
        return categoria;
    }

    public void setProduto(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }
    
    public String getStatusTela() {
        return statusTela.name();
    }
    
}
