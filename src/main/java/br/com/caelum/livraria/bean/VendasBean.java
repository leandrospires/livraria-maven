package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;

import br.com.caelum.livraria.dao.LivroDao;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.modelo.Venda;

@Named
@ViewScoped
public class VendasBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private LivroDao livroDao;
	
	@Inject
	EntityManager manager;

	public PieChartModel getEstoqueLivros() {
    	
    	List<Livro> livros = livroDao.listaTodos();
    	PieChartModel estoqueLivros = new PieChartModel();
    	Integer total = 0;
    	
    	for (Livro livro : livros) {
    		estoqueLivros.set(livro.getTitulo() + " (" + livro.getEstoque().toString() + ")", livro.getEstoque());
    		total = total + livro.getEstoque();
    	}
    	

    	estoqueLivros.setTitle("Estoque Total: " + total.toString());
    	estoqueLivros.setLegendPosition("w");
    	estoqueLivros.setShadow(false);    	
    	
        return estoqueLivros;
    }


	public BarChartModel getVendasModel() {

		BarChartModel model = new BarChartModel();
		model.setAnimate(true);
		
		ChartSeries vendasSerie = new ChartSeries();
		vendasSerie.setLabel("Vendas 2020");

		List<Venda> vendas = getVendas(2020);
		for (Venda venda : vendas) {
			vendasSerie.set(venda.getLivro().getTitulo(), venda.getQuantidade());
		}

		ChartSeries vendasSerieProj = new ChartSeries();
		vendasSerieProj.setLabel("Vendas Projeção");
		
		vendas = getVendas(2021);
		for (Venda venda : vendas) {
			vendasSerieProj.set(venda.getLivro().getTitulo(), venda.getQuantidade());
		}
		
		model.addSeries(vendasSerie);
		model.addSeries(vendasSerieProj);

		return model;
	}

	public List<Venda> getVendas(Integer ano) {
		
		//TypedQuery<Venda> query = this.manager.createQuery("select v from Venda v where v.ano = :pAno", Venda.class);
		//query.setParameter("pAno", ano);
		
		//List<Venda> vendas = (List<Venda>) query.getSingleResult();
		
        //List<Venda> vendas = this.manager.createQuery("select v from Venda v where v.ano = :pAno", Venda.class).getResultList();

		TypedQuery<Venda> query = this.manager.createQuery("select v from Venda v where v.ano = :pAno", Venda.class);
		query.setParameter("pAno", ano);
		
		List<Venda> vendas = query.getResultList();
		
        return vendas;
	}

}
